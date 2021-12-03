package com.example.socialpet.cadastro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.socialpet.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CadastrarFoto extends AppCompatActivity {

    private String IdUsuario;
    private Button btnCadastro,btnFoto,btnSemFoto;
    private String nome,tipo;
    private Uri imageUri;
    ImageView imgFotoCad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_foto);

        Bundle extra = getIntent().getExtras();
        if (extra != null){
            nome = extra.getString("nome");
            tipo = extra.getString("tipo");
        }
        getSupportActionBar().hide();


        String imgSemFoto = "https://firebasestorage.googleapis.com/v0/b/socialpet-fea3e.appspot.com/o/imgPadrao.jpg?alt=media&token=76316ab7-7c92-4e8c-b44a-ee6e7ce86071";
        btnCadastro = findViewById(R.id.btnCadastro);
        btnFoto = findViewById(R.id.btnFoto);
        imgFotoCad = findViewById(R.id.imgFotoCad);
        btnSemFoto = findViewById(R.id.btnSemFoto);


        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Escolha uma imagem"), 0);
            }
        });
        btnSemFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inserindoDados(nome, tipo, imgSemFoto);
                finish();
            }
        });

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (imageUri != null){
                    postarImagem();
                    finish();
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
                        imgFotoCad.setImageDrawable(new BitmapDrawable(bitmap));
                        btnFoto.setAlpha(0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    private void postarImagem() {

            final ProgressDialog pd = new ProgressDialog(this);
            pd.setTitle("Cadastrando...");
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
                                    inserindoDados(nome, tipo, uri.toString());
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
    private void inserindoDados(String nome, String tipo, String imgPerfil) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, String> usuario = new HashMap<>();
        usuario.put("nome", nome);
        usuario.put("tipo", tipo);
        usuario.put("imagemPerfil", imgPerfil);


        IdUsuario = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(IdUsuario);
        documentReference.set(usuario).addOnSuccessListener(unused -> {
            Log.d("db", "Cadastrado com sucesso");
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(getBaseContext(),"Cadastrado com sucesso", Toast.LENGTH_SHORT).show();

        }).addOnFailureListener(e -> {
            Log.d("db", "Falha ao cadastrar usuario" + e.toString());
        });

    }


}