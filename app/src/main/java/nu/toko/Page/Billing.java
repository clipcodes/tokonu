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

import nu.toko.Adapter.GroupBillingAdapter;
import nu.toko.MainActivity;
import nu.toko.Model.BillingItemModel;
import nu.toko.Model.BillingModelNU;
import nu.toko.Model.ProductModelNU;
import nu.toko.R;
import nu.toko.Reqs.ReqString;
import nu.toko.Utils.Others;

import static nu.toko.Utils.Staticvar.GAMBARFIRST;
import static nu.toko.Utils.Staticvar.HARGA_ONGKIR;
import static nu.toko.Utils.Staticvar.HARGA_TOTAL;
import static nu.toko.Utils.Staticvar.ID_MITRA;
import static nu.toko.Utils.Staticvar.ID_PEMBELI;
import static nu.toko.Utils.Staticvar.ID_PRODUK;
import static nu.toko.Utils.Staticvar.ID_TRANSAKSI;
import static nu.toko.Utils.Staticvar.NAMA_PRODUK;
import static nu.toko.Utils.Staticvar.RESI;
import static nu.toko.Utils.Staticvar.STATUS_TRANSAKSI;
import static nu.toko.Utils.Staticvar.SUB_TOTAL;
import static nu.toko.Utils.Staticvar.TGL_PEMESANAN;
import static nu.toko.Utils.Staticvar.TRANSAKSIBAYAR;
import static nu.toko.Utils.Staticvar.TRANSAKSIPEMBELI;

public class Billing extends AppCompatActivity {

    String TAG = getClass().getSimpleName();
    RecyclerView rvbill;
    GroupBillingAdapter groupBillingAdapter;
    List<BillingModelNU> billingModels;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_billingtol);

        Others.MahathirOptionGambar(getApplicationContext());
        init();
    }

    void init(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        billingModels = new ArrayList<>();
        rvbill = findViewById(R.id.rvbill);
        groupBillingAdapter = new GroupBillingAdapter(this, billingModels);
        rvbill.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        rvbill.setAdapter(groupBillingAdapter);
        groupBillingAdapter.setOnItemClickListener(new GroupBillingAdapter.OnClick() {
            @Override
            public void onItemClick(BillingModelNU billingModelNU) {
                Intent i = new Intent(getApplicationContext(), PagePay.class);
                i.putExtra(ID_TRANSAKSI, billingModelNU.getId_transaksi());
                startActivity(i);
            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        billingModels.clear();
        new ReqString(this, requestQueue).go(respontrans, TRANSAKSIBAYAR+"1");
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private Response.Listener<String> respontrans = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    BillingModelNU bill = new BillingModelNU();
                    bill.setId_mitra(jsonObject.getInt(ID_MITRA));
                    bill.setId_pembeli(jsonObject.getInt(ID_PEMBELI));
                    bill.setSub_total(jsonObject.getInt(SUB_TOTAL));
                    bill.setHarga_ongkir(jsonObject.getInt(HARGA_ONGKIR));
                    bill.setHarga_total(jsonObject.getInt(HARGA_TOTAL));
                    bill.setId_transaksi(jsonObject.getInt(ID_TRANSAKSI));
                    bill.setTgl_pemesanan(jsonObject.getString(TGL_PEMESANAN));
                    bill.setStatus_transaksi(jsonObject.getInt(STATUS_TRANSAKSI));
                    bill.setResi(jsonObject.getString(RESI));

                    //Transaksi Item
                    JSONArray item = new JSONArray(jsonObject.getString("item"));
                    List<BillingItemModel> billingItemModels = new ArrayList<>();
                    for (int p = 0; p < item.length(); p++){
                        JSONObject objekitem = item.getJSONObject(p);
                        BillingItemModel billitem = new BillingItemModel();
                        billitem.setId_transaksi(objekitem.getString("id_transaksi"));
                        billitem.setId_transaksi_item(objekitem.getString("id_transaksi_item"));
                        billitem.setQty(objekitem.getInt("qty"));

                        //Produk
                        JSONObject jsonproduk = new JSONObject(objekitem.getString("produk"));
                        ProductModelNU produk = new ProductModelNU();
                        produk.setId_produk(jsonproduk.getString(ID_PRODUK));
                        produk.setNama_produk(jsonproduk.getString(NAMA_PRODUK));
                        produk.setGambarfirst(jsonproduk.getString(GAMBARFIRST));

                        billitem.setProduk(produk);

                        billingItemModels.add(billitem);
                    }

                    bill.setBillingItemModels(billingItemModels);

                    billingModels.add(bill);
                }
                groupBillingAdapter.notifyDataSetChanged();
            } catch (JSONException e){
                Log.i(TAG, "onResponse: Err "+e.getMessage());
            }
        }
    };
}
