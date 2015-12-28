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
 * Created by Windows 7 on 10/30/2015.
 */
public class Electronics_Adapter  extends RecyclerView.Adapter<Electronics_Adapter.ElectronicsViewHolder> {
    public List<Details> electronics_details = Collections.emptyList();

    private LayoutInflater inflater;



    ImageLoader mImageLoader = AppController.getInstance().getImageLoader();

    private static Context context;


    public Electronics_Adapter(Context context, List<Details> electronics_details) {
        this.electronics_details = electronics_details;
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

        Details details = electronics_details.get(postions);
        holder.phoneName.setText(details.getTv_name());
        holder.phonePrice.setText("Price:"+details.getTv_price());
        holder.itemView.setTag(details);

        if (mImageLoader == null)
            mImageLoader = AppController.getInstance().getImageLoader();
        holder.thumbnail.setImageUrl(details.getTv_image(), mImageLoader);


    }


    @Override
    public int getItemCount() {
        if (electronics_details != null) {
            return electronics_details.size();
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

           // cv = (CardView) itemView.findViewById(R.id.card_view);
            phoneName = (TextView) itemView.findViewById(R.id.phone_name);
            phonePrice = (TextView) itemView.findViewById(R.id.phone_price);
            thumbnail = (NetworkImageView) itemView.findViewById(R.id.thumbnail);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(context, Viewactivity.class);

                    Details details = (Details) v.getTag();

                    String strUrl = details.getTv_image();
                    String product_name = details.getTv_name();
                    String product_price = details.getTv_price();
                    String product_description=details.getTv_descrption();
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

