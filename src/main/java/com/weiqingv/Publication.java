package com.weiqingv;

import java.util.*;

public class Publication {
    private final Map<String, Double> attributes;

    public Publication() {
        this.attributes = new HashMap<>();
    }

    public void addAttribute(String name, double value) {
        attributes.put(name, value);
    }

    public Map<String, Double> getAttributes() {
        return attributes;
    }

    public double getValueByName(String name) {
        if (attributes.containsKey(name))
            return attributes.get(name);
        else
            throw new NoSuchElementException();
    }

    public List<String> getSortedAttributeNames() {
        List<String> attributeNames = new ArrayList<>(attributes.keySet());
        attributeNames.sort((a, b) -> {
            if (a.equals(b))
                return 0;
            else if (a.length() > b.length()) {
                return 1;
            } else if (a.length() < b.length()) {
                return -1;
            } else {
                return a.compareTo(b);
            }
        });
        return attributeNames;
    }
}
