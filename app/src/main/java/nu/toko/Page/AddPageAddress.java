package nu.toko.Page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import nu.toko.Model.AddressModel;
import nu.toko.R;
import nu.toko.Sqlite.AddressDB;

public class AddPageAddress extends AppCompatActivity {

    CardView simpan;
    EditText prov, kabkota, kec, pos, address;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_addaddress);

        init();

    }

    void init(){
        prov = findViewById(R.id.prov);
        kabkota = findViewById(R.id.kabkota);
        kec = findViewById(R.id.kec);
        pos = findViewById(R.id.pos);
        address = findViewById(R.id.address);
        simpan = findViewById(R.id.simpan);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressModel model = new AddressModel();
                model.setAddress(address.getText().toString());
                model.setProvinsi(prov.getText().toString());
                model.setKotakab(kabkota.getText().toString());
                model.setKec(kec.getText().toString());
                model.setKodepos(pos.getText().toString());
                new AddressDB(getApplicationContext()).insert(model);
                Intent i = new Intent();
                i.putExtra("address", address.getText().toString());
                i.putExtra("prov", prov.getText().toString());
                i.putExtra("kabkota", kabkota.getText().toString());
                i.putExtra("kec", kec.getText().toString());
                i.putExtra("pos", pos.getText().toString());
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }
}
