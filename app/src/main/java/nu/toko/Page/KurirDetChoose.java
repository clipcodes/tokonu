package nu.toko.Page;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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

import nu.toko.Adapter.KurirChooseAdapter;
import nu.toko.Model.KurirModel;
import nu.toko.R;
import nu.toko.Reqs.ReqString;

import static nu.toko.Utils.Staticvar.DATAKURIR;

public class KurirDetChoose extends AppCompatActivity {

    String TAG = getClass().getSimpleName();
    List<KurirModel> kurirModelList;
    RecyclerView rvkurir;
    KurirChooseAdapter kurirChooseAdapter;
    RequestQueue requestQueue;
    ReqString reqString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_kurirchoose);

        init();

        load(getIntent().getStringExtra("pilihdet"));

//        reqString.go(respon, DATAKURIR);

    }

    void init(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        reqString = new ReqString(this, requestQueue);
        rvkurir = findViewById(R.id.rvkurir);
        kurirModelList = new ArrayList<>();
        kurirChooseAdapter = new KurirChooseAdapter(this, kurirModelList);
        rvkurir.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvkurir.setAdapter(kurirChooseAdapter);

        kurirChooseAdapter.setOnItemClickListener(new KurirChooseAdapter.OnClick() {
            @Override
            public void onItemClick(KurirModel addressModel) {
                Intent i = new Intent();
                i.putExtra("kurir", addressModel.getKurir());
                setResult(RESULT_OK, i);
                onBackPressed();
            }
        });
    }

    void load(String data){
        Log.i(TAG, "load: "+data);
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject costsO = jsonArray.getJSONObject(i);
                KurirModel kurirModel = new KurirModel();
                kurirModel.setKurir(costsO.getString("service"));
                kurirModelList.add(kurirModel);
            }

            kurirChooseAdapter.notifyDataSetChanged();
        } catch (JSONException e){
            Log.i(TAG, "onResponse: err "+e.getMessage());
        }
    }

    Response.Listener<String> respon = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i(TAG, "onResponse: "+response);
            try {
                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++){
                    JSONObject object = array.getJSONObject(i);
                    KurirModel kurirModel = new KurirModel();
                    kurirModel.setKurir(object.getString("nama_kurir"));
                    kurirModel.setKode(object.getString("kode"));
                    kurirModelList.add(kurirModel);
                }

                kurirChooseAdapter.notifyDataSetChanged();
            } catch (JSONException e){
                Log.i(TAG, "onResponse: "+e.getMessage());
            }
        }
    };
}
