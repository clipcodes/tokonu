package nu.toko.Fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nu.toko.Adapter.FeedAdapter;
import nu.toko.Adapter.Product2Adapter;
import nu.toko.Model.FeedModel;
import nu.toko.R;
import nu.toko.Reqs.ReqString;
import nu.toko.Sqlite.FavoritesDB;

import static nu.toko.Utils.Staticvar.insfeed;

public class Feed extends Fragment {

    String TAG = getClass().getSimpleName();
    RecyclerView rvfeed;
    FeedAdapter feedAdapter;
    List<FeedModel> feedModelList;
    ReqString reqString;
    RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.page_feed, container, false);

        init(root);
        reqString.go(respon, insfeed());

        return root;
    }
    
    void init(View root){
        requestQueue = Volley.newRequestQueue(getActivity());
        reqString = new ReqString(getActivity(), requestQueue);
        rvfeed = root.findViewById(R.id.rvfeed);
        feedModelList = new ArrayList<>();
        feedAdapter = new FeedAdapter(getActivity(), feedModelList);
        rvfeed.setLayoutManager(new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false));
        rvfeed.setAdapter(feedAdapter);

        feedAdapter.setOnItemClickListener(new FeedAdapter.OnClick() {
            @Override
            public void onItemClick(FeedModel feed) {
                Uri uri = Uri.parse("http://instagram.com/p/"+feed.getShortcode());
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/p/"+feed.getShortcode())));
                }
            }
        });
    }

    Response.Listener<String> respon = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.i(TAG, "onResponse: "+response);
            try {
                JSONObject json = new JSONObject(response);
                JSONObject data = json.getJSONObject("data");
                JSONObject user = data.getJSONObject("user");
                JSONObject edgeO = user.getJSONObject("edge_owner_to_timeline_media");
                JSONArray edges = edgeO.getJSONArray("edges");
                for (int i = 0; i < edges.length(); i++){
                    JSONObject object = edges.getJSONObject(i);
                    JSONObject node = object.getJSONObject("node");

                    FeedModel feedModel = new FeedModel();
                    feedModel.setDisplay_url(node.getString("display_url"));
                    feedModel.setShortcode(node.getString("shortcode"));

                    feedModelList.add(feedModel);
                }

                feedAdapter.notifyDataSetChanged();
            } catch (JSONException e){
                Log.i(TAG, "onResponse: ERR "+e.getMessage());
            }
        }
    };
}