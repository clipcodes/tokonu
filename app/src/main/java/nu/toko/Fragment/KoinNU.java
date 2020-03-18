package nu.toko.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nu.toko.Adapter.NewsAdapter;
import nu.toko.Adapter.Product2Adapter;
import nu.toko.Model.NewsModel;
import nu.toko.Page.DetailNews;
import nu.toko.R;
import nu.toko.Reqs.ReqString;
import nu.toko.Sqlite.FavoritesDB;

import static nu.toko.Utils.Staticvar.BERITA;
import static nu.toko.Utils.Staticvar.DESK_BERITA_KOIN_NU;
import static nu.toko.Utils.Staticvar.GAMBAR_BERITA;
import static nu.toko.Utils.Staticvar.ID_BERITA_KOIN_NU;
import static nu.toko.Utils.Staticvar.JUDUL_BERITA_KOIN_NU;
import static nu.toko.Utils.Staticvar.PRODUCTHOME;
import static nu.toko.Utils.Staticvar.PUBLISHING_KOIN_NU;
import static nu.toko.Utils.Staticvar.STATUSBYPAGE;
import static nu.toko.Utils.Staticvar.TGL_BERITA_KOIN_NU;

public class KoinNU extends Fragment {

    RecyclerView rvberita;
    NewsAdapter newsAdapter;
    List<NewsModel> newsModelList;
    RequestQueue requestQueue;
    String TAG = getClass().getSimpleName();
    boolean isLoadData = false;
    int PAGE = 1;
    NestedScrollView nested;
    AVLoadingIndicatorView lodingbot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.page_koinnu, container, false);

        init(root);
        new ReqString(getActivity(), requestQueue).go(sukses, BERITA);
        return root;
    }
    
    void init(View root){
        requestQueue = Volley.newRequestQueue(getActivity());
        newsModelList = new ArrayList<>();
        nested = root.findViewById(R.id.nested);
        lodingbot = root.findViewById(R.id.lodingbot);
        rvberita = root.findViewById(R.id.rvberita);
        newsAdapter = new NewsAdapter(getActivity(), newsModelList);
        rvberita.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
        rvberita.setAdapter(newsAdapter);

        newsAdapter.setOnItemClickListener(new NewsAdapter.OnClick() {
            @Override
            public void onItemClick(NewsModel newsModel) {
                Intent i = new Intent(getActivity(), DetailNews.class);
                i.putExtra(DESK_BERITA_KOIN_NU, newsModel.getDesk_berita_koin_nu());
                i.putExtra(JUDUL_BERITA_KOIN_NU, newsModel.getJudul_berita_koin_nu());
                i.putExtra(GAMBAR_BERITA, newsModel.getGambar_berita());
                i.putExtra(TGL_BERITA_KOIN_NU, newsModel.getTgl_berita_koin_nu().split(" ")[0]);
                i.putExtra(PUBLISHING_KOIN_NU, newsModel.getPublishing_koin_nu());
                startActivity(i);
            }
        });
        onscroll();
    }

    private void onscroll(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            nested.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if(v.getChildAt(v.getChildCount() - 1) != null) {
                        if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) && scrollY > oldScrollY && isLoadData) {
                            lodingbot.setVisibility(View.VISIBLE);
                            isLoadData = false;
                            new ReqString(getActivity(), requestQueue).go(sukses, BERITA+STATUSBYPAGE+PAGE);
                        }
                    }
                }
            });
        }
    }

    Response.Listener<String> sukses = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i(TAG, "onResponse: "+response);
            try {
                JSONObject json = new JSONObject(response);
                JSONArray jsonArray = json.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    NewsModel newsModel = new NewsModel();
                    newsModel.setId_berita_koin_nu(jsonObject.getString(ID_BERITA_KOIN_NU));
                    newsModel.setDesk_berita_koin_nu(jsonObject.getString(DESK_BERITA_KOIN_NU));
                    newsModel.setGambar_berita(jsonObject.getString(GAMBAR_BERITA));
                    newsModel.setJudul_berita_koin_nu(jsonObject.getString(JUDUL_BERITA_KOIN_NU));
                    newsModel.setPublishing_koin_nu(jsonObject.getString(PUBLISHING_KOIN_NU));
                    newsModel.setTgl_berita_koin_nu(jsonObject.getString(TGL_BERITA_KOIN_NU));

                    newsModelList.add(newsModel);
                }
                newsAdapter.notifyDataSetChanged();

                lodingbot.setVisibility(View.GONE);
                if (jsonArray.length()<=0){
                    isLoadData = false;
                } else {
                    isLoadData = true;
                }
                PAGE+= 1;
            } catch (JSONException e){
                Log.i(TAG, "onResponse:err "+e.getMessage());
            }
        }
    };
}