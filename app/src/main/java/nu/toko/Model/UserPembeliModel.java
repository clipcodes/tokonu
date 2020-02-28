package nu.toko.Model;

import android.widget.EditText;

public class UserPembeliModel {

    String email_pembeli, nama_pembeli, no_telp, provinsi_pembeli, kabupaten_pembeli, kecamatan_pembeli, kode_pos_pembeli, alamat_pembeli;
    private String foto;

    public UserPembeliModel() {

    }

    public String getEmail_pembeli() {
        return email_pembeli;
    }

    public void setEmail_pembeli(String email_pembeli) {
        this.email_pembeli = email_pembeli;
    }

    public String getNama_pembeli() {
        return nama_pembeli;
    }

    public void setNama_pembeli(String nama_pembeli) {
        this.nama_pembeli = nama_pembeli;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public String getProvinsi_pembeli() {
        return provinsi_pembeli;
    }

    public void setProvinsi_pembeli(String provinsi_pembeli) {
        this.provinsi_pembeli = provinsi_pembeli;
    }

    public String getKabupaten_pembeli() {
        return kabupaten_pembeli;
    }

    public void setKabupaten_pembeli(String kabupaten_pembeli) {
        this.kabupaten_pembeli = kabupaten_pembeli;
    }

    public String getKecamatan_pembeli() {
        return kecamatan_pembeli;
    }

    public void setKecamatan_pembeli(String kecamatan_pembeli) {
        this.kecamatan_pembeli = kecamatan_pembeli;
    }

    public String getKode_pos_pembeli() {
        return kode_pos_pembeli;
    }

    public void setKode_pos_pembeli(String kode_pos_pembeli) {
        this.kode_pos_pembeli = kode_pos_pembeli;
    }

    public String getAlamat_pembeli() {
        return alamat_pembeli;
    }

    public void setAlamat_pembeli(String alamat_pembeli) {
        this.alamat_pembeli = alamat_pembeli;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
