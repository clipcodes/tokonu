package nu.toko.Page;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nu.toko.Adapter.Product2Adapter;
import nu.toko.Model.ProductModelNU;
import nu.toko.Model.UserMitra;
import nu.toko.R;
import nu.toko.Reqs.ReqString;

import static nu.toko.Utils.Staticvar.BERAT_PRODUK;
import static nu.toko.Utils.Staticvar.DESKRIPSI_PRODUK;
import static nu.toko.Utils.Staticvar.DIKIRIMDARI;
import static nu.toko.Utils.Staticvar.DISKON;
import static nu.toko.Utils.Staticvar.DISKONPERCENT;
import static nu.toko.Utils.Staticvar.HARGA_ADMIN;
import static nu.toko.Utils.Staticvar.HARGA_MITRA;
import static nu.toko.Utils.Staticvar.ID_PRODUK;
import static nu.toko.Utils.Staticvar.ID_SUB_KATEGORI;
import static nu.toko.Utils.Staticvar.KONDISI_PRODUK;
import static nu.toko.Utils.Staticvar.NAMA_PRODUK;
import static nu.toko.Utils.Staticvar.PRODUCTHOME;
import static nu.toko.Utils.Staticvar.PRODUCTSEARCH;
import static nu.toko.Utils.Staticvar.RATING;
import static nu.toko.Utils.Staticvar.STATUSBYPAGE;
import static nu.toko.Utils.Staticvar.STOK;
import static nu.toko.Utils.Staticvar.TERJUAL;
import static nu.toko.Utils.Staticvar.TOTALFEEDBACK;
import static nu.toko.Utils.Staticvar.URL_GAMBAR;

public class PagePencarian extends AppCompatActivity {

