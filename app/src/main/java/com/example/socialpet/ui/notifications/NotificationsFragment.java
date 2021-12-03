package com.example.socialpet.ui.notifications;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialpet.ModeloExibicao;
import com.example.socialpet.R;
import com.example.socialpet.databinding.FragmentNotificationsBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class NotificationsFragment extends Fragment {


    private FragmentNotificationsBinding binding;
    private RecyclerView recyclerLoja;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;
    private FirestoreRecyclerOptions<PostLoja> options;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerLoja = binding.recyclerViewLoja;
        firebaseFirestore = FirebaseFirestore.getInstance();
        Query query = firebaseFirestore.collection("Loja");

        options = new FirestoreRecyclerOptions.Builder<PostLoja>()
                .setQuery(query, PostLoja.class).build();

        adapter = new FirestoreRecyclerAdapter<PostLoja, PostLojaViewHolder>(options) {
            @SuppressLint("SetTextI18n")
            @Override
            protected void onBindViewHolder(@NonNull PostLojaViewHolder holder, int position, @NonNull PostLoja model) {
                holder.descricao.setText(model.getDescricao());
                holder.preco.setText("R$" + model.getPreco() + ",00");
                Picasso.get()
                        .load(model.getPostLoja())
                        .into(holder.imagemPost);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(holder.tela, ModeloExibicao.class);
                        intent.putExtra("descricao", model.getDescricao());
                        intent.putExtra("nome", model.getNomeVendedor());
                        intent.putExtra("imagemPost", model.getImagemPost());
                        intent.putExtra("imagemPerfil", model.getImgPerfilDoador());
                        intent.putExtra("preco", model.getPreco());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public PostLojaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate((R.layout.modelo_loja), parent, false);
                return new PostLojaViewHolder(view, parent.getContext());
            }
        };
        recyclerLoja.setHasFixedSize(true);
        recyclerLoja.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerLoja.setAdapter(adapter);

        return root;
    }
    private class PostLojaViewHolder extends RecyclerView.ViewHolder{

        private ImageView imagemPost;
        private TextView preco,descricao;
        private Context tela;
        public PostLojaViewHolder(@NonNull View itemView, final Context context){
            super(itemView);
            descricao = itemView.findViewById(R.id.txtDescricaoLoja);
            imagemPost = itemView.findViewById(R.id.imagemPetAdocao);
            preco = itemView.findViewById(R.id.txtPreco2);
            tela = context;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (adapter != null){
            adapter.stopListening();
        }
        binding = null;
    }
}