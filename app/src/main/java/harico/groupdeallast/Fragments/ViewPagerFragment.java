package harico.groupdeallast.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import harico.groupdeallast.R;
import harico.groupdeallast.adapter.TabPagerItem;
import harico.groupdeallast.adapter.ViewPagerAdapter;


public class ViewPagerFragment extends Fragment {
	private List<TabPagerItem> mTabs = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createTabPagerItem();
    }

    private void createTabPagerItem(){
      mTabs.add(new TabPagerItem(getString(R.string.Home), Fragment_Home.newInstance(getString(R.string.Home))));
        mTabs.add(new TabPagerItem(getString(R.string.Mobile), Fragment_Mobile.newInstance(getString(R.string.Mobile))));
 mTabs.add(new TabPagerItem(getString(R.string.Laptops), Fragment_Laptops.newInstance(getString(R.string.Laptops))));
      //  mTabs.add(new TabPagerItem(getString(R.string.Electronics), Fragment_Electronics.newInstance(getString(R.string.Electronics))));
      // mTabs.add(new TabPagerItem(getString(R.string.MyCart), Fragment_Mycart.newInstance(getString(R.string.MyCart))));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_viewpager, container, false);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ));
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    	ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
    	
    //	mViewPager.setOffscreenPageLimit(mTabs.size());
        mViewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), mTabs));
        TabLayout mSlidingTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mSlidingTabLayout.setElevation(15);
        }
        mSlidingTabLayout.setupWithViewPager(mViewPager);
    }
}