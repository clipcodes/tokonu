package nu.toko.Model;

import java.util.List;

public class CategoriesModel {

    private int viewer;
    private String categories;
    private int id;
    List<ProductModel> productitem;

    public CategoriesModel() {}

    public CategoriesModel(String categories, List<ProductModel> productitem) {
        this.categories = categories;
        this.productitem = productitem;
    }

    public List<ProductModel> getProductitem() {
        return productitem;
    }

    public void setProductitem(List<ProductModel> productitem) {
        this.productitem = productitem;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getViewer() {
        return viewer;
    }

    public void setViewer(int viewer) {
        this.viewer = viewer;
    }
}
