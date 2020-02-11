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

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import nu.toko.Model.TransaksiModel;
import nu.toko.Model.UserPembeliModel;

import static nu.toko.Utils.Staticvar.DOMAIN;

public class ReqString {

    String TAG = getClass().getSimpleName();
    Activity activity;
    RequestQueue requestQueue;

    public ReqString(Activity activity, RequestQueue requestQueue) {
        this.activity = activity;
        requestQueue.getCache().clear();
        this.requestQueue = requestQueue;
    }

    public void go(Response.Listener<String> newroom, String url){
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                newroom, new Response.ErrorListener() {
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
        );

        getRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(getRequest);
    }

    public void go(Response.Listener<String> newroom, Response.ErrorListener errorListener, String url){
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                newroom, errorListener
        );

        getRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(getRequest);
    }

    public void pos(Response.Listener<String> responstatus, final String json, String url){
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
                params.put("json" , json);
                return params;
            }
        };

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }

    public void multipart(Response.Listener<String> responstatus, String url, String idtrans, String tanggal, File uri, String nama, String norek, String bank, String nominal){
        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, url,
                responstatus, new Response.ErrorListener() {
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
        });
        smr.addStringParam("id_transaksi", idtrans);
        smr.addStringParam("tgl_transaksi", tanggal);
        smr.addStringParam("namalengkap", nama);
        smr.addStringParam("norek", norek);
        smr.addStringParam("bank", bank);
        smr.addStringParam("nominal", nominal);
        smr.addFile("image", uri.toString());
        requestQueue.add(smr);
    }

    public void donasi(Response.Listener<String> responstatus, String namalengkap, String jumlah, File uri, String url){
        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, url,
                responstatus, new Response.ErrorListener() {
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
        });
        smr.addStringParam("nama_user", namalengkap);
        smr.addStringParam("jumlah_donasi", jumlah);
        smr.addFile("images", uri.toString());
        requestQueue.add(smr);
    }
}
