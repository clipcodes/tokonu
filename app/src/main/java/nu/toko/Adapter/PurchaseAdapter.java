package nu.toko.Adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import nu.toko.Model.PurchaseModel;
import nu.toko.R;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.ViewHolder>{

    Activity activity;
    List<PurchaseModel> items;
    OnClick onItemClickListener;

    public PurchaseAdapter(Activity activity, List<PurchaseModel> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.modelitem_purchase, parent, false);

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

        if (items.get(position).getStatus()==1){
            holder.paystatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
        }
        if (items.get(position).getStatus()==2){
            holder.paystatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
            holder.waitingstatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
        }
        if (items.get(position).getStatus()==3){
            holder.paystatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
            holder.waitingstatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
            holder.packingstatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
        }
        if (items.get(position).getStatus()==4){
            holder.paystatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
            holder.waitingstatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
            holder.packingstatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
            holder.deliverystatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
        }
        if (items.get(position).getStatus()==5){
            holder.paystatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
            holder.waitingstatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
            holder.packingstatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
            holder.deliverystatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
            holder.donestatus.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView paystatus, waitingstatus, packingstatus, deliverystatus, donestatus;
        TextView productname, productprice;
        ImageView thumb;

        public ViewHolder(final View itemView) {
            super(itemView);
            thumb = itemView.findViewById(R.id.thumb);
            productname = itemView.findViewById(R.id.productname);
            productprice = itemView.findViewById(R.id.productprice);
            paystatus = itemView.findViewById(R.id.paystatus);
            waitingstatus = itemView.findViewById(R.id.waitingstatus);
            packingstatus = itemView.findViewById(R.id.packingstatus);
            deliverystatus = itemView.findViewById(R.id.deliverystatus);
            donestatus = itemView.findViewById(R.id.donestatus);
        }
    }

    public void setOnItemClickListener(OnClick clickListener) {
        onItemClickListener = clickListener;
    }
    public interface OnClick {
        void onItemClick();
    }
}
