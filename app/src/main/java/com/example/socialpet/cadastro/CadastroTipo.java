package com.example.socialpet.cadastro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.socialpet.R;
import com.example.socialpet.databinding.FragmentCadastroTipoBinding;


public class CadastroTipo extends Fragment {

    private FragmentCadastroTipoBinding binding;
    private Button btnAvancar;
    private RadioGroup radioGroup;
    private String tipo = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCadastroTipoBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        btnAvancar = binding.btnAvancar1;
        radioGroup = binding.radioGroupTipo;
        btnAvancar.setVisibility(View.VISIBLE);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i) {
                    case R.id.rdbPessoal:
                        tipo = "Pessoal";
                        break;
                    case R.id.rdbLoja:
                        tipo = "Profissional";
                        break;
                    case R.id.rdbCanil:
                        tipo = "Centro de Adoção";
                        break;
                }
            }
        });
        btnAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (tipo != ""){

                    FragmentManager fragmentTrasicao = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentTrasicao.beginTransaction();
                    btnAvancar.setVisibility(View.INVISIBLE);

                    CadastroEmail cadastroEmail = new CadastroEmail();
                    Bundle bundle = new Bundle();
                    bundle.putString("tipo", tipo);
                    cadastroEmail.setArguments(bundle);
                    transaction.replace(R.id.fragmentContainerCadastro, cadastroEmail);
                    transaction.commit();
                }else{
                    Toast.makeText(getActivity(),"Selecione um tipo de Usuario", Toast.LENGTH_SHORT).show();
                }

            }
        });




        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}