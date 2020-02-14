package nu.toko.Page;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import nu.toko.Fragment.Billing;
import nu.toko.Fragment.Loader;
import nu.toko.Fragment.Purchase;
import nu.toko.Fragment.SignIn;
import nu.toko.Fragment.SignUp;
import nu.toko.MainActivity;
import nu.toko.Model.UserPembeliModel;
import nu.toko.R;
import nu.toko.Reqs.UserReqs;
import nu.toko.Utils.UserPrefs;

import static nu.toko.Utils.Staticvar.USER_DAFTAR;

public class Login extends AppCompatActivity implements SignIn.KirimData {

    String TAG = getClass().getSimpleName();
    FrameLayout container;
    FrameLayout login, signup;
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    RequestQueue requestQueue;

    @Override
    public void trigerhome() {
        signIn();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_auth);

        if (UserPrefs.isLogin(getApplicationContext())){
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mAuth = FirebaseAuth.getInstance();

        init();
        declare();
    }

    void init(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        container = findViewById(R.id.container);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        login.setOnClickListener(new click());
        signup.setOnClickListener(new click());
    }

    void declare(){
        ngloadview(new SignIn());
    }

    class click implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.login:
                    findViewById(R.id.indpurchase).setBackgroundColor(getApplicationContext().getResources().getColor(R.color.colorPrimary));
                    findViewById(R.id.indbilling).setBackgroundColor(getApplicationContext().getResources().getColor(R.color.greytranspa2));
                    ngloadview(new SignIn());
                    break;
                case R.id.signup:
                    findViewById(R.id.indpurchase).setBackgroundColor(getApplicationContext().getResources().getColor(R.color.greytranspa2));
                    findViewById(R.id.indbilling).setBackgroundColor(getApplicationContext().getResources().getColor(R.color.colorPrimary));
                    ngloadview(new SignUp());
                    break;
            }
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private boolean ngloadview(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_user, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    void showProgressBar(){
        Log.i(TAG, "showProgressBar: ");
    }

    void hideProgressBar(){
        Log.i(TAG, "hideProgressBar: ");
    }

    void updateUI(FirebaseUser user){
        Log.i(TAG, "updateUI: ");
        if (user!=null){
//            Log.i(TAG, "updateUI sukses : " + user.getEmail());
//            Intent i = new Intent(getApplicationContext(), MainActivity.class);
//            UserPrefs.setLogin(getApplicationContext(), true);
//            startActivity(i);
//            finish();
            ngloadview(new Loader());
            UserPembeliModel usr = new UserPembeliModel();
            usr.setEmail_pembeli(user.getEmail());
            usr.setNama_pembeli(user.getDisplayName());
            usr.setNo_telp(user.getPhoneNumber());
            usr.setProvinsi_pembeli("0");
            usr.setKabupaten_pembeli("0");
            usr.setKecamatan_pembeli("0");
            usr.setKode_pos_pembeli("0");
            usr.setAlamat_pembeli("0");
            Log.i(TAG, "updateUI: "+user.getPhoneNumber());
            if (user.getPhoneNumber()==null){
                usr.setNo_telp("0");
            }

            new UserReqs(this, requestQueue).daftar(res, usr, "x", USER_DAFTAR);
        }
    }


    private Response.Listener<String> res = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i("RESPON", response);
            try {
                JSONObject object = new JSONObject(response);
                if (response.contains("message")){
                    Toast.makeText(Login.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                    return;
                }
                UserPrefs.setLogin(getApplicationContext(), true);
                UserPrefs.setId(object.getString("id"), getApplicationContext());
                UserPrefs.setAlamat(object.getString("alamat"), getApplicationContext());
                UserPrefs.setEmail(object.getString("email"), getApplicationContext());
                UserPrefs.setNama(object.getString("name"), getApplicationContext());
                UserPrefs.setNo_telp(object.getString("hp"), getApplicationContext());
                UserPrefs.setProvinsi(object.getString("provinsi"), getApplicationContext());
                UserPrefs.setKabupaten(object.getString("kabupaten"), getApplicationContext());
                UserPrefs.setKecamatan(object.getString("kecamatan"), getApplicationContext());
                UserPrefs.setKode_pos(object.getString("kode_pos"), getApplicationContext());
                UserPrefs.setUrl_profil(object.getString("url_profil"), getApplicationContext());
                UserPrefs.setNamakab(object.getString("namakota"), getApplicationContext());
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                FirebaseMessaging.getInstance().subscribeToTopic("user"+object.getString("id"));
                startActivity(i);
                finish();
            } catch (JSONException e) {
                Log.i("RESPON EROR", e.getMessage());
            }
        }
    };

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        showProgressBar();

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }

                        hideProgressBar();
                    }
                });
    }
}
