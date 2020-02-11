package nu.toko.Page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nu.toko.Adapter.NotificationAdapter;
import nu.toko.Model.NotificationModel;
import nu.toko.R;

public class Notification extends Fragment {

    NotificationAdapter notificationAdapter;
    RecyclerView rvnotification;
    List<NotificationModel> notificationModelList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.page_notification, container, false);

        init(root);
        data();

        return root;
    }

    void init(View root){
        rvnotification = root.findViewById(R.id.rvnotification);
        notificationModelList = new ArrayList<>();
        notificationAdapter = new NotificationAdapter(getActivity(), notificationModelList);
        rvnotification.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvnotification.setAdapter(notificationAdapter);
    }

    void data(){
        notificationModelList.add(new NotificationModel(
              1, "Product Received", 1, 5, "20/10/2019"
        ));
        notificationModelList.add(new NotificationModel(
                2, "Product Received", 1, 5, "20/10/2019"
        ));
        notificationModelList.add(new NotificationModel(
                3, "Product Received", 1, 5, "20/10/2019"
        ));
        notificationModelList.add(new NotificationModel(
                4, "Product Received", 1, 5, "20/10/2019"
        ));
    }
}