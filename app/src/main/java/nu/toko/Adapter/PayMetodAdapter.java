package nu.toko.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nu.toko.Model.AddressModel;
import nu.toko.Model.PayMethodModel;
import nu.toko.R;

public class PayMetodAdapter extends RecyclerView.Adapter<PayMetodAdapter.ViewHolder>{

    Activity activity;
    List<PayMethodModel> items;
    OnClick onItemClickListener;

    public PayMetodAdapter(Activity activity, List<PayMethodModel> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.modelitem_paymethod, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.paymentmethod.setText(items.get(position).getMethod());
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

        TextView paymentmethod;

        public ViewHolder(final View itemView) {
            super(itemView);
            paymentmethod = itemView.findViewById(R.id.paymentmethod);
        }
    }

    public void setOnItemClickListener(OnClick clickListener) {
        onItemClickListener = clickListener;
    }
    public interface OnClick {
        void onItemClick(PayMethodModel addressModel);
    }
}
