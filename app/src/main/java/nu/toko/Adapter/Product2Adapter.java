package nu.toko.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import nu.toko.Model.ProductModel;
import nu.toko.Page.Details;
import nu.toko.Model.ProductModelNU;
import nu.toko.R;
import nu.toko.Utils.Others;

import static nu.toko.Utils.Staticvar.FOTOPRODUK;

public class Product2Adapter extends RecyclerView.Adapter<Product2Adapter.ViewHolder>{

    Activity activity;
    List<ProductModelNU> items;
    OnClickBuy onClickBuy;

    public Product2Adapter(Activity activity, List<ProductModelNU> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.modeitem_product2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.price.setText("Rp."+ Others.PercantikHarga(Integer.valueOf(items.get(position).getHarga_mitra()+items.get(position).getHarga_admin())));
        ImageLoader.getInstance().displayImage(FOTOPRODUK + items.get(position).getGambarfirst(), holder.thumb);
        holder.title.setText(items.get(position).getNama_produk());
        holder.star.setRating(items.get(position).getRating());
        holder.stock.setText(items.get(position).getStok()+" Stok");
        holder.lokasi.setText(items.get(position).getDikirimdari());
        holder.namatoko.setText(items.get(position).getOwner().getNama_toko_mitra());
        holder.totalfeedback.setText("("+items.get(position).getTotalfeedback()+")");
        if (Integer.valueOf(items.get(position).getStok())<=0){
            holder.habis.setVisibility(View.VISIBLE);
            return;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, Details.class);
                i.putExtra("idproduk", items.get(position).getId_produk());
                i.putExtra("harga", items.get(position).getHarga_mitra()+items.get(position).getHarga_admin());
                i.putExtra("produknama", items.get(position).getNama_produk());
                activity.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, price, totalfeedback, namatoko, lokasi, stock;
        ImageView thumb;
        FrameLayout habis;
        RatingBar star;

        public ViewHolder(final View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            thumb = itemView.findViewById(R.id.thumb);
            stock = itemView.findViewById(R.id.stock);
            habis = itemView.findViewById(R.id.habis);
            star = itemView.findViewById(R.id.star);
            namatoko = itemView.findViewById(R.id.namatoko);
            lokasi = itemView.findViewById(R.id.lokasi);
            totalfeedback = itemView.findViewById(R.id.totalfeedback);
        }
    }

    public void setOnBuyClickListener(OnClickBuy onClickBuy) {
        this.onClickBuy = onClickBuy;
    }
    public interface OnClickBuy {
        void onClick(ProductModel productModel);
    }
}
