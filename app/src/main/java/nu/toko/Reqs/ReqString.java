package nu.toko.Reqs;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
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
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import nu.toko.Model.TransaksiModel;
import nu.toko.Model.UserPembeliModel;
import nu.toko.Utils.UserPrefs;

import static nu.toko.Utils.Staticvar.DOMAIN;
import static nu.toko.Utils.Staticvar.FOTOBUKTI;
import static nu.toko.Utils.Staticvar.PENGADUAN;

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

    public void multipart(Response.Listener<String> responstatus, String url, final String idtrans, final String tanggal, final String nama, final String norek, final String bank, final String nominal){
        StringRequest smr = new StringRequest(Request.Method.POST, url,
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
        }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("id_transaksi", idtrans);
                params.put("tgl_transaksi", tanggal);
                params.put("namalengkap", nama);
                params.put("norek", norek);
                params.put("bank", bank);
                params.put("nominal", nominal);
                return params;
            }
        };

//        smr.addStringParam("id_transaksi", idtrans);
//        smr.addStringParam("tgl_transaksi", tanggal);
//        smr.addStringParam("namalengkap", nama);
//        smr.addStringParam("norek", norek);
//        smr.addStringParam("bank", bank);
//        smr.addStringParam("nominal", nominal);
//        smr.addFile("images", uri.toString());

//        smr.add("id_transaksi", "xxx");
//        smr.addStringParam("tgl_transaksi", "xxx");
//        smr.addStringParam("namalengkap", "xxx");
//        smr.addStringParam("norek", "xxx");
//        smr.addStringParam("bank", "xxx");
//        smr.addStringParam("nominal", "xxxx");
        requestQueue.add(smr);
    }

    public void bantuan(Response.Listener<String> responstatus, final String pengaduan){
        StringRequest smr = new StringRequest(Request.Method.POST, PENGADUAN,
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
        }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("id_pembeli", UserPrefs.getId(activity));
                params.put("pengaduan", pengaduan);
                return params;
            }
        };
        requestQueue.add(smr);
    }

    public void donasi(Response.Listener<String> responstatus, final String fotobukti, final String namalengkap, final String jumlah, String url){
        StringRequest smr = new StringRequest(Request.Method.POST, url,
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
        }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("nama_user", namalengkap);
                params.put("jumlah_donasi", jumlah);
                params.put("fotobukti", fotobukti);
                return params;
            }
        };
        requestQueue.add(smr);
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public void foto(final String nama, final Bitmap bitmap, Response.Listener<NetworkResponse> res, String url) {
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                res,
                new Response.ErrorListener() {
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
                })  {

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                params.put("images", new DataPart(nama + ".jpg", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        Volley.newRequestQueue(activity).add(volleyMultipartRequest);
    }
}
