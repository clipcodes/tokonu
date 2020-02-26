package nu.toko.Page;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import java.util.concurrent.ThreadLocalRandom;

import nu.toko.Adapter.CheckoutListAdapter;
import nu.toko.Dialog.DialogInfo;
import nu.toko.Model.KodeVocerModel;
import nu.toko.Model.OngkosKirimModel;
import nu.toko.Model.ProductModelNU;
import nu.toko.R;
import nu.toko.Reqs.ReqString;
import nu.toko.Sqlite.CartDB;
import nu.toko.Sqlite.CheckoutDB;
import nu.toko.Utils.Others;
import nu.toko.Utils.UserPrefs;

import static nu.toko.Utils.Staticvar.ALAMAT_KIRIM;
import static nu.toko.Utils.Staticvar.BANK;
import static nu.toko.Utils.Staticvar.CHECKOUT;
import static nu.toko.Utils.Staticvar.CODE_KURIR;
import static nu.toko.Utils.Staticvar.EMAIL;
import static nu.toko.Utils.Staticvar.HARGA_ONGKIR;
import static nu.toko.Utils.Staticvar.HARGA_TOTAL;
import static nu.toko.Utils.Staticvar.ID_MITRA;
import static nu.toko.Utils.Staticvar.ID_PEMBELI;
import static nu.toko.Utils.Staticvar.ID_PRODUK;
import static nu.toko.Utils.Staticvar.ITEM;
import static nu.toko.Utils.Staticvar.KODEPOS;
import static nu.toko.Utils.Staticvar.KODEVOCER;
import static nu.toko.Utils.Staticvar.NAMALENGKAP;
import static nu.toko.Utils.Staticvar.NOMINAL;
import static nu.toko.Utils.Staticvar.NOREK;
import static nu.toko.Utils.Staticvar.ONGKIR;
import static nu.toko.Utils.Staticvar.QTY;
import static nu.toko.Utils.Staticvar.SLASH;
import static nu.toko.Utils.Staticvar.SUB_TOTAL;

public class Checkout extends AppCompatActivity {

