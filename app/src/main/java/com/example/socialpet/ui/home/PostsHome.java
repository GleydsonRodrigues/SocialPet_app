package com.example.socialpet.ui.home;

public class PostsHome {

    private String imagemPerfil;
    private String nomeUsuario;
    private String especiePet;
    private String imagemPost;
    private String mensagemPost;
    private String quantCurtidas;
    private Boolean curtida;

    private  PostsHome(){}


    private PostsHome(String NomeUsuario, String EspeciePet, String MensagemPost, String ImagemPost, String ImagemPerfil){

        this.nomeUsuario = NomeUsuario;
        this.especiePet = EspeciePet;
        this.mensagemPost = MensagemPost;
        this.imagemPost = ImagemPost;
        this.imagemPerfil = ImagemPerfil;
    }

    public String getMensagemPost() {
        return mensagemPost;
    }

    public void setMensagemPost(String mensagemPost) {
        this.mensagemPost = mensagemPost;
    }

    public String getImagemPerfil() {
        return imagemPerfil;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getEspeciePet() {
        return especiePet;
    }

    public String getImagemPost() {
        return imagemPost;
    }

    public String getQuantCurtidas() {
        return quantCurtidas;
    }

    public Boolean getCurtida() {
        return curtida;
    }

    public void setImagemPerfil(String imagemPerfil) {
        this.imagemPerfil = imagemPerfil;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public void setEspeciePet(String especiePet) {
        this.especiePet = especiePet;
    }

    public void setImagemPost(String imagemPost) {
        this.imagemPost = imagemPost;
    }

    public void setQuantCurtidas(String quantCurtidas) {
        this.quantCurtidas = quantCurtidas;
    }

    public void setCurtida(Boolean curtida) {
        this.curtida = curtida;
    }
}
