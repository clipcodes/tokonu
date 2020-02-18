package nu.toko.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nu.toko.Model.OngkosKirimModel;
import nu.toko.Model.ProvModel;
import nu.toko.R;
import nu.toko.Utils.Others;

public class KurirOngkosAdapter extends RecyclerView.Adapter<KurirOngkosAdapter.ViewHolder>{

    Activity activity;
    List<OngkosKirimModel> items;
    OnClick onItemClickListener;

    public KurirOngkosAdapter(Activity activity, List<OngkosKirimModel> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.modelitem_kurirongkos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.kurir.setText(items.get(position).getKurir());
        holder.ongkos.setText("Rp."+Others.PercantikHarga(Integer.valueOf(items.get(position).getValue())));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        TextView kurir, ongkos;

        public ViewHolder(final View itemView) {
            super(itemView);
            kurir = itemView.findViewById(R.id.kurir);
            ongkos = itemView.findViewById(R.id.ongkos);
        }
    }

    public void setOnItemClickListener(OnClick clickListener) {
        onItemClickListener = clickListener;
    }
    public interface OnClick {
        void onItemClick(ProvModel addressModel);
    }
}
