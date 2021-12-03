package com.example.socialpet;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FotosAdocao extends AppCompatActivity {


    public static final int PERMISSAO_REQUEST = 2;
    private Button btnVoltar,btnProcurarImg,btnPostar;
    private Uri imageUri;
    private EditText txtDescricao, txtPreco;
    private TextView lblPreco;
    private ImageView ImagemAdd;
    String IdUsuario, nome, imagemPerfil, tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos_adocao);



        btnVoltar = findViewById(R.id.btnAddFotosVoltar2);
        btnProcurarImg = findViewById(R.id.btnImgPostAdd);
        ImagemAdd = findViewById(R.id.ImagemPostAdd);
        txtDescricao = findViewById(R.id.txtDescricao);
        btnPostar = findViewById(R.id.btnPostar);
        txtPreco = findViewById(R.id.txtPreco2);
        lblPreco = findViewById(R.id.lblPreco);

        Bundle extra = getIntent().getExtras();
        if (extra != null){
            nome = extra.getString("nome");
            imagemPerfil = extra.getString("imagemPerfil");
            tipo = extra.getString("tipo");

            if (tipo.equals("Profissional")){
                txtPreco.setVisibility(View.VISIBLE);
                lblPreco.setVisibility(View.VISIBLE);
            }
        }
        getSupportActionBar().hide();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)){

            }else{
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSAO_REQUEST);
            }
        }



        btnProcurarImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Escolha uma imagem"), 0);
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnPostar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUri != null){
                    if(tipo.equals("Profissional")){
                        if (txtPreco.getText().toString().isEmpty()){
                            Toast.makeText(getBaseContext(),"Adicione o preço do produto", Toast.LENGTH_SHORT).show();
                        }else {
                            postarImagem();
                        }
                    }else {
                        postarImagem();
                    }

                }else {
                    Toast.makeText(getBaseContext(),"Selecione uma imagem", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            if (requestCode == 0) {

                if (data != null) {
                    imageUri = data.getData();

                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                        ImagemAdd.setImageDrawable(new BitmapDrawable(bitmap));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    private void postarImagem() {

        String especie = txtDescricao.getText().toString();
        if (especie.isEmpty()){
            Toast.makeText(getBaseContext(), "Coloque uma Descrição", Toast.LENGTH_SHORT).show();
        }else {
            final ProgressDialog pd = new ProgressDialog(this);
            pd.setTitle("Postando...");
            pd.show();
            String filename = UUID.randomUUID().toString();
            final StorageReference ref = FirebaseStorage.getInstance().getReference("/images/" + filename);
            ref.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Log.i("Teste", uri.toString());

                                    if (tipo.equals("Profissional")){
                                        inserindoDadosLoja(uri.toString(), especie, filename);
                                    }else {
                                        inserindoDadosAdocao(uri.toString(), especie, filename);
                                    }

                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Teste", e.getMessage(), e);
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            pd.setMessage("Carregando: " + (int) progressPercent + "%");
                        }
                    });
        }
    }
    private void inserindoDadosAdocao(String uri, String especie, String idPost){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        IdUsuario = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Map<String, Object> post = new HashMap<>();
        post.put("idUsuario", IdUsuario);
        post.put("nomeDoador", nome);
        post.put("descricao", especie);
        post.put("imgPerfilDoador", imagemPerfil);
        post.put("data", FieldValue.serverTimestamp());
        post.put("imgAdocaoPet", uri);

        DocumentReference documentReference = db.collection("Adocao").document(idPost);
        documentReference.set(post).addOnSuccessListener(unused -> {
            Log.d("db", "Cadastrado com sucesso");
            finish();
        }).addOnFailureListener(e -> {
            Log.d("db", "Falha ao cadastrar usuario" + e.toString());
        });

    }

    private void inserindoDadosLoja(String uri, String descricao, String idPost){
        String preco = txtPreco.getText().toString();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        IdUsuario = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Map<String, Object> post = new HashMap<>();
        post.put("idUsuario", IdUsuario);
        post.put("nomeVendedor", nome);
        post.put("descricao", descricao);
        post.put("imgPerfilDoador", imagemPerfil);
        post.put("preco",preco);
        post.put("data", FieldValue.serverTimestamp());
        post.put("imagemPost", uri);

        DocumentReference documentReference = db.collection("Loja").document(idPost);
        documentReference.set(post).addOnSuccessListener(unused -> {
            Log.d("db", "Cadastrado com sucesso");
            finish();
        }).addOnFailureListener(e -> {
            Log.d("db", "Falha ao cadastrar usuario" + e.toString());
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSAO_REQUEST) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Permissão concedida
            } else {
                //permissao negada, o que posso fazer aqui?
            }
            return;
        }
    }
}
