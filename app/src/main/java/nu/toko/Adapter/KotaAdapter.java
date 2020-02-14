package nu.toko.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nu.toko.Model.KotaModel;
import nu.toko.Model.ProvModel;
import nu.toko.R;

public class KotaAdapter extends RecyclerView.Adapter<KotaAdapter.ViewHolder>{

    Activity activity;
    List<KotaModel> items;
    OnClick onItemClickListener;

    public KotaAdapter(Activity activity, List<KotaModel> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.modelitem_pilihan, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tex.setText(items.get(position).getNama_kota());
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

        TextView tex;

        public ViewHolder(final View itemView) {
            super(itemView);
            tex = itemView.findViewById(R.id.tex);
        }
    }

    public void setOnItemClickListener(OnClick clickListener) {
        onItemClickListener = clickListener;
    }
    public interface OnClick {
        void onItemClick(KotaModel addressModel);
    }
}
