package com.weiqingv;

import com.weiqingv.util.SubscriptionStore;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DomainSeries {
    private final List<String> attributes;
    private final Map<String, Domain> domains;
    private final Map<String, Integer> attributeToIndex;

    public DomainSeries(List<String> attributes) {
        this.attributes = attributes;
        int attributeNum = attributes.size();
        this.domains = new HashMap<>(attributeNum * (attributeNum - 1) / 2);
        this.attributeToIndex = new HashMap<>(attributeNum);

        for (int i = 1; i <= attributeNum; i++) {
            attributeToIndex.put(attributes.get(i - 1), i);
        }

        for (int i = 1; i <= attributeNum - 1; i++) {
            for (int j = i + 1; j <= attributeNum; j++) {
                String domainKey = String.valueOf(i) + String.valueOf(j);
                domains.put(domainKey, new Domain());
            }
        }
    }

    public void add(Subscription sub) {
        sub.sortConstraints();
        int subSize = sub.getConstraints().size();
        assert sub.getConstraints().size() > 1 : "subscription should have more than 1 constraint.";

        for (int i = 0; i < subSize - 1; i++) {
            Subscription.Constraint constraint = sub.getConstraints().get(i);
            String attribute = constraint.getAttribute();
            Integer attributeIndex = attributeToIndex.get(attribute);
            Subscription.Constraint nextConstraint = sub.getConstraints().get(i + 1);
            String nextAttribute = nextConstraint.getAttribute();
            Integer nextAttributeIndex = attributeToIndex.get(nextAttribute);
            String domainKey = attributeIndex.toString() + nextAttributeIndex.toString();
            Domain domain = domains.get(domainKey);
            domain.addFirst(constraint, sub.getId());

            if (i == subSize - 2) {
                domain.addSecond(nextConstraint, sub.getId());
            }
        }
    }

    public void delete(String subID) {
        Subscription sub = SubscriptionStore.subscriptionMap.get(subID);
        sub.sortConstraints();
        int subSize = sub.getConstraints().size();
        for (int i = 0; i < subSize - 1; i++) {
            Subscription.Constraint constraint = sub.getConstraints().get(i);
            String attribute = constraint.getAttribute();
            Integer attributeIndex = attributeToIndex.get(attribute);
            Subscription.Constraint nextConstraint = sub.getConstraints().get(i + 1);
            String nextAttribute = nextConstraint.getAttribute();
            Integer nextAttributeIndex = attributeToIndex.get(nextAttribute);
            String domainKey = attributeIndex.toString() + nextAttributeIndex.toString();
            Domain domain = domains.get(domainKey);
            domain.deleteFirst(constraint, sub.getId());

            if (i == subSize - 2) {
                domain.deleteFirst(nextConstraint, sub.getId());
            }
        }
    }

    public List<String> match(Publication pub) {
        List<String> matchedID = new LinkedList<>();
        Map<String, Integer> countMap = new HashMap<>(SubscriptionStore.subscriptionMap.size());
        List<String> attributeNames = pub.getSortedAttributeNames();
        int attributeSize = attributeNames.size();
        for (int i = 0; i < attributeSize - 1; i++) {
            for (int j = i + 1; j < attributeSize; j++) {
                String attribute = attributeNames.get(i);
                double value = pub.getValueByName(attribute);
                Integer attributeIndex = attributeToIndex.get(attribute);
                String nextAttribute = attributeNames.get(j);
                double nextValue = pub.getValueByName(nextAttribute);
                Integer nextAttributeIndex = attributeToIndex.get(nextAttribute);
                String domainKey = attributeIndex.toString() + nextAttributeIndex.toString();
                Domain domain = domains.get(domainKey);

                countForCell(domain.firstCell, countMap, value);
                countForCell(domain.secondCell, countMap, nextValue);
            }
        }

        for (String id : countMap.keySet()) {
            int expectedCount = SubscriptionStore.subscriptionMap.get(id).getConstraints().size() * 2;
            if (countMap.get(id) == expectedCount) {
                matchedID.add(id);
            }
        }

        return matchedID;
    }

    private void countForCell(Cell cell, Map<String, Integer> countMap, double eventValue) {
        boolean highFlag = false;   // indicate whether the latter bounds can all add count.
        for (int j = 0; j < cell.highBounds.size(); j++) {
            Pair boundPair = cell.highBounds.get(j);
            if (highFlag) {
                countMap.put(boundPair.id, countMap.getOrDefault(boundPair.id, 0) + 1);
                continue;
            }
            if (eventValue <= boundPair.value) {
                countMap.put(boundPair.id, countMap.getOrDefault(boundPair.id, 0) + 1);
                highFlag = true;
            }
        }

        boolean lowFlag = false;
        for (int j = 0; j < cell.lowBounds.size(); j++) {
            Pair boundPair = cell.lowBounds.get(j);
            if (lowFlag) {
                countMap.put(boundPair.id, countMap.getOrDefault(boundPair.id, 0) + 1);
                continue;
            }
            if (eventValue >= boundPair.value) {
                countMap.put(boundPair.id, countMap.getOrDefault(boundPair.id, 0) + 1);
                lowFlag = true;
            }
        }
    }

    public static class Domain {
        private final Cell firstCell;
        private final Cell secondCell;

        public Domain() {
            this.firstCell = new Cell();
            this.secondCell = new Cell();
        }

        public void addFirst(Subscription.Constraint constraint, String id) {
            double lowValue = constraint.getLowValue();
            double highValue = constraint.getHighValue();
            firstCell.addLowBound(id, lowValue);
            firstCell.addHighBound(id, highValue);
        }

        public void addSecond(Subscription.Constraint constraint, String id) {
            double lowValue = constraint.getLowValue();
            double highValue = constraint.getHighValue();
            secondCell.addLowBound(id, lowValue);
            secondCell.addHighBound(id, highValue);
        }

        public void deleteFirst(Subscription.Constraint constraint, String id) {
            double lowValue = constraint.getLowValue();
            double highValue = constraint.getHighValue();
            firstCell.deleteLowBound(id, lowValue);
            firstCell.deleteHighBound(id, highValue);
        }

        public void deleteSecond(Subscription.Constraint constraint, String id) {
            double lowValue = constraint.getLowValue();
            double highValue = constraint.getHighValue();
            secondCell.deleteLowBound(id, lowValue);
            secondCell.deleteHighBound(id, highValue);
        }
    }

    public static class Cell {
        private final List<Pair> lowBounds;
        private final List<Pair> highBounds;

        public Cell() {
            this.lowBounds = new LinkedList<>();
            this.highBounds = new LinkedList<>();
        }

        public void addLowBound(String id, double value) {
            insertValue(lowBounds, new Pair(id, value), false);
        }

        public void addHighBound(String id, double value) {
            insertValue(highBounds, new Pair(id, value), true);
        }

        public void deleteLowBound(String id, double value) {
            deleteValue(lowBounds, id, value);
        }

        public void deleteHighBound(String id, double value) {
            deleteValue(highBounds, id, value);
        }

        public void insertValue(List<Pair> valueList, Pair pair, boolean isAscend) {
            if (valueList.size() == 0) {
                valueList.add(pair);
                return;
            }

            int index = 0;
            for (int i = 0; i < valueList.size(); i++) {
                boolean condition;
                if (isAscend) {
                    condition = pair.value < valueList.get(i).value;
                } else {
                    condition = pair.value > valueList.get(i).value;
                }

                if (condition) {
                    index = i;
                    break;
                }
                if (i == valueList.size() - 1) {
                    index = i + 1;
                    break;
                }
            }
            valueList.add(index, pair);
        }

        public void deleteValue(List<Pair> valueList, String id, double value) {
            int index = 0;
            for (int i = 0; i < valueList.size(); i++) {
                if (valueList.get(i).value == value && valueList.get(i).id.equals(id)) {
                    index = i;
                    break;
                }
            }
            valueList.remove(index);
        }
    }

    public static class Pair {
        private final String id;
        private final double value;

        public Pair(String id, double value) {
            this.id = id;
            this.value = value;
        }
    }
}
