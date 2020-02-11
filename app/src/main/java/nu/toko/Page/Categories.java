package nu.toko.Page;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nu.toko.Adapter.CategoriesAdapter;
import nu.toko.Adapter.FilterLocationAdapter;
import nu.toko.Adapter.Product2Adapter;
import nu.toko.Model.CategoriesModelNU;
import nu.toko.Model.ProductModelNU;
import nu.toko.Model.UserMitra;
import nu.toko.R;
import nu.toko.Reqs.ReqString;

import static nu.toko.Utils.Staticvar.BERAT_PRODUK;
import static nu.toko.Utils.Staticvar.DESKRIPSI_PRODUK;
import static nu.toko.Utils.Staticvar.DIKIRIMDARI;
import static nu.toko.Utils.Staticvar.FILTERBYLOCATION;
import static nu.toko.Utils.Staticvar.FILTERBYRANGE;
import static nu.toko.Utils.Staticvar.FILTERLOCATION;
import static nu.toko.Utils.Staticvar.HARGA_ADMIN;
import static nu.toko.Utils.Staticvar.HARGA_MITRA;
import static nu.toko.Utils.Staticvar.ID_KATEGORI;
import static nu.toko.Utils.Staticvar.ID_PRODUK;
import static nu.toko.Utils.Staticvar.ID_SUBKATEGORI;
import static nu.toko.Utils.Staticvar.ID_SUB_KATEGORI;
import static nu.toko.Utils.Staticvar.KONDISI_PRODUK;
import static nu.toko.Utils.Staticvar.NAMA_KATEGORI;
import static nu.toko.Utils.Staticvar.NAMA_PRODUK;
import static nu.toko.Utils.Staticvar.PRODUCTKATEGORI;
import static nu.toko.Utils.Staticvar.PRODUCTSUBKATEGORI;
import static nu.toko.Utils.Staticvar.RATING;
import static nu.toko.Utils.Staticvar.SLASH;
import static nu.toko.Utils.Staticvar.STATUSBYPAGE;
import static nu.toko.Utils.Staticvar.STOK;
import static nu.toko.Utils.Staticvar.SUBKATEGORI;
import static nu.toko.Utils.Staticvar.TERJUAL;
import static nu.toko.Utils.Staticvar.TOTALFEEDBACK;
import static nu.toko.Utils.Staticvar.URL_GAMBAR;

public class Categories extends AppCompatActivity {

    String TAG = getClass().getSimpleName();
    RecyclerView rvcategories, rvsubcategories;
    Product2Adapter product2Adapter;
    List<ProductModelNU> productModelList;
    List<CategoriesModelNU> categoriesModelList;
    CategoriesAdapter categoriesAdapter;
    TextView categoriesname;
    String CATEGORY;
    int IDCATEGORY;
    boolean isLoadData = false;
    int PAGE = 1;
    ReqString reqString;
    NestedScrollView nested;
    RequestQueue requestQueue;
//    EditText pencarian;
    TextView listdatatex;
    FrameLayout kembali;
    AVLoadingIndicatorView loadingbot;
    String URLFLEKSIBEL;
    FilterLocationAdapter filterLocationAdapter;
    RecyclerView rvlocation;
    NavigationView nav;
    DrawerLayout drawer;
    EditText minedt, maxedt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categoriesdrawer);

        IDCATEGORY = getIntent().getIntExtra("id", 0);
        CATEGORY = getIntent().getStringExtra("kat");
        init();
        URLFLEKSIBEL = PRODUCTKATEGORI+IDCATEGORY;
        reqString.go(suksesproducthome, URLFLEKSIBEL);
        reqString.go(suksessubkategori, SUBKATEGORI+IDCATEGORY);
        reqString.go(sukseslokasifilter, FILTERLOCATION);
    }

    void init(){
        productModelList = new ArrayList<>();
        rvcategories = findViewById(R.id.rvcategories);
        product2Adapter = new Product2Adapter(this, productModelList);
        rvcategories.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false));
        rvcategories.setAdapter(product2Adapter);
        categoriesname = findViewById(R.id.categoriesname);
        loadingbot = findViewById(R.id.lodingbot);
        listdatatex = findViewById(R.id.listdata);
        kembali = findViewById(R.id.kembali);
