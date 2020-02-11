package nu.toko.Model;

public class BuyerFeedbackModel {

    private int id_user_pembeli;
    private int id_feedback;
    private int id_produk;
    private int id_transaksi;
    private double rating;
    private String komen;
    private String tanggal;
    private String nama;

    public BuyerFeedbackModel() {
    }

    public int getId_user_pembeli() {
        return id_user_pembeli;
    }

    public void setId_user_pembeli(int id_user_pembeli) {
        this.id_user_pembeli = id_user_pembeli;
    }

    public int getId_feedback() {
        return id_feedback;
    }

    public void setId_feedback(int id_feedback) {
        this.id_feedback = id_feedback;
    }

    public int getId_produk() {
        return id_produk;
    }

    public void setId_produk(int id_produk) {
        this.id_produk = id_produk;
    }

    public int getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(int id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getKomen() {
        return komen;
    }

    public void setKomen(String komen) {
        this.komen = komen;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
