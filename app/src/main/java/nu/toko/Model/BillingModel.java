package nu.toko.Model;

import java.util.ArrayList;
import java.util.List;

public class BillingModel {

    private int idbill;
    private int idproduct;
    private int price;
    private String img;
    private int statusbill;
    private String productname;

    public BillingModel() {

    }

    public BillingModel(int price, String productname, String img) {
        this.productname = productname;
        this.setPrice(price);
        this.setStatusbill(statusbill);
        this.setImg(img);
    }

    public int getIdbill() {
        return idbill;
    }

    public void setIdbill(int idbill) {
        this.idbill = idbill;
    }

    public int getIdproduct() {
        return idproduct;
    }

    public void setIdproduct(int idproduct) {
        this.idproduct = idproduct;
    }


    public int getStatusbill() {
        return statusbill;
    }

    public void setStatusbill(int statusbill) {
        this.statusbill = statusbill;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public static List<BillingModel> DataBill() {
        List<BillingModel> data = new ArrayList<>();
        data.add(new BillingModel(
                43,
                "Global Blank Heavyweight Sherpa Lined Zip Up Fleece Hoodie Jacket Men",
                "img5.jpg"
        ));
        data.add(new BillingModel(
                60,
                "Flygo Men's Classic Sherpa Lined Full Zip Up Hoodies Sweatshirt Jacket Outwear",
                "img10.jpg"
        ));
        data.add(new BillingModel(
                40,
                "Giolshon Women’s Faux Fur Jacket The Lovely Jacket with Hood for Autum and Winter",
                "img19.jpg"
        ));

        return data;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
