package nu.toko.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nu.toko.Model.AddressModel;
import nu.toko.Model.KurirModel;
import nu.toko.R;

public class KurirChooseAdapter extends RecyclerView.Adapter<KurirChooseAdapter.ViewHolder>{

    Activity activity;
    List<KurirModel> items;
    OnClick onItemClickListener;

    public KurirChooseAdapter(Activity activity, List<KurirModel> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.modelitem_kurirchoose, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.kurir.setText(items.get(position).getKurir());
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

        TextView kurir;

        public ViewHolder(final View itemView) {
            super(itemView);
            kurir = itemView.findViewById(R.id.kurir);
        }
    }

    public void setOnItemClickListener(OnClick clickListener) {
        onItemClickListener = clickListener;
    }
    public interface OnClick {
        void onItemClick(KurirModel addressModel);
    }
}
