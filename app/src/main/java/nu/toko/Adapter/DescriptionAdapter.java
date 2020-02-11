package nu.toko.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import nu.toko.R;

public class DescriptionAdapter extends RecyclerView.Adapter<DescriptionAdapter.ViewHolder>{

    Activity activity;
    String[] items;
    OnClick onItemClickListener;

    public DescriptionAdapter(Activity activity, String[] items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.modelitem_description, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.description.setText(items[position]);
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        TextView description;

        public ViewHolder(final View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.description);
        }
    }

    public void setOnItemClickListener(OnClick clickListener) {
        onItemClickListener = clickListener;
    }
    public interface OnClick {
        void onItemClick();
    }
}
