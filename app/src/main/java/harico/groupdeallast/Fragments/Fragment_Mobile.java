package harico.groupdeallast.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import harico.groupdeallast.R;
import harico.groupdeallast.adapter.Mobile_Adapter;
import harico.groupdeallast.details.Details;

/**
 * Created by Windows 7 on 10/29/2015.
 */
public class Fragment_Mobile extends Fragment {
    private static final String TAG = "RecyclerViewExample";
    private final List<Details> Mobile_List = new ArrayList<>();
    private ProgressDialog pDialog;
    private int MY_SOCKET_TIMEOUT_MS = 5000;
    private static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";


    public Fragment_Mobile() {

    }
    public static Fragment_Mobile newInstance(String text){
        Fragment_Mobile mFragment = new Fragment_Mobile();
        Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mobile, container, false);
        Context context = getActivity();


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        final Mobile_Adapter MobAdapter = new Mobile_Adapter(context, Mobile_List);
        recyclerView.setAdapter(MobAdapter);


        //rvAdapter.setClickListener(this);
        pDialog = new ProgressDialog(context);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = "http://onam.leah.in/new/item_details.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());
                hidePDialog();
                try {


                    for (int i = 0; i < response.length(); i++) {

                        JSONObject jsonObject = response.getJSONObject(i);
                        Details details = new Details();
                        details.setPhone_name(jsonObject.getString("Product"));
                        details.setPhone_price(jsonObject.getString("Price"));
                        details.setPhone_image(jsonObject.getString("Image"));
                        details.setPhone_descrption(jsonObject.getString("Description"));
                        Mobile_List.add(details);


                    }
                    MobAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("VOLLEY", error.toString());
                hidePDialog();

            }
        });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);


        return view;


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }

    }
}