package nu.toko.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nu.toko.Adapter.CategoriesAdapter;
import nu.toko.Adapter.Product2Adapter;
import nu.toko.Adapter.PromoAdapter;
import nu.toko.Model.CategoriesModelNU;
import nu.toko.Model.ProductModelNU;
import nu.toko.Model.SlideModel;
import nu.toko.Model.UserMitra;
import nu.toko.Page.CartOrder;
import nu.toko.Page.Chats;
import nu.toko.Page.Donasi;
import nu.toko.Page.PagePencarian;
import nu.toko.R;
import nu.toko.Reqs.ReqString;
import nu.toko.Utils.Others;

import static nu.toko.Utils.Staticvar.BERAT_PRODUK;
import static nu.toko.Utils.Staticvar.DESKRIPSI_PRODUK;
import static nu.toko.Utils.Staticvar.DIKIRIMDARI;
import static nu.toko.Utils.Staticvar.HARGA_ADMIN;
import static nu.toko.Utils.Staticvar.HARGA_MITRA;
import static nu.toko.Utils.Staticvar.ID_KATEGORI;
import static nu.toko.Utils.Staticvar.ID_PRODUK;
import static nu.toko.Utils.Staticvar.ID_SLIDE;
import static nu.toko.Utils.Staticvar.ID_SUB_KATEGORI;
import static nu.toko.Utils.Staticvar.KATEGORI;
import static nu.toko.Utils.Staticvar.KONDISI_PRODUK;
import static nu.toko.Utils.Staticvar.NAMA_KATEGORI;
import static nu.toko.Utils.Staticvar.NAMA_PRODUK;
import static nu.toko.Utils.Staticvar.PRODUCTHOME;
import static nu.toko.Utils.Staticvar.RATING;
import static nu.toko.Utils.Staticvar.SLIDE;
import static nu.toko.Utils.Staticvar.STATUSBYPAGE;
import static nu.toko.Utils.Staticvar.STOK;
import static nu.toko.Utils.Staticvar.TERJUAL;
import static nu.toko.Utils.Staticvar.TOTALFEEDBACK;
import static nu.toko.Utils.Staticvar.URL_GAMBAR;
import static nu.toko.Utils.Staticvar.URL_GAMBAR_KATEGORI;
import static nu.toko.Utils.Staticvar.URL_SLIDE;

public class Home extends Fragment {

    String TAG = getClass().getSimpleName();
    Product2Adapter product2Adapter;
    List<CategoriesModelNU> categoriesModelList;
    List<ProductModelNU> productModelList;
    RecyclerView rvgroup, rvcategories;
    ViewPager vppromo;
    PromoAdapter promoAdapter;
    WormDotsIndicator wormDotsIndicator;
    CategoriesAdapter categoriesAdapter;
    boolean isLoadData = false;
    int PAGE = 1;
    NestedScrollView nested;
    AVLoadingIndicatorView loadingbot;
    ReqString reqString;
    RequestQueue requestQueue;
    CardView cari, donasi;
    FrameLayout cart, chat;
    List<SlideModel> slideModelList;
    SwipeRefreshLayout refresh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.page_home, container, false);

        Others.MahathirOptionGambar(getActivity());
        init(root);

        return root;
    }

    void init(View root){
        requestQueue = Volley.newRequestQueue(getActivity());
        rvgroup = root.findViewById(R.id.rvhome);
        slideModelList = new ArrayList<>();
        categoriesModelList = new ArrayList<>();
        productModelList = new ArrayList<>();
        product2Adapter = new Product2Adapter(getActivity(), productModelList);
        rvgroup.setLayoutManager(new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false));
        rvgroup.setAdapter(product2Adapter);
        reqString = new ReqString(getActivity(), requestQueue);
        nested = root.findViewById(R.id.nested);
        vppromo = root.findViewById(R.id.vppromo);
        donasi = root.findViewById(R.id.donasi);
        promoAdapter = new PromoAdapter(getActivity(), slideModelList);

        cart = root.findViewById(R.id.cart);
        chat = root.findViewById(R.id.chat);
        cari = root.findViewById(R.id.cari);

        wormDotsIndicator = root.findViewById(R.id.wormDotsIndicator);

        rvcategories = root.findViewById(R.id.rvcategories);
        categoriesAdapter = new CategoriesAdapter(getActivity(), categoriesModelList, 1);
        rvcategories.setLayoutManager(new GridLayoutManager(getActivity(), 4, RecyclerView.VERTICAL, false));
        rvcategories.setAdapter(categoriesAdapter);

        loadingbot = root.findViewById(R.id.lodingbot);

        refresh = root.findViewById(R.id.refresh);

        reqString.go(suksesslide, SLIDE);
        reqString.go(sukseskategori, KATEGORI);
        reqString.go(suksesproducthome, PRODUCTHOME);
        onscroll();

        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), PagePencarian.class);
                startActivity(i);
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CartOrder.class);
                startActivity(i);
            }
        });
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Chats.class);
                startActivity(i);
            }
        });
        donasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Donasi.class);
                startActivity(i);
            }
        });
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productModelList.clear();
                product2Adapter.notifyDataSetChanged();
                PAGE = 1;
                reqString.go(suksesproducthome, PRODUCTHOME);
            }
        });

    }

    private void onscroll(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            nested.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if(v.getChildAt(v.getChildCount() - 1) != null) {
                        if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) && scrollY > oldScrollY && isLoadData) {
                            loadingbot.setVisibility(View.VISIBLE);
                            isLoadData = false;
                            reqString.go(suksesproducthome, PRODUCTHOME+STATUSBYPAGE+PAGE);
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
                    product2Adapter.notifyItemInserted(productModelList.size());
                    rvgroup.scrollToPosition(productModelList.size());
                }

                if (jsonArray.length()<=0){
                    isLoadData = false;
                } else {
                    isLoadData = true;
                }
                if (PAGE==1){
                    refresh.setRefreshing(false);
                }
                loadingbot.setVisibility(View.GONE);
                PAGE+= 1;
            } catch (JSONException e){
                Log.i("Error", "onResponse: Err:"+e.getMessage());
            }
        }
    };

    private Response.Listener<String> sukseskategori = new Response.Listener<String>() {
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
                    categoriesModelNU.setUrl_gambar_kategori(object.getString(URL_GAMBAR_KATEGORI));

                    categoriesModelList.add(categoriesModelNU);
                    categoriesAdapter.notifyItemInserted(-1);
                }

            } catch (JSONException e){
                Log.i(TAG, "onResponse: Err:"+e.getMessage());
            }
        }
    };

    private Response.Listener<String> suksesslide = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i(TAG, "onResponse: "+response);
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    SlideModel slideModel = new SlideModel();
                    slideModel.setId_slide(object.getInt(ID_SLIDE));
                    slideModel.setUrl_slide(object.getString(URL_SLIDE));

                    slideModelList.add(slideModel);
                }

                vppromo.setAdapter(promoAdapter);
                wormDotsIndicator.setViewPager(vppromo);
            } catch (JSONException e){
                Log.i(TAG, "onResponse: Err:"+e.getMessage());
            }
        }
    };

}