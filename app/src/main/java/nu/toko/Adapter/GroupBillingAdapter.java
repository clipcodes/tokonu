package nu.toko.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import nu.toko.Model.BillingModel;
import nu.toko.Model.BillingModelNU;
import nu.toko.Page.DetailPayment;
import nu.toko.R;
import nu.toko.Utils.Others;

public class GroupBillingAdapter extends RecyclerView.Adapter<GroupBillingAdapter.ViewHolder>{

    Activity activity;
    List<BillingModelNU> items;
    OnClick onItemClickListener;

    public GroupBillingAdapter(Activity activity, List<BillingModelNU> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.modelitem_groupbill, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tanggalpemesanan.setText(items.get(position).getTgl_pemesanan());
        holder.statustex.setText(Others.TransactionStatus(items.get(position).getStatus_transaksi()));

        holder.bilitemadap = new BillingItemAdapter(activity, items.get(position).getBillingItemModels());
        holder.itemtrans.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        holder.itemtrans.setAdapter(holder.bilitemadap);

        holder.paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(items.get(position));
            }
        });

        if (items.get(position).getStatus_transaksi()!=0){
            holder.paynow.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        CardView paynow;
        RecyclerView itemtrans;
        BillingItemAdapter bilitemadap;
        TextView tanggalpemesanan, statustex;

        public ViewHolder(final View itemView) {
            super(itemView);
            paynow = itemView.findViewById(R.id.paynow);
            itemtrans = itemView.findViewById(R.id.itemtrans);
            tanggalpemesanan = itemView.findViewById(R.id.tanggalpemesanan);
            statustex = itemView.findViewById(R.id.statustex);
        }
    }

    public void setOnItemClickListener(OnClick clickListener) {
        onItemClickListener = clickListener;
    }
    public interface OnClick {
        void onItemClick(BillingModelNU billingModelNU);
    }
}
