package nu.toko.Fragment;

import android.app.Activity;
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
import com.google.android.gms.common.SignInButton;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import nu.toko.MainActivity;
import nu.toko.R;
import nu.toko.Reqs.UserReqs;
import nu.toko.Utils.UserPrefs;

import static nu.toko.Utils.Staticvar.USER_LOGIN;

public class Loader extends Fragment {

    public Loader() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.users_signin, container, false);
    }

}