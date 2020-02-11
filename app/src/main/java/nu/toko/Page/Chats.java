package nu.toko.Page;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

import nu.toko.Adapter.ChatsAdapter;
import nu.toko.Model.ChatsModel;
import nu.toko.R;
import nu.toko.Utils.UserPrefs;

public class Chats extends AppCompatActivity {

    RecyclerView rvchats;
    List<ChatsModel> chatsModelList;
    ChatsAdapter chatsAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_chats);

        activity = this;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("chat");
        init();
    }

    void init(){
        chatsModelList = new ArrayList<>();
        rvchats = findViewById(R.id.rvchats);
        chatsAdapter = new ChatsAdapter(activity, chatsModelList);
        rvchats.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        rvchats.setAdapter(chatsAdapter);

        chatsAdapter.setOnItemClickListener(new ChatsAdapter.OnClick() {
            @Override
            public void onItemClick(ChatsModel chatsModel) {
                Intent i = new Intent(getApplicationContext(), ChatRoom.class);
                i.putExtra("id_mitra", chatsModel.getTo());
                i.putExtra("namamitra", chatsModel.getNamauser());
                startActivity(i);
            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        chatsModelList.clear();
        chatsAdapter.notifyDataSetChanged();
        ngisi();
        super.onResume();
    }

    public void ngisi(){
        Query myTopPostsQuery = databaseReference.child("user"+ UserPrefs.getId(getApplicationContext())).orderByChild("created");
        myTopPostsQuery.addChildEventListener(
                new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Log.i("KEY", dataSnapshot.getKey());
                        ChatsModel c = dataSnapshot.getValue(ChatsModel.class);
                        chatsModelList.add(0, c);
                        chatsAdapter.notifyItemInserted(0);
                        rvchats.scrollToPosition(0);
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Log.i("KEY", dataSnapshot.getKey());
//                        ChatsModel c = dataSnapshot.getValue(ChatsModel.class);
//                        chatsModelList.add(0, c);
//                        chatsAdapter.notifyItemInserted(0);
//                        chatsAdapter.notifyItemRangeChanged(1, chatsModelList.size());
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
