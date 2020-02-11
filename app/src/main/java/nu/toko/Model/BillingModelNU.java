package nu.toko.Model;

import java.util.List;

public class BillingModelNU {

    private int id_transaksi;
    private int id_pembeli;
    private int id_mitra;
    private int id_user_admin;
    private int status_transaksi;
    private int sub_total;
    private int harga_ongkir;
    private int harga_total;
    private String tgl_pemesanan;
    private String resi;
    private String alamat_kirim;
    private String kurir;
    private String url_bukti_transaksi;
    private String namalengkapbayar;
    private String norek;
    private int nominaltransfer;
    private boolean feedadded;
    private String bank;
    private List<BillingItemModel> billingItemModels;

    public BillingModelNU() {

    }

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

    public int getId_mitra() {
        return id_mitra;
    }

    public void setId_mitra(int id_mitra) {
        this.id_mitra = id_mitra;
    }

    public int getId_user_admin() {
        return id_user_admin;
    }

    public void setId_user_admin(int id_user_admin) {
        this.id_user_admin = id_user_admin;
    }

    public int getStatus_transaksi() {
        return status_transaksi;
    }

    public void setStatus_transaksi(int status_transaksi) {
        this.status_transaksi = status_transaksi;
    }

    public int getSub_total() {
        return sub_total;
    }

    public void setSub_total(int sub_total) {
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

    public String getTgl_pemesanan() {
        return tgl_pemesanan;
    }

    public void setTgl_pemesanan(String tgl_pemesanan) {
        this.tgl_pemesanan = tgl_pemesanan;
    }

    public String getResi() {
        return resi;
    }

    public void setResi(String resi) {
        this.resi = resi;
    }

    public List<BillingItemModel> getBillingItemModels() {
        return billingItemModels;
    }

    public void setBillingItemModels(List<BillingItemModel> billingItemModels) {
        this.billingItemModels = billingItemModels;
    }

    public String getAlamat_kirim() {
        return alamat_kirim;
    }

    public void setAlamat_kirim(String alamat_kirim) {
        this.alamat_kirim = alamat_kirim;
    }

    public String getKurir() {
        return kurir;
    }

    public void setKurir(String metode_kirim) {
        this.kurir = metode_kirim;
    }

    public String getUrl_bukti_transaksi() {
        return url_bukti_transaksi;
    }

    public void setUrl_bukti_transaksi(String url_bukti_transaksi) {
        this.url_bukti_transaksi = url_bukti_transaksi;
    }

    public String getNamalengkapbayar() {
        return namalengkapbayar;
    }

    public void setNamalengkapbayar(String namalengkapbayar) {
        this.namalengkapbayar = namalengkapbayar;
    }

    public String getNorek() {
        return norek;
    }

    public void setNorek(String norek) {
        this.norek = norek;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public int getNominaltransfer() {
        return nominaltransfer;
    }

    public void setNominaltransfer(int nominaltransfer) {
        this.nominaltransfer = nominaltransfer;
    }

    public boolean isFeedadded() {
        return feedadded;
    }

    public void setFeedadded(boolean feedadded) {
        this.feedadded = feedadded;
    }
}
