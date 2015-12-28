package harico.groupdeallast.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import harico.groupdeallast.R;
import harico.groupdeallast.details.Details;

/**
 * Created by Windows 7 on 10/29/2015.
 */
public class Fragment_Mycart extends Fragment{

    private static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";

    public Fragment_Mycart() {

    }
    public static Fragment_Mycart newInstance(String text){
        Fragment_Mycart mFragment = new Fragment_Mycart();
        Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mycart, container, false);
        Details details=new Details();
        String m=details.getEmail();
        System.out.println(m+"hooo");

        return view;

    }
}