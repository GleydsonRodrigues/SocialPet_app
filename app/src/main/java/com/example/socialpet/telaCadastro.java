package com.example.socialpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.util.Objects;

public class telaCadastro extends AppCompatActivity {


    private EditText txtNomeUsuario, txtEmailUsuario, txtSenhaUsuario, txtConfirmarSenha;
    private Button btnCadastrarUsuario;
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
    public void IniciarComponentes(){
        txtNomeUsuario = findViewById(R.id.TxtNomeUsuario);
        txtEmailUsuario = findViewById(R.id.TxtEmailD);
        txtConfirmarSenha = findViewById(R.id.TxtConfirmarSenha);
        txtSenhaUsuario = findViewById(R.id.TxtSenhaUsuario);
        btnCadastrarUsuario = findViewById(R.id.BtnCriar);
    }
}