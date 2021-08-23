package com.example.socialpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class telaCadastro extends AppCompatActivity {


    private EditText txtNomeUsuario, txtEmailUsuario, txtSenhaUsuario, txtConfirmarSenha;
    private Button btnCadastrarUsuario;
    String IdUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);

        IniciarComponentes();

        btnCadastrarUsuario.setOnClickListener(view -> {
            String nome = txtNomeUsuario.getText().toString();
            String email = txtEmailUsuario.getText().toString();
            String senha = txtSenhaUsuario.getText().toString();
            String confSenha = txtConfirmarSenha.getText().toString();

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confSenha.isEmpty()){
                Toast.makeText(getBaseContext(), "Preencha todos os campos ", Toast.LENGTH_SHORT).show();
            }else if (!senha.equals(confSenha)){
                Toast.makeText(getBaseContext(), "Senha e confirmar senha estão diferentes", Toast.LENGTH_SHORT).show();
            }
            else{
                cadastrarUsuario();
            }

        });
    }
    private void cadastrarUsuario(){
        String email = txtEmailUsuario.getText().toString();
        String senha = txtSenhaUsuario.getText().toString();

       FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,senha).addOnCompleteListener(task -> {
           if(task.isSuccessful()){


               inserindoDados();
               Toast.makeText(getBaseContext(), "Cadastrado com sucesso", Toast.LENGTH_SHORT).show();

           }else{
               String erro;

               try {
                   throw Objects.requireNonNull(task.getException());
               }catch (FirebaseAuthWeakPasswordException e){
                   erro = "A senha deve conter no minimo 6 caracteres";
               }catch (FirebaseAuthUserCollisionException e){
                   erro = "Esse email já foi cadastrado";
               }catch (FirebaseAuthInvalidCredentialsException e){
                   erro = "Digite um e-mail válido";
               }catch (Exception e){
                    erro = "Erro ao cadastrar";
               }

               Toast.makeText(getBaseContext(),erro,Toast.LENGTH_SHORT).show();
           }
       });
    }

    private void inserindoDados(){
        String nome = txtNomeUsuario.getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, String> usuario = new HashMap<>();
        usuario.put("nome",nome);

        IdUsuario = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(IdUsuario);
        documentReference.set(usuario).addOnSuccessListener(unused -> {
            Log.d("db","Cadastrado com sucesso");

        }).addOnFailureListener(e -> {
            Log.d("db","Falha ao cadastrar usuario" + e.toString());
        });


    }
    public void IniciarComponentes(){
        txtNomeUsuario = findViewById(R.id.TxtNomeUsuario);
        txtEmailUsuario = findViewById(R.id.TxtEmailD);
        txtConfirmarSenha = findViewById(R.id.TxtConfirmarSenha);
        txtSenhaUsuario = findViewById(R.id.TxtSenhaUsuario);
        btnCadastrarUsuario = findViewById(R.id.BtnCriar);
    }
}