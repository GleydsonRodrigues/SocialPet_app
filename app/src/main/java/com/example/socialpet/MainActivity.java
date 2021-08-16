package com.example.socialpet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {


    private EditText txtEmail, txtSenha;
    private Button btnEntrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciarComponentes();


        btnEntrar.setOnClickListener(view -> {
            String nome = txtEmail.getText().toString();
            String senha = txtSenha.getText().toString();


        });
    }

    public void iniciarComponentes(){

        txtEmail = findViewById(R.id.TxtEmail);
        txtSenha = findViewById(R.id.TxtSenha);
        btnEntrar = findViewById(R.id.BtnEntrar);

    }

    public void onClickCadastrar(View view){
        startActivity(new Intent(getBaseContext(),telaCadastro.class));

    }
}