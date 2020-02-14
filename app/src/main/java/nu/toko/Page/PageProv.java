package nu.toko.Page;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

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

import nu.toko.Adapter.AddressAdapter;
import nu.toko.Adapter.ProvAdapter;
import nu.toko.Model.AddressModel;
import nu.toko.Model.ProvModel;
import nu.toko.R;
import nu.toko.Reqs.ReqString;
import nu.toko.Sqlite.AddressDB;

import static nu.toko.Utils.Staticvar.DATPROVINSI;

public class PageProv extends AppCompatActivity {

    RecyclerView rvaddress;
    ProvAdapter provAdapter;
    int NEWALAMAT = 665;
    List<ProvModel> provModelList;
    ReqString reqString;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_prov);

        init();
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        reqString.go(prov, DATPROVINSI);
    }

    void init(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        reqString = new ReqString(this, requestQueue);
        provModelList = new ArrayList<>();
        rvaddress = findViewById(R.id.rv);
        provAdapter = new ProvAdapter(this, provModelList);
        rvaddress.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rvaddress.setAdapter(provAdapter);

        provAdapter.setOnItemClickListener(new ProvAdapter.OnClick() {
            @Override
            public void onItemClick(ProvModel addressModel) {
                Intent i = new Intent();
                i.putExtra("nama", addressModel.getNama_provinsi());
                i.putExtra("id", addressModel.getProvinsi_id());
                setResult(RESULT_OK, i);
                finish();
            }
        });

    }

    private Response.Listener<String> prov = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i("RESPON", response);
            try {
                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++){
                    JSONObject object = array.getJSONObject(i);
                    ProvModel provModel = new ProvModel();
                    provModel.setNama_provinsi(object.getString("nama_provinsi"));
                    provModel.setProvinsi_id(object.getString("provinsi_id"));

                    provModelList.add(provModel);
                }

                provAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                Log.i("RESPON EROR", e.getMessage());
            }
        }
    };
}
