/*
 * Copyright 2015 Rudson Lima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package harico.groupdeallast.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

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
import harico.groupdeallast.SessionManager;
import harico.groupdeallast.activity.MainActivity;
import harico.groupdeallast.adapter.Home_adapter;
import harico.groupdeallast.details.Details;
import harico.groupdeallast.login.LoginActivity;


public class Fragment_Home extends Fragment {
	private static final String TAG = "RecyclerViewExample";
	private final List<Details> mDetailsList = new ArrayList<>();
	private ProgressDialog pDialog;
	private  int  MY_SOCKET_TIMEOUT_MS=50000;
	private SessionManager session;

    private boolean mSearchCheck;
    private static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";

	public static Fragment_Home newInstance(String text){
		Fragment_Home mFragment = new Fragment_Home();
		Bundle mBundle = new Bundle();
		mBundle.putString(TEXT_FRAGMENT, text);
		mFragment.setArguments(mBundle);
		return mFragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub		
		View view = inflater.inflate(R.layout.fragment_main, container, false);


		Context context = getActivity();




		RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
		recyclerView.setHasFixedSize(true);

		LinearLayoutManager layoutManager = new LinearLayoutManager(context);
		recyclerView.setLayoutManager(layoutManager);

		final Home_adapter homeAdapter = new Home_adapter(context, mDetailsList);
		recyclerView.setAdapter(homeAdapter);

		recyclerView.setItemAnimator(new DefaultItemAnimator());

		//rvAdapter.setClickListener(this);
		pDialog = new ProgressDialog(context);
		// Showing progress dialog before making http request
		pDialog.setMessage("Loading...");
		pDialog.show();
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		// String url for Home group
		String url = "http://onam.leah.in/new/item_home.php ";

		JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				Log.d(TAG, "log"+response.toString());
				hidePDialog();
				try {


					for (int i = 0; i < response.length(); i++) {

						JSONObject jsonObject = response.getJSONObject(i);
						Details details =new Details();
						details.setHome_item(jsonObject.getString("Item"));
						details.setHome_itemPrice(jsonObject.getString("Actual Price"));
						details.setHome_itemImage(jsonObject.getString("image"));
						details.setHome_itemCount(jsonObject.getString("count"));
						details.setHome_offerprice(jsonObject.getString("Offer Price"));
						details.setHome_createdDate(jsonObject.getString("Start Date"));
						details.setHome_enddate(jsonObject.getString("End Date"));
						details.setHome_proDescrption(jsonObject.getString("Details"));
						mDetailsList.add(details);


					}
					homeAdapter.notifyDataSetChanged();

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
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_main, menu);
        
        //Select search item
        final MenuItem menuItem = menu.findItem(R.id.menu_search);
        menuItem.setVisible(true);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(this.getString(R.string.search));

        ((EditText) searchView.findViewById(R.id.search_src_text))
                .setHintTextColor(getResources().getColor(R.color.nliveo_white));
        searchView.setOnQueryTextListener(onQuerySearchView);

		menu.findItem(R.id.menu_logout).setVisible(true);

		mSearchCheck = false;	
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {

		case R.id.menu_logout:

		//		logoutUser();
				break;



			case R.id.menu_search:
			mSearchCheck = true;
            Toast.makeText(getActivity(), "your selected", Toast.LENGTH_SHORT).show();
			break;
		}
		return true;
	}	

   private SearchView.OnQueryTextListener onQuerySearchView = new SearchView.OnQueryTextListener() {
       @Override
       public boolean onQueryTextSubmit(String s) {
           return false;
       }

       @Override
       public boolean onQueryTextChange(String s) {
           if (mSearchCheck){
               // implement your search here
           }
           return false;
       }
   };

}
