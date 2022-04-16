package com.weiqingv;

import com.weiqingv.util.SubscriptionStore;

import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> attributes = new LinkedList<>();
        attributes.add("1");
        attributes.add("2");
        attributes.add("3");
        attributes.add("4");
        attributes.add("5");

        Subscription sub0 = new Subscription("sub0");
        sub0.addConstraint("1", 3, 7);
        sub0.addConstraint("2", 2, 8);
        sub0.addConstraint("4", 9, 14);

        Subscription sub1 = new Subscription("sub1");
        sub1.addConstraint("1", 7, 12);
        sub1.addConstraint("2", 2, 7);
        sub1.addConstraint("3", 1, 8);

        Subscription sub4 = new Subscription("sub4");
        sub4.addConstraint("1", 17, 19);
        sub4.addConstraint("2", 3, 9);
        sub4.addConstraint("5", 12, 18);

        Subscription sub7 = new Subscription("sub7");
        sub7.addConstraint("1", 2, 13);
        sub7.addConstraint("4", 7, 12);
        sub7.addConstraint("5", 15, 20);

        SubscriptionStore.subscriptionMap.put("sub0", sub0);
        SubscriptionStore.subscriptionMap.put("sub1", sub1);
        SubscriptionStore.subscriptionMap.put("sub4", sub4);
        SubscriptionStore.subscriptionMap.put("sub7", sub7);

        DomainSeries series = new DomainSeries(attributes);
        series.add(sub0);
        series.add(sub1);
        series.add(sub4);
        series.add(sub7);

        Publication pub0 = new Publication();
        pub0.addAttribute("1", 6);
        pub0.addAttribute("2", 4);
        pub0.addAttribute("4", 11);
        pub0.addAttribute("5", 17);

        System.out.println(series.match(pub0));
    }
}
