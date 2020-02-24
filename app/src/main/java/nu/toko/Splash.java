package nu.toko;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import nu.toko.Adapter.PageAdapter;
import nu.toko.Fragment.Intro1;
import nu.toko.Fragment.Intro2;
import nu.toko.Fragment.Intro3;
import nu.toko.Page.Login;
import nu.toko.R;
import nu.toko.Utils.Pref;

public class Splash extends AppCompatActivity {

    ViewPager pager;
    WormDotsIndicator wormDotsIndicator;
    LinearLayout prev, next;
    CardView finish;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_introkontainer);

        if (Pref.read(getApplicationContext(), "intro", "0").equals("1")){
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
        }

        init();

    }

    void init(){
        wormDotsIndicator = findViewById(R.id.wormDotsIndicator);
        prev = findViewById(R.id.prev);
        finish = findViewById(R.id.finish);
        next = findViewById(R.id.next);
        pager = findViewById(R.id.viewPager);
        pager.setOffscreenPageLimit(10);
        SetPage(getSupportFragmentManager());
        wormDotsIndicator.setViewPager(pager);

        prev.setOnClickListener(new klik());
        next.setOnClickListener(new klik());
        finish.setOnClickListener(new klik());

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("curitem", "onClick: "+pager.getCurrentItem());
                switch (position){
                    case 0:
                        prev.setVisibility(View.GONE);
                        break;
                    case 1:
                        prev.setVisibility(View.VISIBLE);
                        next.setVisibility(View.VISIBLE);
                        finish.setVisibility(View.GONE);
                        break;
                    case 2:
                        next.setVisibility(View.GONE);
                        finish.setVisibility(View.VISIBLE);
                        finish.setOnClickListener(new klik());
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    void SetPage(FragmentManager fragmentManager){
        PageAdapter Adapter = new PageAdapter(fragmentManager);
        Adapter.Tambai(new Intro1());
        Adapter.Tambai(new Intro2());
        Adapter.Tambai(new Intro3());
        pager.setAdapter(Adapter);
    }

    class klik implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.prev:
                    if (pager.getCurrentItem()==2){
                        pager.setCurrentItem(1);
                    } else if (pager.getCurrentItem()==1){
                        pager.setCurrentItem(0);
                    }
                    break;
                case R.id.next:
                    if (pager.getCurrentItem()==0){
                        pager.setCurrentItem(1);
                    } else if (pager.getCurrentItem()==1){
                        pager.setCurrentItem(2);
                    }
                    break;
                case R.id.finish:
                    Intent i = new Intent(getApplicationContext(), Login.class);
                    Pref.write(getApplicationContext(), "intro", "1");
                    startActivity(i);
                    break;
            }
        }
    }
}
