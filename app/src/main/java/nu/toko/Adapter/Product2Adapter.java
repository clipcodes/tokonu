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

public class Product2Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Activity activity;
    List<ProductModelNU> items;
    OnClickBuy onClickBuy;
    private static final int MODEL_ONE = 1;
    private static final int MODEL_TWO = 2;
    private static final int MODEL_THREE = 3;

    public Product2Adapter(Activity activity, List<ProductModelNU> items){
        this.activity = activity;
        this.items = items;
    }


    @Override
    public int getItemViewType(int position) {
        if (Integer.valueOf(items.get(position).getStok())<=0){
            return MODEL_TWO;
        } else if (!items.get(position).getDiskonpercent().equals("0")) {
            return MODEL_THREE;
        }  else {
            return MODEL_ONE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MODEL_ONE) {
            //Inflating recycle view item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelitem_product2, parent, false);
            return new One(itemView);
        } else if (viewType == MODEL_TWO) {
            //Inflating recycle view item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelitem_product2habis, parent, false);
            return new Two(itemView);
        } else if (viewType == MODEL_THREE) {
            //Inflating recycle view item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelitem_product2diskon, parent, false);
            return new Three(itemView);
        } else return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof One) {
            One one = (One) holder;
            one.init(items.get(position));
        } else if (holder instanceof Two) {
            Two one = (Two) holder;
            one.init(items.get(position));
        } else if (holder instanceof Three) {
            Three one = (Three) holder;
            one.init(items.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    } 

    protected class One extends RecyclerView.ViewHolder {
 
        TextView title, price, totalfeedback, namatoko, lokasi, stock, texkategori;
        ImageView thumb, kategorimitra;
        RatingBar star;

        public One(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            thumb = itemView.findViewById(R.id.thumb);
            stock = itemView.findViewById(R.id.stock);
            star = itemView.findViewById(R.id.star);
            namatoko = itemView.findViewById(R.id.namatoko);
            lokasi = itemView.findViewById(R.id.lokasi);
            totalfeedback = itemView.findViewById(R.id.totalfeedback);
            kategorimitra = itemView.findViewById(R.id.kategorimitra);
            texkategori = itemView.findViewById(R.id.texkategori);
        }
        
        public void init(final ProductModelNU productModelNU){
            price.setText("Rp."+ Others.PercantikHarga(productModelNU.getHarga_admin()-productModelNU.getDiskon()));
            ImageLoader.getInstance().displayImage(FOTOPRODUK + productModelNU.getGambarfirst(), thumb);
            title.setText(productModelNU.getNama_produk());
            star.setRating(productModelNU.getRating());
            stock.setText(productModelNU.getStok()+" Stok");
            lokasi.setText(productModelNU.getDikirimdari());
            namatoko.setText(productModelNU.getOwner().getNama_toko_mitra());
            totalfeedback.setText("("+productModelNU.getTotalfeedback()+")");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(activity, Details.class);
                    i.putExtra("idproduk", productModelNU.getId_produk());
                    i.putExtra("harga", productModelNU.getHarga_admin());
                    i.putExtra("produknama", productModelNU.getNama_produk());
                    activity.startActivity(i);
                }
            });
            if(productModelNU.getKategorimitra().contains("Official")){
                kategorimitra.setImageDrawable(activity.getResources().getDrawable(R.drawable.officialstore));
                texkategori.setText("(Official Store)");
            } else if(productModelNU.getKategorimitra().contains("Seller")) {
                kategorimitra.setImageDrawable(activity.getResources().getDrawable(R.drawable.starseller));
                texkategori.setText("(Star Seller)");
            } else {
                kategorimitra.setImageDrawable(activity.getResources().getDrawable(R.drawable.starseller));
                texkategori.setText("(Store)");
            }
        }
    }

    protected class Two extends RecyclerView.ViewHolder {

        TextView title, price, totalfeedback, namatoko, lokasi, stock, diskon, texkategori;
        ImageView thumb, kategorimitra;
        RatingBar star;

        public Two(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            thumb = itemView.findViewById(R.id.thumb);
            stock = itemView.findViewById(R.id.stock);
            diskon = itemView.findViewById(R.id.diskon);
            star = itemView.findViewById(R.id.star);
            namatoko = itemView.findViewById(R.id.namatoko);
            lokasi = itemView.findViewById(R.id.lokasi);
            totalfeedback = itemView.findViewById(R.id.totalfeedback);
            kategorimitra = itemView.findViewById(R.id.kategorimitra);
            texkategori = itemView.findViewById(R.id.texkategori);
        }

        public void init(final ProductModelNU productModelNU){
            price.setText("Rp."+ Others.PercantikHarga(productModelNU.getHarga_admin()-productModelNU.getDiskon()));
            ImageLoader.getInstance().displayImage(FOTOPRODUK + productModelNU.getGambarfirst(), thumb);
            title.setText(productModelNU.getNama_produk());
            star.setRating(productModelNU.getRating());
            stock.setText(productModelNU.getStok()+" Stok");
            lokasi.setText(productModelNU.getDikirimdari());
            namatoko.setText(productModelNU.getOwner().getNama_toko_mitra());
            totalfeedback.setText("("+productModelNU.getTotalfeedback()+")");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(activity, Details.class);
                    i.putExtra("idproduk", productModelNU.getId_produk());
                    i.putExtra("harga", productModelNU.getHarga_admin());
                    i.putExtra("produknama", productModelNU.getNama_produk());
                    activity.startActivity(i);
                }
            });
            if(productModelNU.getKategorimitra().contains("Official")){
                kategorimitra.setImageDrawable(activity.getResources().getDrawable(R.drawable.officialstore));
                texkategori.setText("(Official Store)");
            } else if(productModelNU.getKategorimitra().contains("Seller")) {
                kategorimitra.setImageDrawable(activity.getResources().getDrawable(R.drawable.starseller));
                texkategori.setText("(Star Seller)");
            } else {
                kategorimitra.setImageDrawable(activity.getResources().getDrawable(R.drawable.starseller));
                texkategori.setText("(Store)");
            }
        }
    }

    protected class Three extends RecyclerView.ViewHolder {

        TextView title, price, totalfeedback, namatoko, lokasi, stock, diskon, texkategori;
        ImageView thumb, kategorimitra;
        FrameLayout diskoncontainer;
        RatingBar star;

        public Three(View itemView) {
            super(itemView);
            diskoncontainer = itemView.findViewById(R.id.diskoncontainer);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            thumb = itemView.findViewById(R.id.thumb);
            stock = itemView.findViewById(R.id.stock);
            diskon = itemView.findViewById(R.id.diskon);
            star = itemView.findViewById(R.id.star);
            namatoko = itemView.findViewById(R.id.namatoko);
            lokasi = itemView.findViewById(R.id.lokasi);
            totalfeedback = itemView.findViewById(R.id.totalfeedback);
            kategorimitra = itemView.findViewById(R.id.kategorimitra);
            texkategori = itemView.findViewById(R.id.texkategori);
        }

        public void init(final ProductModelNU productModelNU){
            price.setText("Rp."+ Others.PercantikHarga(productModelNU.getHarga_admin()-productModelNU.getDiskon()));
            ImageLoader.getInstance().displayImage(FOTOPRODUK + productModelNU.getGambarfirst(), thumb);
            title.setText(productModelNU.getNama_produk());
            star.setRating(productModelNU.getRating());
            stock.setText(productModelNU.getStok()+" Stok");
            lokasi.setText(productModelNU.getDikirimdari());
            namatoko.setText(productModelNU.getOwner().getNama_toko_mitra());
            totalfeedback.setText("("+productModelNU.getTotalfeedback()+")");
            diskon.setText(productModelNU.getDiskonpercent()+"%");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(activity, Details.class);
                    i.putExtra("idproduk", productModelNU.getId_produk());
                    i.putExtra("harga", productModelNU.getHarga_admin());
                    i.putExtra("produknama", productModelNU.getNama_produk());
                    activity.startActivity(i);
                }
            });
            if(productModelNU.getKategorimitra().contains("Official")){
                kategorimitra.setImageDrawable(activity.getResources().getDrawable(R.drawable.officialstore));
                texkategori.setText("(Official Store)");
            } else if(productModelNU.getKategorimitra().contains("Seller")) {
                kategorimitra.setImageDrawable(activity.getResources().getDrawable(R.drawable.starseller));
                texkategori.setText("(Star Seller)");
            } else {
                kategorimitra.setImageDrawable(activity.getResources().getDrawable(R.drawable.starseller));
                texkategori.setText("(Store)");
            }
        }
    }

    public void setOnBuyClickListener(OnClickBuy onClickBuy) {
        this.onClickBuy = onClickBuy;
    }
    public interface OnClickBuy {
        void onClick(ProductModel productModel);
    }
}
