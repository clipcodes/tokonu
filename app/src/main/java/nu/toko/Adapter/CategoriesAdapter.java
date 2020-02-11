package nu.toko.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import nu.toko.Model.CategoriesModelNU;
import nu.toko.Page.Categories;
import nu.toko.R;

import static nu.toko.Utils.Staticvar.DOMAIN;
import static nu.toko.Utils.Staticvar.FOTOKATEGORI;

public class CategoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Activity activity;
    List<CategoriesModelNU> items;
    OnClick onItemClickListener;
    int TYPE;
    private static final int MODEL_ONE = 1;
    private static final int MODEL_TWO = 2;

    public CategoriesAdapter(Activity activity, List<CategoriesModelNU> items, int type){
        this.activity = activity;
        this.items = items;
        this.TYPE = type;
    }

    @Override
    public int getItemViewType(int position) {
        if (TYPE == 1) {
            return MODEL_ONE;
        } else {
            return MODEL_TWO;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MODEL_ONE) {
            //Inflating recycle view item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelitem_categoriesone, parent, false);
            return new One(itemView);
        } else if (viewType == MODEL_TWO) {
            //Inflating header view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelitem_categoriestwo, parent, false);
            return new Two(itemView);
        } else return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof One) {
            One headerHolder = (One) holder;
            headerHolder.init(items.get(position));
        } else if (holder instanceof Two) {
            Two footerHolder = (Two) holder;
            footerHolder.init(items.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private class One extends RecyclerView.ViewHolder {

        TextView categoriesname;
        ImageView circleImageView;

        public One(View view) {
            super(view);
            categoriesname = view.findViewById(R.id.categoriesname);
            circleImageView = view.findViewById(R.id.circleImageView);
        }

        public void init(final CategoriesModelNU categoriesModel){
            categoriesname.setText(categoriesModel.getNama_kategori());
            ImageLoader.getInstance().displayImage(FOTOKATEGORI + categoriesModel.getUrl_gambar_kategori(), circleImageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(activity, Categories.class);
                    i.putExtra("kat", categoriesModel.getNama_kategori());
                    i.putExtra("id", categoriesModel.getId_kategori());
                    activity.startActivity(i);
                }
            });
        }
    }

    private class Two extends RecyclerView.ViewHolder {

        TextView categoriesname;

        public Two(View view) {
            super(view);
            categoriesname = view.findViewById(R.id.categoriesname);
        }

        public void init(final CategoriesModelNU categoriesModel){
            categoriesname.setText(categoriesModel.getNama_kategori());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(categoriesModel);
                }
            });
        }
    }

    public void setOnItemClickListener(OnClick clickListener) {
        onItemClickListener = clickListener;
    }
    public interface OnClick {
        void onItemClick(CategoriesModelNU categoriesModelNU);
    }
}
