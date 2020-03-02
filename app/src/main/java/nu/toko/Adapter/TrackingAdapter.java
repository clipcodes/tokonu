package nu.toko.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nu.toko.Model.KotaModel;
import nu.toko.Model.TrackingModel;
import nu.toko.R;

public class TrackingAdapter extends RecyclerView.Adapter<TrackingAdapter.ViewHolder>{

    Activity activity;
    List<TrackingModel> items;
    OnClick onItemClickListener;

    public TrackingAdapter(Activity activity, List<TrackingModel> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.modelitem_tracking, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.city.setText(items.get(position).getCity_name());
        holder.jam.setText(items.get(position).getManifest_time());
        holder.date.setText("Pada : "+items.get(position).getManifest_date());
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

        TextView date, city, jam;

        public ViewHolder(final View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            city = itemView.findViewById(R.id.city);
            jam = itemView.findViewById(R.id.jam);
        }
    }

    public void setOnItemClickListener(OnClick clickListener) {
        onItemClickListener = clickListener;
    }
    public interface OnClick {
        void onItemClick(TrackingModel trackingModel);
    }
}
