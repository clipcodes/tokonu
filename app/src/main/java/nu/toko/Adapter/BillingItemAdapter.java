package nu.toko.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import nu.toko.Model.BillingItemModel;
import nu.toko.Model.ProductModelNU;
import nu.toko.R;
import nu.toko.Utils.Others;

import static nu.toko.Utils.Staticvar.FOTOPRODUK;

public class BillingItemAdapter extends RecyclerView.Adapter<BillingItemAdapter.ViewHolder>{

    Activity activity;
    List<BillingItemModel> items;
    OnClick onItemClickListener;

    public BillingItemAdapter(Activity activity, List<BillingItemModel> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.modelitem_groupbillitem, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ImageLoader.getInstance().displayImage(FOTOPRODUK + items.get(position).getProduk().getGambarfirst(), holder.productimg);
        holder.productname.setText(items.get(position).getProduk().getNama_produk());
        holder.qty.setText(String.valueOf(items.get(position).getQty())+"x");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        TextView productname, productprice, qty;
        ImageView productimg;

        public ViewHolder(final View itemView) {
            super(itemView);
            productname = itemView.findViewById(R.id.productname);
            productprice = itemView.findViewById(R.id.productprice);
            qty = itemView.findViewById(R.id.qty);
            productimg = itemView.findViewById(R.id.productimg);
        }
    }

    public void setOnItemClickListener(OnClick clickListener) {
        onItemClickListener = clickListener;
    }
    public interface OnClick {
        void onItemClick();
    }
}
