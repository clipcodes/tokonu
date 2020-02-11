package nu.toko.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import nu.toko.MainActivity;
import nu.toko.R;
import nu.toko.Reqs.UserReqs;
import nu.toko.Utils.UserPrefs;

import static nu.toko.Utils.Staticvar.EMAIL;
import static nu.toko.Utils.Staticvar.FULLNAME;
import static nu.toko.Utils.Staticvar.ID;
import static nu.toko.Utils.Staticvar.PHONE;
import static nu.toko.Utils.Staticvar.USERNAME;
import static nu.toko.Utils.Staticvar.USER_DAFTAR;
import static nu.toko.Utils.Staticvar.USER_LOGIN;

public class SignIn extends Fragment {

    String TAG = getClass().getSimpleName();
    CardView go;
    ProgressBar loding;
    EditText email, pass;
    TextView err;
    TextView logintex;
    CardView login;
    TextView gotex;
    ProgressBar progress;
    RequestQueue requestQueue;

    public SignIn() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.users_signin, container, false);

        init(view);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().isEmpty()){
                    err.setText("Isikan Email");
                    return;
                }

                if (pass.getText().toString().isEmpty()){
                    err.setText("Isikan Password");
                    return;
                }

                go(true);

                new UserReqs(getActivity(), requestQueue).login(res, email.getText().toString(), pass.getText().toString(), USER_LOGIN);
            }
        });

        return view;
    }

    void init(View view){
        requestQueue = Volley.newRequestQueue(getActivity());
        email = view.findViewById(R.id.email);
        pass = view.findViewById(R.id.password);
        login = view.findViewById(R.id.login);
        progress = view.findViewById(R.id.progress);
        gotex = view.findViewById(R.id.gotex);
        err = view.findViewById(R.id.err);
    }

    void go(boolean go){
        if (go){
            login.setCardBackgroundColor(getActivity().getResources().getColor(R.color.white));
            progress.setVisibility(View.VISIBLE);
            gotex.setVisibility(View.INVISIBLE);
            err.setText("");
        } else {
            login.setCardBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
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
                Intent i = new Intent(getActivity(), MainActivity.class);
                UserPrefs.setLogin(getActivity(), true);

                FirebaseMessaging.getInstance().subscribeToTopic("user"+object.getString("id"));

                startActivity(i);
                getActivity().finish();
            } catch (JSONException e) {
                Log.i("RESPON EROR", e.getMessage());
            }
        }
    };
}