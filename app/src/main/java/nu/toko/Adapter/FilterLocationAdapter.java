package nu.toko.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nu.toko.Model.AddressModel;
import nu.toko.R;

public class FilterLocationAdapter extends RecyclerView.Adapter<FilterLocationAdapter.ViewHolder>{

    Activity activity;
    ArrayList<String> items;
    ArrayList<String> nama;
    OnClick onItemClickListener;

    public FilterLocationAdapter(Activity activity, ArrayList<String> items, ArrayList<String> nama){
        this.activity = activity;
        this.items = items;
        this.nama = nama;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.modelitem_filterloc, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.kabkot.setText(nama.get(position));
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

        TextView kabkot;

        public ViewHolder(final View itemView) {
            super(itemView);
            kabkot = itemView.findViewById(R.id.kabkot);
        }
    }

    public void setOnItemClickListener(OnClick clickListener) {
        onItemClickListener = clickListener;
    }
    public interface OnClick {
        void onItemClick(String addressModel);
    }
}
