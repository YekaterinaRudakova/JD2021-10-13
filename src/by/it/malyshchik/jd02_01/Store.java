package by.it.malyshchik.jd02_01;


import by.it.malyshchik.jd02_01.helper.RandomInt;
import by.it.malyshchik.jd02_01.helper.Timeout;
import by.it.malyshchik.jd02_01.model.Customer;
import by.it.malyshchik.jd02_01.service.CustomerWorker;
import by.it.malyshchik.jd02_01.service.StoreException;

import java.util.ArrayList;
import java.util.List;

public class Store extends Thread{

    @Override
    public synchronized void start() {
        System.out.println("Store opened");
        int number = 0;
        List<CustomerWorker> threads = new ArrayList<>();
        for (int second = 0; second < 120; second++) {
            int count = RandomInt.random(0, 2);
            for (int i = 0; i < count; i++) {
                Customer customer = new Customer(++number);
                CustomerWorker customerThread = new CustomerWorker(customer);
                threads.add(customerThread);
                customerThread.start();
            }
            Timeout.sleep(1000);
        }
        for (CustomerWorker thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
               throw new StoreException("Interrupted", e);
            }
        }
        System.out.println("Store closed");
    }
}
