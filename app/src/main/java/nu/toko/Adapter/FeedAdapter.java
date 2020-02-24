package nu.toko.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import nu.toko.Model.FeedModel;
import nu.toko.Model.NewsModel;
import nu.toko.R;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder>{

    Activity activity;
    List<FeedModel> items;
    OnClick onItemClickListener;

    public FeedAdapter(Activity activity, List<FeedModel> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.modelitem_feed, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ImageLoader.getInstance().displayImage(items.get(position).getDisplay_url(), holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(items.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

//        TextView username;
        ImageView image;

        public ViewHolder(final View itemView) {
            super(itemView);
//            username = itemView.findViewById(R.id.username);
            image = itemView.findViewById(R.id.image);
        }
    }

    public void setOnItemClickListener(OnClick clickListener) {
        onItemClickListener = clickListener;
    }

    public interface OnClick {
        void onItemClick(FeedModel newsModel);
    }
}
