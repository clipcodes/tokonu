package nu.toko.Page;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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

import nu.toko.Adapter.PayListAdapter;
import nu.toko.Dialog.DialogInfo;
import nu.toko.Model.BillingItemModel;
import nu.toko.Model.BillingModelNU;
import nu.toko.Model.ProductModelNU;
import nu.toko.R;
import nu.toko.Reqs.ReqString;
import nu.toko.Utils.Others;
import nu.toko.Utils.UserPrefs;

import static nu.toko.Utils.Staticvar.ALAMAT_KIRIM;
import static nu.toko.Utils.Staticvar.FEEDADDED;
import static nu.toko.Utils.Staticvar.FEEDBACK;
import static nu.toko.Utils.Staticvar.GAMBARFIRST;
import static nu.toko.Utils.Staticvar.HARGA_ADMIN;
import static nu.toko.Utils.Staticvar.HARGA_MITRA;
import static nu.toko.Utils.Staticvar.HARGA_ONGKIR;
import static nu.toko.Utils.Staticvar.HARGA_TOTAL;
import static nu.toko.Utils.Staticvar.ID_MITRA;
import static nu.toko.Utils.Staticvar.ID_PEMBELI;
import static nu.toko.Utils.Staticvar.ID_PRODUK;
import static nu.toko.Utils.Staticvar.ID_TRANSAKSI;
import static nu.toko.Utils.Staticvar.ID_TRANSAKSI_ITEM;
import static nu.toko.Utils.Staticvar.ITEM;
import static nu.toko.Utils.Staticvar.KURIR;
import static nu.toko.Utils.Staticvar.NAMA_PRODUK;
import static nu.toko.Utils.Staticvar.PRODUK;
import static nu.toko.Utils.Staticvar.QTY;
import static nu.toko.Utils.Staticvar.RESI;
import static nu.toko.Utils.Staticvar.STATUS_TRANSAKSI;
import static nu.toko.Utils.Staticvar.SUB_TOTAL;
import static nu.toko.Utils.Staticvar.TGL_PEMESANAN;
import static nu.toko.Utils.Staticvar.TRANSAKSIDETAIL;
import static nu.toko.Utils.Staticvar.TRANSAKSIDITERIMA;

public class PageOrders  extends AppCompatActivity {

