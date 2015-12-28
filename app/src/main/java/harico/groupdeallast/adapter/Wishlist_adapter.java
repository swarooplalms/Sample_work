package harico.groupdeallast.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.Collections;
import java.util.List;

import harico.groupdeallast.R;
import harico.groupdeallast.activity.Mywishlist_details;
import harico.groupdeallast.appcon.AppController;
import harico.groupdeallast.details.Details;

/**
 * Created by Windows 7 on 11/18/2015.
 */
public class Wishlist_adapter extends RecyclerView.Adapter<Wishlist_adapter.ElectronicsViewHolder> {
public List<Details> wishdetails = Collections.emptyList();

private LayoutInflater inflater;



    ImageLoader mImageLoader = AppController.getInstance().getImageLoader();

    private static Context context;


    public Wishlist_adapter(Context context, List<Details> wishdetails) {
        this.wishdetails = wishdetails;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ElectronicsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        ElectronicsViewHolder pvh = new ElectronicsViewHolder(v);


        return pvh;
    }

    @Override
    public void onBindViewHolder(ElectronicsViewHolder holder, int postions) {

        Details details = wishdetails.get(postions);
        holder.phoneName.setText(details.getWishlist_item());
        holder.phonePrice.setText("Price:"+details.getWishlist_price());
        holder.itemView.setTag(details);

        if (mImageLoader == null)
            mImageLoader = AppController.getInstance().getImageLoader();
        holder.thumbnail.setImageUrl(details.getWishlist_image(), mImageLoader);


    }


    @Override
    public int getItemCount() {
        if (wishdetails != null) {
            return wishdetails.size();
        }
        return 0;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ElectronicsViewHolder extends RecyclerView .ViewHolder
    {
        CardView cv;
        TextView phoneName;
        TextView phonePrice;



        NetworkImageView thumbnail;

        ElectronicsViewHolder(View itemView) {
            super(itemView);

        //    cv = (CardView) itemView.findViewById(R.id.card_view);
            phoneName = (TextView) itemView.findViewById(R.id.phone_name);
            phonePrice = (TextView) itemView.findViewById(R.id.phone_price);
            thumbnail = (NetworkImageView) itemView.findViewById(R.id.thumbnail);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(context, Mywishlist_details.class);

                    Details details = (Details) v.getTag();

                    String strUrl = details.getWishlist_image();
                    String product_name = details.getWishlist_item();
                    String product_price = details.getWishlist_price();
                    String product_description=details.getWishlist_Description();
                    intent.putExtra("Product", product_name);
                    intent.putExtra("Price", product_price);
                    intent.putExtra("Image", strUrl);
                    intent.putExtra("Description",product_description);
                    context.startActivity(intent);

                }
            });





        }


    }

}