    String TAG = getClass().getSimpleName();
    CheckoutListAdapter checkoutListAdapter;
    RecyclerView rvcartlist;
    ImageView back;
    CheckoutDB checkoutDB;
    int ALAMAT = 992;
    int PAYMETHOD = 5443;
    int VOCER = 5233;
    int KURIR = 665;
    TextView alamatpengiriman, subtotal, biayapengiriman, paytotal, paynowtex;
    FrameLayout address;
    int subtotalintent;
    int biayakirim = 0;
    ProgressBar progress;
    CardView paynow;
    List<ProductModelNU> productModelNU;
    String alamatkirim;
    RequestQueue requestQueue;
    TextView koinu;
    int koinusumbang = 0;
    String kurirdipilih = null;
    int vocersum =0;
    int i;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_checkout);

        Others.MahathirOptionGambar(getApplicationContext());
        subtotalintent = getIntent().getIntExtra("subtotal", 0);
        init();

    }

    void init(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        productModelNU = new ArrayList<>();
        checkoutDB = new CheckoutDB(getApplicationContext());
        productModelNU = checkoutDB.getAll();
        rvcartlist = findViewById(R.id.rvcartlist);
        checkoutListAdapter = new CheckoutListAdapter(this, productModelNU);
        address = findViewById(R.id.address);

        progress = findViewById(R.id.progress);
        alamatpengiriman = findViewById(R.id.alamatpengiriman);
        back = findViewById(R.id.back);
//        method = findViewById(R.id.method);
        subtotal = findViewById(R.id.subtotal);
        biayapengiriman = findViewById(R.id.biayapengiriman);
        paytotal = findViewById(R.id.paytotal);
        paynow = findViewById(R.id.paynow);
        paynowtex = findViewById(R.id.paynowtex);
        koinu = findViewById(R.id.koinu);

        koinusumbang = ThreadLocalRandom.current().nextInt(500, 999);

        subtotal.setText("Rp."+Others.PercantikHarga(subtotalintent));
        biayapengiriman.setText("Rp."+Others.PercantikHarga(biayakirim));
        koinu.setText("Rp."+Others.PercantikHarga(koinusumbang));
        total();

        String alamatholder =
                UserPrefs.getProvinsi(getApplicationContext())+" "+
                UserPrefs.getNamakab(getApplicationContext())+" "+
                UserPrefs.getKecamatan(getApplicationContext())+", "+
                UserPrefs.getAlamat(getApplicationContext());

        alamatpengiriman.setText(alamatholder);
        alamatkirim = alamatholder;

        rvcartlist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvcartlist.setAdapter(checkoutListAdapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//        address.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(), PageAddress.class);
//                startActivityForResult(i, ALAMAT);
//            }
//        });
//        paymentmethod.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(), PagePaymentMethod.class);
//                startActivityForResult(i, PAYMETHOD);
//            }
//        });
        findViewById(R.id.kurir).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), KurirChoose.class);
                startActivityForResult(i, KURIR);
            }
        });
        paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kurirdipilih == null) {
                    Toast.makeText(getApplicationContext(), "Pilih Opsi Pengiriman", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (alamatkirim == null) {
                    Toast.makeText(getApplicationContext(), "Alamat Pengiriman Kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                paynowtex.setVisibility(View.INVISIBLE);
                progress.setVisibility(View.VISIBLE);
                paynow.setCardBackgroundColor(getResources().getColor(R.color.white));

                ArrayList<String> mitra = new ArrayList<>();
                String id_mitra = "mitra";
                for (int i = 0; i < productModelNU.size(); i++){
                    if (!id_mitra.equals(productModelNU.get(i).getId_mitra())){
                        id_mitra = productModelNU.get(i).getId_mitra();
                        mitra.add(productModelNU.get(i).getId_mitra());
                    }
                }

                try {
                    JSONArray array = new JSONArray();
                    for (int i = 0; i < mitra.size(); i++){
                        JSONObject transaks = new JSONObject();
                        transaks.put(ID_MITRA, mitra.get(i));
                        transaks.put(ID_PEMBELI, UserPrefs.getId(getApplicationContext()));
                        transaks.put(ALAMAT_KIRIM, alamatkirim);
                        transaks.put(CODE_KURIR, kurirdipilih);
                        JSONArray jsonArray = new JSONArray();
                        int sub_total = 0;
                        int harga_ongkir = 0;
                        int harga_total;
                        for (int l = 0; l < productModelNU.size(); l++){
                            if (productModelNU.get(l).getId_mitra().equals(mitra.get(i))){
                                sub_total += productModelNU.get(l).getHarga_admin()*productModelNU.get(l).getQty();
                                harga_ongkir += productModelNU.get(l).getFixongkir();

                                JSONObject object = new JSONObject();
                                object.put(QTY, productModelNU.get(i).getQty());
                                object.put(ID_PRODUK, productModelNU.get(i).getId_produk());
                                jsonArray.put(object);

                                new CartDB(getApplicationContext()).delete(productModelNU.get(i).getId_produk());
                                new CheckoutDB(getApplicationContext()).delete(productModelNU.get(i).getId_produk());
                            }
                        }
                        harga_total = sub_total+harga_ongkir+koinusumbang;
                        transaks.put(SUB_TOTAL, sub_total);
                        transaks.put(HARGA_ONGKIR, harga_ongkir);
                        transaks.put(HARGA_TOTAL, harga_total);
                        transaks.put(ITEM, jsonArray);

                        array.put(transaks);
                    }

                    Log.i(TAG, "onClick: Go "+array.toString());
                    new ReqString(Checkout.this, requestQueue).pos(responcheckout, array.toString(), CHECKOUT);
                } catch (JSONException e){
                    Log.i(TAG, "createTrans: er "+e.getMessage());
                }
            }
        });

        findViewById(R.id.vocer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), KodeVocer.class);
                startActivityForResult(i, VOCER);
            }
        });
    }

    void total(){
        int total = 0;
        total += subtotalintent;
        total += biayakirim;
        total += koinusumbang;
        total -= vocersum;
        paytotal.setText("Rp."+Others.PercantikHarga(total));
    }

    Response.Listener<String> responcheckout = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i(TAG, "onResponse: Berhasil "+ response);
            Intent i = new Intent(getApplicationContext(), Billing.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == ALAMAT){
                alamatpengiriman.setText(data.getStringExtra("prov")+" "+data.getStringExtra("kabkota")+" "+data.getStringExtra("kec")+", "+data.getStringExtra("address"));
                alamatkirim = data.getStringExtra("prov")+" "+data.getStringExtra("kabkota")+" "+data.getStringExtra("kec")+", "+data.getStringExtra("address");
            }
            if (requestCode == PAYMETHOD){
//                method.setText("Dikirim Menggunakan "+data.getStringExtra("method"));
            }
            if (requestCode == VOCER){
                KodeVocerModel km = new KodeVocerModel();
                km.setKode(data.getStringExtra("kode"));
                km.setParameter(data.getIntExtra("parameter", 0));
                km.setNominal(data.getIntExtra("nominal", 0));
                km.setKeterangan(data.getStringExtra("keterangan"));

                if (subtotalintent >= km.getParameter()){
                    findViewById(R.id.vocercontainer).setVisibility(View.VISIBLE);
                    ((TextView)findViewById(R.id.potongan)).setText("-Rp."+Others.PercantikHarga(km.getNominal()));
                    ((TextView)findViewById(R.id.vocertex)).setText("Menggunakan Kode Voucher : ");
                    ((TextView)findViewById(R.id.vocertexterpilih)).setText(km.getKode());
                    vocersum = km.getNominal();
                    total();
                } else {
                    new DialogInfo(Checkout.this, km.getKeterangan()).mentriger(new DialogInfo.Go() {
                        @Override
                        public void trigerbos() {

                        }
                    });
                }
                Log.i(TAG, "onActivityResult: Vocer "+km.getKode());
            }
            if (requestCode == KURIR){
                ((TextView)findViewById(R.id.kurirtex)).setText("Menggunakan Kurir");
                ((TextView)findViewById(R.id.selectkurirtex)).setText(data.getStringExtra("kurir"));
                kurirdipilih = data.getStringExtra("kode");

                //Reload Totalan Biaya Kirim
                biayakirim = 0;

                //Lopig by produk yang ada
                for (int i = 0; i < productModelNU.size(); i++) {
                    try {
                        //Ambil Array Produk Ongkir
                        JSONArray jsonArray = new JSONArray(productModelNU.get(i).getOngkir());
                        //Loop Panjang Ongkir
                        for (int x = 0; x < jsonArray.length(); x++) {
                            //Fetch Data Ongkir
                            JSONArray parentarr = jsonArray.getJSONArray(x);
                            JSONObject json = parentarr.getJSONObject(0);
                            JSONArray costs = json.getJSONArray("costs");
                            //Ketika Kode Sama Dengan Code Ongkir Yang Dipilih Maka Lanjut
                            if (json.getString("code").equals(kurirdipilih)){
                                JSONObject costsO = costs.getJSONObject(0);
                                JSONArray cost = costsO.getJSONArray("cost");
                                JSONObject costO = cost.getJSONObject(0);

                                productModelNU.get(i).setFixongkir(costO.getInt("value"));

                                biayakirim += costO.getInt("value");

                                Log.i(TAG, "onActivityResult: val "+costO.getInt("value"));
                            }
                        }
                    } catch (JSONException e) {
                        Log.i(TAG, "onActivityResult: err "+e.getMessage());
                    }
                }

                biayapengiriman.setText("Rp."+Others.PercantikHarga(biayakirim));
                total();

                Log.i(TAG, "onActivityResult: biaya "+biayakirim);
            }
        }
    }

    @Override
    protected void onDestroy() {
        new CheckoutDB(getApplicationContext()).deleteAll();
        super.onDestroy();
    }
}