    TextView alamat, subtotal, ongkir, total, kurir;
    String TAG = getClass().getSimpleName();
    List<BillingItemModel> billingItemModels;
    PayListAdapter billadap;
    RecyclerView rvpay;
    RequestQueue requestQueue;
    TextView paytex;
    CardView diterima;
    int status, idtrans = 0;
    RatingBar star;
    EditText ulasan;
    JSONObject j;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_orders);

        Others.MahathirOptionGambar(getApplicationContext());
        idtrans = getIntent().getIntExtra(ID_TRANSAKSI, 0);
        status = getIntent().getIntExtra(STATUS_TRANSAKSI, 0);

        init();
        new ReqString(this, requestQueue).go(respon, TRANSAKSIDETAIL+idtrans);
    }

    void init(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        rvpay = findViewById(R.id.rvorders);
        billingItemModels = new ArrayList<>();
        billadap = new PayListAdapter(this, billingItemModels);
        rvpay.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvpay.setAdapter(billadap);
        diterima = findViewById(R.id.diterima);
        paytex = findViewById(R.id.paytex);
        star = findViewById(R.id.star);
        ulasan = findViewById(R.id.ulasan);

        alamat = findViewById(R.id.alamat);
        subtotal = findViewById(R.id.subtotal);
        ongkir = findViewById(R.id.ongkir);
        total = findViewById(R.id.total);
        kurir = findViewById(R.id.kurir);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Log.i(TAG, "init: "+status);
        if (status == 1){
            diterima.setCardBackgroundColor(getResources().getColor(R.color.white));
            paytex.setText("Menunggu Konfirmasi");
            paytex.setTextColor(getResources().getColor(R.color.textcolor2));
            diterima.setOnClickListener(null);
            return;
        }
        if (status == 2){
            diterima.setCardBackgroundColor(getResources().getColor(R.color.white));
            paytex.setText("Barang Anda Dikemas");
            paytex.setTextColor(getResources().getColor(R.color.textcolor2));
            diterima.setOnClickListener(null);
            return;
        }
        if (status == 4){
            diterima.setCardBackgroundColor(getResources().getColor(R.color.white));
            paytex.setText("Pesanan Selesai");
            paytex.setTextColor(getResources().getColor(R.color.textcolor2));
            diterima.setOnClickListener(null);
            return;
        }
        if (status == 5){
            diterima.setCardBackgroundColor(getResources().getColor(R.color.white));
            paytex.setText("Pesanan Ditolak");
            diterima.setOnClickListener(null);
            return;
        }
        diterima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogInfo(PageOrders.this, "Pastikan Anda Menekan Tombol Ini Setelah Barang Sudah Anda Terima.").mentriger(new DialogInfo.Go() {
                    @Override
                    public void trigerbos() {
                        diterima.setCardBackgroundColor(getResources().getColor(R.color.white));
                        findViewById(R.id.progres).setVisibility(View.VISIBLE);
                        new ReqString(PageOrders.this, requestQueue).go(respontrans, TRANSAKSIDITERIMA+idtrans);
                    }
                });
            }
        });
    }

    Response.Listener<String> respontrans = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i(TAG, "onResponse: " + response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("msg").equals("diterima")) {
                    addulasan();
                }
            } catch (JSONException e) {
                Log.i(TAG, "extracdata: Err " + e.getMessage());
            }
        }
    };

    Response.Listener<String> responulasan = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i(TAG, "onResponse: " + response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("msg").equals("selesai")) {
                    diterima.setVisibility(View.GONE);
                    findViewById(R.id.progres).setVisibility(View.GONE);
                    findViewById(R.id.containerulasan).setVisibility(View.GONE);
                    findViewById(R.id.terimakasih).setVisibility(View.VISIBLE);
                    diterima.setOnClickListener(null);
                }
            } catch (JSONException e) {
                Log.i(TAG, "extracdata: Err " + e.getMessage());
            }
        }
    };

    Response.Listener<String> respon = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i(TAG, "onResponse: "+response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                BillingModelNU bill = new BillingModelNU();
                bill.setId_transaksi(jsonObject.getInt(ID_TRANSAKSI));
                bill.setId_pembeli(jsonObject.getInt(ID_PEMBELI));
                bill.setSub_total(jsonObject.getInt(SUB_TOTAL));
                bill.setHarga_ongkir(jsonObject.getInt(HARGA_ONGKIR));
                bill.setHarga_total(jsonObject.getInt(HARGA_TOTAL));
                bill.setTgl_pemesanan(jsonObject.getString(TGL_PEMESANAN));
                bill.setResi(jsonObject.getString(RESI));
                bill.setAlamat_kirim(jsonObject.getString(ALAMAT_KIRIM));
                bill.setKurir(jsonObject.getString(KURIR));

                JSONObject mitra = jsonObject.getJSONObject("mitra");
                bill.setId_mitra(mitra.getInt(ID_MITRA));

                if (status == 4 && !jsonObject.getBoolean(FEEDADDED)){
                    addulasan();
                }

                alamat.setText(jsonObject.getString(ALAMAT_KIRIM));
                kurir.setText(jsonObject.getString(KURIR));
                subtotal.setText("Rp."+ Others.PercantikHarga(jsonObject.getInt(SUB_TOTAL)));
                ongkir.setText("Rp."+Others.PercantikHarga(jsonObject.getInt(HARGA_ONGKIR)));
                total.setText("Rp."+Others.PercantikHarga(jsonObject.getInt(HARGA_TOTAL)));

                //Transaksi Item
                JSONArray item = new JSONArray(jsonObject.getString(ITEM));
                for (int p = 0; p < item.length(); p++){
                    Log.i(TAG, "onResponse: XXX");

                    JSONObject objekitem = item.getJSONObject(p);
                    BillingItemModel billitem = new BillingItemModel();
                    billitem.setId_transaksi(objekitem.getString(ID_TRANSAKSI));
                    billitem.setId_transaksi_item(objekitem.getString(ID_TRANSAKSI_ITEM));
                    billitem.setQty(objekitem.getInt(QTY));

                    Log.i(TAG, "onResponse: COK");

                    //Produk
                    JSONObject jsonproduk = new JSONObject(objekitem.getString(PRODUK));
                    ProductModelNU produk = new ProductModelNU();
                    produk.setId_produk(jsonproduk.getString(ID_PRODUK));
                    produk.setNama_produk(jsonproduk.getString(NAMA_PRODUK));
                    produk.setGambarfirst(jsonproduk.getString(GAMBARFIRST));
                    produk.setHarga_admin(jsonproduk.getInt(HARGA_ADMIN));
                    produk.setHarga_mitra(jsonproduk.getInt(HARGA_MITRA));

                    Log.i(TAG, "onResponse: RES");

                    billitem.setProduk(produk);

                    billingItemModels.add(billitem);
                }

                billadap.notifyDataSetChanged();
            } catch (JSONException e){
                Log.i(TAG, "extracdata: Err "+e.getMessage());
            }
        }
    };

    void addulasan(){
        diterima.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
        paytex.setText("Kirim Ulasan");
        paytex.setTextColor(getResources().getColor(R.color.white));
        findViewById(R.id.progres).setVisibility(View.GONE);
        findViewById(R.id.containerulasan).setVisibility(View.VISIBLE);
        diterima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (String.valueOf(star.getRating()).equals("0.0")){
                    Toast.makeText(getApplicationContext(), "Berikan Bintang", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (ulasan.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Tuliskan Ulasan", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    j = new JSONObject();
                    j.put("komen", ulasan.getText().toString());
                    j.put("id_user_pembeli", UserPrefs.getId(getApplicationContext()));
                    j.put("rating", String.valueOf(star.getRating()).split("\\.")[0]);
                    j.put("id_transaksi", idtrans);
                } catch (JSONException e){
                    Log.i(TAG, "onClick: JSON ERR " +e.getMessage());
                }

                Log.i(TAG, "onClick: "+j.toString());
                findViewById(R.id.progres).setVisibility(View.VISIBLE);
                diterima.setCardBackgroundColor(getResources().getColor(R.color.white));
                paytex.setVisibility(View.GONE);
                new ReqString(PageOrders.this, requestQueue).pos(responulasan, j.toString(), FEEDBACK);
            }
        });
    }
}
