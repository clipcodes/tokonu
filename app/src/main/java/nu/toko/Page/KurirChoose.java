package nu.toko.Page;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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

import javax.xml.transform.Result;

import nu.toko.Adapter.KurirChooseAdapter;
import nu.toko.Model.KurirModel;
import nu.toko.R;
import nu.toko.Reqs.ReqString;

import static nu.toko.Utils.Staticvar.DATAKURIR;
import static nu.toko.Utils.Staticvar.MITRAKURIR;

public class KurirChoose extends AppCompatActivity {

    String TAG = getClass().getSimpleName();
    List<KurirModel> kurirModelList;
    RecyclerView rvkurir;
    KurirChooseAdapter kurirChooseAdapter;
    RequestQueue requestQueue;
    int ONGKIRDET = 993;
    ReqString reqString;
    String kode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_kurirchoose);
        init();

        load(getIntent().getStringExtra("kurir"));

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
                kode = addressModel.getKode();
                Intent i = new Intent(getApplicationContext(), KurirDetChoose.class);
                i.putExtra("pilihdet", addressModel.getArrchoose());
                startActivityForResult(i, ONGKIRDET);
            }
        });
    }

    void load(String data){
        Log.i(TAG, "load: "+data);
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++){
                JSONArray parr = jsonArray.getJSONArray(i);
                Log.i(TAG, "onResponse: String "+parr.getJSONObject(0));
                if (!parr.getString(0).equals("false")){
                    JSONObject object = parr.getJSONObject(0);
                    KurirModel kurirModel = new KurirModel();
                    kurirModel.setKurir(object.getString("name"));
                    kurirModel.setKode(object.getString("code"));
                    JSONArray costs = object.getJSONArray("costs");
                    if (costs.length()!=0){
                        kurirModel.setArrchoose(object.getString("costs"));
                        kurirModelList.add(kurirModel);
                    }
                }
            }

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == ONGKIRDET){
                Intent i = new Intent();
                i.putExtra("kode", kode);
                i.putExtra("kurirdet", data.getStringExtra("kurir"));
                setResult(RESULT_OK, i);
                onBackPressed();
//                alamatpengiriman.setText(data.getStringExtra("prov")+" "+data.getStringExtra("kabkota")+" "+data.getStringExtra("kec")+", "+data.getStringExtra("address"));
//                alamatkirim = data.getStringExtra("prov")+" "+data.getStringExtra("kabkota")+" "+data.getStringExtra("kec")+", "+data.getStringExtra("address");
            }
        }
    }

}
