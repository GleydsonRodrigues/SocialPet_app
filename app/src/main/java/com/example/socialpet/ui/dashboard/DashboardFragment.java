package com.example.socialpet.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialpet.ModeloExibicao;
import com.example.socialpet.R;
import com.example.socialpet.databinding.FragmentDashboardBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView recyclerAdocao;
    private FirestoreRecyclerAdapter adapter;
    private ImageView imagemPerfilAdocao;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerAdocao = binding.recyclerViewAdocao;
        firebaseFirestore = FirebaseFirestore.getInstance();
        imagemPerfilAdocao = binding.imgPerfilPostAdocao;
        String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null){

                    String ImagemPerfil = value.getString("imagemPerfil");
                    Picasso.get()
                            .load(ImagemPerfil)
                            .into(imagemPerfilAdocao);

                }
            }
        });

        //Query
        Query query = firebaseFirestore.collection("Adocao");

        FirestoreRecyclerOptions<PostAdocao> options = new FirestoreRecyclerOptions.Builder<PostAdocao>()
                .setQuery(query, PostAdocao.class).build();

        adapter = new FirestoreRecyclerAdapter<PostAdocao, AdocaoViewHolder>(options) {

            @NonNull
            @Override
            public AdocaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate((R.layout.modelo_adocao),parent,false);
                return new AdocaoViewHolder(view, parent.getContext());
            }

            @Override
            protected void onBindViewHolder(@NonNull AdocaoViewHolder holder, int position, @NonNull PostAdocao model) {
                holder.descricao.setText(model.getDescricao());
                Picasso.get()
                        .load(model.getImgAdocaoPet())
                        .into(holder.imgPet);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(holder.tela, ModeloExibicao.class);
                        intent.putExtra("descricao", model.getDescricao());
                        intent.putExtra("nome", model.getNomeDoador());
                        intent.putExtra("imagemPost", model.getImgAdocaoPet());
                        intent.putExtra("imagemPerfil", model.getImgPerfilDoador());
                        startActivity(intent);
                    }
                });
            }
        };

        recyclerAdocao.setHasFixedSize(true);
        recyclerAdocao.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerAdocao.setAdapter(adapter);

        return root;
    }

    private class AdocaoViewHolder extends RecyclerView.ViewHolder{

        private TextView descricao;
        private ImageView imgPet;
        private Context tela;
        public AdocaoViewHolder(@NonNull View itemView, final Context context) {
            super(itemView);

            imgPet = itemView.findViewById(R.id.imagemPetAdocao);
            descricao = itemView.findViewById(R.id.txtDescricaoLoja);
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
        if (adapter != null) {
            adapter.stopListening();
        }
        binding = null;
    }
}