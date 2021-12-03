package com.example.socialpet.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialpet.R;
import com.example.socialpet.databinding.FragmentHomeBinding;
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

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerHome;
    private FirebaseFirestore firebaseFirestore;
    private ImageView imgPerfilHome;
    private EditText pesquisaHome;

    private FirestoreRecyclerAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        imgPerfilHome = binding.imgPerfilPostHome;
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
                            .into(imgPerfilHome);

                }
            }
        });

        recyclerHome = binding.recyclerViewHome;
        firebaseFirestore = FirebaseFirestore.getInstance();
        String IdUsuario = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //Query
        Query query = firebaseFirestore.collection("Postagem").whereNotEqualTo("idUsuario",IdUsuario);
        FirestoreRecyclerOptions<PostsHome> options = new FirestoreRecyclerOptions.Builder<PostsHome>()
                .setQuery(query, PostsHome.class).build();

        adapter = new FirestoreRecyclerAdapter<PostsHome, PostsViewHolder>(options) {
            @NonNull
            @Override
            public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View teste = LayoutInflater.from(parent.getContext()).inflate((R.layout.),parent,false);

                View view = LayoutInflater.from(parent.getContext()).inflate((R.layout.modelo_postagem),parent,false);
                return new PostsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull PostsViewHolder holder, int position, @NonNull PostsHome model) {
                holder.nomeEspecie.setText(model.getEspeciePet());
                holder.nomeUsuario.setText(model.getNomeUsuario());
                holder.mensagemPost.setText(model.getMensagemPost());
                Picasso.get()
                        .load(model.getImagemPost())
                        .into(holder.imagemPost);
                Picasso.get()
                        .load(model.getImagemPerfil())
                        .into(holder.imagemPerfil);

            }
        };

        recyclerHome.setHasFixedSize(true);
        recyclerHome.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerHome.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (adapter != null) {
            adapter.stopListening();
        }
        binding = null;
    }

    private class PostsViewHolder extends RecyclerView.ViewHolder{

        private TextView nomeUsuario;
        private TextView nomeEspecie;
        private TextView mensagemPost;
        private ImageView imagemPost, imagemPerfil;
        public PostsViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeUsuario = itemView.findViewById(R.id.txtNomeUsuarioPost);
            nomeEspecie = itemView.findViewById(R.id.txtEspeciePost);
            mensagemPost = itemView.findViewById(R.id.txtMensagem);
            imagemPost = itemView.findViewById(R.id.imgPost);
            imagemPerfil = itemView.findViewById(R.id.imgPerfilPost);

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();


    }
}