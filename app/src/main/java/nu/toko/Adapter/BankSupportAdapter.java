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

import java.util.List;

import nu.toko.Model.BankSupportModel;
import nu.toko.Model.ProductModelNU;
import nu.toko.R;
import nu.toko.Sqlite.CartDB;

import static nu.toko.Utils.Staticvar.FOTOPRODUK;

public class BankSupportAdapter extends RecyclerView.Adapter<BankSupportAdapter.ViewHolder>{

    int selected = -1;
    Activity activity;
    List<BankSupportModel> items;
    OnClick onItemClickListener;

    public BankSupportAdapter(Activity activity, List<BankSupportModel> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.modelitem_banksupport, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.nama.setText(items.get(position).getNama());
        holder.bank.setText(items.get(position).getBank());
        holder.norek.setText(items.get(position).getNorek());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected == position){
                    selected = -1;
                    holder.container.setBackgroundColor(activity.getResources().getColor(R.color.greytranspa0));
                }
                onItemClickListener.onItemClick(items.get(position));
                holder.container.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimarytranspa1));
                selected = position;
                notifyDataSetChanged();
            }
        });

        if (selected == position)
            holder.container.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimarytranspa1));
        else
            holder.container.setBackgroundColor(activity.getResources().getColor(R.color.greytranspa0));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout container;
        TextView nama, bank, norek;
        public ViewHolder(final View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            nama = itemView.findViewById(R.id.nama);
            norek = itemView.findViewById(R.id.norek);
            bank = itemView.findViewById(R.id.bank);
        }
    }

    public void setOnItemClickListener(OnClick clickListener) {
        onItemClickListener = clickListener;
    }
    public interface OnClick {
        void onItemClick(BankSupportModel bs);
    }
}
