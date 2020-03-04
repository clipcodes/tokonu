package nu.toko.Page;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nu.toko.Adapter.AllCategoriesAdapter;
import nu.toko.Model.CategoriesModelNU;
import nu.toko.R;
import nu.toko.Reqs.ReqString;

import static nu.toko.Utils.Staticvar.ID_KATEGORI;
import static nu.toko.Utils.Staticvar.KATEGORI;
import static nu.toko.Utils.Staticvar.NAMA_KATEGORI;
import static nu.toko.Utils.Staticvar.URL_GAMBAR_KATEGORI;

public class AllCategories extends AppCompatActivity {

    String TAG = getClass().getSimpleName();
    RequestQueue requestQueue;
    List<CategoriesModelNU> categoriesModelList;
    ReqString reqString;
    RecyclerView rvcategories;
    AllCategoriesAdapter allCategoriesAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_allcategories);

        init();
        reqString.go(sukseskategori, KATEGORI);

    }

    void init(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        reqString = new ReqString(this, requestQueue);
        categoriesModelList = new ArrayList<>();
        allCategoriesAdapter = new AllCategoriesAdapter(this, categoriesModelList);
        rvcategories = findViewById(R.id.rvcategories);
        rvcategories.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        rvcategories.setAdapter(allCategoriesAdapter);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

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
                    allCategoriesAdapter.notifyItemInserted(-1);

                }

            } catch (JSONException e){
                Log.i(TAG, "onResponse: Err:"+e.getMessage());
            }
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
