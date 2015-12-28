package harico.groupdeallast.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import br.liveo.Model.HelpLiveo;
import br.liveo.interfaces.OnItemClickListener;
import br.liveo.interfaces.OnPrepareOptionsMenuLiveo;
import br.liveo.navigationliveo.NavigationActionBarLiveo;
import harico.groupdeallast.Fragments.Fragment_Electronics;
import harico.groupdeallast.Fragments.Fragment_Laptops;
import harico.groupdeallast.Fragments.Fragment_Mobile;
import harico.groupdeallast.Fragments.Fragment_Mycart;
import harico.groupdeallast.Fragments.Fragment_Mygroup;
import harico.groupdeallast.Fragments.Fragment_Home;
import harico.groupdeallast.Fragments.Fragment_Wishlist;
import harico.groupdeallast.Fragments.ViewPagerFragment;
import harico.groupdeallast.R;
import harico.groupdeallast.SessionManager;

import harico.groupdeallast.appcon.AppController;
import harico.groupdeallast.details.Details;
import harico.groupdeallast.login.LoginActivity;


public class MainActivity extends NavigationActionBarLiveo  implements OnItemClickListener {
    //naviagtion Drawer libarary

    private HelpLiveo mHelpLiveo;
    private static final String TAG =MainActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private SessionManager session;
    private static final int REQUEST_CODE = 1;
    private Bitmap bitmap;


   // SharedPreferences.Editor editor;
    public void onInt(Bundle savedInstanceState) {



//       Bundle bundle = getIntent().getExtras();
//
//        String username=bundle.getString("name");
//        String User_email=bundle.getString("email");
//
//
        String email = AppController.getString(this, "email");
        String username = AppController.getString(this, "name");
        this.userName.setText(username);
        this.userEmail.setText(email);
 this.userPhoto.setImageResource(R.drawable.ic_rudsonlive);
       this.userBackground.setImageResource(R.drawable.ic_user_background_first);


//       String p= pref.getString("email", null);
//System.out.println(p+"print");


        // Creating items navigation

        mHelpLiveo = new HelpLiveo();
        mHelpLiveo.add(getString(R.string.Home), R.mipmap.home);
        mHelpLiveo.add(getString(R.string.MyGorups), R.mipmap.mygroup);

        mHelpLiveo.add(getString(R.string.MyCart), R.mipmap.mycart);
        mHelpLiveo.add(getString(R.string.MyWishlist),R.mipmap.wishlist);

//        mHelpLiveo.addSeparator(); // Item separator
      mHelpLiveo.addSubHeader(getString(R.string.categories)); //Item subHeader

        mHelpLiveo.add(getString(R.string.Mobile), R.mipmap.mobile);

        mHelpLiveo.add(getString(R.string.Electronics), R.mipmap.electronics);
        mHelpLiveo.add(getString(R.string.Laptops), R.mipmap.laptop);

        //{optional} - Header Customization - method customHeader
//        View mCustomHeader = getLayoutInflater().inflate(R.layout.custom_header_user, this.getListView(), false);
//        ImageView imageView = (ImageView) mCustomHeader.findViewById(R.id.imageView);

        with(this).startingPosition(0) //Starting position in the list
                .addAllHelpItem(mHelpLiveo.getHelp())

                        //{optional} - List Customization "If you remove these methods and the list will take his white standard color"
                        //.selectorCheck(R.drawable.selector_check) //Inform the background of the selected item color
                        //.colorItemDefault(R.color.nliveo_blue_colorPrimary) //Inform the standard color name, icon and counter
                        //.colorItemSelected(R.color.nliveo_purple_colorPrimary) //State the name of the color, icon and meter when it is selected
                        //.backgroundList(R.color.nliveo_black_light) //Inform the list of background color
                        //.colorLineSeparator(R.color.nliveo_transparent) //Inform the color of the subheader line

                        //{optional} - SubHeader Customization
                .colorItemSelected(R.color.nliveo_blue_colorPrimary)
                .colorNameSubHeader(R.color.nliveo_blue_colorPrimary)
                        //.colorLineSeparator(R.color.nliveo_blue_colorPrimary)


                        //.footerSecondItem(R.string.settings, R.mipmap.ic_settings_black_24dp)

                        //{optional} - Header Customization
                        //.customHeader(mCustomHeader)

                        //{optional} - Footer Customization
                        .footerNameColor(R.color.nliveo_blue_colorPrimary)
                        .footerIconColor(R.color.nliveo_blue_colorPrimary)
                        .footerBackground(R.color.nliveo_white)

                .setOnClickUser(onClickPhoto)
                .setOnPrepareOptionsMenu(onPrepare)

                        .setOnClickFooterSecond(onClickFooter)
                .build();

        int position = this.getCurrentPosition();
 this.setElevationToolBar(position != 0 ? 10 : 0);
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
    }



    @Override
    public void onItemClick(int position) {
        Fragment mFragment=null;
        FragmentManager mFragmentManager = getSupportFragmentManager();
        String title = getString(R.string.app_name);
        switch (position){
            case 0:
                mFragment = new ViewPagerFragment();
                title = getString(R.string.title_home);
                break;
            case 1:
                mFragment=new Fragment_Mygroup();
                title=getString(R.string.title_mygroup);
                break;
            case 2:
                mFragment=new Fragment_Mycart();
                title=getString(R.string.title_mycart);
                break;
            case 3:
                mFragment=new Fragment_Wishlist();
                title=getString(R.string.title_wislist);
                break;
            case 5:
                mFragment=new Fragment_Mobile();
                title=getString(R.string.title_mobile);
                break;
            case 6:
                mFragment=new Fragment_Electronics();
                title=getString(R.string.title_electronics);
                break;
            case 7:
                mFragment=new Fragment_Laptops();
                title=getString(R.string.title_Laptop);
                break;

            default:

                break;
        }
        ;
        if (mFragment != null){
            mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
            getSupportActionBar().setTitle(title);
        }

     // setElevationToolBar(position != 0 ? 7 : 0);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_logout) {
            logoutUser();
            return true;
        }

        if(id == R.id.menu_search){
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    private OnPrepareOptionsMenuLiveo onPrepare = new OnPrepareOptionsMenuLiveo() {
        @Override
        public void onPrepareOptionsMenu(Menu menu, int position, boolean visible) {
        }
    };

    private View.OnClickListener onClickPhoto = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "onClickPhoto :D", Toast.LENGTH_SHORT).show();
           // pickImage(v);
            closeDrawer();
        }
    };

    private View.OnClickListener onClickFooter =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          //   startActivity(new Intent(getApplicationContext(), Se.class));
            closeDrawer();
        }
    };
//Log out
    private void logoutUser() {
        session.setLogin(false);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
//    public void pickImage(View View) {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        startActivityForResult(intent, REQUEST_CODE);
//    }
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK)
//            try {
//                // We need to recyle unused bitmaps
//                if (bitmap != null) {
//                    bitmap.recycle();
//                }
//                InputStream stream = getContentResolver().openInputStream(
//                        data.getData());
//                bitmap = BitmapFactory.decodeStream(stream);
//                stream.close();
//                this.userPhoto.setImageBitmap(bitmap);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        super.onActivityResult(requestCode, resultCode, data);
  //  }
}
