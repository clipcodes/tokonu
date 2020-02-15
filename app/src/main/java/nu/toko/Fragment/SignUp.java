package nu.toko.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import nu.toko.MainActivity;
import nu.toko.Model.KotaModel;
import nu.toko.Model.ProvModel;
import nu.toko.Model.UserPembeliModel;
import nu.toko.Page.PageKota;
import nu.toko.Page.PageProv;
import nu.toko.R;
import nu.toko.Reqs.UserReqs;
import nu.toko.Utils.UserPrefs;

import static android.app.Activity.RESULT_OK;
import static nu.toko.Utils.Staticvar.USER_DAFTAR;

public class SignUp extends Fragment {

    CardView signup;
    ProgressBar loding;
    RequestQueue requestQueue;
    EditText email, nama_pembeli, no_telp, kecamatan_pembeli, kode_pos_pembeli, alamat_pembeli, password;
    TextView err;
    TextView gotex;
    ProgressBar progress;
    KirimData kirimData;
    String idprovterpilih = null;
    String kabterpilih = "";
    TextView provinsi_pembeli, kabupaten_pembeli;

    public SignUp() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.users_signup, container, false);

        init(view);

        signup.setOnClickListener(new View.OnClickListener() {
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
                    err.setText("Pilih Provinsi Tinggal");
                    return;
                }

                if (kabterpilih.contains("Pilih")){
                    err.setText("Pilih Kota/Kab Tinggal");
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

                if (password.getText().toString().isEmpty()){
                    err.setText("Isikan Password");
                    return;
                }

                if (kode_pos_pembeli.getText().toString().isEmpty()){
                    err.setText("Isikan Kode Pos");
                    return;
                }

                go(true);

                new UserReqs(getActivity(), requestQueue).daftar(res, usr, password.getText().toString(), USER_DAFTAR);
            }
        });

        return view;
    }

    void go(boolean go){
        if (go){
            signup.setCardBackgroundColor(getActivity().getResources().getColor(R.color.white));
            progress.setVisibility(View.VISIBLE);
            gotex.setVisibility(View.INVISIBLE);
            err.setText("");
        } else {
            signup.setCardBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
            progress.setVisibility(View.INVISIBLE);
            gotex.setVisibility(View.VISIBLE);
        }
    }

    void init(View view){
        requestQueue = Volley.newRequestQueue(getActivity());
        signup = view.findViewById(R.id.signup);
        email = view.findViewById(R.id.email);
        progress = view.findViewById(R.id.progress);
        gotex = view.findViewById(R.id.gotex);
        err = view.findViewById(R.id.err);
        password = view.findViewById(R.id.password);
        nama_pembeli = view.findViewById(R.id.nama_pembeli);
        no_telp = view.findViewById(R.id.no_telp);
        provinsi_pembeli = view.findViewById(R.id.provinsi_pembeli);
        kecamatan_pembeli = view.findViewById(R.id.kecamatan_pembeli);
        kabupaten_pembeli = view.findViewById(R.id.kabupaten_pembeli);
        kode_pos_pembeli = view.findViewById(R.id.kode_pos_pembeli);
        alamat_pembeli = view.findViewById(R.id.alamat_pembeli);


        view.findViewById(R.id.pilihprov).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), PageProv.class);
                startActivityForResult(i, 121);
            }
        });

        view.findViewById(R.id.pilihkabkot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idprovterpilih==null){
                    Toast.makeText(getActivity(), "Pilih Provinsi Dahulu", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent(getActivity(), PageKota.class);
                i.putExtra("idprov", idprovterpilih);
                startActivityForResult(i, 131);
            }
        });
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
                UserPrefs.setLogin(getActivity(), true);
                UserPrefs.setId(object.getString("id"), getActivity());
                UserPrefs.setAlamat(object.getString("alamat"), getActivity());
                UserPrefs.setEmail(object.getString("email"), getActivity());
                UserPrefs.setNama(object.getString("name"), getActivity());
                UserPrefs.setNo_telp(object.getString("hp"), getActivity());
                UserPrefs.setProvinsi(object.getString("provinsi"), getActivity());
                UserPrefs.setKabupaten(object.getString("kabupaten"), getActivity());
                UserPrefs.setKecamatan(object.getString("kecamatan"), getActivity());
                UserPrefs.setKode_pos(object.getString("kode_pos"), getActivity());
                UserPrefs.setUrl_profil(object.getString("url_profil"), getActivity());
                UserPrefs.setNamakab(object.getString("namakota"), getActivity());
                Intent i = new Intent(getActivity(), MainActivity.class);
                FirebaseMessaging.getInstance().subscribeToTopic("user"+object.getString("id"));
                startActivity(i);
                getActivity().finish();
            } catch (JSONException e) {
                Log.i("RESPON EROR", e.getMessage());
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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

    public interface KirimData{
        void trigerhome();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            kirimData = (KirimData) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement TextClicked");
        }
    }

    @Override
    public void onDetach() {
        kirimData = null;
        super.onDetach();
    }

}