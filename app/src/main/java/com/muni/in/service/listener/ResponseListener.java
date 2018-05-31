package com.muni.in.service.listener;

import com.muni.in.model.restaurant.Restaurant;

import java.util.List;

/**
 * Created by sathish on 7/5/18.
 */

public interface ResponseListener {

    void onSuccess(String listener, String response);

    void onSuccess(String listener, List<Restaurant> response);

    void onFailure(String message);
}
