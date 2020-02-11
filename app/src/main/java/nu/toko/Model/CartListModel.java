package nu.toko.Model;

public class CartListModel {

    private int idcart;
    private int price;
    private String productname;

    public CartListModel() {

    }

    public CartListModel(String productname) {
        this.productname = productname;
    }

    public int getIdcart() {
        return idcart;
    }

    public void setIdcart(int idcart) {
        this.idcart = idcart;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }
}
