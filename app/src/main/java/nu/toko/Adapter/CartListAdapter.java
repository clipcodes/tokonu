package nu.toko.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.w3c.dom.Text;

import java.util.List;

import nu.toko.Model.CartListModel;
import nu.toko.Model.ProductModelNU;
import nu.toko.R;
import nu.toko.Sqlite.CartDB;

import static nu.toko.Utils.Staticvar.DOMAIN;
import static nu.toko.Utils.Staticvar.FOTOPRODUK;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder>{

    Activity activity;
    List<ProductModelNU> items;
    OnClick onItemClickListener;

    public CartListAdapter(Activity activity, List<ProductModelNU> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.modelitem_cart, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ImageLoader.getInstance().displayImage(FOTOPRODUK + items.get(position).getGambarfirst(), holder.productimg);
        holder.productname.setText(items.get(position).getNama_produk());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CartDB(activity).delete(items.get(position).getId_produk());
                items.remove(position);
                onItemClickListener.onItemClick();
                notifyDataSetChanged();
            }
        });
        holder.qty.setText(String.valueOf(items.get(position).getQty()));
        holder.quantity = items.get(position).getQty();
        holder.stok = Integer.valueOf(items.get(position).getStok());
        holder.sisastok.setText("Sisa "+String.valueOf(holder.stok-1)+" Stok");

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.stok == 0)return;
                holder.quantity += 1;
                holder.stok = Integer.valueOf(items.get(position).getStok()) - holder.quantity;
                holder.sisastok.setText("Sisa "+String.valueOf(holder.stok)+" Stok");
                holder.qty.setText(String.valueOf(holder.quantity));
                items.get(position).setQty(holder.quantity);
                onItemClickListener.onItemClick();
            }
        });
        holder.reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.quantity == 1)return;
                holder.quantity -= 1;
                holder.stok = Integer.valueOf(items.get(position).getStok()) - holder.quantity;
                holder.sisastok.setText("Sisa "+String.valueOf(holder.stok)+" Stok");
                holder.qty.setText(String.valueOf(holder.quantity));
                items.get(position).setQty(holder.quantity);
                onItemClickListener.onItemClick();
            }
        });
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                items.get(position).setChecked(isChecked);
                onItemClickListener.onItemClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        TextView productname, productprice, qty;
        int quantity;
        int stok;
        CheckBox checkBox;
        TextView sisastok;
        FrameLayout delete;
        LinearLayout reduce, add;
        ImageView productimg;

        public ViewHolder(final View itemView) {
            super(itemView);
            productname = itemView.findViewById(R.id.productname);
            productprice = itemView.findViewById(R.id.productprice);
            delete = itemView.findViewById(R.id.delete);
            reduce = itemView.findViewById(R.id.reduce);
            add = itemView.findViewById(R.id.add);
            qty = itemView.findViewById(R.id.qty);
            sisastok = itemView.findViewById(R.id.sisastok);
            checkBox = itemView.findViewById(R.id.checkBox);
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
