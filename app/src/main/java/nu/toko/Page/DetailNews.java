package nu.toko.Page;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nostra13.universalimageloader.core.ImageLoader;

import nu.toko.R;
import nu.toko.Utils.Others;

import static nu.toko.Utils.Staticvar.DESK_BERITA_KOIN_NU;
import static nu.toko.Utils.Staticvar.FOTOBERITA;
import static nu.toko.Utils.Staticvar.FOTOPRODUK;
import static nu.toko.Utils.Staticvar.GAMBAR_BERITA;
import static nu.toko.Utils.Staticvar.JUDUL_BERITA_KOIN_NU;
import static nu.toko.Utils.Staticvar.PUBLISHING_KOIN_NU;
import static nu.toko.Utils.Staticvar.TGL_BERITA_KOIN_NU;

public class DetailNews extends AppCompatActivity {

    TextView titel, topics, date, title, content;
    ImageView image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_detailnews);

        Others.MahathirOptionGambar(getApplicationContext());

        init();

        ImageLoader.getInstance().displayImage(FOTOBERITA + getIntent().getStringExtra(GAMBAR_BERITA), image);
        titel.setText(getIntent().getStringExtra(JUDUL_BERITA_KOIN_NU));
        title.setText(getIntent().getStringExtra(JUDUL_BERITA_KOIN_NU));
        date.setText(getIntent().getStringExtra(TGL_BERITA_KOIN_NU));
        content.setText(getIntent().getStringExtra(DESK_BERITA_KOIN_NU));
        topics.setText(getIntent().getStringExtra(PUBLISHING_KOIN_NU));

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    void init(){
        titel = findViewById(R.id.titel);
        topics = findViewById(R.id.topics);
        date = findViewById(R.id.date);
        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        image = findViewById(R.id.image);
    }
}
