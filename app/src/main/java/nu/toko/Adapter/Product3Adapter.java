package nu.toko.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nu.toko.Model.ProductModel;
import nu.toko.Model.ProductModelNU;
import nu.toko.Page.Details;
import nu.toko.R;

public class Product3Adapter extends RecyclerView.Adapter<Product3Adapter.ViewHolder>{

    Activity activity;
    List<ProductModelNU> items;
    OnClickBuy onClickBuy;

    public Product3Adapter(Activity activity, List<ProductModelNU> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.modelitem_product3, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.title.setText(items.get(position).getNama_produk());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, Details.class);
                activity.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public ViewHolder(final View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
    }

    public void setOnBuyClickListener(OnClickBuy onClickBuy) {
        this.onClickBuy = onClickBuy;
    }
    public interface OnClickBuy {
        void onClick(ProductModel productModel);
    }
}
