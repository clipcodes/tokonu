package nu.toko.Model;

import java.util.ArrayList;
import java.util.List;

import static nu.toko.Utils.Staticvar.DESCRIPTION;
import static nu.toko.Utils.Staticvar.ID;
import static nu.toko.Utils.Staticvar.IMAGES;
import static nu.toko.Utils.Staticvar.PRICE;
import static nu.toko.Utils.Staticvar.TITLE;

public class ProductModel {

    public static final String TABLE_NAME = "tables";

    private String title;
    private String images;
    private String description;
    private String specification;
    private String tags;
    private String categories;
    private String subcategories;
    private int id;
    private int userid;
    private int price;
    private int viewer;
    private int diskon;
    private String rating;
    private String created_at;
    private String updated_at;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + TITLE + " TEXT,"
                    + IMAGES + " TEXT,"
                    + DESCRIPTION + " TEXT,"
                    + PRICE + " TEXT"
                    + ")";

    public ProductModel() {}

    public ProductModel(String title, String images, String description, int price) {
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getViewer() {
        return viewer;
    }

    public void setViewer(int viewer) {
        this.viewer = viewer;
    }

    public int getDiskon() {
        return diskon;
    }

    public void setDiskon(int diskon) {
        this.diskon = diskon;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public static List<ProductModel> DataMen() {
        List<ProductModel> data = new ArrayList<>();
        data.add(new ProductModel(
                "Amazon Essentials Men's Sherpa Lined Full-Zip Hooded Fleece Sweatshirt",
                "img1.jpg,img2.jpg,img3.jpg,img4.jpg",
                "Shell: 56% Cotton, 44% Polyester; Lining: 100% Polyester-" +
                        "Imported-" +
                        "Machine Wash-" +
                        "The body and hood of this premium full-zip sweatshirt is lined with plush 100% polyester Sherpa for a casual look that keeps out the cold-" +
                        "Split kangaroo pockets, flat drawstring, and defined seams-" +
                        "Everyday made better: we listen to customer feedback and fine-tune every detail to ensure quality, fit, and comfort-" +
                        "Model is 6'2\" and wearing size Medium",
                20
        ));
        data.add(new ProductModel(
                "Global Blank Heavyweight Sherpa Lined Zip Up Fleece Hoodie Jacket Men",
                "img5.jpg,img6.jpg",
                "PREMIUM FABRICS - 7.5 oz pre-laundered cotton polyester blend French terry super soft warm full zipper hooded sweatshirt.-" +
                        "Contrast Zipper Tape with Metal Full Zip closure-" +
                        "FEATURES – thumbholes in cuff, insulated throughout body and arms, kangaroo pockets, hidden pocket for phone and headphone wire access, contrast or matching nickel tipped drawstrings, metal nickel full front zipper, antique nickel style eyelets, unisex fit for men, women, teen boys and girl teenagers.-" +
                        "STYLISH durable classic design goes great with jeans, shorts, sweatpants, work wear, hunting gear, slacks, skate attire, surfing apparel.-" +
                        "GREAT FOR ANY OCCASION – fishing, outdoor events, snow, winter and cold weather, after workouts or athletics like boxing, gym, pilates, yoga, soccer, running, training or swimming.-" +
                        "QUALITY CONSTRUCTION - All seams are split stitch double needle sewn, making this thick zip up jacket warm, cozy and attractive. Available in 6 great colors - Black, Burgundy, Light Blue, Navy, Light Grey, and Dark Grey-" +
                        "MEN’S SIZES LISTED for Women order one size down(i.e. Men’s Medium = Women’s Large). For a looser fit order size up. Please refer to reviews for recommendations if you are a first time buyer",
                34
        ));
        data.add(new ProductModel(
                "Full-Zip Hooded Fleece Sweatshirt Black",
                "img7.jpg,img8.jpg,img9.jpg",
                "Shell: 56% Cotton, 44% Polyester; Lining: 100% Polyester-" +
                        "Imported-" +
                        "Machine Wash-" +
                        "The body and hood of this premium full-zip sweatshirt is lined with plush 100% polyester Sherpa for a casual look that keeps out the cold-" +
                        "Split kangaroo pockets, flat drawstring, and defined seams-" +
                        "Everyday made better: we listen to customer feedback and fine-tune every detail to ensure quality, fit, and comfort-" +
                        "Model is 6'2\" and wearing size Medium",
                40
        ));
        data.add(new ProductModel(
                "Flygo Men's Classic Sherpa Lined Full Zip Up Hoodies Sweatshirt Jacket Outwear",
                "img10.jpg,img11.jpg",
                "Fabric: 95% Cotton, 5% Spandex. Fleece Lined.-" +
                        "Fully soft and cozy fleece lined, include hoodie and sleeves, comfortable to wear and warmth keeping.-" +
                        "2 zipped hand pockets, 2 big inner pockets, offer enought room for daily life to put your daily essentials, and warm your hands.-" +
                        "Classic design, hooded with drawstring, long sleeve, full-zip front, ribbed cuffs and hemline.-" +
                        "Perfect for daily life, work, school, dating, travel, trip, holiday, vacation, sports etc. Ideal for men, juniors and schoolboys.-" +
                        "US SIZE - Please check detailed size info in product description before ordering. Thanks!",
                60
        ));
        data.add(new ProductModel(
                "Men's Sherpa Lined Pullover Hoodie Sweatshirt",
                "img12.jpg,img13.jpg",
                "Shell: 56% Cotton, 44% Polyester; 100% Polyester-" +
                        "Imported-" +
                        "No Closure closure-" +
                        "Machine Wash-" +
                        "This everyday-classic sweatshirt is a go-to for an easy, casual look-" +
                        "Everyday made better: we listen to customer feedback and fine-tune every detail to ensure quality, fit, and comfort",
                45
        ));
        return data;
    }

    public static List<ProductModel> DataWomen() {
        List<ProductModel> data = new ArrayList<>();
        data.add(new ProductModel(
                "CAMEL Women Fleece Jackets with Pockets Fashion Hooded Long Sleeve Soft Full Zip Fleece Coat for Outdoor",
                "img14.jpg,img15.jpg,img16.jpg",
                "Imported-" +
                        "Zipper closure-" +
                        "Material: Fleece lining, This women fleece jacket is made out of cotton velvet, lightweight and ultra soft to wear, comfortable and warm. It would be touching your skin with soft feeling. Comfortable velveteen,the womens hooded jacket suitable for spring, autumn, winter using.-" +
                        "Design: Our fashion cotton jacket has hood and side pocket. SBS zipper opening design to make the convenience for wearing. Always keep your body warm.-" +
                        "Matching: Our outdoor cotton coat could match the sweater shirt or long sleeve T-shirts inside. You could wear the long causal pants or Jeans down bottom part with this jacket.-" +
                        "Multifunctions: CAMEL women fleece jacket can be a fleece coat in fall and spring or winter, you can wear or take off it easily, and fold it in your bag. It is a perfect women zip jacket for hiking, camping, running, jogging, cycling, sports and any outdoor activities or just for daily wear.-" +
                        "Guarantee: If you have any problem or suggestion about this premium fleece jacket please contact us. CAMEL is dedicated to creating cost-effective products. We offer 30-Day Money Back Service for any quality issues.",
                37
        ));
        data.add(new ProductModel(
                "Bellivera Women’s Faux Fur Jacket with 2 Side-Seam Pockets, The Coat with Hood and Bottom Ribbing",
                "img17.jpg,img18.jpg",
                "BASIC FEATURES:Cuff thread, Elastic hem, Drawstring hood, Long Sleeve, Full Lined， Suit for Daily wear, School, Vacation, Work, Club, Party, Street, great for Office or Sport Outdoor. You will fall in love with this trendy fleece coat!-" +
                        "WARM AND SOFT: The whisper-soft faux fur keeps you warm and cozy in winters.-" +
                        "POCKETS: Two hidden pockets warm your hands and keeps your belonging safe.-" +
                        "PROMPT DESCRIPTION：Due to monitor settings, monitor pixel definitions, we cannot guarantee that the color you see on your screen as the exact color of the product. We strive to make our colors as accurate as possible. However, colors are approximations of actual colors.-" +
                        "ALL-AROUND CUSTOMER SUPPORT: We listen to customer feedback and fine-tune every detail to ensure quality, fit and comfort. Please contact us if you have any question about the quality of our products or if you are experiencing any difficulty about size and delivery.",
                42
        ));
        data.add(new ProductModel(
                "Giolshon Women’s Faux Fur Jacket The Lovely Jacket with Hood for Autum and Winter",
                "img19.jpg,img20.jpg,img21.jpg",
                "Imported-" +
                        "Zipper closure-" +
                        "BASIC FEATURES:Cuff thread, Elastic hem, Drawstring hood, Long Sleeve, Full Lined-" +
                        "WARM AND SOFT: The whisper-soft faux fur keeps you warm and cozy in winters.-" +
                        "POCKETS: Two hidden pockets warm your hands and keeps your belonging safe.-" +
                        "PROMPT DESCRIPTION：Due to monitor settings, monitor pixel definitions, we cannot guarantee that the color you see on your screen as the exact color of the product. We strive to make our colors as accurate as possible. However, colors are approximations of actual colors.-" +
                        "ALL-AROUND CUSTOMER SUPPORT: We listen to customer feedback and fine-tune every detail to ensure quality, fit and comfort. Please contact us if you have any question about the quality of our products or if you are experiencing any difficulty about size and delivery.",
                40
        ));
        data.add(new ProductModel(
                "Geschallino Women's Soft Faux Fur Hooded Jacket, 2 Pockets Short/Long Coat Outwear Warm Fluffy Fleece Tops for Winter, Spring",
                "img22.jpg,img23.jpg",
                "BASIC FEATURES: Shell: 100% Polyester; Line: 100% Polyester; Long Sleeves; 2 Pockets; Full-Fur Hood; Zipper Closure; Cuff thread; Drawstring hood.-" +
                        "CUTE AND VERSATILE: You can build different fashion styles with this cute faux fur jacket, suitable for lots of occasions: daily life, office, party, cocktail party, travel, street.-" +
                        "SOFT AND WARM: Made of soft faux fur which touches like real rabbit fur, this lightweight jacket keeps you warm and snug in cold fall, spring and winter. You can easily tighten the warm hood with its drawstring against the chill wind in winters. Two side pockets warm your hands as well as storing your belongings.-" +
                        "DURABLE AND STURDY: We applied a special washing technique on the fur fabric to make it durable. It will keep new and fresh even after many years' wearing with easy proper maintenance.-" +
                        "ALL-AROUND CUSTOMER SUPPORT: We listen to customer feedback and fine-tune every detail to ensure quality, fit and comfort. Please contact us if you have any question about the quality of our products or if you are experiencing any difficulty with size and delivery.",
                37
        ));
        data.add(new ProductModel(
                "Pink Platinum Women's Faux Fur Bomber Jacket",
                "img24.jpg,img25.jpg",
                "100% Polyester-" +
                        "Imported-" +
                        "Zipper closure-" +
                        "Machine Wash-" +
                        "Comfort: we own quality material to give you the best Available comfort around, no matter the weather.-" +
                        "Versatility: our jackets can be worn in the fall or winter, in the freezing cold or during a light breeze. The versatility allows your child to have one jacket for both seasons.-" +
                        "Style: our designers keep in touch with the most up to date designs, keeping you warm and stylish at the same time. No need to sacrifice your fashion taste for a bulky jacket.-" +
                        "special size type: standard-" +
                        "weave type: Woven",
                45
        ));
        return data;
    }

    public String getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(String subcategories) {
        this.subcategories = subcategories;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
