package com.muni.in.model.restaurant;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sathish on 5/5/2018.
 */

public class requestRestaurant {
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
   /* @SerializedName("createdAt")
    String createedAt;
    @SerializedName("lastModifiedAt")
    String lastModifiedAt;
    @SerializedName("deletedTimeStamp")
    String deletedTimestamp;*/
    @SerializedName("createdUser")
    int createdUser;
    @SerializedName("updatedUser")
    int updateUser;

    public requestRestaurant(String restaurantName,String desc,String owner, String branch, String address, String googleLoc,
                             String contactNo, String cusine, String restaurantType, String openTime,
                             String closeTime, String restaurantStatus, String restaurantImage,
                            /* String createdAt,String lastmodified,String deletedtimestamp,*/int createuser,int updateuser) {
        this.restaurantName = restaurantName;
        this.description = desc;
        this.ownername = owner;
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
       // this.createedAt = createdAt;
       // this.lastModifiedAt = lastmodified;
        //this.deletedTimestamp = deletedtimestamp;
        this.createdUser = createuser;
        this.updateUser = updateuser;
    }

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

    public int getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(int createdUser) {
        this.createdUser = createdUser;
    }

    public int getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(int updateUser) {
        this.updateUser = updateUser;
    }

    public void setRestaurantImage(String restaurantImage) {
        this.restaurantImage = restaurantImage;
    }

}
