package nu.toko.Model;

import java.util.List;

public class CategoriesModelNU {

    private int id_kategori;
    private int id_sub_kategori;
    private String nama_kategori;
    private String url_gambar_kategori;

    public CategoriesModelNU() {}

    public int getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(int id_kategori) {
        this.id_kategori = id_kategori;
    }

    public String getNama_kategori() {
        return nama_kategori;
    }

    public void setNama_kategori(String nama_kategori) {
        this.nama_kategori = nama_kategori;
    }

    public String getUrl_gambar_kategori() {
        return url_gambar_kategori;
    }

    public void setUrl_gambar_kategori(String url_gambar_kategori) {
        this.url_gambar_kategori = url_gambar_kategori;
    }

    public int getId_sub_kategori() {
        return id_sub_kategori;
    }

    public void setId_sub_kategori(int id_sub_kategori) {
        this.id_sub_kategori = id_sub_kategori;
    }
}
