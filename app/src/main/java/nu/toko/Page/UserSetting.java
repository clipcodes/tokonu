package nu.toko.Page;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import nu.toko.MainActivity;
import nu.toko.Model.AddressModel;
import nu.toko.Model.KotaModel;
import nu.toko.Model.ProvModel;
import nu.toko.Model.UserPembeliModel;
import nu.toko.R;
import nu.toko.Reqs.ReqString;
import nu.toko.Reqs.UserReqs;
import nu.toko.Utils.UserPrefs;

import static nu.toko.Utils.Staticvar.DATPROVINSI;
import static nu.toko.Utils.Staticvar.PROVINSI;
import static nu.toko.Utils.Staticvar.USER_DAFTAR;
import static nu.toko.Utils.Staticvar.USER_EDIT;

public class UserSetting extends AppCompatActivity {

    EditText email, nama_pembeli, no_telp, kecamatan_pembeli, kode_pos_pembeli, alamat_pembeli, password;
    TextView err;
    TextView gotex;
    ProgressBar progress;
    TextView provinsi_pembeli, kabupaten_pembeli;
    RequestQueue requestQueue;
    CardView save;
    String TAG = getClass().getSimpleName();
    ArrayList<String> PROVNAME;
    ArrayList<String> PROVID;
    ReqString reqString;
    String idprovterpilih = null;
    String kabterpilih = "";
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_setting);

        init();

    }
    
    void init(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        reqString = new ReqString(this, requestQueue);
        PROVNAME = new ArrayList<>();
        PROVID = new ArrayList<>();
        save = findViewById(R.id.save);
        email = findViewById(R.id.email);
        progress = findViewById(R.id.progress);
        gotex = findViewById(R.id.gotex);
        err = findViewById(R.id.err);
        password = findViewById(R.id.password);
        nama_pembeli = findViewById(R.id.nama_pembeli);
        no_telp = findViewById(R.id.no_telp);
        provinsi_pembeli = findViewById(R.id.provinsi_pembeli);
        kecamatan_pembeli = findViewById(R.id.kecamatan_pembeli);
        kabupaten_pembeli = findViewById(R.id.kabupaten_pembeli);
        kode_pos_pembeli = findViewById(R.id.kode_pos_pembeli);
        alamat_pembeli = findViewById(R.id.alamat_pembeli);

        findViewById(R.id.pilihprov).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PageProv.class);
                startActivityForResult(i, 121);
            }
        });

        findViewById(R.id.pilihkabkot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idprovterpilih==null){
                    Toast.makeText(getApplicationContext(), "Pilih Provinsi Dahulu", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent(getApplicationContext(), PageKota.class);
                i.putExtra("idprov", idprovterpilih);
                startActivityForResult(i, 131);
            }
        });

        email.setText(UserPrefs.getEmail(getApplicationContext()));
        nama_pembeli.setText(UserPrefs.getNama(getApplicationContext()));

        if (!UserPrefs.getNo_telp(getApplicationContext()).equals("null")){
            no_telp.setText(UserPrefs.getNo_telp(getApplicationContext()));
        }
        if (!UserPrefs.getProvinsi(getApplicationContext()).equals("null")){
            provinsi_pembeli.setText(UserPrefs.getProvinsi(getApplicationContext()));
        }
        if (!UserPrefs.getKecamatan(getApplicationContext()).equals("null")){
            kecamatan_pembeli.setText(UserPrefs.getKecamatan(getApplicationContext()));
        }
        if (!UserPrefs.getNamakab(getApplicationContext()).equals("null")){
            kabupaten_pembeli.setText(UserPrefs.getNamakab(getApplicationContext()));
        }
        if (!UserPrefs.getKode_pos(getApplicationContext()).equals("null")){
            kode_pos_pembeli.setText(UserPrefs.getKode_pos(getApplicationContext()));
        }
        if (!UserPrefs.getAlamat(getApplicationContext()).equals("null")){
            alamat_pembeli.setText(UserPrefs.getAlamat(getApplicationContext()));
        }

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserPembeliModel usr = new UserPembeliModel();
                usr.setAlamat_pembeli(alamat_pembeli.getText().toString());
                usr.setEmail_pembeli(email.getText().toString());
                usr.setProvinsi_pembeli(provinsi_pembeli.getText().toString());
                usr.setKabupaten_pembeli(kabterpilih);
                usr.setKecamatan_pembeli(kecamatan_pembeli.getText().toString());
                usr.setKode_pos_pembeli(kode_pos_pembeli.getText().toString());
                usr.setNama_pembeli(nama_pembeli.getText().toString());
                usr.setNo_telp(no_telp.getText().toString());

                if (usr.getEmail_pembeli().isEmpty()){
                    err.setText("Isikan Email");
                    return;
                }

                if (usr.getNama_pembeli().isEmpty()){
                    err.setText("Isikan Nama Lengkap");
                    return;
                }

                if (usr.getNo_telp().isEmpty()){
                    err.setText("Isikan Nomor Anda");
                    return;
                }

                if (usr.getProvinsi_pembeli().contains("Pilih")){
                    err.setText("Isikan Provinsi Tinggal");
                    return;
                }

                if (kabterpilih.contains("Pilih")){
                    err.setText("Isikan Kota/Kab Tinggal");
                    return;
                }

                if (usr.getKecamatan_pembeli().isEmpty()){
                    err.setText("Isikan Kecamatan Tinggal");
                    return;
                }

                if (usr.getAlamat_pembeli().isEmpty()){
                    err.setText("Isikan Alamat Pelengkap");
                    return;
                }
                go(true);

                new UserReqs(UserSetting.this, requestQueue).daftar(res, usr, null, USER_EDIT+UserPrefs.getId(getApplicationContext()));
            }
        });
    }

    void go(boolean go){
        if (go){
            save.setCardBackgroundColor(getApplicationContext().getResources().getColor(R.color.white));
            progress.setVisibility(View.VISIBLE);
            gotex.setVisibility(View.INVISIBLE);
            err.setText("");
        } else {
            save.setCardBackgroundColor(getApplicationContext().getResources().getColor(R.color.colorPrimary));
            progress.setVisibility(View.INVISIBLE);
            gotex.setVisibility(View.VISIBLE);
        }
    }

    private Response.Listener<String> res = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i("RESPON", response);
            try {
                JSONObject object = new JSONObject(response);
                if (response.contains("message")){
                    err.setText(object.getString("message"));
                    go(false);
                    return;
                }
                UserPrefs.setId(object.getString("id"), getApplicationContext());
                UserPrefs.setAlamat(object.getString("alamat"), getApplicationContext());
                UserPrefs.setEmail(object.getString("email"), getApplicationContext());
                UserPrefs.setNama(object.getString("name"), getApplicationContext());
                UserPrefs.setNo_telp(object.getString("hp"), getApplicationContext());
                UserPrefs.setProvinsi(object.getString("provinsi"), getApplicationContext());
                UserPrefs.setKabupaten(object.getString("kabupaten"), getApplicationContext());
                UserPrefs.setKecamatan(object.getString("kecamatan"), getApplicationContext());
                UserPrefs.setKode_pos(object.getString("kode_pos"), getApplicationContext());
                UserPrefs.setUrl_profil(object.getString("url_profil"), getApplicationContext());
                UserPrefs.setNamakab(object.getString("namakota"), getApplicationContext());

                go(false);

                Toast.makeText(UserSetting.this, "Tersimpan", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                Log.i("RESPON EROR", e.getMessage());
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == 121){
                ProvModel model = new ProvModel();
                model.setNama_provinsi(data.getStringExtra("nama"));
                model.setProvinsi_id(data.getStringExtra("id"));
                provinsi_pembeli.setText(data.getStringExtra("nama"));
                idprovterpilih = data.getStringExtra("id");
            }
            if (requestCode == 131){
                KotaModel model = new KotaModel();
                model.setNama_kota(data.getStringExtra("nama"));
                model.setKota_id(data.getStringExtra("id"));
                kabupaten_pembeli.setText(data.getStringExtra("nama"));
                kabterpilih = data.getStringExtra("id");
            }
        }
    }
}
