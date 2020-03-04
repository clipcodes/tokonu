package nu.toko.Page;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.AppBarLayout;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nu.toko.Adapter.AdapImages;
import nu.toko.Adapter.BuyerFeedbackAdapter;
import nu.toko.Adapter.DescriptionAdapter;
import nu.toko.Adapter.KurirOngkosAdapter;
import nu.toko.Adapter.ProductAdapter;
import nu.toko.Model.BuyerFeedbackModel;
import nu.toko.Model.ImagesModel;
import nu.toko.Model.OngkosKirimModel;
import nu.toko.Model.ProductModelNU;
import nu.toko.Model.UserMitra;
import nu.toko.R;
import nu.toko.Reqs.ReqString;
import nu.toko.Sqlite.CartDB;
import nu.toko.Utils.Others;
import nu.toko.Utils.UserPrefs;

import static nu.toko.Utils.Staticvar.BERAT_PRODUK;
import static nu.toko.Utils.Staticvar.CREATED_AT;
import static nu.toko.Utils.Staticvar.DESKRIPSI_PRODUK;
import static nu.toko.Utils.Staticvar.DIKIRIMDARI;
import static nu.toko.Utils.Staticvar.DISKON;
import static nu.toko.Utils.Staticvar.DISKONPERCENT;
import static nu.toko.Utils.Staticvar.FEEDBACKALL;
import static nu.toko.Utils.Staticvar.HARGA_ADMIN;
import static nu.toko.Utils.Staticvar.HARGA_MITRA;
import static nu.toko.Utils.Staticvar.ID;
import static nu.toko.Utils.Staticvar.ID_FEEDBACK;
import static nu.toko.Utils.Staticvar.ID_GAMBAR;
import static nu.toko.Utils.Staticvar.ID_PRODUK;
import static nu.toko.Utils.Staticvar.ID_SUB_KATEGORI;
import static nu.toko.Utils.Staticvar.ID_TRANSAKSI;
import static nu.toko.Utils.Staticvar.KOMEN;
import static nu.toko.Utils.Staticvar.KONDISI_PRODUK;
import static nu.toko.Utils.Staticvar.NAMA_PRODUK;
import static nu.toko.Utils.Staticvar.NAME;
import static nu.toko.Utils.Staticvar.ONGKIR;
import static nu.toko.Utils.Staticvar.PRODUCTSUBKATEGORI;
import static nu.toko.Utils.Staticvar.PRODUKDETAIL;
import static nu.toko.Utils.Staticvar.RATING;
import static nu.toko.Utils.Staticvar.SLASH;
import static nu.toko.Utils.Staticvar.STOK;
import static nu.toko.Utils.Staticvar.TANGGAL;
import static nu.toko.Utils.Staticvar.TERJUAL;
import static nu.toko.Utils.Staticvar.TOTALFEEDBACK;
import static nu.toko.Utils.Staticvar.URL_GAMBAR;

public class Details extends AppCompatActivity {

    String TAG = getClass().getSimpleName();

    AppBarLayout appBarLayout;
    ViewPager vpphotos;
    AdapImages adapImages;
    LinearLayout container_toolbar;
    WormDotsIndicator wormDotsIndicator;
    List<BuyerFeedbackModel> buyerFeedbackModelList;

    RecyclerView rvdescription;
    DescriptionAdapter descriptionAdapter;

    RecyclerView rvbuyerfeedback;
    BuyerFeedbackAdapter buyerFeedbackAdapter;

    RecyclerView rvmore;
    ProductAdapter product3Adapter;
    List<ProductModelNU> productModelNUList;

    CardView buynow;

    TextView productname, price, title, rating, review, kondisi, ratingfeed
            , stok, kategori, pengirimanmitra, deskripsi, namamitra;

    ImageView favorites;

    List<ImagesModel> imagesModels;

    String idproduk, produknama;
    int harga, idmitra;
    String[] deskripsiarr = {"a-v"};

    RequestQueue requestQueue;
    ReqString reqString;

    ProductModelNU pnu;
    RatingBar star;

    FrameLayout cart, chat;
    ImageView cartimg;

    FrameLayout diskonkontainer;

    TextView pricediscount, ongkirtex;

    RecyclerView rvongkoskirim;
    KurirOngkosAdapter kurirOngkosAdapter;
    List<OngkosKirimModel> ongkosKirimModelList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        idproduk = getIntent().getStringExtra("idproduk");
        harga = getIntent().getIntExtra("harga", 0);
        produknama = getIntent().getStringExtra("produknama");

        init();

