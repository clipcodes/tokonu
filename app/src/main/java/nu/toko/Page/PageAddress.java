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
import nu.toko.Model.AddressModel;
import nu.toko.R;
import nu.toko.Sqlite.AddressDB;

public class PageAddress extends AppCompatActivity {

    ImageView add;
    RecyclerView rvaddress;
    AddressAdapter addressAdapter;
    AddressDB addressDB;
    int NEWALAMAT = 665;
    List<AddressModel> addressModelList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_address);

        init();
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    void init(){
        addressModelList = new ArrayList<>();
        addressDB = new AddressDB(getApplicationContext());
        addressModelList = addressDB.getAll();
        rvaddress = findViewById(R.id.rvaddress);
        addressAdapter = new AddressAdapter(this, addressModelList);
        rvaddress.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rvaddress.setAdapter(addressAdapter);

        add = findViewById(R.id.add);

        addressAdapter.setOnItemClickListener(new AddressAdapter.OnClick() {
            @Override
            public void onItemClick(AddressModel addressModel) {
                Intent i = new Intent();
                i.putExtra("prov", addressModel.getProvinsi());
                i.putExtra("kabkota", addressModel.getKotakab());
                i.putExtra("kec", addressModel.getKec());
                i.putExtra("pos", addressModel.getKodepos());
                i.putExtra("address", addressModel.getAddress());
                setResult(RESULT_OK, i);
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddPageAddress.class);
                startActivityForResult(i, NEWALAMAT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == NEWALAMAT){
                AddressModel model = new AddressModel();
                model.setAddress(data.getStringExtra("address"));
                model.setProvinsi(data.getStringExtra("prov"));
                model.setKotakab(data.getStringExtra("kabkota"));
                model.setKec(data.getStringExtra("kec"));
                model.setKodepos(data.getStringExtra("pos"));
                addressModelList.add(model);
                addressAdapter.notifyDataSetChanged();
            }
        }
    }
}
