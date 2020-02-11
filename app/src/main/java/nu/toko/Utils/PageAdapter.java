package nu.toko.Utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haade on 26/11/2017.
 */

public class PageAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments = new ArrayList<>();

    public PageAdapter(FragmentManager manager){
        super(manager);
    }

    public void Add(Fragment fragment){
        fragments.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}