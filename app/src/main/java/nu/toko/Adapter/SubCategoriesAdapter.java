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

import static nu.toko.Utils.Staticvar.FOTOKATEGORI;
import static nu.toko.Utils.Staticvar.FOTOSUBKATEGORI;

public class SubCategoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Activity activity;
    List<CategoriesModelNU> items;
    OnClick onItemClickListener;
    private static final int MODEL_TWO = 2;

    public SubCategoriesAdapter(Activity activity, List<CategoriesModelNU> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        return MODEL_TWO;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MODEL_TWO) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelitem_categoriestwo, parent, false);
            return new Two(itemView);
        } else return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof Two) {
            Two footerHolder = (Two) holder;
            footerHolder.init(items.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private class Two extends RecyclerView.ViewHolder {

        TextView categoriesname;
        ImageView circleImageView;

        public Two(View view) {
            super(view);
            categoriesname = view.findViewById(R.id.categoriesname);
            circleImageView = view.findViewById(R.id.circleImageView);
        }

        public void init(final CategoriesModelNU categoriesModel){
            categoriesname.setText(categoriesModel.getNama_kategori());
            ImageLoader.getInstance().displayImage(FOTOSUBKATEGORI + categoriesModel.getUrl_gambar_kategori(), circleImageView);
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
