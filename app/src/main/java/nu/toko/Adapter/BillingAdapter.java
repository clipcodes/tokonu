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
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import nu.toko.Model.BillingModel;
import nu.toko.Page.DetailPayment;
import nu.toko.R;

public class BillingAdapter extends RecyclerView.Adapter<BillingAdapter.ViewHolder>{

    Activity activity;
    List<BillingModel> items;
    OnClick onItemClickListener;

    public BillingAdapter(Activity activity, List<BillingModel> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.modelitem_billing, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (items.get(position).getImg()!=null){
            try {
                InputStream ims = activity.getAssets().open("images/"+items.get(position).getImg());
                Drawable d = Drawable.createFromStream(ims, null);
                holder.thumb.setImageDrawable(d);
            }
            catch(IOException ex) {
                Log.i("PHOTO ERR", ex.getMessage());
            }
        }
        holder.productname.setText(items.get(position).getProductname());
        holder.productprice.setText("$"+items.get(position).getPrice());
        holder.completenow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, DetailPayment.class);
                activity.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        CardView completenow;
        TextView productname, productprice;
        ImageView thumb;

        public ViewHolder(final View itemView) {
            super(itemView);
            thumb = itemView.findViewById(R.id.thumb);
            productname = itemView.findViewById(R.id.productname);
            productprice = itemView.findViewById(R.id.productprice);
            completenow = itemView.findViewById(R.id.completenow);
        }
    }

    public void setOnItemClickListener(OnClick clickListener) {
        onItemClickListener = clickListener;
    }
    public interface OnClick {
        void onItemClick();
    }
}
