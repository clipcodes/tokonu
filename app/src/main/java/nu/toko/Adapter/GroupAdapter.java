package nu.toko.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nu.toko.Model.CategoriesModel;
import nu.toko.Model.ProductModel;
import nu.toko.Page.Categories;
import nu.toko.R;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder>{

    Activity activity;
    List<CategoriesModel> items;
    OnClick onItemClickListener;

    public GroupAdapter(Activity activity, List<CategoriesModel> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.modelitem_group, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.categoriesname.setText(items.get(position).getCategories());
        holder.objItemslist = items.get(position).getProductitem();
//        holder.productAdapter = new ProductAdapter(activity, holder.objItemslist);
//        holder.rvproduct.setLayoutManager(holder.lm);
//        holder.rvproduct.setAdapter(holder.productAdapter);
//        holder.more.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(activity, Categories.class);
//                activity.startActivity(i);
//            }
//        });
    }

    @Override
    public int getItemCount() {

        return items.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder{
        TextView categoriesname;
        RecyclerView rvproduct;
        ProductAdapter productAdapter;
        LinearLayoutManager lm;
        List<ProductModel> objItemslist;
        ImageView more;

        public ViewHolder(final View itemView) {
            super(itemView);
            categoriesname = itemView.findViewById(R.id.categoriesname);
            rvproduct = itemView.findViewById(R.id.rvproduct);
            lm = new GridLayoutManager(activity, 1, LinearLayoutManager.HORIZONTAL, false);
            more = itemView.findViewById(R.id.more);
        }
    }

    public void setOnItemClickListener(OnClick clickListener) {
        onItemClickListener = clickListener;
    }
    public interface OnClick {
        void onItemClick();
    }
}