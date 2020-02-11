package nu.toko.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nu.toko.Model.ChatRoomModel;
import nu.toko.R;

public class ChatRoomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Activity activity;
    List<ChatRoomModel> items;
    OnClick onItemClickListener;
    int TYPE = 0;
    private static final int MODEL_ONE = 1;
    private static final int MODEL_TWO = 2;

    public ChatRoomAdapter(Activity activity, List<ChatRoomModel> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position).getUser().equals("user")){
            return MODEL_ONE;
        } else {
            return MODEL_TWO;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MODEL_ONE) {
            //Inflating recycle view item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelitem_chatroomone, parent, false);
            return new One(itemView);
        } else if (viewType == MODEL_TWO) {
            //Inflating recycle view item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelitem_chatroomtwo, parent, false);
            return new One(itemView);
        } else return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof One) {
            One headerHolder = (One) holder;
            headerHolder.init(items.get(position));
        } else if (holder instanceof Two) {
            Two headerHolder = (Two) holder;
            headerHolder.init(items.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private class One extends RecyclerView.ViewHolder {

        TextView msg;

        public One(View view) {
            super(view);
        }

        public void init(ChatRoomModel data){
            msg = itemView.findViewById(R.id.msg);
            msg.setText(data.getMsg());
        }
    }

    private class Two extends RecyclerView.ViewHolder {

        TextView msg;

        public Two(View view) {
            super(view);
        }

        public void init(ChatRoomModel data){
            msg = itemView.findViewById(R.id.msg);
            msg.setText(data.getMsg());
        }
    }

    public void setOnItemClickListener(OnClick clickListener) {
        onItemClickListener = clickListener;
    }
    public interface OnClick {
        void onItemClick();
    }
}
