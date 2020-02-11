package nu.toko.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nu.toko.Model.ChatsModel;
import nu.toko.R;

public class ChatsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Activity activity;
    List<ChatsModel> items;
    OnClick onItemClickListener;
    private static final int MODEL_ONE = 1;

    public ChatsAdapter(Activity activity, List<ChatsModel> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        return MODEL_ONE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MODEL_ONE) {
            //Inflating recycle view item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelitem_chats, parent, false);
            return new One(itemView);
        } else return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof One) {
            One headerHolder = (One) holder;
            headerHolder.init(items.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private class One extends RecyclerView.ViewHolder {

        TextView namalengkap, last, tanggal;
        ImageView images, dibaca;

        public One(View view) {
            super(view);
        }

        public void init(final ChatsModel data){
            images = itemView.findViewById(R.id.images);
            namalengkap = itemView.findViewById(R.id.namalengkap);
            last = itemView.findViewById(R.id.last);
            tanggal = itemView.findViewById(R.id.tanggal);
            dibaca = itemView.findViewById(R.id.dibaca);
            namalengkap.setText(data.getNamauser());
            last.setText(data.getLast());
            tanggal.setText(data.getCreated());
            if (data.getDibaca()==1){
                dibaca.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(data);
                }
            });
        }
    }

    public void setOnItemClickListener(OnClick clickListener) {
        onItemClickListener = clickListener;
    }
    public interface OnClick {
        void onItemClick(ChatsModel chatsModel);
    }
}
