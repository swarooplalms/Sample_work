package harico.groupdeallast.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;


import java.util.Collections;
import java.util.List;

import harico.groupdeallast.Fragments.Homegroup_detail;
import harico.groupdeallast.R;
import harico.groupdeallast.Viewactivity;
import harico.groupdeallast.activity.Homegroup;
import harico.groupdeallast.appcon.AppController;
import harico.groupdeallast.details.Details;

/**
 * Created by Windows 7 on 10/14/2015.
 */
public class Home_adapter extends RecyclerView.Adapter<Home_adapter.PersonViewHolder> {
   public List<Details> detailses = Collections.emptyList();

private LayoutInflater inflater;



    ImageLoader mImageLoader = AppController.getInstance().getImageLoader();

    private static Context context;


    public Home_adapter(Context context, List<Details> detailses) {
        this.detailses = detailses;
          this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_groupcardview, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);


        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int postions) {

        Details details = detailses.get(postions);
        holder.ItemName .setText(details.getHome_item());
        holder.ItemPrice.setText("OrigionalPrice: " + details.getHome_itemPrice());
        holder.offer_price.setText("Offer Price: " + details.getHome_offerprice());
        holder.group_count.setText(details.getHome_itemCount());
        holder.itemView.setTag(details);
//        holder.Item_Image.setImageBitmap(null);
//        Picasso.with(holder.Item_Image.setImageUrl(details.getHome_itemImage()).load(details.getImage()).into(holder.image);
   if (mImageLoader == null)
           mImageLoader = AppController.getInstance().getImageLoader();
      holder.Item_Image.setImageUrl(details.getHome_itemImage(), mImageLoader);


    }


    @Override
    public int getItemCount() {
        if (detailses != null) {
            return detailses.size();
        }
        return 0;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class PersonViewHolder extends RecyclerView .ViewHolder
            {
        CardView cv;
        TextView ItemName;
        TextView ItemPrice,offer_price,group_count;



        NetworkImageView Item_Image;

        PersonViewHolder(View itemView) {
            super(itemView);

            //cv = (RelativeLayout) itemView.findViewById(R.id.reltive);
           ItemName = (TextView) itemView.findViewById(R.id.item_name);
            ItemPrice = (TextView) itemView.findViewById(R.id.item_price);
            Item_Image = (NetworkImageView) itemView.findViewById(R.id.item_image);
            offer_price=(TextView)itemView.findViewById(R.id.offer_price);
            group_count=(TextView)itemView.findViewById(R.id.group_count);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, Homegroup.class);

                    Details details = (Details) v.getTag();

                   String strUrl = details.getHome_itemImage();
                   String product_name = details.getHome_item();
                  String product_price = details.getHome_itemPrice();
                    String offer_price=details.getHome_offerprice();
                    String count=details.getHome_itemCount();
                    String end_Date=details.getHome_enddate();
                    String created_Date=details.getHome_createdDate();
                    String home_Product=details.getHome_proDescrption();


                    intent.putExtra("Product", product_name);
                    intent.putExtra("Price", product_price);
                    intent.putExtra("Image", strUrl);
                    intent.putExtra("offer_price",offer_price);
                    intent.putExtra("count",count);
                    intent.putExtra("end_date",end_Date);
                    intent.putExtra("created_date",created_Date);
                    intent.putExtra("home_product",home_Product);


                    context.startActivity(intent);

                }
            });





        }


    }


}

