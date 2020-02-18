package nu.toko.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import nu.toko.Model.ProductModel;
import nu.toko.Model.ProductModelNU;
import nu.toko.Page.Details;
import nu.toko.R;
import nu.toko.Utils.Others;

import static nu.toko.Utils.Staticvar.FOTOPRODUK;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{

    Activity activity;
    List<ProductModelNU> items;
    OnClickBuy onClickBuy;

    public ProductAdapter(Activity activity, List<ProductModelNU> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.modelitem_product, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.price.setText("Rp."+ Others.PercantikHarga(Integer.valueOf(items.get(position).getHarga_mitra()+items.get(position).getHarga_admin())));
        ImageLoader.getInstance().displayImage(FOTOPRODUK + items.get(position).getGambarfirst(), holder.thumb);
        holder.title.setText(items.get(position).getNama_produk());
        holder.star.setRating(items.get(position).getRating());
        holder.totalfeedback.setText("("+items.get(position).getTotalfeedback()+")");
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

        ImageView thumb;
        TextView title, price, totalfeedback;
        RatingBar star;

        public ViewHolder(View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.price);
            title = itemView.findViewById(R.id.title);
            thumb = itemView.findViewById(R.id.thumb);
            star = itemView.findViewById(R.id.bintangg);
            totalfeedback = itemView.findViewById(R.id.totalfeed);
        }
    }

    public void setOnBuyClickListener(OnClickBuy onClickBuy) {
        this.onClickBuy = onClickBuy;
    }
    public interface OnClickBuy {
        void onClick(ProductModel productModel);
    }
}
