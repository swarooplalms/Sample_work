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
import harico.groupdeallast.Viewactivity;
import harico.groupdeallast.appcon.AppController;
import harico.groupdeallast.details.Details;


/**
 * Created by Windows 7 on 10/14/2015.
 */
public class Mobile_Adapter extends RecyclerView.Adapter<Mobile_Adapter.MobileViewHolder> {
    List<Details> mobile_details= Collections.emptyList();


    private LayoutInflater inflater;



    ImageLoader mImageLoader = AppController.getInstance().getImageLoader();

    private static Context context;


    public Mobile_Adapter(Context context, List<Details> mobile_details) {
        this.mobile_details = mobile_details;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MobileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
      MobileViewHolder pvh = new MobileViewHolder(v);


        return pvh;
    }

    @Override
    public void onBindViewHolder(MobileViewHolder holder, int postions) {

        Details details = mobile_details.get(postions);
        holder.phoneName.setText(details.getPhone_name());
        holder.phonePrice.setText("Price:"+details.getPhone_price());
        holder.itemView.setTag(details);

        if (mImageLoader == null)
            mImageLoader = AppController.getInstance().getImageLoader();
        holder.thumbnail.setImageUrl(details.getPhone_image(), mImageLoader);


    }


    @Override
    public int getItemCount() {
        if (mobile_details != null) {
            return mobile_details.size();
        }
        return 0;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class MobileViewHolder extends RecyclerView .ViewHolder
    {
        CardView cv;
        TextView phoneName;
        TextView phonePrice;



        NetworkImageView thumbnail;

        MobileViewHolder(View itemView) {
            super(itemView);

         //   cv = (CardView) itemView.findViewById(R.id.card_view);
            phoneName = (TextView) itemView.findViewById(R.id.phone_name);
            phonePrice = (TextView) itemView.findViewById(R.id.phone_price);
            thumbnail = (NetworkImageView) itemView.findViewById(R.id.thumbnail);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(context, Viewactivity.class);

                    Details details = (Details) v.getTag();

                    String strUrl = details.getPhone_image();
                    String product_name = details.getPhone_name();
                    String product_price = details.getPhone_price();
                    String product_description=details.getPhone_descrption();
                    intent.putExtra("Description",product_description);
                    intent.putExtra("Product", product_name);
                    intent.putExtra("Price", product_price);
                    intent.putExtra("Image", strUrl);
                    context.startActivity(intent);

                }
            });





        }


    }


}

