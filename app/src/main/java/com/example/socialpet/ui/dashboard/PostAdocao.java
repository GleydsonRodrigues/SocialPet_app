package com.example.socialpet.ui.dashboard;

public class PostAdocao {
    private String idUsuario;
    private String imgAdocaoPet;
    private String nomeDoador;
    private String imgPerfilDoador;
    private String descricao;

    private PostAdocao(){}

    private PostAdocao(String IdUsuario, String ImgAdocaoPet, String NomeDoador, String ImgPerfil, String Descricao){
        this.idUsuario = IdUsuario;
        this.imgAdocaoPet = ImgAdocaoPet;
        this.nomeDoador = NomeDoador;
        this.imgPerfilDoador = ImgPerfil;
        this.descricao = Descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImgAdocaoPet() {
        return imgAdocaoPet;
    }

    public void setImgAdocaoPet(String imgAdocaoPet) {
        this.imgAdocaoPet = imgAdocaoPet;
    }

    public String getNomeDoador() {
        return nomeDoador;
    }

    public void setNomeDoador(String nomeDoador) {
        this.nomeDoador = nomeDoador;
    }

    public String getImgPerfilDoador() {
        return imgPerfilDoador;
    }

    public void setImgPerfilDoador(String imgPerfilDoador) {
        this.imgPerfilDoador = imgPerfilDoador;
    }
}
