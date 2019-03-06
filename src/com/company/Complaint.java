package com.company;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

public class Complaint {

    private String contactPhoneNumber;
    private String contactName;
    private String description;
    private String id;
    private boolean isResolved;

    Complaint(String contactPhoneNumber, String contactName, String description) {
        this.contactPhoneNumber = contactPhoneNumber;
        this.contactName = contactName;
        this.description = description;
        this.isResolved = false;
        this.id = createUniqueId();
    }

    private String createUniqueId() {
        return UUID.randomUUID().toString();
    }

    void declareCreation() {
        String creationTime = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss").format(Calendar.getInstance().getTime());

        System.out.println("Complaint with ID - " + this.id + " was created in - " + creationTime + "\n");
    }

    void setResolved() {
        this.isResolved = true;
    }

    void declareResolved() {
        String resolvedTime = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss").format(Calendar.getInstance().getTime());

        System.out.println("Complaint with ID - " + this.id + " was resolved in - " + resolvedTime + "\n");
    }
}
