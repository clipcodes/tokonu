package nu.toko.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PageAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> namatitel = new ArrayList<>();

    public PageAdapter(FragmentManager manager){
        super(manager);
    }

    public void Tambai(Fragment Frag){
        fragments.add(Frag);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return namatitel.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}