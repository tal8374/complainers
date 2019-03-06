package com.company;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Main {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter the number of people in customer service department");
        int customersNumber = reader.nextInt();
        System.out.println("Enter the number of people in Resolvers department");
        int resolversNumber = reader.nextInt();
        System.out.println("Enter the number of complaints that will enter to the customer service department");
        int complaintsNumber = reader.nextInt();
        reader.close();

        SynchronizedList synchronizedList = new SynchronizedList();

        Thread[] complaintsHandler = new Thread[resolversNumber];
        Thread[] complaintsGenerators = new Thread[customersNumber];

        Semaphore complaintsHandlerSemaphore = new Semaphore(1);

        for (int i = 0; i < complaintsHandler.length; i++) {
            complaintsHandler[i] = new Thread(new ComplaintsHandler(synchronizedList, complaintsNumber,
                    complaintsHandlerSemaphore));

            complaintsHandler[i].start();
        }

        Semaphore complaintsGeneratorSemaphore = new Semaphore(1);

        for (int i = 0; i < complaintsGenerators.length; i++) {
            complaintsGenerators[i] = new Thread(new ComplaintsGenerator(synchronizedList, complaintsNumber,
                    complaintsGeneratorSemaphore));

            complaintsGenerators[i].start();
        }

        for (int i = 0; i < complaintsHandler.length; i++) {
            try {
                complaintsHandler[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < complaintsGenerators.length; i++) {
            try {
                complaintsGenerators[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
