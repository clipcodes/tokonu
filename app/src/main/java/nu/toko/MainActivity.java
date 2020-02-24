package nu.toko;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import nu.toko.Fragment.Account;
import nu.toko.Fragment.KoinNU;
import nu.toko.Fragment.Feed;
import nu.toko.Fragment.Home;
import nu.toko.Fragment.Transaction;
import nu.toko.Fragment.TransactionNU;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    int page = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.getInstance();
        FirebaseMessaging.getInstance().subscribeToTopic("suh");
        init();
        declare();
    }

    public void init(){

        bottomNavigationView = findViewById(R.id.NAV);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TOKEN", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        Log.d("TOKEN", token);
                    }
                });
    }

    void declare(){
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home:
                    loadPage(new Home());
                    page = 1;
                    return true;
                case R.id.feed:
                    page = 2;
                    loadPage(new Feed());
                    return true;
                case R.id.transaction:
                    page = 3;
                    loadPage(new TransactionNU());
                    return true;
                case R.id.koinnu:
                    page = 4;
                    loadPage(new KoinNU());
                    return true;
                case R.id.account:
                    page = 5;
                    loadPage(new Account());
                    return true;
            }
            return false;
        }

    };

    private boolean loadPage(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (page!=1){
            loadPage(new KoinNU());
            bottomNavigationView.setSelectedItemId(R.id.home);
        }
        super.onBackPressed();
    }
}
