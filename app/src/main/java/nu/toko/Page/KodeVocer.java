package nu.toko.Page;

import android.content.Intent;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nu.toko.Adapter.KodeVocerAdapter;
import nu.toko.Model.KodeVocerModel;
import nu.toko.R;
import nu.toko.Reqs.ReqString;

import static nu.toko.Utils.Staticvar.KODEVOCER;

public class KodeVocer extends AppCompatActivity {

    String TAG = getClass().getSimpleName();
    KodeVocerAdapter kodeVocerAdapter;
    List<KodeVocerModel> kodeVocerModelList;
    RecyclerView rvvocer;
    ReqString reqString;
    RequestQueue requestQueue;

    @Override
    protected void
    onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_kodevocer);

        init();
        reqString.go(respon, KODEVOCER);

    }

    void init(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        reqString = new ReqString(this, requestQueue);
        kodeVocerModelList = new ArrayList<>();
        kodeVocerAdapter = new KodeVocerAdapter(this, kodeVocerModelList);
        rvvocer = findViewById(R.id.rvvocer);
        rvvocer.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        rvvocer.setAdapter(kodeVocerAdapter);

        kodeVocerAdapter.setOnItemClickListener(new KodeVocerAdapter.OnClick() {
            @Override
            public void onItemClick(KodeVocerModel newsModel) {
                Intent i = new Intent();
                i.putExtra("kode", newsModel.getKode());
                i.putExtra("nominal", newsModel.getNominal());
                i.putExtra("parameter", newsModel.getParameter());
                i.putExtra("keterangan", newsModel.getKeterangan());
                setResult(RESULT_OK, i);
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
                    KodeVocerModel kv = new KodeVocerModel();
                    kv.setId_kode_vocer(object.getInt("id_kode_vocer"));
                    kv.setNominal(object.getInt("nominal"));
                    kv.setParameter(object.getInt("parameter"));
                    kv.setKode(object.getString("kode"));
                    kv.setKeterangan(object.getString("keterangan"));
                    kv.setWaktuakhir(object.getString("waktu_ahir"));
                    kv.setWaktuawal(object.getString("waktu_awal"));

                    final DateFormat nama = new SimpleDateFormat("yyyyMMdd");
                    final Date date = new Date();
                    String[] nomer = object.getString("waktu_ahir").split("-");
                    String oke = nomer[0]+nomer[1]+nomer[2];
                    int exp = Integer.valueOf(oke);
                    int now = Integer.valueOf(nama.format(date));
                    if (now <= exp){
                        kodeVocerModelList.add(kv);
                    }
                }

                if (kodeVocerModelList.size()<=0){
                    findViewById(R.id.nodata).setVisibility(View.GONE);
                }
                kodeVocerAdapter.notifyDataSetChanged();
            } catch (JSONException e){
                Log.i(TAG, "onResponse: ERR "+e.getMessage());
            }
        }
    };
}
