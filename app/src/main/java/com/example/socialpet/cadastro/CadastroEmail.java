package com.example.socialpet.cadastro;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.socialpet.databinding.FragmentCadastroEmailBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.util.Objects;


public class CadastroEmail extends Fragment {


    private FragmentCadastroEmailBinding binding;
    private Button btnAvancar;
    private EditText txtNomeUsuario, txtEmailUsuario, txtSenhaUsuario, txtConfirmarSenha;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String tipo = getArguments().getString("tipo");
        binding = FragmentCadastroEmailBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        btnAvancar = binding.btnAvancar2;
        txtNomeUsuario = binding.TxtNomeUsuario;
        txtEmailUsuario = binding.TxtEmailD;
        txtConfirmarSenha = binding.TxtConfirmarSenha;
        txtSenhaUsuario = binding.TxtSenhaUsuario;

        btnAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            String nome = txtNomeUsuario.getText().toString();
            String email = txtEmailUsuario.getText().toString();
            String senha = txtSenhaUsuario.getText().toString();
            String confSenha = txtConfirmarSenha.getText().toString();

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confSenha.isEmpty()){
                Toast.makeText(getActivity(), "Preencha todos os campos ", Toast.LENGTH_SHORT).show();
            }else if (!senha.equals(confSenha)){
                Toast.makeText(getActivity(), "Senha e confirmar senha estão diferentes", Toast.LENGTH_SHORT).show();
            }
            else{

                cadastrarUsuario(nome, email, senha, tipo);
            }

            }
        });


        return root;
    }

    private void cadastrarUsuario(String nome, String email, String senha, String tipo){

       FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,senha).addOnCompleteListener(task -> {
           if(task.isSuccessful()){

               Intent intent = new Intent(getActivity(), CadastrarFoto.class);
               intent.putExtra("nome", nome);
               intent.putExtra("tipo", tipo);
               startActivity(intent);
               getActivity().finish();
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

               Toast.makeText(getActivity(),erro,Toast.LENGTH_SHORT).show();
           }
       });
    }

}