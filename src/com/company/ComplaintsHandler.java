package com.company;

import java.util.concurrent.Semaphore;

public class ComplaintsHandler extends Thread {

    private SynchronizedList complaints;
    private Semaphore semaphore;
    private static int complaintsNumber;

    ComplaintsHandler(SynchronizedList complaints, int complaintsNumber, Semaphore semaphore) {
        this.complaints = complaints;
        ComplaintsHandler.complaintsNumber = complaintsNumber;
        this.semaphore = semaphore;
    }

    public void run() {
        while (this.areComplaintsLeft()) {
            try {
                this.semaphore.acquire();
                if (this.areComplaintsLeft()) {
                    this.reduceNumberOfComplaints();
                    this.semaphore.release();
                    resolveComplaint();
                } else {
                    this.semaphore.release();
                    break;
                }
            } catch (InterruptedException ignored) {
            }
        }
    }

    private void resolveComplaint() {
        Complaint complaint = complaints.getUnresolvedComplaint();

        complaint.setResolved();

        complaint.declareResolved();
    }

    private void reduceNumberOfComplaints() {
        ComplaintsHandler.complaintsNumber--;
    }

    private boolean areComplaintsLeft() {
        return ComplaintsHandler.complaintsNumber > 0;
    }

}
