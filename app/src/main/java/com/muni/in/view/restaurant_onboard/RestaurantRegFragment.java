package com.muni.in.view.restaurant_onboard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.muni.in.R;
import com.muni.in.data.SharedPreference;
import com.muni.in.model.restaurant.Restaurant;
import com.muni.in.model.restaurant.requestRestaurant;
import com.muni.in.service.Client;
import com.muni.in.service.listener.ResponseListener;
import com.muni.in.utils.RestaurantStatus;
import com.muni.in.view.home.HomeActivity;
import com.muni.in.view.home.Toggle;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;


public class RestaurantRegFragment extends Fragment implements View.OnClickListener, TimeListener,
        Imageutils.ImageAttachmentListener, ResponseListener, MapFragment.LocationCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Restaurant mParam1;
    private String mParam2;

    private EditText editResName;
    private EditText editResBranch;
    private EditText editResAddress;
    private EditText editResLocation;
    private EditText editResContactno;
    private EditText editResCuisines;
    private EditText editOpenTime;
    private EditText editCloseTime;
    private EditText editDesc;
    private TextInputLayout editLinearLoc;
    private AppCompatSpinner spinStatus;
    private EditText editOwner;
    private ImageView resImage;
    private Imageutils imageutils;
    private HomeActivity activity;
    private SharedPreference sharedpref;
    private View mProgressView;
    private TextView textTitle;
    private String strResName, strBranch, strAddr, strLoc, strContactNo, strCuisines;
    private String strOpentime, strClosetime;
    private String strResType, strDesc, strStatus, strOwner, strImg;
    private RadioGroup resType;
    private RadioButton radioVeg, radioNonveg;
    private View rootView = null;
    private Bitmap bitmap;
    private String FileName;
    private String Path;
    private File imageFile;
    private Location currentLocation;
    private ImageLoader imageLoader;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestaurantRegFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RestaurantRegFragment newInstance(Restaurant param1, String param2) {
        RestaurantRegFragment fragment = new RestaurantRegFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public RestaurantRegFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle("On Board Restaurant");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (getArguments() == null)
        getActivity().setTitle("Restaurant List");
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        imageutils = new Imageutils(getActivity(), this, true);
        sharedpref = new SharedPreference(getActivity());
        ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), R.layout.spin_list_style, RestaurantStatus.getEnumvalue());
        adapter.setDropDownViewResource(R.layout.spin_dropdown_item);
        spinStatus.setAdapter(adapter);
        imageLoader = ImageLoader.getInstance();
        if (getArguments() == null) {
            textTitle.setText("ADD RESTAURANT");
            editOpenTime.setText(sdf.format(c.getTime()));
            editCloseTime.setText(sdf.format(c.getTime()));
            String Status = RestaurantStatus.values()[0].toString();
            strStatus = String.valueOf(RestaurantStatus.valueOf(Status).ordinal());
        }else{
            textTitle.setText("EDIT RESTAURANT");
            editDesc.setText(mParam1.getDescription());
            editOwner.setText(mParam1.getOwnername());
            editOpenTime.setText(mParam1.getOpenTime());
            editCloseTime.setText(mParam1.getCloseTime());
            spinStatus.setSelection(Integer.parseInt(mParam1.getRestaurantStatus()));

            editResName.setText(mParam1.getRestaurantName());
            editResBranch.setText(mParam1.getBranch());
            editResAddress.setText(mParam1.getAddress());
            editResLocation.setText(mParam1.getGoogleLoc());
            editResContactno.setText(mParam1.getContactNo());
            editResCuisines.setText(mParam1.getCusine());

            if(mParam1.getRestaurantType().equalsIgnoreCase("0") ||
                    mParam1.getRestaurantType().equalsIgnoreCase("Vegetarian")){
                radioVeg.setChecked(true);
            }else
                radioNonveg.setChecked(true);

            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity())
                    .memoryCacheExtraOptions(250, 250) // default = device screen dimensions
                    .diskCacheExtraOptions(250, 250, null)
                    .build();
            imageLoader.init(config);
            strImg = mParam1.getRestaurantImage();
            imageLoader.displayImage(mParam1.getRestaurantImage(),resImage);
        }
        //Log.d("TAG", strStatus);
        //Log.d("TAG", "onItemSelected: " + Status);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null)
            rootView = inflater.inflate(R.layout.fragment_restaurant_reg, container, false);
        textTitle = (TextView) rootView.findViewById(R.id.frag_Title);
        editResName = (EditText) rootView.findViewById(R.id.edit_resName);
        editResBranch = (EditText) rootView.findViewById(R.id.edit_resBranch);
        editResAddress = (EditText) rootView.findViewById(R.id.edit_resAddress);
        editResLocation = (EditText) rootView.findViewById(R.id.edit_resloc);
        editResContactno = (EditText) rootView.findViewById(R.id.edit_rescontactno);
        editResCuisines = (EditText) rootView.findViewById(R.id.edit_rescuisines);
        editOpenTime = (EditText) rootView.findViewById(R.id.edit_opentime);
        editCloseTime = (EditText) rootView.findViewById(R.id.edit_closetime);
        resType = (RadioGroup) rootView.findViewById(R.id.radioGroup);
        radioVeg = (RadioButton) rootView.findViewById(R.id.res_veg);
        radioNonveg = (RadioButton) rootView.findViewById(R.id.res_nonVeg);
        editLinearLoc = (TextInputLayout) rootView.findViewById(R.id.linearloc);
        mProgressView = rootView.findViewById(R.id.progress);
        editResLocation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //Log.e("TAG", "onclick map");
                    int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
                    if (status == ConnectionResult.SUCCESS) {
                        // Log.e("TAG","onclick success");
                        MapFragment fragment = new MapFragment(RestaurantRegFragment.this);
                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.setCustomAnimations(R.anim.slide_enter, R.anim.slide_exit, R.anim.slide_reverse_exit, R.anim.slide_reverse_enter);
                        transaction.replace(R.id.frag_container, fragment).addToBackStack("map").commit();
                        manager.executePendingTransactions();
                    } else {
                        Toast.makeText(getActivity(), "Google play service not avaialable", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        editOpenTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call_timepicker("open");
            }
        });
        editCloseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call_timepicker("close");
            }
        });
        editDesc = (EditText) rootView.findViewById(R.id.edit_desc);
        spinStatus = (AppCompatSpinner) rootView.findViewById(R.id.edit_status);
        editOwner = (EditText) rootView.findViewById(R.id.edit_owner_name);
        resImage = (ImageView) rootView.findViewById(R.id.res_images);
        resImage.setOnClickListener(this);
        resType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radio = (RadioButton) rootView.findViewById(checkedId);
                strResType = radio.getText().toString();
               /* if (radio.getText().toString().equalsIgnoreCase("Vegetarian"))
                    strResType = "0";
                else
                    strResType = "1";*/

            }
        });
        spinStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String Status = RestaurantStatus.values()[position].toString();
                strStatus = String.valueOf(RestaurantStatus.valueOf(Status).ordinal());
                //Log.d("TAG",strStatus);
                //Log.d("TAG", "onItemSelected: "+Status);
                //Toast.makeText(getActivity(),"@@@"+strStatus,Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity(),"***"+strStatus,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.reg_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem menuItemEdit = menu.findItem(R.id.action_search);
        menuItemEdit.setVisible(false);
        MenuItem menuItemadd = menu.findItem(R.id.action_add);
        menuItemadd.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_ok:
                addRestaurant(rootView);
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    private void addRestaurant(View root) {
        boolean cancel = false;
        View focusView = null;

        strResName = editResName.getText().toString().trim();
        strBranch = editResBranch.getText().toString().trim();
        strAddr = editResAddress.getText().toString().trim();
        strLoc = editResLocation.getText().toString().trim();
        strContactNo = editResContactno.getText().toString().trim();
        strCuisines = editResCuisines.getText().toString().trim();
        strOpentime = editOpenTime.getText().toString().trim();
        strClosetime = editCloseTime.getText().toString().trim();
        strDesc = editDesc.getText().toString().trim();
        // strStatus = editStatus.getText().toString().trim();
        strOwner = editOwner.getText().toString().trim();
        int selectedid = resType.getCheckedRadioButtonId();
        RadioButton restype = (RadioButton) root.findViewById(selectedid);
        strResType = restype.getText().toString();
       /* if (restype.getText().toString().equalsIgnoreCase("Vegetarian"))
            strResType = "0";
        else
            strResType = "1";*/
        if (TextUtils.isEmpty(strResName)) {
            editResName.setError("Name is required");
            focusView = editResName;
            cancel = true;
        } else if (TextUtils.isEmpty(strBranch)) {
            editResBranch.setError("BranchName is required");
            focusView = editResName;
            cancel = true;
        } else if (TextUtils.isEmpty(strAddr)) {
            editResAddress.setError("Address is required");
            focusView = editResAddress;
            cancel = true;
        } /*else if (TextUtils.isEmpty(strLoc)) {
            editResLocation.setError("Location is required");
            focusView = editResLocation;
            cancel = true;
        } */ else if (TextUtils.isEmpty(strContactNo)) {
            editResContactno.setError("Contact is required");
            focusView = editResName;
            cancel = true;
        } else if (TextUtils.isEmpty(strCuisines)) {
            editResCuisines.setError("Cuisines is required");
            focusView = editResCuisines;
            cancel = true;
        } else if (TextUtils.isEmpty(strOpentime)) {
            editOpenTime.setError("Opentime is required");
            focusView = editOpenTime;
            cancel = true;
        }else if (!checkTimeFormate(strOpentime)) {
            editOpenTime.setError("Enter Proper Opentime");
            focusView = editOpenTime;
            cancel = true;
        } else if (TextUtils.isEmpty(strClosetime)) {
            editCloseTime.setError("CloseTime is required");
            focusView = editCloseTime;
            cancel = true;
        }else if (!checkTimeFormate(strClosetime)) {
            editCloseTime.setError("Enter Proper CloseTime");
            focusView = editCloseTime;
            cancel = true;
        }else if(TextUtils.isEmpty(strDesc)){
            editDesc.setError("Description is required");
            focusView = editDesc;
            cancel = true;
        }/*else if(TextUtils.isEmpty(strStatus)){
            editStatus.setError("Restaurant Status is required");
            focusView = editStatus;
            cancel = true;
        }*/else if(TextUtils.isEmpty(strOwner)){
            editOwner.setError("Restaurant Owner is required");
            focusView = editOwner;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {

            if (!TextUtils.isEmpty(strImg)) {
                //showProgress(true);
                requestRestaurant request = new requestRestaurant(strResName,strDesc,strOwner,strBranch,
                        strAddr, strLoc, strContactNo, strCuisines, strResType, strOpentime, strClosetime,
                        strStatus, strImg,mParam1.getCreateedAt(),mParam1.getLastModifiedAt(),mParam1.getDeletedTimestamp(),sharedpref.getUserId(),sharedpref.getUserId());
                if(getArguments() == null)
                    new Client(getActivity(), this).addRestaurant(sharedpref.getAccessToken(), request);
                else
                    new Client(getActivity(), this).updateRestaurant(sharedpref.getAccessToken(), request,
                            Integer.parseInt(mParam1.getRestaurantId()));

            } else{
                Snackbar snackbar = Snackbar.make(resImage, "Restaurant Image needed", Snackbar.LENGTH_LONG);
                View sview = snackbar.getView();
                sview.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
                TextView textView = (TextView) sview.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(getResources().getColor(R.color.white));
                snackbar.show();
            }
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
   /* @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

//            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//                }
//            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            // mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }*/
    @Override
    public void onResume() {
        super.onResume();

        if (currentLocation != null) {
            Log.d("CURRENT LOC", currentLocation.toString());
            String lat = getLatitudeAsDMS(currentLocation,2);
            String lang = getLongitudeAsDMS(currentLocation,2);
            editResLocation.setText(lat + "," + lang);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imageutils.request_permission_result(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageutils.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onClick(View v) {
        if (imageutils.isDeviceSupportCamera()) {
            imageutils.imagepicker(1);
        } else
            Toast.makeText(getActivity(), "Camera not support", Toast.LENGTH_SHORT).show();
    }

    private void call_timepicker(String str) {
        DialogFragment newFragment = new TimePickerFragment(this);
        newFragment.show(getActivity().getSupportFragmentManager(), str);
    }

    @Override
    public void setTime(String time, String tag) {
        if (tag.equalsIgnoreCase("open")) {
            editOpenTime.setText(time);
        } else if (tag.equalsIgnoreCase("close")) {
            editCloseTime.setText(time);
        } else {
            editOpenTime.setText(time);
            editCloseTime.setText(time);
        }

    }

    @Override
    public void image_attachment(int from, String filename, Bitmap bitmap, Uri uri) {
        //resImage.setImageBitmap(bitmap);
        this.bitmap = bitmap;
        this.FileName = filename;
        String path = imageutils.getRealPathFromURI(uri.toString());
        File f = new File(path);
        //Toast.makeText(getActivity(), "" + f.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        //showProgress(true);
        new Client(getActivity(), this).uploadImage(sharedpref.getAccessToken(), f);
    }

    @Override
    public void onSuccess(String listener, String response) {
        //showProgress(false);
        if (listener.equalsIgnoreCase("image uploaded")) {
            strImg = response.substring(response.indexOf("https:"), response.indexOf("\"}}]}"));
            //Log.d("location",strImg);
            resImage.setImageBitmap(bitmap);
        } else if (listener.equalsIgnoreCase("restaurant added")) {
            try {
                JSONObject jobj = new JSONObject(response);
                Restaurant res = new Restaurant(jobj.getString("name"),jobj.getString("description"),jobj.getString("ownername"), jobj.getString("branch"),
                        jobj.getString("address"), jobj.getString("googlelocation"), jobj.getString("contactnumbers"),
                        jobj.getString("cusine"), jobj.getString("type"), jobj.getString("openingtiming"),
                        jobj.getString("closingtiming"), jobj.getString("status"), jobj.getString("image"),
                        jobj.getString("closingtiming"), jobj.getString("lastModifiedAt"), jobj.getString("deletedTimeStamp"), jobj.getString("createdUser"),
                        jobj.getString("updatedUser"), jobj.getString("id"));
                ((HomeActivity) getActivity()).adapter.addItemList(res);
                ((HomeActivity) getActivity()).adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ((HomeActivity) getActivity()).onBackPressed();
        }else if(listener.equalsIgnoreCase("updateRes")){
            try {
                JSONObject jobj = new JSONObject(response);
                Restaurant res = new Restaurant(jobj.getString("name"),jobj.getString("description"),jobj.getString("ownername"), jobj.getString("branch"),
                        jobj.getString("address"), jobj.getString("googlelocation"), jobj.getString("contactnumbers"),
                        jobj.getString("cusine"), jobj.getString("type"), jobj.getString("openingtiming"),
                        jobj.getString("closingtiming"), jobj.getString("status"), jobj.getString("image"),
                        jobj.getString("createdAt"), jobj.getString("lastModifiedAt"), null, jobj.getString("createdUser"),
                        jobj.getString("updatedUser"), jobj.getString("id"));
                ((HomeActivity) getActivity()).adapter.resList.set(Integer.parseInt(mParam2),res);
                ((HomeActivity) getActivity()).adapter.notifyDataSetChanged();
                if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getActivity().getSupportFragmentManager()
                            .popBackStack(getActivity().getSupportFragmentManager().getBackStackEntryAt(0).getId(),
                                    getActivity().getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);
                    ((HomeActivity)getActivity()).showUpButton(false, Toggle.CLOSE);
                    ((HomeActivity)getActivity()).setTitle("Restaurant List");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onSuccess(String listener, List<Restaurant> response) {

    }

    @Override
    public void onFailure(String message) {
        Log.e("TAG", message);
    }

    @Override
    public void currentLoc(Location location) {
        currentLocation = location;
    }

    @SuppressLint("ValidFragment")
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {
        private TimeListener listener;
        private String Tag = "";

        public TimePickerFragment(TimeListener listener) {
            this.listener = listener;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            Fragment fragment = getFragmentManager().findFragmentByTag("open");
            if (fragment instanceof TimePickerFragment)
                Tag = "open";
            else
                Tag = "close";
            listener.setTime(String.format("%02d:%02d %s", hourOfDay, minute,
                    hourOfDay < 12 ? "AM" : "PM"), Tag);
        }
    }

    public boolean checkTimeFormate(String time){
        StringTokenizer stringTokenizer = new StringTokenizer(time);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        Date dt;
        try{
            dt = sdf.parse(time);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    //// Location converter

    public static String getLatitudeAsDMS(Location location, int decimalPlace){
        String strLatitude = Location.convert(location.getLatitude(), Location.FORMAT_SECONDS);
        strLatitude = replaceDelimiters(strLatitude, decimalPlace);
        strLatitude = strLatitude + " N";
        return strLatitude;
    }

    public static String getLongitudeAsDMS(Location location, int decimalPlace){
        String strLongitude = Location.convert(location.getLongitude(), Location.FORMAT_SECONDS);
        strLongitude = replaceDelimiters(strLongitude, decimalPlace);
        strLongitude = strLongitude + " W";
        return strLongitude;
    }

    @NonNull
    private static String replaceDelimiters(String str, int decimalPlace) {
        str = str.replaceFirst(":", "°");
        str = str.replaceFirst(":", "'");
        int pointIndex = str.indexOf(".");
        int endIndex = pointIndex + 1 + decimalPlace;
        if(endIndex < str.length()) {
            str = str.substring(0, endIndex);
        }
        str = str + "\"";
        return str;
    }

}
