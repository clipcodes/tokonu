package nu.toko.Adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import nu.toko.Model.BillingModelNU;
import nu.toko.R;
import nu.toko.Utils.Others;

public class GroupBillingHomeAdapter extends RecyclerView.Adapter<GroupBillingHomeAdapter.ViewHolder>{

    Activity activity;
    List<BillingModelNU> items;
    OnClick onItemClickListener;

    public GroupBillingHomeAdapter(Activity activity, List<BillingModelNU> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.modelitem_groupbillhome, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tanggalpemesanan.setText(items.get(position).getTgl_pemesanan());
        holder.statustex.setText(Others.TransactionStatus(items.get(position).getStatus_transaksi()));

        holder.bilitemadap = new BillingItemAdapter(activity, items.get(position).getBillingItemModels());
        holder.itemtrans.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        holder.itemtrans.setAdapter(holder.bilitemadap);

        holder.lihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(items.get(position));
            }
        });

        if (items.get(position).getStatus_transaksi()==1){
            holder.paystatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
        }
        if (items.get(position).getStatus_transaksi()==2){
            holder.paystatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
            holder.waitingstatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
        }
        if (items.get(position).getStatus_transaksi()==3){
            holder.paystatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
            holder.waitingstatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
            holder.packingstatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
            holder.deliverystatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
        }
        if (items.get(position).getStatus_transaksi()==4){
            holder.paystatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
            holder.waitingstatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
            holder.packingstatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
            holder.deliverystatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
            holder.donestatus.setColorFilter(activity.getResources().getColor(R.color.googlecolor));
        }
        if (items.get(position).getStatus_transaksi()==5){
            holder.paystatus.setColorFilter(activity.getResources().getColor(R.color.merah));
            holder.waitingstatus.setColorFilter(activity.getResources().getColor(R.color.merah));
            holder.packingstatus.setColorFilter(activity.getResources().getColor(R.color.merah));
            holder.deliverystatus.setColorFilter(activity.getResources().getColor(R.color.merah));
            holder.donestatus.setColorFilter(activity.getResources().getColor(R.color.merah));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView paystatus, waitingstatus, packingstatus, deliverystatus, donestatus;
        RecyclerView itemtrans;
        BillingItemAdapter bilitemadap;
        CardView lihat;
        TextView tanggalpemesanan, statustex;

        public ViewHolder(final View itemView) {
            super(itemView);
            itemtrans = itemView.findViewById(R.id.itemtrans);
            tanggalpemesanan = itemView.findViewById(R.id.tanggalpemesanan);
            statustex = itemView.findViewById(R.id.statustex);
            paystatus = itemView.findViewById(R.id.paystatus);
            waitingstatus = itemView.findViewById(R.id.waitingstatus);
            packingstatus = itemView.findViewById(R.id.packingstatus);
            deliverystatus = itemView.findViewById(R.id.deliverystatus);
            donestatus = itemView.findViewById(R.id.donestatus);
            lihat = itemView.findViewById(R.id.lihat);
        }
    }

    public void setOnItemClickListener(OnClick clickListener) {
        onItemClickListener = clickListener;
    }
    public interface OnClick {
        void onItemClick(BillingModelNU billingModelNU);
    }
}
