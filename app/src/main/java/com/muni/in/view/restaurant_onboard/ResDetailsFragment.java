package com.muni.in.view.restaurant_onboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.muni.in.R;
import com.muni.in.data.SharedPreference;
import com.muni.in.model.restaurant.Restaurant;
import com.muni.in.service.Client;
import com.muni.in.service.listener.ResponseListener;
import com.muni.in.utils.RestaurantStatus;
import com.muni.in.view.home.HomeActivity;
import com.muni.in.view.home.Toggle;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;


public class ResDetailsFragment extends Fragment implements ResponseListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Restaurant mParam1;
    private String mParam2;

    private SharedPreference sharedpref;
    private View rootView;
    private TextView textResName,textResDesc,textResAddr,textResLoc,textResContact;
    private TableRow rowCuisines;
    private TextView textResType,textResTiming,textResStatus,textResOwner;
    private ImageView resImage;
    private ImageLoader imageLoader;
    public ResDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResDetailsFragment newInstance(Restaurant param1, String param2) {
        ResDetailsFragment fragment = new ResDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedpref = new SharedPreference(getActivity());
        textResName.setText(mParam1.getRestaurantName());
        textResDesc.setText(mParam1.getDescription());
        textResOwner.setText(mParam1.getOwnername());
        textResAddr.setText(mParam1.getAddress());
        textResLoc.setText(mParam1.getGoogleLoc());
        textResContact.setText(mParam1.getContactNo());
        String cus[] = mParam1.getCusine().split(",");
        rowCuisines.removeAllViews();
        for (String s:cus) {
            TextView text = new TextView(getActivity());
            text.setTextAppearance(getActivity(),android.R.style.TextAppearance_Medium);
            text.setText(s);
            text.setBackground(getResources().getDrawable(R.drawable.rect_stoke));
            text.setPadding(10,10,10,10);
            TableRow.LayoutParams param = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            param.setMargins(0,0,5,0);
            text.setLayoutParams(param);
            rowCuisines.addView(text);
        }
        textResType.setText(mParam1.getRestaurantType());
        textResTiming.setText(mParam1.getOpenTime() +" - "+mParam1.getCloseTime());
        textResStatus.setText(RestaurantStatus.getStatus(mParam1.getRestaurantStatus()));
    }

    @Override
    public void onStart() {
        super.onStart();
        ((HomeActivity)getActivity()).setTitle(mParam1.getRestaurantName());
        ((HomeActivity)getActivity()).showUpButton(true, Toggle.BACK);
    }

    @Override
    public void onStop() {
        super.onStop();
        ((HomeActivity)getActivity()).setTitle("Restaurant List");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.edit:
                FragmentTransaction transaction = ((HomeActivity)getActivity()).getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_enter, R.anim.slide_exit, R.anim.slide_reverse_exit, R.anim.slide_reverse_enter);
                transaction.replace(R.id.frag_container, RestaurantRegFragment.newInstance(mParam1,
                        mParam1.getRestaurantId()), "Reg").addToBackStack(null).commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.res_details,menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem menuItemEdit = menu.findItem(R.id.action_search);
        menuItemEdit.setVisible(false);
        MenuItem menuItemadd = menu.findItem(R.id.action_add);
        menuItemadd.setVisible(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(rootView == null)
            rootView = inflater.inflate(R.layout.fragment_res_details, container, false);
        textResName = (TextView)rootView.findViewById(R.id.textResname);
        textResDesc = (TextView)rootView.findViewById(R.id.textResDesc);
        textResAddr = (TextView)rootView.findViewById(R.id.resAddress);
        textResLoc = (TextView)rootView.findViewById(R.id.resLocation);
        textResContact = (TextView)rootView.findViewById(R.id.resContactno);
        rowCuisines = (TableRow)rootView.findViewById(R.id.tablerow_cuisines);
        textResType = (TextView)rootView.findViewById(R.id.resType);
        textResTiming = (TextView)rootView.findViewById(R.id.resTiming);
        textResStatus = (TextView)rootView.findViewById(R.id.resStatus);
        textResOwner = (TextView)rootView.findViewById(R.id.resOwner);
        resImage = (ImageView)rootView.findViewById(R.id.resImage);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_restaurant) // resource or drawable
                .showImageForEmptyUri(R.drawable.ic_restaurant) // resource or drawable
                .showImageOnFail(R.drawable.ic_restaurant) // resource or drawable
                .resetViewBeforeLoading(false) // default
                .delayBeforeLoading(1000)
                .cacheInMemory(false) // default
                .cacheOnDisk(false) // default
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity())
                .memoryCacheExtraOptions(250, 250) // default = device screen dimensions
                .diskCacheExtraOptions(250, 250, null)
                .build();
        imageLoader.init(config);
        imageLoader.displayImage(mParam1.getRestaurantImage(), resImage, options, new ImageLoadingListener() {
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
                resImage.setImageBitmap(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
        return rootView;
    }


    @Override
    public void onSuccess(String listener, String response) {

    }

    @Override
    public void onSuccess(String listener, List<Restaurant> response) {

    }

    @Override
    public void onFailure(String message) {

    }
}
