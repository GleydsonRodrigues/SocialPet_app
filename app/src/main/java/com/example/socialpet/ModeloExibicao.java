package com.example.socialpet;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class ModeloExibicao extends AppCompatActivity {


    private String nome, imgPost, imgPerfil, preco, descricao;
    private ImageView imgPostExibicao, imgPerfilExibicao;
    private TextView txtNome, txtDescricao, txtPreco, lblPreco;
    private Button btnVoltarExibicao;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modelo_exibicao);

        getSupportActionBar().hide();
        imgPerfilExibicao = findViewById(R.id.imgPerfilPostExibicao);
        imgPostExibicao = findViewById(R.id.imgPostExibicao);
        txtNome = findViewById(R.id.txtNomeUsuarioPostExibicao);
        txtDescricao = findViewById(R.id.txtDescricaoExibicao);
        btnVoltarExibicao = findViewById(R.id.btnVoltarExibicao);
        txtPreco = findViewById(R.id.txtPrecoExibicao);
        lblPreco = findViewById(R.id.lblPrecoExibicao);
//        txtPreco = findViewById(R.id.txtP

        Bundle extra = getIntent().getExtras();
        if (extra != null){
            nome = extra.getString("nome");
            descricao = extra.getString("descricao");
            imgPost = extra.getString("imagemPost");
            imgPerfil = extra.getString("imagemPerfil");
            preco = extra.getString("preco");

            txtNome.setText(nome);
            txtDescricao.setText(descricao);
            Picasso.get()
                    .load(imgPerfil)
                    .into(imgPerfilExibicao);
            Picasso.get()
                    .load(imgPost)
                    .into(imgPostExibicao);

            if (preco != null){

                txtPreco.setVisibility(View.VISIBLE);
                lblPreco.setVisibility(View.VISIBLE);

                txtPreco.setText("R$" + preco +",00");

            }
        }

        btnVoltarExibicao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}