    String TAG = getClass().getSimpleName();
    FrameLayout terlaris, terbaru, termurah, termahal;
    Product2Adapter product2Adapter;
    List<ProductModelNU> productModelList;
    RecyclerView rvsearch;
    NestedScrollView nested;
    RequestQueue requestQueue;
    String URLFLEKSIBEL;
    String SEARCH;
    ReqString reqString;
    boolean isLoadData = false;
    int PAGE = 1;
    String TYPE;
    AVLoadingIndicatorView loadingbot;
    ImageView go;
    EditText pencarian;
    FrameLayout kembali;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_pencarian);

        init();
        reqString.go(suksesproducthome, PRODUCTHOME);
    }

    void init(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        reqString = new ReqString(this,requestQueue);
        pencarian = findViewById(R.id.pencarian);

        go = findViewById(R.id.go);

        terlaris = findViewById(R.id.terlaris);
        terbaru = findViewById(R.id.terbaru);
        termurah = findViewById(R.id.termurah);
        termahal = findViewById(R.id.termahal);
        nested = findViewById(R.id.nested);

        terlaris.setOnClickListener(new klik());
        terbaru.setOnClickListener(new klik());
        termurah.setOnClickListener(new klik());
        termahal.setOnClickListener(new klik());

        kembali = findViewById(R.id.kembali);
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        productModelList = new ArrayList<>();
        rvsearch = findViewById(R.id.rvsearch);
        product2Adapter = new Product2Adapter(this, productModelList);
        rvsearch.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL, false));
        rvsearch.setAdapter(product2Adapter);
        loadingbot = findViewById(R.id.lodingbot);
        loadingbot.hide();

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
        pencarian.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                search();
                return false;
            }
        });
        onscroll();
    }

    void search(){
        SEARCH = pencarian.getText().toString();
        if (SEARCH.isEmpty()){
            Toast.makeText(PagePencarian.this, "Pencarian Kosong", Toast.LENGTH_SHORT).show();
            return;
        }
        productModelList.clear();
        product2Adapter.notifyDataSetChanged();
        findViewById(R.id.interbaru).setBackgroundColor(getApplicationContext().getResources().getColor(R.color.greytranspa2));
        findViewById(R.id.interlaris).setBackgroundColor(getApplicationContext().getResources().getColor(R.color.colorPrimary));
        findViewById(R.id.intermahal).setBackgroundColor(getApplicationContext().getResources().getColor(R.color.greytranspa2));
        findViewById(R.id.intermurah).setBackgroundColor(getApplicationContext().getResources().getColor(R.color.greytranspa2));
        TYPE = "/terlaris";
        PAGE = 1;
        URLFLEKSIBEL = PRODUCTSEARCH+SEARCH+TYPE;
        loadingbot.show();
        reqString.go(suksesproducthome, URLFLEKSIBEL);
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
                            reqString.go(suksesproducthome, PRODUCTSEARCH+SEARCH+TYPE+STATUSBYPAGE+PAGE);
                        }
                    }
                }
            });
        }
    }

    private class klik implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            SEARCH = pencarian.getText().toString();
            if (SEARCH.isEmpty()){
                Toast.makeText(PagePencarian.this, "Pencarian Kosong", Toast.LENGTH_SHORT).show();
                return;
            }
            productModelList.clear();
            product2Adapter.notifyDataSetChanged();
            switch (v.getId()){
                case R.id.terlaris:
                    findViewById(R.id.interbaru).setBackgroundColor(getApplicationContext().getResources().getColor(R.color.greytranspa2));
                    findViewById(R.id.interlaris).setBackgroundColor(getApplicationContext().getResources().getColor(R.color.colorPrimary));
                    findViewById(R.id.intermahal).setBackgroundColor(getApplicationContext().getResources().getColor(R.color.greytranspa2));
                    findViewById(R.id.intermurah).setBackgroundColor(getApplicationContext().getResources().getColor(R.color.greytranspa2));
                    TYPE = "/terlaris";
                    PAGE = 1;
                    URLFLEKSIBEL = PRODUCTSEARCH+SEARCH+TYPE;
                    break;
                case R.id.terbaru:
                    findViewById(R.id.interbaru).setBackgroundColor(getApplicationContext().getResources().getColor(R.color.colorPrimary));
                    findViewById(R.id.interlaris).setBackgroundColor(getApplicationContext().getResources().getColor(R.color.greytranspa2));
                    findViewById(R.id.intermahal).setBackgroundColor(getApplicationContext().getResources().getColor(R.color.greytranspa2));
                    findViewById(R.id.intermurah).setBackgroundColor(getApplicationContext().getResources().getColor(R.color.greytranspa2));
                    TYPE = "/terbaru";
                    PAGE = 1;
                    URLFLEKSIBEL = PRODUCTSEARCH+SEARCH+TYPE;
                    break;
                case R.id.termurah:
                    findViewById(R.id.interbaru).setBackgroundColor(getApplicationContext().getResources().getColor(R.color.greytranspa2));
                    findViewById(R.id.interlaris).setBackgroundColor(getApplicationContext().getResources().getColor(R.color.greytranspa2));
                    findViewById(R.id.intermahal).setBackgroundColor(getApplicationContext().getResources().getColor(R.color.greytranspa2));
                    findViewById(R.id.intermurah).setBackgroundColor(getApplicationContext().getResources().getColor(R.color.colorPrimary));
                    TYPE = "/termurah";
                    PAGE = 1;
                    URLFLEKSIBEL = PRODUCTSEARCH+SEARCH+TYPE;
                    break;
                case R.id.termahal:
                    findViewById(R.id.interbaru).setBackgroundColor(getApplicationContext().getResources().getColor(R.color.greytranspa2));
                    findViewById(R.id.interlaris).setBackgroundColor(getApplicationContext().getResources().getColor(R.color.greytranspa2));
                    findViewById(R.id.intermahal).setBackgroundColor(getApplicationContext().getResources().getColor(R.color.colorPrimary));
                    findViewById(R.id.intermurah).setBackgroundColor(getApplicationContext().getResources().getColor(R.color.greytranspa2));
                    TYPE = "/termahal";
                    PAGE = 1;
                    URLFLEKSIBEL = PRODUCTSEARCH+SEARCH+TYPE;
                    break;
            }
            loadingbot.show();
            reqString.go(suksescari, URLFLEKSIBEL);
        }
    }

    private Response.Listener<String> suksesproducthome = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i(TAG, "onResponse: "+response);
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
                    pnu.setKategorimitra(mitrajson.getString("kategori"));
                    pnu.setId_mitra(mitrajson.getString("id_mitra"));

                    pnu.setOwner(um);

                    JSONArray jsonArray1 = new JSONArray(object.getJSONArray("gambar").toString());
                    if (jsonArray1.length() >= 1){
                        JSONObject j = jsonArray1.getJSONObject(0);
                        pnu.setGambarfirst(j.getString(URL_GAMBAR));
                    }

                    productModelList.add(pnu);
                    product2Adapter.notifyItemInserted(productModelList.size());
                    rvsearch.scrollToPosition(productModelList.size());
                }

                if (jsonArray.length()<=0){
                    isLoadData = false;
                } else {
                    isLoadData = true;
                }

                loadingbot.setVisibility(View.GONE);
                PAGE+= 1;
            } catch (JSONException e){
                Log.i("Error", "onResponse: Err:"+e.getMessage());
            }
        }
    };


    private Response.Listener<String> suksescari = new Response.Listener<String>() {
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

}
