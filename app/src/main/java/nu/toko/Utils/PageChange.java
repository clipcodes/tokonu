package nu.toko.Utils;

import android.app.Activity;

import androidx.viewpager.widget.ViewPager;

public class PageChange implements ViewPager.OnPageChangeListener {

    int page = 0;
    Activity activity;
    OnChange onChange;

    public PageChange(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        onChange.onPageChange(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void setOnChange(OnChange onChange) {
        this.onChange = onChange;
    }
    public interface OnChange {
        void onPageChange(int i);
    }
}
