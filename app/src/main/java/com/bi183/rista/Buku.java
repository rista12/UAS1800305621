package com.bi183.rista;

import java.util.Date;

public class Buku {
    private int idBuku;
    private String judul;
    private Date terbit;
    private String gambar;
    private String genre;
    private String penulis;
    private String sinopsis;


    public Buku(int idBuku, String judul, Date terbit, String gambar, String genre, String penulis, String sinopsis) {
        this.idBuku = idBuku;
        this.judul = judul;
        this.terbit = terbit;
        this.gambar = gambar;
        this.genre = genre;
        this.penulis = penulis;
        this.sinopsis = sinopsis;


    }

    public int getIdBuku() {
        return idBuku;
    }

    public void setIdBuku(int idBuku) {
        this.idBuku = idBuku;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public Date getTerbit() {
        return terbit;
    }

    public void setTerbit(Date terbit) {
        this.terbit= terbit;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }


}
