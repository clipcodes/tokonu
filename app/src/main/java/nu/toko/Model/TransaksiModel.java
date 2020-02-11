package nu.toko.Model;

import static nu.toko.Utils.Staticvar.ADDRESS;
import static nu.toko.Utils.Staticvar.ID_ADDRESS;
import static nu.toko.Utils.Staticvar.KEC;
import static nu.toko.Utils.Staticvar.KODEPOS;
import static nu.toko.Utils.Staticvar.KOTAKAB;
import static nu.toko.Utils.Staticvar.PROVINSI;

public class TransaksiModel {

    int id_transaksi;
    int id_pembeli;
    String jumlah;
    String tgl_pemesanan;
    String sub_total;
    int harga_ongkir;
    int harga_total;
    String resi;
    int status_transaksi;
    int id_user_admin;

    public int getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(int id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public int getId_pembeli() {
        return id_pembeli;
    }

    public void setId_pembeli(int id_pembeli) {
        this.id_pembeli = id_pembeli;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getTgl_pemesanan() {
        return tgl_pemesanan;
    }

    public void setTgl_pemesanan(String tgl_pemesanan) {
        this.tgl_pemesanan = tgl_pemesanan;
    }

    public String getSub_total() {
        return sub_total;
    }

    public void setSub_total(String sub_total) {
        this.sub_total = sub_total;
    }

    public int getHarga_ongkir() {
        return harga_ongkir;
    }

    public void setHarga_ongkir(int harga_ongkir) {
        this.harga_ongkir = harga_ongkir;
    }

    public int getHarga_total() {
        return harga_total;
    }

    public void setHarga_total(int harga_total) {
        this.harga_total = harga_total;
    }

    public String getResi() {
        return resi;
    }

    public void setResi(String resi) {
        this.resi = resi;
    }

    public int getStatus_transaksi() {
        return status_transaksi;
    }

    public void setStatus_transaksi(int status_transaksi) {
        this.status_transaksi = status_transaksi;
    }

    public int getId_user_admin() {
        return id_user_admin;
    }

    public void setId_user_admin(int id_user_admin) {
        this.id_user_admin = id_user_admin;
    }
}
