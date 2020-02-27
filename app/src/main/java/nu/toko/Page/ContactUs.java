package nu.toko.Page;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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

import nu.toko.MainActivity;
import nu.toko.R;
import nu.toko.Reqs.ReqString;

public class ContactUs extends AppCompatActivity {

    String TAG = getClass().getSimpleName();
    EditText pengaduan;
    CardView kirim;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_contactus);

        findViewById(R.id.hubungihp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+6282137241320"));
                startActivity(intent);
            }
        });

        findViewById(R.id.hubungiwa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsApp();
            }
        });

        findViewById(R.id.hubungiemail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","nginepajaid@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subjek Pengaduan");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Tulis Pengaduan");
                startActivity(Intent.createChooser(emailIntent, "Mengirim Email.."));
            }
        });

    }

    public void openWhatsApp() {
        try {
            String toNumber = "+6282137241320";
            Intent sendIntent = new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:" + "" + toNumber + "?body=" + ""));
            sendIntent.setPackage("com.whatsapp");
            startActivity(sendIntent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ContactUs.this, "it may be you dont have whats app", Toast.LENGTH_LONG).show();

        }
    }
}
