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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import harico.groupdeallast.AppConfig;
import harico.groupdeallast.R;
import harico.groupdeallast.adapter.Mygroup_adapter;
import harico.groupdeallast.adapter.Wishlist_adapter;
import harico.groupdeallast.appcon.AppController;
import harico.groupdeallast.details.Details;

/**
 * Created by Windows 7 on 10/29/2015.
 */
public class Fragment_Mygroup extends Fragment {

    private ProgressDialog pDialog;
    String tag_string_req = "req_login";
    private static final String TAG = "RecyclerViewExample";
    private final List<Details> mDetailsList = new ArrayList<>();
    public Fragment_Mygroup() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mygroup, container, false);

        final Context context = getActivity();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        final Mygroup_adapter mygropt_adapter = new Mygroup_adapter(context, mDetailsList);
        recyclerView.setAdapter(mygropt_adapter);


        pDialog = new ProgressDialog(context);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_Mygroup, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "login Response: " + response.toString());
                hidePDialog();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Product");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject json = jsonArray.getJSONObject(i);
                        Details details = new Details();
                        details.setMygroup_item(json.getString("Item"));
                        details.setMygroup_price(json.getString("Actual Price"));
                        details.setMygroup_image(json.getString("Image"));
                        details.setMygroup_count(json.getString("Count"));
                        details.setMygroup_offerPrice(json.getString("Offer Price"));
                        details.setMygroup_description(json.getString("Description"));
                        details.setMygroup_createDate(json.getString("Start Date"));
                        details.setMygroup_endDate(json.getString("End Date"));
                        mDetailsList.add(details);
                    }
               mygropt_adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(LoginActivity.this,
//                        error.getMessage(), Toast.LENGTH_LONG).show();
//                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Post params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "login");
                String email = AppController.getString(context, "email");
                params.put("email", email);
                System.out.println("ema" + email);


                return params;
            }

        };

        // Adding request to  queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

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