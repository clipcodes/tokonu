package nu.toko.Model;

public class BillingItemModel {

    private String id_transaksi_item;
    private String id_transaksi;
    private int qty;
    private ProductModelNU produk;

    public String getId_transaksi_item() {
        return id_transaksi_item;
    }

    public void setId_transaksi_item(String id_transaksi_item) {
        this.id_transaksi_item = id_transaksi_item;
    }

    public String getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public ProductModelNU getProduk() {
        return produk;
    }

    public void setProduk(ProductModelNU produk) {
        this.produk = produk;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
