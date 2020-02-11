package nu.toko.Page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nu.toko.Adapter.AddressAdapter;
import nu.toko.Adapter.PayMetodAdapter;
import nu.toko.Model.AddressModel;
import nu.toko.Model.PayMethodModel;
import nu.toko.R;
import nu.toko.Sqlite.AddressDB;

public class PagePaymentMethod extends AppCompatActivity {

    RecyclerView rvpaymentmethod;
    PayMetodAdapter payMetodAdapter;
    List<PayMethodModel> payMethodModels;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_paymentmethod);

        init();

    }

    void init(){
        rvpaymentmethod = findViewById(R.id.rvpaymentmethod);
        payMethodModels = new ArrayList<>();
        method();
        payMetodAdapter = new PayMetodAdapter(this, payMethodModels);
        rvpaymentmethod.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rvpaymentmethod.setAdapter(payMetodAdapter);

        payMetodAdapter.setOnItemClickListener(new PayMetodAdapter.OnClick() {
            @Override
            public void onItemClick(PayMethodModel addressModel) {
                Intent i = new Intent();
                i.putExtra("method", addressModel.getMethod());
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }

    void method(){
        payMethodModels.add(new PayMethodModel("JNE"));
        payMethodModels.add(new PayMethodModel("SiCepat"));
        payMethodModels.add(new PayMethodModel("POS Indonesia"));
    }
}
