package nu.toko.Reqs;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.NetworkError;
import com.android.volley.error.NoConnectionError;
import com.android.volley.error.ParseError;
import com.android.volley.error.ServerError;
import com.android.volley.error.TimeoutError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.request.StringRequest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import nu.toko.Model.UserPembeliModel;

public class UserReqs {

    String TAG = getClass().getSimpleName();
    Activity activity;
    RequestQueue requestQueue;

    public UserReqs(Activity activity, RequestQueue requestQueue) {
        this.activity = activity;
        this.requestQueue = requestQueue;
    }

    public void daftar(Response.Listener<String> responstatus, final UserPembeliModel usr, final String pass, String url){
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, responstatus, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Log.i("RESPON EROR", "TimeoutError NoConnectionError");
                } else if (error instanceof ServerError) {
                    Log.i("RESPON EROR", "ServerError");
                } else if (error instanceof NetworkError) {
                    Log.i("RESPON EROR", "NetworkError");
                } else if (error instanceof ParseError) {
                    Log.i("RESPON EROR", "ParseError");
                }
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("email" , usr.getEmail_pembeli());
                params.put("name" , usr.getNama_pembeli());
                if (pass!=null){
                    params.put("password" , pass);
                }
                params.put("hp" , usr.getNo_telp());
                params.put("provinsi" , usr.getProvinsi_pembeli());
                params.put("kabupaten" , usr.getKabupaten_pembeli());
                params.put("kecamatan" , usr.getKecamatan_pembeli());
                params.put("kode_pos" , usr.getKode_pos_pembeli());
                params.put("alamat" , usr.getAlamat_pembeli());
                return params;
            }
        };

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }

    public void login(Response.Listener<String> responstatus, final String email, final String pass, String url){
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, responstatus, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Log.i("RESPON EROR", "TimeoutError NoConnectionError");
                } else if (error instanceof ServerError) {
                    Log.i("RESPON EROR", "ServerError");
                } else if (error instanceof NetworkError) {
                    Log.i("RESPON EROR", "NetworkError");
                } else if (error instanceof ParseError) {
                    Log.i("RESPON EROR", "ParseError");
                }
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("email" , email);
                params.put("password" , pass);
                return params;
            }
        };

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }

}
