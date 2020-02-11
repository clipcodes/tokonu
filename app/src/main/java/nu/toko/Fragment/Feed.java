package nu.toko.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import nu.toko.Adapter.Product2Adapter;
import nu.toko.R;
import nu.toko.Sqlite.FavoritesDB;

public class Feed extends Fragment {

    RecyclerView rvproduct;
    Product2Adapter product2Adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.page_feed, container, false);

        init(root);

        return root;
    }
    
    void init(View root){
    }
}