package com.muni.in.view.home;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.muni.in.R;
import com.muni.in.model.restaurant.Restaurant;
import com.muni.in.utils.RestaurantStatus;
import com.muni.in.view.restaurant_onboard.ResDetailsFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

/**
 * Created by sathish on 8/5/18.
 */

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.MyViewHolder>
        implements Filterable{
    public List<Restaurant> resList = new ArrayList<>();
    public List<Restaurant> filterresList = new ArrayList<>();
    private ImageLoader imageLoader;
    private Context context;
    private searchSelectionListener searchSelectionListener;
    //private int cliked_pos;
    private RecycleViewItemClickListener itemClickListener;
    public RestaurantAdapter(Context context,List<Restaurant> resList,
                             searchSelectionListener listener,
                             RecycleViewItemClickListener recycleViewItemClickListener) {
        this.context = context;
        this.resList = resList;
        this.filterresList = resList;
        this.searchSelectionListener = listener;
        imageLoader = ImageLoader.getInstance();
        this.itemClickListener = recycleViewItemClickListener;
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String str = constraint.toString();
                if(str.isEmpty()){
                    filterresList = resList;
                }else{
                    List<Restaurant> filter = new ArrayList<>();
                    for (Restaurant res:resList) {
                        if(str.contains(res.getRestaurantName())){
                            filter.add(res);
                        }
                    }
                    filterresList = filter;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterresList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filterresList = (List<Restaurant>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView resImage;
        TextView resName;
        TextView resType;
        TextView resAddr;
        TextView resStatus;
        public MyViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.card_view);
            resImage = (ImageView)itemView.findViewById(R.id.resImage);
            resName = (TextView) itemView.findViewById(R.id.resName);
            resType = (TextView)itemView.findViewById(R.id.resType);
            resAddr = (TextView)itemView.findViewById(R.id.resAddr);
            resStatus = (TextView)itemView.findViewById(R.id.resStatus);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchSelectionListener.onItemSeleced(filterresList.get(getAdapterPosition()));
                }
            });
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_row,parent,false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.OnItemClick(v,myViewHolder.getAdapterPosition());
            }
        });
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Restaurant restaurant = resList.get(position);
        holder.cardView.setTag(position);
        holder.resName.setText(restaurant.getRestaurantName());
        holder.resType.setText(restaurant.getRestaurantType());
        holder.resAddr.setText(restaurant.getAddress());
       // RestaurantStatus status = RestaurantStatus.values()[Integer.parseInt(restaurant.getRestaurantStatus())];
        holder.resStatus.setText(RestaurantStatus.getStatus(restaurant.getRestaurantStatus()));
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_restaurant) // resource or drawable
                .showImageForEmptyUri(R.drawable.ic_restaurant) // resource or drawable
                .showImageOnFail(R.drawable.ic_restaurant) // resource or drawable
                .resetViewBeforeLoading(false) // default
                .delayBeforeLoading(1000)
                .cacheInMemory(false) // default
                .cacheOnDisk(false) // default
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(250, 250) // default = device screen dimensions
                .diskCacheExtraOptions(250, 250, null)
                .build();
        imageLoader.init(config);
        imageLoader.displayImage(restaurant.getRestaurantImage(), holder.resImage, options, new ImageLoadingListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onLoadingStarted(String imageUri, View view) {

                //holder.resImage.setBackgroundTintList(context.getResources().getColorStateList(R.color.login_button));
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
               // holder.resImage.setImageBitmap(imageUri);
                Log.d("failReason",""+failReason);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                holder.resImage.setImageBitmap(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
       /* holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cliked_pos = Integer.parseInt(""+holder.cardView.getTag());

            }
        });*/
    }
    @Override
    public int getItemCount() {
        return filterresList.size();
    }


    public void addItemList(Restaurant res){
        resList.add(res);

    }

    interface searchSelectionListener{
        void onItemSeleced(Restaurant res);
    }



}