//        pencarian = findViewById(R.id.pencarian);
//        pencarian.setHint("Cari "+CATEGORY);
        listdatatex.setText("Semua "+CATEGORY);
        categoriesname.setText("Sub "+CATEGORY);
        nested = findViewById(R.id.nested);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        reqString = new ReqString(this, requestQueue);
        rvlocation = findViewById(R.id.rvlokasi);
        rvlocation.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false));
        drawer = findViewById(R.id.drawer);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.END);
        nav = findViewById(R.id.nav_view);
        findViewById(R.id.filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.END);
            }
        });
        minedt = findViewById(R.id.minedt);
        maxedt = findViewById(R.id.maxedt);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                String minX = minedt.getText().toString();
                String maxX = maxedt.getText().toString();

                if (minX.isEmpty() && maxX.isEmpty()){
                    return;
                }

                if (minX.isEmpty()){
                    minX = "0";
                }
                if (maxX.isEmpty()){
                    maxX = "1000000";
                }

                productModelList.clear();
                product2Adapter.notifyDataSetChanged();
                reqString.go(suksesproducthome, FILTERBYRANGE+minX+SLASH+maxX);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        rvsubcategories = findViewById(R.id.rvsubcategories);
        categoriesModelList = new ArrayList<>();
        categoriesAdapter = new CategoriesAdapter(this, categoriesModelList, 2);
        rvsubcategories.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false));
        rvsubcategories.setAdapter(categoriesAdapter);
        categoriesAdapter.setOnItemClickListener(new CategoriesAdapter.OnClick() {
            @Override
            public void onItemClick(CategoriesModelNU categoriesModelNU) {
                Log.i(TAG, "onItemClick: "+categoriesModelNU.getNama_kategori());
                productModelList.clear();
                PAGE = 1;
                listdatatex.setText(CATEGORY+" "+categoriesModelNU.getNama_kategori());
                product2Adapter.notifyDataSetChanged();
                URLFLEKSIBEL = PRODUCTSUBKATEGORI+categoriesModelNU.getId_sub_kategori();
                reqString.go(suksesproducthome, URLFLEKSIBEL);
            }
        });
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findViewById(R.id.cari).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PagePencarian.class);
                startActivity(i);
            }
        });
        onscroll();
    }

    private void onscroll(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            nested.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if(v.getChildAt(v.getChildCount() - 1) != null) {
                        if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) && scrollY > oldScrollY && isLoadData) {
                            Log.i("CEK", "Scroll Down");
                            isLoadData = false;
                            loadingbot.show();
                            reqString.go(suksesproducthome, URLFLEKSIBEL+STATUSBYPAGE+PAGE);
                        }
                    }
                }
            });
        }
    }

    private Response.Listener<String> suksesproducthome = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i(TAG, "onResponse: "+response);
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++){
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

                    productModelList.add(pnu);
                    product2Adapter.notifyItemInserted(-1);
                }

                isLoadData = true;
                loadingbot.hide();
                PAGE+= 1;
            } catch (JSONException e){
                Log.i("Error", "onResponse: Err:"+e.getMessage());
            }
        }
    };

    private Response.Listener<String> suksessubkategori = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i(TAG, "onResponse: "+response);
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    CategoriesModelNU categoriesModelNU = new CategoriesModelNU();
                    categoriesModelNU.setId_kategori(object.getInt(ID_KATEGORI));
                    categoriesModelNU.setNama_kategori(object.getString(NAMA_KATEGORI));
                    categoriesModelNU.setId_sub_kategori(object.getInt(ID_SUBKATEGORI));

                    categoriesModelList.add(categoriesModelNU);
                }

                Log.i(TAG, "onResponse: productModelList size="+productModelList.size());
                categoriesAdapter.notifyDataSetChanged();
            } catch (JSONException e){
                Log.i(TAG, "onResponse: Err:"+e.getMessage());
            }
        }
    };

    private Response.Listener<String> sukseslokasifilter = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i(TAG, "onResponse lokasi: "+response);
            ArrayList<String> strings = new ArrayList<>();
            ArrayList<String> stringname = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    strings.add(object.getString("kabkot"));
                    stringname.add(object.getString("nama"));
                }
                filterLocationAdapter = new FilterLocationAdapter(Categories.this, strings, stringname);
                rvlocation.setAdapter(filterLocationAdapter);

                filterLocationAdapter.setOnItemClickListener(new FilterLocationAdapter.OnClick() {
                    @Override
                    public void onItemClick(String addressModel) {
                        drawer.closeDrawer(GravityCompat.END);
                        productModelList.clear();
                        product2Adapter.notifyDataSetChanged();
                        reqString.go(suksesproducthome, FILTERBYLOCATION+addressModel);
                    }
                });
            } catch (JSONException e){
                Log.i(TAG, "onResponse: "+e.getMessage());
            }
        }
    };

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.END)){
            drawer.closeDrawer(GravityCompat.END);
        }
        super.onBackPressed();
    }
}
