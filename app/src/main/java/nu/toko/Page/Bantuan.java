package nu.toko.Page;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import nu.toko.R;

public class Bantuan extends AppCompatActivity {

    String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_bantuan);

        init();


    }

    void init(){
    }
}
