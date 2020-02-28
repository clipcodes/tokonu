package nu.toko.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nu.toko.Model.AddressModel;
import nu.toko.Model.KoinNuModel;
import nu.toko.R;
import nu.toko.Utils.Others;

public class KoinNuAdapter extends RecyclerView.Adapter<KoinNuAdapter.ViewHolder>{

    Activity activity;
    List<KoinNuModel> items;
    OnClick onItemClickListener;

    public KoinNuAdapter(Activity activity, List<KoinNuModel> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.modelitem_koinnu, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.nominal.setText("Rp."+Others.PercantikHarga(items.get(position).getNominal()));
        holder.tanggal.setText(items.get(position).getTanggal());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        TextView nominal, tanggal;

        public ViewHolder(final View itemView) {
            super(itemView);
            nominal = itemView.findViewById(R.id.nominal);
            tanggal = itemView.findViewById(R.id.tanggal);
        }
    }

    public void setOnItemClickListener(OnClick clickListener) {
        onItemClickListener = clickListener;
    }
    public interface OnClick {
        void onItemClick(AddressModel addressModel);
    }
}
