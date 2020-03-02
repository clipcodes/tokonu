package nu.toko.Page;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import nu.toko.Dialog.DialogInfo;
import nu.toko.R;
import nu.toko.Reqs.ReqString;
import nu.toko.Utils.UserPrefs;

import static nu.toko.Utils.Staticvar.BAYAR;
import static nu.toko.Utils.Staticvar.DONASI;
import static nu.toko.Utils.Staticvar.FOTODONASI;

public class Donasi extends AppCompatActivity {

    String TAG = getClass().getSimpleName();
    Bitmap bukti = null;
    CardView kirim;
    TextView kirimtex;
    ProgressBar progres;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    EditText namalengkap, jumlahtransfer;
    FrameLayout pilihfile;
    ImageView imagetampil, ikonupload;
    RequestQueue requestQueue;
    String nll = null;
    String namafile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_donasi);

        DateFormat nama = new SimpleDateFormat("yyyyMMddHHmmSSss");
        Date date = new Date();
        namafile = "donasi"+nama.format(date);
        init();
    }

    void init(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        namalengkap = findViewById(R.id.namalengkap);
        jumlahtransfer = findViewById(R.id.jumlahtransfer);
        kirimtex = findViewById(R.id.kirimtex);
        kirim = findViewById(R.id.kirim);
        progres = findViewById(R.id.progres);
        pilihfile = findViewById(R.id.pilihfile);
        imagetampil = findViewById(R.id.imagetampil);
        ikonupload = findViewById(R.id.ikonupload);

        namalengkap.setText(UserPrefs.getNama(getApplicationContext()));

        pilihfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
                } else {
                    CropImage.startPickImageActivity(Donasi.this);
                }
            }
        });

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nl = namalengkap.getText().toString();
                nll = namalengkap.getText().toString();
                String jml = jumlahtransfer.getText().toString();
                if (nl.isEmpty()){
                    Toast.makeText(Donasi.this, "Nama Lengkap Kosong", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (jml.isEmpty()){
                    Toast.makeText(Donasi.this, "Jumlah Tranfer Kosong", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (bukti == null){
                    Toast.makeText(Donasi.this, "Tambah Bukti Transfer", Toast.LENGTH_SHORT).show();
                    return;
                }

                kirim.setCardBackgroundColor(getResources().getColor(R.color.white));
                kirimtex.setVisibility(View.INVISIBLE);
                progres.setVisibility(View.VISIBLE);

                new ReqString(Donasi.this, requestQueue).foto(namafile, bukti, fotorespon, FOTODONASI);
            }
        });
    }

    Response.Listener<String> respon = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i(TAG, "onResponse: "+response);
            kirim.setVisibility(View.GONE);
            new DialogInfo(Donasi.this, "Terima Kasih "+nll+", Anda berhasil melakukan donasi.").mentriger(new DialogInfo.Go() {
                @Override
                public void trigerbos() {
                    onBackPressed();
                }
            });
        }
    };

    public Response.Listener<NetworkResponse> fotorespon = new Response.Listener<NetworkResponse>() {
        @Override
        public void onResponse(NetworkResponse response) {
            new ReqString(Donasi.this, requestQueue).donasi(respon, namafile, namalengkap.getText().toString(), jumlahtransfer.getText().toString(), DONASI);
        }
    };

    private void Ngecorep(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            CropImage.startPickImageActivity(this);
        }
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            } else {
                Ngecorep(imageUri);
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imagetampil.setImageURI(result.getUri());
                Log.i(TAG, "onActivityResult: uri "+result.getUri());
                findViewById(R.id.ikonupload).setVisibility(View.GONE);
                String path = result.getUri().toString();
                try {
                    Bitmap foto = MediaStore.Images.Media.getBitmap(getContentResolver(), result.getUri());
                    imagetampil.setImageBitmap(foto);
                    ikonupload.setVisibility(View.GONE);

                    bukti = foto;
                } catch (Exception e){
                    Log.i("Catch", e.getMessage());
                }
            }
        }

    }

}
