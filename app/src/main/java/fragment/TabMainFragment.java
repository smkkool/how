package fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import minhpvn.com.swipedemo.MainActivity;
import minhpvn.com.swipedemo.R;

/**
 * Created by smkko on 12/13/2017.
 */

public class TabMainFragment extends Fragment {
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_layout, container, false);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) view.findViewById(R.id.containerTab);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        tabLayout.setupWithViewPager(mViewPager);
        ((MainActivity) getActivity()).setActionBarTitle("Tabs");
        setHasOptionsMenu(false);
        return view;
    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    TabOneFragment tabOneFragment = new TabOneFragment();
                    return tabOneFragment;
                case 1:
                    TabTwoFragment tabTwoFragment = new TabTwoFragment();
                    return tabTwoFragment;
                case 2:
                    TabThreeFragment tabThreeFragment = new TabThreeFragment();
                    return tabThreeFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "JSON Language";
                case 1:
                    return "COMMENT";
                case 2:
                    return "ABOUT";
            }
            return null;
        }
    }
}
