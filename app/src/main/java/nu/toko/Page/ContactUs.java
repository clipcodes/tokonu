package nu.toko.Page;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;

import nu.toko.R;

public class ContactUs extends AppCompatActivity {

    String TAG = getClass().getSimpleName();
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
