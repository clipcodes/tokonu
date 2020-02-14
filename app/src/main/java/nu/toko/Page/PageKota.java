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

import java.util.ArrayList;
import java.util.List;

import nu.toko.Adapter.KotaAdapter;
import nu.toko.Model.KotaModel;
import nu.toko.R;
import nu.toko.Reqs.ReqString;

import static nu.toko.Utils.Staticvar.DATKOTA;
import static nu.toko.Utils.Staticvar.DATPROVINSI;

public class PageKota extends AppCompatActivity {

    RecyclerView rvaddress;
    KotaAdapter provAdapter;
    int NEWALAMAT = 665;
    List<KotaModel> kotaModelList;
    ReqString reqString;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_prov);

        String idprov = getIntent().getStringExtra("idprov");
        init();
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        reqString.go(kota, DATKOTA+idprov);
    }

    void init(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        reqString = new ReqString(this, requestQueue);
        kotaModelList = new ArrayList<>();
        rvaddress = findViewById(R.id.rv);
        provAdapter = new KotaAdapter(this, kotaModelList);
        rvaddress.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rvaddress.setAdapter(provAdapter);

        provAdapter.setOnItemClickListener(new KotaAdapter.OnClick() {
            @Override
            public void onItemClick(KotaModel addressModel) {
                Intent i = new Intent();
                i.putExtra("nama", addressModel.getNama_kota());
                i.putExtra("id", addressModel.getKota_id());
                setResult(RESULT_OK, i);
                finish();
            }
        });

    }

    private Response.Listener<String> kota = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i("RESPON", response);
            try {
                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++){
                    JSONObject object = array.getJSONObject(i);
                    KotaModel kotaModel = new KotaModel();
                    kotaModel.setNama_kota(object.getString("nama_kota"));
                    kotaModel.setProvinsi_id(object.getString("provinsi_id"));
                    kotaModel.setKota_id(object.getString("kota_id"));

                    kotaModelList.add(kotaModel);
                }

                provAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                Log.i("RESPON EROR", e.getMessage());
            }
        }
    };
}
