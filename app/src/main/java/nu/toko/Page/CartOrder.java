package nu.toko.Page;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import nu.toko.Adapter.CartListAdapter;
import nu.toko.Adapter.CheckoutListAdapter;
import nu.toko.Dialog.DialogLengkapi;
import nu.toko.Model.CartListModel;
import nu.toko.Model.ProductModelNU;
import nu.toko.R;
import nu.toko.Sqlite.CartDB;
import nu.toko.Sqlite.CheckoutDB;
import nu.toko.Utils.Others;
import nu.toko.Utils.UserPrefs;

public class CartOrder extends AppCompatActivity {

    CartListAdapter checkoutListAdapter;
    RecyclerView rvcartlist;
    CartDB cartDB;
    CardView continuebuy;
    List<ProductModelNU> productModelNU;
    ImageView back;
    int Total;
    TextView continuebuytex;
    TextView subtotal;
    String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_cartorder);

        init();
    }

    void init(){
        productModelNU = new ArrayList<>();
        cartDB = new CartDB(getApplicationContext());
        productModelNU = cartDB.getAll();

        continuebuytex = findViewById(R.id.continuebuytex);

        continuebuy = findViewById(R.id.continuebuy);

        subtotal = findViewById(R.id.subtotal);

        rvcartlist = findViewById(R.id.rvcartlist);
        checkoutListAdapter = new CartListAdapter(this, productModelNU);

        if (productModelNU.size()<=0){
            continuebuytex.setText("Kembali");
        }

        back = findViewById(R.id.back);

        rvcartlist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvcartlist.setAdapter(checkoutListAdapter);

        continuebuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productModelNU.size()<=0){
                    onBackPressed();
                }
                for (int i = 0; i < productModelNU.size(); i++){
                    if (productModelNU.get(i).isChecked()){
                        new CheckoutDB(getApplicationContext()).insert(productModelNU.get(i));
                    }
                }
                Intent i = new Intent(getApplicationContext(), Checkout.class);
                i.putExtra("subtotal", Total);

                if (UserPrefs.getKabupaten(getApplicationContext()).equals("0")
                        && UserPrefs.getKecamatan(getApplicationContext()).equals("0")
                        && UserPrefs.getProvinsi(getApplicationContext()).equals("0")
                        && UserPrefs.getKode_pos(getApplicationContext()).equals("0")
                        && UserPrefs.getAlamat(getApplicationContext()).equals("0")){
                    new DialogLengkapi(CartOrder.this).mentriger(new DialogLengkapi.Go() {
                        @Override
                        public void trigerbos() {
                            Intent i = new Intent(getApplicationContext(), UserSetting.class);
                            startActivity(i);
                        }
                    });
                } else {
                    startActivity(i);
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Total = 0;
        for (int i = 0; i < productModelNU.size(); i++){
            if (productModelNU.get(i).isChecked()){
                Log.i(TAG, "onItemClick: "+productModelNU.get(i).getHarga_admin());
                Log.i(TAG, "onItemClick: "+productModelNU.get(i).getHarga_mitra());
                Total+=(productModelNU.get(i).getHarga_admin()+productModelNU.get(i).getHarga_mitra()) * productModelNU.get(i).getQty();
                subtotal.setText("Rp."+Others.PercantikHarga(Total));
            }
        }

        checkoutListAdapter.setOnItemClickListener(new CartListAdapter.OnClick() {
            @Override
            public void onItemClick() {
                Total = 0;
                subtotal.setText("0");
                for (int i = 0; i < productModelNU.size(); i++){
                    if (productModelNU.get(i).isChecked()){
                        Log.i(TAG, "onItemClick: "+productModelNU.get(i).getHarga_admin());
                        Log.i(TAG, "onItemClick: "+productModelNU.get(i).getHarga_mitra());
                        Total+=(productModelNU.get(i).getHarga_admin()+productModelNU.get(i).getHarga_mitra()) * productModelNU.get(i).getQty();
                        subtotal.setText("Rp."+Others.PercantikHarga(Total));
                    }
                }
            }
        });
    }


}
