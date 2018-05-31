package com.muni.in.model.restaurant;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sathish on 5/5/2018.
 */

public class Restaurant implements Parcelable{
    @SerializedName("name")
    String restaurantName;
    @SerializedName("description")
    String description;
    @SerializedName("ownername")
    String ownername;
    @SerializedName("branch")
    String branch;
    @SerializedName("address")
    String address;
    @SerializedName("googlelocation")
    String googleLoc;
    @SerializedName("contactnumbers")
    String contactNo;
    @SerializedName("cusine")
    String cusine;
    @SerializedName("type")
    String restaurantType;
    @SerializedName("openingtiming")
    String openTime;
    @SerializedName("closingtiming")
    String closeTime;
    @SerializedName("status")
    String restaurantStatus;
    @SerializedName("image")
    String restaurantImage;
    @SerializedName("createdAt")
    String createedAt;
    @SerializedName("lastModifiedAt")
    String lastModifiedAt;
    @SerializedName("deletedTimeStamp")
    String deletedTimestamp;
    @SerializedName("createdUser")
    String createdUser;
    @SerializedName("updatedUser")
    String updateUser;
    @SerializedName("id")
    String restaurantId;

    public Restaurant(String restaurantName,String desc,String ownername, String branch, String address, String googleLoc, String contactNo, String cusine, String restaurantType, String openTime, String closeTime,
                      String restaurantStatus, String restaurantImage, String createedAt, String lastModifiedAt, String deletedTimestamp, String createdUser, String updateUser, String restaurantId) {
        this.restaurantName = restaurantName;
        this.description = desc;
        this.ownername = ownername;
        this.branch = branch;
        this.address = address;
        this.googleLoc = googleLoc;
        this.contactNo = contactNo;
        this.cusine = cusine;
        this.restaurantType = restaurantType;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.restaurantStatus = restaurantStatus;
        this.restaurantImage = restaurantImage;
        this.createedAt = createedAt;
        this.lastModifiedAt = lastModifiedAt;
        this.deletedTimestamp = deletedTimestamp;
        this.createdUser = createdUser;
        this.updateUser = updateUser;
        this.restaurantId = restaurantId;
    }

    protected Restaurant(Parcel in) {
        restaurantName = in.readString();
        description = in.readString();
        ownername = in.readString();
        branch = in.readString();
        address = in.readString();
        googleLoc = in.readString();
        contactNo = in.readString();
        cusine = in.readString();
        restaurantType = in.readString();
        openTime = in.readString();
        closeTime = in.readString();
        restaurantStatus = in.readString();
        restaurantImage = in.readString();
        createedAt = in.readString();
        lastModifiedAt = in.readString();
        deletedTimestamp = in.readString();
        createdUser = in.readString();
        updateUser = in.readString();
        restaurantId = in.readString();
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGoogleLoc() {
        return googleLoc;
    }

    public void setGoogleLoc(String googleLoc) {
        this.googleLoc = googleLoc;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getCusine() {
        return cusine;
    }

    public void setCusine(String cusine) {
        this.cusine = cusine;
    }

    public String getRestaurantType() {
        return restaurantType;
    }

    public void setRestaurantType(String restaurantType) {
        this.restaurantType = restaurantType;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getRestaurantStatus() {
        return restaurantStatus;
    }

    public void setRestaurantStatus(String restaurantStatus) {
        this.restaurantStatus = restaurantStatus;
    }

    public String getRestaurantImage() {
        return restaurantImage;
    }

    public void setRestaurantImage(String restaurantImage) {
        this.restaurantImage = restaurantImage;
    }

    public String getCreateedAt() {
        return createedAt;
    }

    public void setCreateedAt(String createedAt) {
        this.createedAt = createedAt;
    }

    public String getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(String lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public String getDeletedTimestamp() {
        return deletedTimestamp;
    }

    public void setDeletedTimestamp(String deletedTimestamp) {
        this.deletedTimestamp = deletedTimestamp;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwnername() {
        return ownername;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(restaurantName);
        dest.writeString(description);
        dest.writeString(ownername);
        dest.writeString(branch);
        dest.writeString(address);
        dest.writeString(googleLoc);
        dest.writeString(contactNo);
        dest.writeString(cusine);
        dest.writeString(restaurantType);
        dest.writeString(openTime);
        dest.writeString(closeTime);
        dest.writeString(restaurantStatus);
        dest.writeString(restaurantImage);
        dest.writeString(createedAt);
        dest.writeString(lastModifiedAt);
        dest.writeString(deletedTimestamp);
        dest.writeString(createdUser);
        dest.writeString(updateUser);
        dest.writeString(restaurantId);
    }
}
