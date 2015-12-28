package harico.groupdeallast;


import android.app.ProgressDialog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import harico.groupdeallast.Fragments.Fragment_Home;
import harico.groupdeallast.Fragments.Fragment_Mycart;
import harico.groupdeallast.activity.MainActivity;
import harico.groupdeallast.appcon.AppController;
import harico.groupdeallast.details.Details;


public class Viewactivity extends AppCompatActivity implements View.OnClickListener {
    TextView phone,price,description;
    ImageView product;
    Button wishList,orderList;
    private CollapsingToolbarLayout collapsingToolbarLayout = null;


    private ProgressDialog progressDialog;
    private static final String TAG = Viewactivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_viewactivity);
      //  getSupportActionBar().hide();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        phone=(TextView)findViewById(R.id.product_title);
        price=(TextView)findViewById(R.id.product_price);
        description=(TextView)findViewById(R.id.descrption);
        product=(ImageView)findViewById(R.id.product_expandedImage);
        wishList=(Button)findViewById(R.id.button_wishlist);
        orderList=(Button)findViewById(R.id.button_order);

        // collapsing toolbar

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);



      // order & wishlist
        wishList.setOnClickListener(this);
        orderList.setOnClickListener(this);

        //setting progressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);





// Getting the product details from the product class

        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString("Image");
        String product_name=bundle.getString("Product");
        String product_description=bundle.getString("Description");
        phone.setText(bundle.getString("Product"));
        price.setText("Price:   " + bundle.getString("Price"));
      description.setText(product_description);
        Picasso.with(getApplicationContext()).load(url).into(product);
//setting the title of collapsing layout

        collapsingToolbarLayout.setTitle(product_name);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.Expandcollapsing);







    }
    /**
     * function to booking the order
    * */
    private void Orderplacing( final String product_name, final String email) {
        // Tag used to cancel the ryequest
        String tag_string_req = "req_order";

        progressDialog.setMessage("Your order is placing ...");
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

                      //String order=jObj.getString("order");

                    if (!error) {



                        // Launching  Fragment_home activity
                        Fragment mFragment;
                        FragmentManager mFragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction=mFragmentManager.beginTransaction();
                        Fragment_Home fragment_home=new Fragment_Home();
                        fragmentTransaction.replace(android.R.id.content,fragment_home);
                        fragmentTransaction.commit();

                        mFragment = new Fragment_Home();

                        String responseMsg = jObj.getString("response");
                        Toast.makeText(getApplicationContext(),
                                responseMsg, Toast.LENGTH_LONG).show();
                        System.out.println("Your" + responseMsg);
                // startActivity(intent);
                      //  startActivity(intent);

                    }

                    else {
                        // order error
                        String responseMsg = jObj.getString("response");
                        Toast.makeText(Viewactivity.this,
                          "ooola kirane"+ responseMsg, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Viewactivity.this,
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Post product to orderplacing url
                Map<String, String> params = new HashMap<String, String>();

                params.put("item", product_name);
                params.put("email",email);

                return params;
            }

        };

        // Adding request to  queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    //adding to the product to wishlist


    private void Addwishlist( final String product_name, final String email) {
        // Tag used to cancel the ryequest
        String tag_string_req = "req_order";

        progressDialog.setMessage("The product is adding in your wishlist ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_WISHLIST_PLACING, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    Boolean error = jObj.getBoolean("error");
                    System.out.println("Your now at if"+error);

                    if (!error) {


                        String responseMsg = jObj.getString("response");
                        Toast.makeText(getApplicationContext(),
                                responseMsg, Toast.LENGTH_LONG).show();
                        System.out.println("Your" + responseMsg);


                    }

                    else {
                        // order error
                        String responseMsg = jObj.getString("response");
                        Toast.makeText(Viewactivity.this,
                                "ooola kirane"+ responseMsg, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Viewactivity.this,
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Post product to orderplacing url
                Map<String, String> params = new HashMap<String, String>();

                params.put("item", product_name);
                params.put("email",email);

                return params;
            }

        };

        // Adding request to  queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    /*
    function to show dialog
     */
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_viewactivity, menu);
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

    @Override
    public void onClick(View view) {



        Bundle bundle = getIntent().getExtras();
        String email = AppController.getString(this, "email");

        String product_name=bundle.getString("Product");
        switch (view.getId())
        {
            //on clicking order button move to Home activity Activity
            case R.id.button_order:
                Orderplacing(product_name,email);
                break;
            //on clicking the signin button check for the empty field then call the checkLogin() function
            case R.id.button_wishlist:
              Addwishlist(product_name,email);

                break;
        }

    }

}
