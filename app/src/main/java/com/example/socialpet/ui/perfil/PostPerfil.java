package com.example.socialpet.ui.perfil;

public class PostPerfil {
    private String imagemPost;


    private  PostPerfil(){}


    private PostPerfil(String ImagemPost){


        this.imagemPost = ImagemPost;

    }


    public String getImagemPost() {
        return imagemPost;
    }

    public void setImagemPost(String imagemPost) {
        this.imagemPost = imagemPost;
    }


}