        reqString.go(suksesproduct, PRODUKDETAIL+idproduk);
        reqString.go(suksesfeedback, FEEDBACKALL+idproduk);
    }

    void init(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        reqString = new ReqString(this, requestQueue);
        appBarLayout = findViewById(R.id.appBarLayout);
        container_toolbar = findViewById(R.id.container_toolbar);
        rvongkoskirim = findViewById(R.id.rvongkoskirim);
        ongkosKirimModelList = new ArrayList<>();
        kurirOngkosAdapter = new KurirOngkosAdapter(this, ongkosKirimModelList);
        rvongkoskirim.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvongkoskirim.setAdapter(kurirOngkosAdapter);
        favorites = findViewById(R.id.favorites);
        buynow = findViewById(R.id.buynow);
        ongkirtex = findViewById(R.id.ongkir);
        imagesModels = new ArrayList<>();
        productModelNUList = new ArrayList<>();
        productname = findViewById(R.id.productname);
        price = findViewById(R.id.price);
        title = findViewById(R.id.title);
        star = findViewById(R.id.star);
        kondisi = findViewById(R.id.kondisi);
        stok = findViewById(R.id.stok);
        kategori = findViewById(R.id.kategori);
        pengirimanmitra = findViewById(R.id.pengirimanmitra);
        deskripsi = findViewById(R.id.deskripsi);
        pnu = new ProductModelNU();
        namamitra = findViewById(R.id.namamitra);
        cart = findViewById(R.id.cart);
        chat = findViewById(R.id.chat);
        cartimg = findViewById(R.id.cartimg);
        diskonkontainer = findViewById(R.id.diskonkontainer);
        pricediscount = findViewById(R.id.pricediscount);

		ratingfeed = findViewById(R.id.ratingfeed);
        review = findViewById(R.id.review);
        rating = findViewById(R.id.rating);
        productname.setText(produknama);
        price.setText("Rp."+ Others.PercantikHarga(harga));
        title.setText(produknama);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Viewpager Photo Product
        vpphotos = findViewById(R.id.vpphotos);
        adapImages = new AdapImages(getApplicationContext(), imagesModels);
        vpphotos.setAdapter(adapImages);
        wormDotsIndicator = findViewById(R.id.wormDotsIndicator);
        wormDotsIndicator.setViewPager(vpphotos);

        //RecyclerView List of Description Product
        rvdescription = findViewById(R.id.rvdescription);
        rvdescription.setLayoutManager(new LinearLayoutManager(Details.this, LinearLayoutManager.VERTICAL, false));

        rvmore = findViewById(R.id.rvmore);
        product3Adapter = new ProductAdapter(this, productModelNUList);
        rvmore.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1, RecyclerView.HORIZONTAL, false));
        rvmore.setAdapter(product3Adapter);

        buyerFeedbackModelList = new ArrayList<>();
        rvbuyerfeedback = findViewById(R.id.rvbuyerfeedback);
        buyerFeedbackAdapter = new BuyerFeedbackAdapter(this, buyerFeedbackModelList);
        rvbuyerfeedback.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvbuyerfeedback.setAdapter(buyerFeedbackAdapter);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) > 400){
                    container_toolbar.setVisibility(View.VISIBLE);
                } else {
                    container_toolbar.setVisibility(View.INVISIBLE);
                }
            }
        });

        findViewById(R.id.bantuan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Bantuan.class);
                startActivity(i);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ChatRoom.class);
                i.putExtra("id_mitra", idmitra);
                i.putExtra("namamitra", namamitra.getText().toString());
                startActivity(i);
            }
        });
    }

    //NGECEK ONGKIR
    Response.Listener<String> ongkir = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i(TAG, "onResponse ongkir: "+response);
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++){
                    Log.i(TAG, "onResponse: String "+jsonArray.getString(i));
                    if (!jsonArray.getString(i).equals("false")){
                        JSONObject json = jsonArray.getJSONObject(i);
                        JSONArray costs = json.getJSONArray("costs");
                        if (costs.length()!=0){
                            JSONObject costsO = costs.getJSONObject(0);
                            JSONArray cost = costsO.getJSONArray("cost");
                            JSONObject costO = cost.getJSONObject(0);

                            OngkosKirimModel ongkosKirimModel = new OngkosKirimModel();
                            ongkosKirimModel.setKurir(json.getString("name"));
                            ongkosKirimModel.setCode(json.getString("code"));
                            ongkosKirimModel.setValue(costO.getInt("value"));

                            ongkosKirimModelList.add(ongkosKirimModel);
                        }
                    }
                }

                pnu.setOngkir(response);

                kurirOngkosAdapter.notifyDataSetChanged();

                //Bisa diklik ketika sudah ada harga ongkir
                buynow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (new CartDB(getApplicationContext()).get(idproduk).getNama_produk() == null){
                            new CartDB(getApplicationContext()).insert(pnu);
                        }
                        Intent i = new Intent(getApplicationContext(), CartOrder.class);
                        startActivity(i);
                    }
                });

                cart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (new CartDB(getApplicationContext()).get(idproduk).getNama_produk() == null){
                            cartimg.setColorFilter(getResources().getColor(R.color.colorPrimary));
                            new CartDB(getApplicationContext()).insert(pnu);
                        } else {
                            cartimg.setColorFilter(getResources().getColor(R.color.greytranspa));
                            new CartDB(getApplicationContext()).delete(idproduk);
                        }
                    }
                });

                buynow.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
            } catch (JSONException e){
                Log.i(TAG, "onResponse: err "+e.getMessage());
            }
        }
    };

    Response.Listener<String> suksesfeedback = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i(TAG, "onResponse suksesfeedback: "+response);
            try {
                JSONArray jsonArray = new JSONArray(response);
                if (jsonArray.length()>0){
                    findViewById(R.id.feedbackcontainer).setVisibility(View.VISIBLE);
                }
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    BuyerFeedbackModel fm = new BuyerFeedbackModel();
                    fm.setId_feedback(object.getInt(ID_FEEDBACK));
                    fm.setId_produk(object.getInt(ID_PRODUK));
                    fm.setId_transaksi(object.getInt(ID_TRANSAKSI));
                    fm.setKomen(object.getString(KOMEN));
                    fm.setRating(object.getDouble(RATING));
                    fm.setTanggal(object.getString(TANGGAL));
                    JSONObject pembeli = object.getJSONObject("pembeli");
                    fm.setNama(pembeli.getString(NAME));
                    fm.setId_user_pembeli(pembeli.getInt(ID));

                    buyerFeedbackModelList.add(fm);
                }

                buyerFeedbackAdapter.notifyDataSetChanged();
            } catch (JSONException e){
                Log.i(TAG, "onResponse: "+e.getMessage());
            }
        }
    };

    Response.Listener<String> sukseslainnya = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i(TAG, "onResponse sukseslainnya: "+response);
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++){
                    Log.i(TAG, "onResponse: "+i);
                    JSONObject object = jsonArray.getJSONObject(i);
                    ProductModelNU pnu = new ProductModelNU();
                    pnu.setId_produk(object.getString(ID_PRODUK));
                    pnu.setNama_produk(object.getString(NAMA_PRODUK));
                    pnu.setHarga_admin(object.getInt(HARGA_ADMIN));
                    pnu.setHarga_mitra(object.getInt(HARGA_MITRA));
                    pnu.setDeskripsi_produk(object.getString(DESKRIPSI_PRODUK));
                    pnu.setId_sub_kategori(object.getString(ID_SUB_KATEGORI));
                    pnu.setKondisi_produk(object.getString(KONDISI_PRODUK));
                    pnu.setStok(object.getString(STOK));
                    pnu.setTerjual(object.getString(TERJUAL));
                    pnu.setBerat_produk(object.getString(BERAT_PRODUK));
                    pnu.setRating((float)object.getDouble(RATING));
                    pnu.setTotalfeedback(object.getString(TOTALFEEDBACK));
                    pnu.setDikirimdari(object.getString(DIKIRIMDARI));
                    pnu.setDiskon(object.getInt(DISKON));
                    pnu.setDiskonpercent(object.getString(DISKONPERCENT));

                    JSONObject mitrajson = new JSONObject(object.getString("mitra"));
                    UserMitra um = new UserMitra();
                    um.setKabupaten_mitra(mitrajson.getString("kabupaten_mitra"));
                    um.setNama_toko_mitra(mitrajson.getString("nama_toko_mitra"));
                    pnu.setId_mitra(mitrajson.getString("id_mitra"));

                    pnu.setOwner(um);

                    JSONArray jsonArray1 = new JSONArray(object.getJSONArray("gambar").toString());
                    if (jsonArray1.length() >= 1){
                        JSONObject j = jsonArray1.getJSONObject(0);
                        pnu.setGambarfirst(j.getString(URL_GAMBAR));
                    }

                    if(!object.getString(ID_PRODUK).equals(idproduk)){
                        productModelNUList.add(pnu);
                    }
                }

                product3Adapter.notifyDataSetChanged();
            } catch (JSONException e){
                Log.i("Error", "onResponse: Err:"+e.getMessage());
            }
        }
    };

    private Response.Listener<String> suksesproduct = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i(TAG, "onResponse suksesproduct: "+response);
            try {
                JSONObject jsonObject = new JSONObject(response);

                review.setText("  "+jsonObject.getString("totalfeedback")+" Ulasan | "+jsonObject.getString("terjual")+" Terjual");
                rating.setText("("+jsonObject.getString("rating")+")");
                ratingfeed.setText("("+jsonObject.getString("rating")+")");
                star.setRating(Float.valueOf(jsonObject.getString("rating")));
                kondisi.setText(jsonObject.getString("kondisi_produk"));
                stok.setText(jsonObject.getString("stok"));
                pengirimanmitra.setText(jsonObject.getString("dikirimdari"));

                JSONObject kategorijson = new JSONObject(jsonObject.getString("kategori"));
                kategori.setText(kategorijson.getString("nama_kategori"));

                JSONObject subkategorijson = new JSONObject(jsonObject.getString("subkategori"));
                reqString.go(sukseslainnya, PRODUCTSUBKATEGORI+subkategorijson.getString(ID_SUB_KATEGORI));

                JSONObject mitrajson = new JSONObject(jsonObject.getString("mitra"));
                namamitra.setText(mitrajson.getString("nama_toko_mitra"));
                idmitra = mitrajson.getInt("id_mitra");

                Log.i(TAG, "onResponse: "+jsonObject.getInt("diskon"));

                if (jsonObject.getInt("diskon")!=0){
                    findViewById(R.id.diskonkontainer).setVisibility(View.VISIBLE);
                    pricediscount.setText("Rp."+Others.PercantikHarga(harga));
                    price.setText("Rp."+Others.PercantikHarga(harga-jsonObject.getInt("diskon")));
                }

                UserMitra um = new UserMitra();
                um.setKabupaten_mitra(mitrajson.getString("kabupaten_mitra"));
                um.setNama_toko_mitra(mitrajson.getString("nama_toko_mitra"));

                deskripsiarr = jsonObject.getString("deskripsi_produk").split("-");
                descriptionAdapter = new DescriptionAdapter(Details.this, deskripsiarr);
                rvdescription.setAdapter(descriptionAdapter);
                deskripsi.setVisibility(View.GONE);

                JSONArray jsonArray = new JSONArray(jsonObject.getJSONArray("gambar").toString());

                pnu.setGambarfirst(jsonArray.getJSONObject(0).getString(URL_GAMBAR));
                pnu.setId_mitra(mitrajson.getString("id_mitra"));
                pnu.setId_produk(jsonObject.getString(ID_PRODUK));
                pnu.setNama_produk(jsonObject.getString(NAMA_PRODUK));
                pnu.setDeskripsi_produk(jsonObject.getString(DESKRIPSI_PRODUK));
                pnu.setId_sub_kategori(subkategorijson.getString(ID_SUB_KATEGORI));
                pnu.setBerat_produk(jsonObject.getString(BERAT_PRODUK));
                pnu.setKondisi_produk(jsonObject.getString(KONDISI_PRODUK));
                pnu.setTerjual(jsonObject.getString(TERJUAL));
                pnu.setStok(jsonObject.getString(STOK));
                pnu.setHarga_mitra(jsonObject.getInt(HARGA_MITRA));
                pnu.setHarga_admin(jsonObject.getInt(HARGA_ADMIN));
                pnu.setCreated_at(jsonObject.getString(CREATED_AT));
                pnu.setQty(1);
                pnu.setDiskon(jsonObject.getInt(DISKON));
                pnu.setOwner(um);

                for (int g = 0; g < jsonArray.length(); g++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(g);
                    ImagesModel imagesModel = new ImagesModel();
                    imagesModel.setId_gambar(jsonObject1.getString(ID_GAMBAR));
                    imagesModel.setId_produk(jsonObject1.getString(ID_PRODUK));
                    imagesModel.setUrl_gambar(jsonObject1.getString(URL_GAMBAR));
                    imagesModels.add(imagesModel);
                }

                String Url = SLASH+UserPrefs.getId(getApplicationContext())+SLASH+jsonObject.getString("id_produk");
                reqString.go(ongkir, ONGKIR+Url);

                adapImages.notifyDataSetChanged();
            } catch (JSONException e){
                Log.i(TAG, "onResponse: ER"+e.getMessage());
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        if (new CartDB(getApplicationContext()).get(idproduk).getNama_produk() != null){
            cartimg.setColorFilter(getResources().getColor(R.color.colorPrimary));
        }
        super.onResume();
    }
}
