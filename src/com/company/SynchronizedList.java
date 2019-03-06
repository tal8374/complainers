package com.company;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

class SynchronizedList {

    private final List<Complaint> complaints = Collections.synchronizedList(new LinkedList<Complaint>());
    private List<Complaint> handledComplaints = new LinkedList<>();

    Complaint getUnresolvedComplaint() {
        synchronized (this.complaints) {

            while (this.complaints.isEmpty()) {
                try {
                    this.complaints.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Complaint complaint = this.complaints.remove(0);

            this.handledComplaints.add(complaint);

            return complaint;
        }
    }

    void addComplaint(Complaint complaint) {
        synchronized (this.complaints) {
            this.complaints.add(complaint);

            this.complaints.notifyAll();
        }
    }

}



