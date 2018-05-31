package com.muni.in.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sathish on 15/5/18.
 */

public enum RestaurantStatus {
    Initiated("Initiated"),
    Follow_up_required("Follow up required"),
    Submitted_details("Submitted details"),
    Userid_generated("Userid generated"),
    Agreement_signed("Agreement signed"),
    Affixed_sticker("Affixed sticker"),
    Completed("Completed");
    public int value;
    public String value_string;
    private static List<String> enumvalue;

    RestaurantStatus(String value){
        this.value_string = value;
    }

    RestaurantStatus() {
        this.value = ordinal();
    }

    public static List<String> getEnumvalue(){
        enumvalue = new ArrayList<>();
        for (RestaurantStatus s: RestaurantStatus.values()) {
            enumvalue.add(s.value_string);
        }

        return enumvalue;

    }


    public static String getStatus(String value){
        switch (value){
            case "0":
                return "Initiated";
            case "1":
                return "Follow up required";
            case "2":
                return "Submitted details";
            case "3":
                return "Userid generated";
            case "4":
                return "Agreement signed";
            case "5":
                return "Affixed sticker";
            case "6":
                return "Completed";

        }
        return null;
    }


}
