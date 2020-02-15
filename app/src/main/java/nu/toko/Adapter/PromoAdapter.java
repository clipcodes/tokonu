package nu.toko.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import nu.toko.Model.SlideModel;
import nu.toko.Page.Categories;
import nu.toko.R;

import static nu.toko.Utils.Staticvar.FOTOPRODUK;
import static nu.toko.Utils.Staticvar.FOTOSLIDE;

public class PromoAdapter extends PagerAdapter {
    private Context context;
    List<SlideModel> images;

    public PromoAdapter(Context context, List<SlideModel> images) {
        this.context = context;
        this.images = images;
    }
    /*
    This callback is responsible for creating a page. We inflate the layout and set the drawable
    to the ImageView based on the position. In the end we add the inflated layout to the parent
    container .This method returns an object key to identify the page view, but in this example page view
    itself acts as the object key
    */
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.modelitem_promo, null);
        ImageView imageView = view.findViewById(R.id.imagepromo);

        ImageLoader.getInstance().displayImage(FOTOSLIDE + images.get(position).getUrl_slide(), imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, Categories.class);
                i.putExtra("id", images.get(position).getParameter());
                i.putExtra("kat", images.get(position).getLink_slide());
                context.startActivity(i);
            }
        });

//        imageView.setImageDrawable(context.getResources().getDrawable(images[position]));

        container.addView(view);
        return view;
    }
    /*
    This callback is responsible for destroying a page. Since we are using view only as the
    object key we just directly remove the view from parent container
    */
    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }
    /*
    Returns the count of the total pages
    */
    @Override
    public int getCount() {
        return images.size();
    }
    /*
    Used to determine whether the page view is associated with object key returned by instantiateItem.
    Since here view only is the key we return view==object
    */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }
}
