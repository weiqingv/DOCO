package com.weiqingv.util;

import com.weiqingv.Subscription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubscriptionStore {
    public static Map<String, Subscription> subscriptionMap = new HashMap<>();
    public static List<String> idList;
    public static List<Subscription> subscriptionList;

    public static List<Subscription> getSubscriptionList() {
        if (subscriptionList == null) {
            subscriptionList = new ArrayList<>(subscriptionMap.values());
        }
        return subscriptionList;
    }

    public static List<String> getIdList() {
        if (idList == null) {
            idList = new ArrayList<>(subscriptionMap.keySet());
        }
        return idList;
    }
}
