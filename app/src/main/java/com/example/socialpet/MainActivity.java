package com.example.socialpet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {


    private EditText txtEmail, txtSenha;
    private Button btnEntrar;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciarComponentes();

        getSupportActionBar().hide();

        verificarAutentificacao();

        btnEntrar.setOnClickListener(view -> {
            String email = txtEmail.getText().toString();
            String senha = txtSenha.getText().toString();

            if(email.isEmpty() || senha.isEmpty()){

                Toast.makeText(getBaseContext(),"Preencha todos os campos", Toast.LENGTH_SHORT).show();
            }else{
                progressBar.setVisibility(View.VISIBLE);
                autenticicarUsuario();
            }
        });

    }

    private void verificarAutentificacao() {
        if (FirebaseAuth.getInstance().getUid() != null){
            Intent intent = new Intent(MainActivity.this, Navegacao.class);
            startActivity(intent);
            finish();
        }
    }

    private void autenticicarUsuario(){

        String email = txtEmail.getText().toString();
        String senha = txtSenha.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,senha)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    String erro = null;
                        Intent intent = new Intent(getBaseContext(),Navegacao.class);
                        startActivity(intent);
                        finish();
                }else{
                    String erro;
                    try {
                        throw Objects.requireNonNull(task.getException());
                    }catch (Exception e){
                        erro = getString(R.string.email_naoEncontrado);
                    }
                    Toast.makeText(getBaseContext(),erro,Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    public void iniciarComponentes(){

        txtEmail = findViewById(R.id.TxtEmail);
        txtSenha = findViewById(R.id.TxtSenha);
        btnEntrar = findViewById(R.id.BtnEntrar);
        progressBar = findViewById(R.id.progressBar);

    }

    public void onClickCadastrar(View v){
        startActivity(new Intent(getBaseContext(),telaCadastro.class));

    }
}