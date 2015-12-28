package harico.groupdeallast.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import harico.groupdeallast.R;
import harico.groupdeallast.activity.Homegroup;

/**
 * Created by Windows 7 on 11/24/2015.
 */
public class Homegroup_detail extends Fragment {

    public Homegroup_detail()
    {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hoegroupdetail, container, false);
        return view;
    }
}
