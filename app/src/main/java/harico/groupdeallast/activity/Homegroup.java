package harico.groupdeallast.activity;

import android.app.ProgressDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import harico.groupdeallast.AppConfig;
import harico.groupdeallast.Fragments.Fragment_Home;
import harico.groupdeallast.R;
import harico.groupdeallast.appcon.AppController;
import harico.groupdeallast.details.Details;

public class Homegroup extends AppCompatActivity implements View.OnClickListener {
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    ImageView product;
    TextView product_tilte, actual_price, offer_price, cretaed_date, end_date, product_description, user_count;


    private ProgressDialog progressDialog;
    String tag_string_req = "req_login";
    private static final String TAG = "Groupcration";
    Button join_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homegroup);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        product_tilte = (TextView) findViewById(R.id.product_title);
        offer_price = (TextView) findViewById(R.id.offer_price);
        actual_price = (TextView) findViewById(R.id.actual_price);
        product_description = (TextView) findViewById(R.id.descrption);
        user_count = (TextView) findViewById(R.id.user_count);
        cretaed_date = (TextView) findViewById(R.id.created_date);
        end_date = (TextView) findViewById(R.id.end_date);
        join_group = (Button) findViewById(R.id.joingroup);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);


        // Getting the  details from the Home Fragment class

        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString("Image");
       String product_name = bundle.getString("Product");


        product = (ImageView) findViewById(R.id.expandedImage);
        Picasso.with(getApplicationContext()).load(url).into(product);
        product_tilte.setText(bundle.getString("Product"));
        product_description.setText(bundle.getString("home_product"));
        user_count.setText(bundle.getString("count"));
        actual_price.setText("Actual Price :" + bundle.getString("Price") + "Rs");
        offer_price.setText("Offer Price :" + bundle.getString("offer_price") + "Rs");
        cretaed_date.setText("Created Date :" + bundle.getString("created_date"));
        end_date.setText("End date :" + bundle.getString("end_date"));
        collapsingToolbarLayout.setTitle(product_name);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.Expandcollapsing);

        join_group.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_homegroup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void Orderplacing( ) {
        // Tag used to cancel the ryequest
        String tag_string_req = "req_order";

         progressDialog.setMessage("your new Group is creating...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ORDERPLACING, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "login Response: " + response.toString());
      hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);


                    Boolean error = jObj.getBoolean("error");



                    if (!error) {




                        String responseMsg = jObj.getString("response");



                        Toast.makeText(Homegroup.this,
                                "You are succesfully Joined this Group", Toast.LENGTH_LONG).show();

                    }

                    else {
                        // order error
                        String responseMsg = jObj.getString("response");
                        Toast.makeText(Homegroup.this,
                                "ooola kirane"+ responseMsg, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Homegroup.this,
                        error.getMessage(), Toast.LENGTH_LONG).show();
              hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Post product to orderplacing url
                Map<String, String> params = new HashMap<String, String>();
                Bundle bundle = getIntent().getExtras();
                String email = AppController.getString(getApplicationContext(), "email");

                String product_name=bundle.getString("Product");
                params.put("item", product_name);
                params.put("email",email);

                return params;
            }

        };

        // Adding request to  queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    /*
//    function to show dialog
//     */
    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    /*
    function to hide dialog
     */
    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            //on clicking join group
            case R.id.joingroup:
                Orderplacing();

                break;
        }

    }
}