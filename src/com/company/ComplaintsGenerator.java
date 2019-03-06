package com.company;

import java.util.concurrent.Semaphore;

public class ComplaintsGenerator extends Thread {

    private SynchronizedList complaints;
    private Semaphore semaphore;
    private volatile static int complaintsNumberLeft;

    ComplaintsGenerator(SynchronizedList complaints, int complaintsNumber, Semaphore semaphore) {
        this.complaints = complaints;
        this.semaphore = semaphore;
        ComplaintsGenerator.complaintsNumberLeft = complaintsNumber;
    }

    public void run() {
        while (this.areComplaintsLeft()) {
            try {
                this.semaphore.acquire();
                if (this.areComplaintsLeft()) {
                    this.reduceNumberOfComplaints();
                    this.semaphore.release();
                    generateComplaint();
                } else {
                    this.semaphore.release();
                    break;
                }
            } catch (InterruptedException ignored) {
            }
            try {
                sleep((int) (Math.random() * 100));
            } catch (InterruptedException ignored) {
            }
        }
    }

    private void generateComplaint() {
        Complaint complaint = new Complaint("0541234567", "Tal", "Some complaint");

        complaint.declareCreation();

        this.complaints.addComplaint(complaint);
    }

    private void reduceNumberOfComplaints() {
        ComplaintsGenerator.complaintsNumberLeft--;
    }

    private boolean areComplaintsLeft() {
        return ComplaintsGenerator.complaintsNumberLeft > 0;
    }

}
