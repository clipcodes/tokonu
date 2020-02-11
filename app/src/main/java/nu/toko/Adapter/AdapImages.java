package nu.toko.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import nu.toko.Model.ImagesModel;
import nu.toko.R;

import static nu.toko.Utils.Staticvar.DOMAIN;
import static nu.toko.Utils.Staticvar.FOTOPRODUK;

public class AdapImages extends PagerAdapter {
    private Context context;
    List<ImagesModel> images;

    public AdapImages(Context context, List<ImagesModel> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.modelitem_images, null);
        ImageView imageView = view.findViewById(R.id.images);
        ImageLoader.getInstance().displayImage( FOTOPRODUK + images.get(position).getUrl_gambar(), imageView);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }
}
