package com.sandesh.subscriptionplanner;

import java.io.Serializable;
import java.util.Date;

public class SubscriptionClass implements Serializable {
    private String subsName;
    private String subsDescription;
    private String subsDate;
    private String subsAmount;

    public SubscriptionClass(String itemName, String desc, String date, String amount) {
        subsName = itemName;
        subsDescription = desc;
        subsDate = date;
        subsAmount = amount;
    }

    public String getSubsName() {
        return subsName;
    }

    public String getSubsDescription() {
        return subsDescription;
    }

    public String getSubsDate() {
        return subsDate;
    }

    public String getSubsAmount() {
        return subsAmount;
    }
}
