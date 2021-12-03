package com.example.socialpet.ui.perfil;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialpet.AddFotos;
import com.example.socialpet.FotosAdocao;
import com.example.socialpet.MainActivity;
import com.example.socialpet.R;
import com.example.socialpet.databinding.FragmentPerfilUsuarioBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class PerfilUsuario extends Fragment {

    private FragmentPerfilUsuarioBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String usuarioID, nome,ImagemPerfil, tipo;
    private FirebaseFirestore firebaseFirestore;
    private TextView nomeUsuario, tipoUsuario;
    ImageView imgPerfil, btnEditar;
    private FirestoreRecyclerAdapter adapter;
    private RecyclerView perfilRecyclerView;
    Uri imageUri;

    Button btnSair, btnAddFoto, btnAdocao;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPerfilUsuarioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btnSair = binding.btnSair;
        btnAddFoto = binding.btnAddFoto;
        btnEditar = binding.btnEditarFoto;
        imgPerfil = binding.imgFotoPerfil;
        btnAdocao = binding.btnAddFoto2;
        nomeUsuario = binding.NomeUsuarioPerfil;
        tipoUsuario = binding.lblTipoUsu;

        perfilRecyclerView = binding.recyclerViewPerfil;

        firebaseFirestore = FirebaseFirestore.getInstance();
        String IdUsuario = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //Query
        Query query = firebaseFirestore.collection("Postagem").whereEqualTo("idUsuario", IdUsuario);
        FirestoreRecyclerOptions<PostPerfil> options = new FirestoreRecyclerOptions.Builder<PostPerfil>()
                .setQuery(query, PostPerfil.class).build();

        adapter = new FirestoreRecyclerAdapter<PostPerfil, PostsPerfilViewHolder>(options){

            @NonNull
            @Override
            public PerfilUsuario.PostsPerfilViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_perfil,parent,false);
                return new PostsPerfilViewHolder(view);
            }
            @Override
            protected void onBindViewHolder(@NonNull PerfilUsuario.PostsPerfilViewHolder holder, int position, @NonNull PostPerfil model) {
                Picasso.get()
                        .load(model.getImagemPost())
                        .into(holder.imagemPost);

            }

        };


        perfilRecyclerView.setHasFixedSize(true);
        perfilRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        perfilRecyclerView.setAdapter(adapter);

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null){

                    nome = value.getString("nome");
                    tipo = value.getString("tipo");

                    ImagemPerfil = value.getString("imagemPerfil");
                    Picasso.get()
                            .load(ImagemPerfil)
                            .into(imgPerfil);
                    nomeUsuario.setText(nome);
                    tipoUsuario.setText(tipo);

                    if (tipo.equals("Centro de Adoção")){
                        btnAdocao.setText("Doação");
                        btnAdocao.setVisibility(View.VISIBLE);
                    }else if (tipo.equals("Profissional")){
                        btnAdocao.setText("Vender");
                        btnAdocao.setVisibility(View.VISIBLE);
                    }

                }else {
                    nomeUsuario.setText("Não encontrado");
                    tipoUsuario.setText("Não encontrado");
                }
            }
        });

        btnAddFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(getActivity(), AddFotos.class);
                    intent.putExtra("nome", nome);
                    intent.putExtra("usuarioID", usuarioID);
                    intent.putExtra("imagemPerfil", ImagemPerfil);
                    intent.putExtra("tipo", tipo);
                    startActivity(intent);



            }
        });
        btnAdocao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FotosAdocao.class);
                intent.putExtra("nome", nome);
                intent.putExtra("usuarioID", usuarioID);
                intent.putExtra("imagemPerfil", ImagemPerfil);
                intent.putExtra("tipo", tipo);
                startActivity(intent);
            }
        });
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/*");

                getActivity().startActivityForResult(Intent.createChooser(intent,"Escolha uma imagem"), 0);

            }
        });
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder mensagem = new AlertDialog.Builder(getActivity());
                mensagem.setTitle("Quer sair?");
                mensagem.setMessage("Gostaria de deslogar da sua conta?");
                mensagem.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();
                    }
                });
                mensagem.setNeutralButton("Cancelar", null);
                mensagem.show();
            }
        });


        return root;
    }
    private class PostsPerfilViewHolder extends RecyclerView.ViewHolder{
        private ImageView imagemPost;
        public PostsPerfilViewHolder(@NonNull View itemView){
            super(itemView);
            imagemPost = itemView.findViewById(R.id.imagePerfil);
            imagemPost.setMaxHeight(imagemPost.getMaxWidth());
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();

    }

    public void postarImagem() {
            final ProgressDialog pd = new ProgressDialog(getActivity());
            pd.setTitle("Postando...");
            pd.show();
            String filename = UUID.randomUUID().toString();
            final StorageReference ref = FirebaseStorage.getInstance().getReference("/imgPerfil/" + filename);
            ref.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Log.i("Teste", uri.toString());
                                    inserindoDados(uri.toString());
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Teste", e.getMessage(), e);
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            pd.setMessage("Carregando: " + (int) progressPercent + "%");
                        }
                    });

    }
    private void inserindoDados(String uri){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, String> post = new HashMap<>();
        post.put("imagemPerfil", uri);

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.set(post).addOnSuccessListener(unused -> {
            Log.d("db", "Cadastrado com sucesso");
        }).addOnFailureListener(e -> {
            Log.d("db", "Falha ao cadastrar usuario" + e.toString());
        });

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}