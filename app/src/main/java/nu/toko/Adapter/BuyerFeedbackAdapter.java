package nu.toko.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nu.toko.Model.BuyerFeedbackModel;
import nu.toko.R;

public class BuyerFeedbackAdapter extends RecyclerView.Adapter<BuyerFeedbackAdapter.ViewHolder>{

    Activity activity;
    List<BuyerFeedbackModel> items;
    OnClick onItemClickListener;

    public BuyerFeedbackAdapter(Activity activity, List<BuyerFeedbackModel> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.modelitem_buyerfeedback, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.comment.setText(items.get(position).getKomen());
        holder.bintangg.setRating((float)items.get(position).getRating());
        holder.user.setText(items.get(position).getNama());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        TextView comment, user;
        RatingBar bintangg;

        public ViewHolder(final View itemView) {
            super(itemView);
            comment = itemView.findViewById(R.id.comment);
            user = itemView.findViewById(R.id.user);
            bintangg = itemView.findViewById(R.id.bintangg);
        }
    }

    public void setOnItemClickListener(OnClick clickListener) {
        onItemClickListener = clickListener;
    }
    public interface OnClick {
        void onItemClick();
    }
}
