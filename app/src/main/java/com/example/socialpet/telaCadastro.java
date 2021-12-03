package com.example.socialpet;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class telaCadastro extends AppCompatActivity {


//    private EditText txtNomeUsuario, txtEmailUsuario, txtSenhaUsuario, txtConfirmarSenha;
//    private Button btnCadastrarUsuario, btnFotoCad;
    private ImageView imgFotoCad;
//    String IdUsuario;
//    public Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);

        getSupportActionBar().hide();


//        IniciarComponentes();
//
//        btnFotoCad.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//                intent.setType("image/*");
//                startActivityForResult(Intent.createChooser(intent,"Escolha uma imagem"), 0);
//            }
//        });
//
//        btnCadastrarUsuario.setOnClickListener(view -> {
//            String nome = txtNomeUsuario.getText().toString();
//            String email = txtEmailUsuario.getText().toString();
//            String senha = txtSenhaUsuario.getText().toString();
//            String confSenha = txtConfirmarSenha.getText().toString();
//
//            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confSenha.isEmpty()){
//                Toast.makeText(getBaseContext(), "Preencha todos os campos ", Toast.LENGTH_SHORT).show();
//            }else if (!senha.equals(confSenha)){
//                Toast.makeText(getBaseContext(), "Senha e confirmar senha estão diferentes", Toast.LENGTH_SHORT).show();
//            }
//            else{
//                if (imageUri != null){
//                    postarImagem();
//                }else {
//                    cadastrarUsuario("Sem");
//                }
//
//            }
//
//        });
    }
//    private void cadastrarUsuario(){
//        String email = txtEmailUsuario.getText().toString();
//        String senha = txtSenhaUsuario.getText().toString();
//
//       FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,senha).addOnCompleteListener(task -> {
//           if(task.isSuccessful()){
//
//               if (imagemPerfil == "Sem"){
//                   String imgFixa = "https://firebasestorage.googleapis.com/v0/b/socialpet-fea3e.appspot.com/o/perfil.png?alt=media&token=38a529de-47e6-4d89-934a-0c9ef7fb23d8";
//                   inserindoDados(imgFixa);
//               }else{
//                   inserindoDados(imagemPerfil);
//               }
//
//
//               Toast.makeText(getBaseContext(),"Cadastrado com sucesso",Toast.LENGTH_SHORT).show();
//               finish();
//
//           }else{
//               String erro;
//
//               try {
//                   throw Objects.requireNonNull(task.getException());
//               }catch (FirebaseAuthWeakPasswordException e){
//                   erro = "A senha deve conter no minimo 6 caracteres";
//               }catch (FirebaseAuthUserCollisionException e){
//                   erro = "Esse email já foi cadastrado";
//               }catch (FirebaseAuthInvalidCredentialsException e){
//                   erro = "Digite um e-mail válido";
//               }catch (Exception e){
//                    erro = "Erro ao cadastrar";
//               }
//
//               Toast.makeText(getBaseContext(),erro,Toast.LENGTH_SHORT).show();
//           }
//       });
//    }
//

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainerCadastro);
//        fragment.onActivityResult(requestCode, resultCode, data);
//    }
//
//    private void inserindoDados(String imgPerfil){
//        String nome = txtNomeUsuario.getText().toString();
//        String senha = txtSenhaUsuario.getText().toString();
//
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//        Map<String, String> usuario = new HashMap<>();
//        usuario.put("nome",nome);
//        usuario.put("senha", senha);
//        usuario.put("imagemPerfil", imgPerfil);
//
//
//        IdUsuario = FirebaseAuth.getInstance().getCurrentUser().getUid();
//
//        DocumentReference documentReference = db.collection("Usuarios").document(IdUsuario);
//        documentReference.set(usuario).addOnSuccessListener(unused -> {
//            Log.d("db","Cadastrado com sucesso");
//
//        }).addOnFailureListener(e -> {
//            Log.d("db","Falha ao cadastrar usuario" + e.toString());
//        });
//
//
//    }
//    private void postarImagem() {
//        final ProgressDialog pd = new ProgressDialog(this);
//        pd.setTitle("Cadastrando...");
//        pd.show();
//        String filename = UUID.randomUUID().toString();
//        final StorageReference ref = FirebaseStorage.getInstance().getReference("/images/" + filename);
//        ref.putFile(imageUri)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                Log.i("Teste", uri.toString());
//                                cadastrarUsuario(uri.toString());
//                            }
//                        });
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.e("Teste", e.getMessage(), e);
//                    }
//                })
//                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
//                        pd.setMessage("Carregando: " + (int) progressPercent + "%");
//                    }
//                });
//    }
//    public void IniciarComponentes(){
//        txtNomeUsuario = findViewById(R.id.TxtNomeUsuario);
//        txtEmailUsuario = findViewById(R.id.TxtEmailD);
//        txtConfirmarSenha = findViewById(R.id.TxtConfirmarSenha);
//        txtSenhaUsuario = findViewById(R.id.TxtSenhaUsuario);
//        btnCadastrarUsuario = findViewById(R.id.BtnCriar);
//        btnFotoCad = findViewById(R.id.btnFotoCad);
//        imgFotoCad =findViewById(R.id.imgFotoCad);
//    }
}