package nu.toko.Page;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import nu.toko.R;
import nu.toko.Reqs.ReqString;
import nu.toko.Utils.UserPrefs;

import static nu.toko.Utils.Staticvar.PENGADUAN;

public class Bantuan extends AppCompatActivity {

    String TAG = getClass().getSimpleName();
    EditText pengaduan;
    CardView kirim;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_bantuan);

        init();
    }

    void init(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        pengaduan = findViewById(R.id.pengaduan);
        kirim = findViewById(R.id.kirim);

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String p = pengaduan.getText().toString();
                if (p.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Keterangan Kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                findViewById(R.id.gotex).setVisibility(View.INVISIBLE);
                findViewById(R.id.progress).setVisibility(View.VISIBLE);

                new ReqString(Bantuan.this, requestQueue).bantuan(respon, p);
            }
        });
    }

    Response.Listener<String> respon = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pengaduan.setText("");
                    findViewById(R.id.gotex).setVisibility(View.VISIBLE);
                    findViewById(R.id.progress).setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Pengaduan Sukses Terkirim !", Toast.LENGTH_SHORT).show();
                }
            }, 1500);
        }
    };
}
