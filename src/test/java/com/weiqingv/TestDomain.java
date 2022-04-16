package com.weiqingv;

import com.weiqingv.util.SubscriptionStore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestDomain {
    public DomainSeries buildDomain1() {
        List<String> attributes = new ArrayList<>();
        attributes.add("1");
        attributes.add("2");

        DomainSeries domain = new DomainSeries(attributes);

        Subscription sub0 = new Subscription("sub0");
        sub0.addConstraint("1", 0.0, 0.1);
        sub0.addConstraint("2", 0.2, 0.3);

        Subscription sub1 = new Subscription("sub1");
        sub1.addConstraint("1", 0.2, 0.3);
        sub1.addConstraint("2", 0.8, 0.9);

        Subscription sub2 = new Subscription("sub2");
        sub2.addConstraint("1", 0.2, 0.3);
        sub2.addConstraint("2", 0.1, 0.2);

        Subscription sub3 = new Subscription("sub3");
        sub3.addConstraint("1", 0.7, 0.8);
        sub3.addConstraint("2", 0.3, 0.4);

        Subscription sub4 = new Subscription("sub4");
        sub4.addConstraint("1", 0.5, 0.6);
        sub4.addConstraint("2", 0.4, 0.5);

        Subscription sub5 = new Subscription("sub5");
        sub5.addConstraint("1", 0.1, 0.2);
        sub5.addConstraint("2", 0.8, 0.9);

        Subscription sub6 = new Subscription("sub6");
        sub6.addConstraint("1", 0.4, 0.5);
        sub6.addConstraint("2", 0.6, 0.7);

        Subscription sub7 = new Subscription("sub7");
        sub7.addConstraint("1", 0.9, 1.0);
        sub7.addConstraint("2", 0.9, 1.0);

        Subscription sub8 = new Subscription("sub8");
        sub8.addConstraint("1", 0.6, 0.7);
        sub8.addConstraint("2", 0.5, 0.6);

        Subscription sub9 = new Subscription("sub9");
        sub9.addConstraint("1", 0.8, 0.9);
        sub9.addConstraint("2", 0.3, 0.4);

        SubscriptionStore.subscriptionMap.put("sub0", sub0);
        SubscriptionStore.subscriptionMap.put("sub1", sub1);
        SubscriptionStore.subscriptionMap.put("sub2", sub2);
        SubscriptionStore.subscriptionMap.put("sub3", sub3);
        SubscriptionStore.subscriptionMap.put("sub4", sub4);
        SubscriptionStore.subscriptionMap.put("sub5", sub5);
        SubscriptionStore.subscriptionMap.put("sub6", sub6);
        SubscriptionStore.subscriptionMap.put("sub7", sub7);
        SubscriptionStore.subscriptionMap.put("sub8", sub8);
        SubscriptionStore.subscriptionMap.put("sub9", sub9);

        domain.add(sub0);
        domain.add(sub1);
        domain.add(sub2);
        domain.add(sub3);
        domain.add(sub4);
        domain.add(sub5);
        domain.add(sub6);
        domain.add(sub7);
        domain.add(sub8);
        domain.add(sub9);

        return domain;
    }

    public DomainSeries buildDomain2() {
        List<String> attributes = new ArrayList<>();
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

        Subscription sub2 = new Subscription("sub2");
        sub2.addConstraint("1", 2, 9);
        sub2.addConstraint("3", 13, 16);
        sub2.addConstraint("4", 6, 14);

        Subscription sub3 = new Subscription("sub3");
        sub3.addConstraint("2", 7, 13);
        sub3.addConstraint("3", 3, 6);
        sub3.addConstraint("5", 8, 17);


        Subscription sub4 = new Subscription("sub4");
        sub4.addConstraint("1", 17, 19);
        sub4.addConstraint("2", 3, 9);
        sub4.addConstraint("5", 12, 18);

        Subscription sub5 = new Subscription("sub5");
        sub5.addConstraint("2", 16, 18);
        sub5.addConstraint("3", 13, 17);
        sub5.addConstraint("4", 4, 9);

        Subscription sub6 = new Subscription("sub6");
        sub6.addConstraint("3", 11, 19);
        sub6.addConstraint("4", 4, 16);
        sub6.addConstraint("5", 4, 14);

        Subscription sub7 = new Subscription("sub7");
        sub7.addConstraint("1", 2, 13);
        sub7.addConstraint("4", 7, 12);
        sub7.addConstraint("5", 15, 20);

        Subscription sub8 = new Subscription("sub8");
        sub8.addConstraint("1", 6, 11);
        sub8.addConstraint("3", 11, 14);
        sub8.addConstraint("5", 13, 18);

        Subscription sub9 = new Subscription("sub9");
        sub9.addConstraint("2", 8, 11);
        sub9.addConstraint("4", 2, 4);
        sub9.addConstraint("5", 2, 13);

        SubscriptionStore.subscriptionMap.put("sub0", sub0);
        SubscriptionStore.subscriptionMap.put("sub1", sub1);
        SubscriptionStore.subscriptionMap.put("sub2", sub2);
        SubscriptionStore.subscriptionMap.put("sub3", sub3);
        SubscriptionStore.subscriptionMap.put("sub4", sub4);
        SubscriptionStore.subscriptionMap.put("sub5", sub5);
        SubscriptionStore.subscriptionMap.put("sub6", sub6);
        SubscriptionStore.subscriptionMap.put("sub7", sub7);
        SubscriptionStore.subscriptionMap.put("sub8", sub8);
        SubscriptionStore.subscriptionMap.put("sub9", sub9);

        DomainSeries series = new DomainSeries(attributes);
        series.add(sub0);
        series.add(sub1);
        series.add(sub2);
        series.add(sub3);
        series.add(sub4);
        series.add(sub5);
        series.add(sub6);
        series.add(sub7);
        series.add(sub8);
        series.add(sub9);

        return series;
    }

    @Test
    public void testMatch1() {
        DomainSeries domain = buildDomain1();
        Publication pub1 = new Publication();
        pub1.addAttribute("1", 0.24);
        pub1.addAttribute("2", 0.82);

        List<String> matchedIDs = domain.match(pub1);

        assert matchedIDs.size() == 1;
        assert matchedIDs.get(0).equals("sub1");
    }

    @Test
    public void testMatch2() {
        DomainSeries series = buildDomain2();
        Publication pub0 = new Publication();
        pub0.addAttribute("1", 6);
        pub0.addAttribute("2", 4);
        pub0.addAttribute("4", 11);
        pub0.addAttribute("5", 17);

        List<String> matchedIDs = series.match(pub0);

        assert matchedIDs.size() == 2;
        assert matchedIDs.contains("sub0");
        assert matchedIDs.contains("sub7");
    }

    @Test
    public void testDelete1() {
        DomainSeries series = buildDomain2();

        series.delete("sub0");

        Publication pub0 = new Publication();
        pub0.addAttribute("1", 6);
        pub0.addAttribute("2", 4);
        pub0.addAttribute("4", 11);
        pub0.addAttribute("5", 17);

        List<String> matchedIDs = series.match(pub0);

        assert matchedIDs.size() == 1;
        assert matchedIDs.contains("sub7");
    }

    @Test
    public void testDelete2() {
        DomainSeries series = buildDomain1();

        series.delete("sub1");

        Publication pub1 = new Publication();
        pub1.addAttribute("1", 0.24);
        pub1.addAttribute("2", 0.82);

        List<String> matchedIDs = series.match(pub1);

        assert matchedIDs.size() == 0;
    }
}
