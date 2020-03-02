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

import nu.toko.Adapter.KoinNuAdapter;
import nu.toko.Model.KoinNuModel;
import nu.toko.R;
import nu.toko.Reqs.ReqString;
import nu.toko.Utils.UserPrefs;

import static nu.toko.Utils.Staticvar.KOINNU;

public class Page_KoinNu extends AppCompatActivity {

    String TAG = getClass().getSimpleName();
    RecyclerView rvkoinnu;
    KoinNuAdapter koinNuAdapter;
    List<KoinNuModel> koinNuModelList;
    RequestQueue requestQueue;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_koinnudonais);

        init();

        new ReqString(this, requestQueue).go(respon, KOINNU+UserPrefs.getId(getApplicationContext()));
    }

    void init(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        rvkoinnu = findViewById(R.id.rvkoinnu);
        koinNuModelList = new ArrayList<>();
        koinNuAdapter = new KoinNuAdapter(this, koinNuModelList);
        rvkoinnu.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        rvkoinnu.setAdapter(koinNuAdapter);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    Response.Listener<String> respon = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i(TAG, "onResponse: "+response);
            try {
                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++){
                    JSONObject object = array.getJSONObject(i);
                    KoinNuModel km = new KoinNuModel();
                    km.setNominal(object.getInt("donasi_koin"));
                    if (!object.getString("trans").equals("null")){
                        JSONObject trans = object.getJSONObject("trans");
                        km.setTanggal(trans.getString("tgl_pemesanan"));
                    }

                    koinNuModelList.add(km);
                }

                if (array.length()<=0){
                    findViewById(R.id.nodata).setVisibility(View.VISIBLE);
                }
                koinNuAdapter.notifyDataSetChanged();
            } catch (JSONException e){
                Log.i(TAG, "onResponse: Err "+e.getMessage());
            }
        }
    };
}
