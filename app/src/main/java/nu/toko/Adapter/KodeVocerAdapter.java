package nu.toko.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nu.toko.Model.KodeVocerModel;
import nu.toko.R;

public class KodeVocerAdapter extends RecyclerView.Adapter<KodeVocerAdapter.ViewHolder>{

    Activity activity;
    List<KodeVocerModel> items;
    OnClick onItemClickListener;

    public KodeVocerAdapter(Activity activity, List<KodeVocerModel> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.modelitem_kodevocer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.kode.setText(items.get(position).getKode());
        holder.ket.setText(items.get(position).getKeterangan());
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

        TextView kode, ket;

        public ViewHolder(final View itemView) {
            super(itemView);
            kode = itemView.findViewById(R.id.kode);
            ket = itemView.findViewById(R.id.ket);
        }
    }

    public void setOnItemClickListener(OnClick clickListener) {
        onItemClickListener = clickListener;
    }

    public interface OnClick {
        void onItemClick(KodeVocerModel newsModel);
    }
}
