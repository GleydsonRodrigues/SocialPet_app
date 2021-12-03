package com.example.socialpet.ui.notifications;

public class PostLoja {
    private String idUsuario;
    private String imagemPost;
    private String nomeVendedor;
    private String descricao;
    private String preco;
    private String imgPerfilDoador;

    private PostLoja(){}

    private PostLoja(String Preco, String IdUsuario, String PostLoja, String NomeVendedor, String Descricao, String imgPerfil){
        this.idUsuario = IdUsuario;
        this.imagemPost = PostLoja;
        this.nomeVendedor = NomeVendedor;
        this.descricao = Descricao;
        this.preco = Preco;
        this.imgPerfilDoador = imgPerfil;
    }

    public String getImgPerfilDoador() {
        return imgPerfilDoador;
    }

    public void setImgPerfilDoador(String imgPerfilDoador) {
        this.imgPerfilDoador = imgPerfilDoador;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getImagemPost() {
        return imagemPost;
    }

    public void setImagemPost(String imagemPost) {
        this.imagemPost = imagemPost;
    }

    public String getPostLoja() {
        return imagemPost;
    }

    public void setPostLoja(String postLoja) {
        this.imagemPost = postLoja;
    }

    public String getNomeVendedor() {
        return nomeVendedor;
    }

    public void setNomeVendedor(String nomeVendedor) {
        this.nomeVendedor = nomeVendedor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
