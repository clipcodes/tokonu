package nu.toko.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import nu.toko.Adapter.GroupBillingHomeAdapter;
import nu.toko.Model.BillingItemModel;
import nu.toko.Model.BillingModelNU;
import nu.toko.Model.ProductModelNU;
import nu.toko.Page.PageOrders;
import nu.toko.Page.PagePay;
import nu.toko.R;
import nu.toko.Reqs.ReqString;
import nu.toko.Utils.Others;
import nu.toko.Utils.UserPrefs;

import static nu.toko.Utils.Staticvar.FEEDADDED;
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
import static nu.toko.Utils.Staticvar.TRANSAKSIPEMBELI;

public class TransactionNU extends Fragment {

    FrameLayout container;
    String TAG = getClass().getSimpleName();
    RecyclerView rvbill;
    GroupBillingHomeAdapter groupBillingAdapter;
    List<BillingModelNU> billingModels;
    RequestQueue requestQueue;
    LinearLayout nodata;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.page_billinghome, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Others.MahathirOptionGambar(getActivity());
        init(view);
    }

    void init(View root){
        requestQueue = Volley.newRequestQueue(getActivity());
        billingModels = new ArrayList<>();
        rvbill = root.findViewById(R.id.rvbill);
        nodata = root.findViewById(R.id.nodata);
        groupBillingAdapter = new GroupBillingHomeAdapter(getActivity(), billingModels);
        rvbill.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvbill.setAdapter(groupBillingAdapter);

        groupBillingAdapter.setOnItemClickListener(new GroupBillingHomeAdapter.OnClick() {
            @Override
            public void onItemClick(BillingModelNU billingModelNU) {
                if (billingModelNU.getStatus_transaksi()>=1){
                    Intent i = new Intent(getActivity(), PageOrders.class);
                    i.putExtra(ID_TRANSAKSI, billingModelNU.getId_transaksi());
                    i.putExtra(STATUS_TRANSAKSI, billingModelNU.getStatus_transaksi());
                    startActivity(i);
                } else {
                    Intent i = new Intent(getActivity(), PagePay.class);
                    i.putExtra(ID_TRANSAKSI, billingModelNU.getId_transaksi());
                    startActivity(i);
                }
            }
        });
    }

    class click implements View.OnClickListener {
        View root;

        public click(View root) {
            this.root = root;
        }

        @Override
        public void onClick(View v) {
        }
    }

    private Response.Listener<String> respontrans = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i(TAG, "onResponse: "+response);
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
                    bill.setFeedadded(jsonObject.getBoolean(FEEDADDED));

                    Log.i(TAG, "onResponse: S "+jsonObject.getInt(STATUS_TRANSAKSI));

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

                if (billingModels.size()<=0){
                    nodata.setVisibility(View.VISIBLE);
                }
                groupBillingAdapter.notifyDataSetChanged();
            } catch (JSONException e){
                Log.i(TAG, "onResponse: Err "+e.getMessage());
            }
        }
    };

    @Override
    public void onStart() {
        Log.i(TAG, "onStart: "+UserPrefs.getId(getActivity()));
        billingModels.clear();
        groupBillingAdapter.notifyDataSetChanged();
        new ReqString(getActivity(), requestQueue).go(respontrans, TRANSAKSIPEMBELI+ UserPrefs.getId(getActivity()));
        super.onStart();
    }
}