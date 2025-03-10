package by.it.malyshchik.jd02_02;

import by.it.malyshchik.jd02_02.helper.RandomInt;
import by.it.malyshchik.jd02_02.helper.Timeout;
import by.it.malyshchik.jd02_02.model.Casheir;
import by.it.malyshchik.jd02_02.model.Customer;
import by.it.malyshchik.jd02_02.model.Manager;
import by.it.malyshchik.jd02_02.model.Queue;
import by.it.malyshchik.jd02_02.service.CashierWorker;
import by.it.malyshchik.jd02_02.service.CustomerWorker;
import by.it.malyshchik.jd02_02.service.StoreException;

import java.util.ArrayList;
import java.util.List;

public class Store extends Thread {

    private final Manager manager;
    private final Queue queue = new Queue();

    public Store(Manager manager) {
        this.manager = manager;
    }

    @Override
    public synchronized void start() {
        System.out.println("Store opened");
        int number = 0;
        List<Thread> threads = new ArrayList<>();

        for (int numberCasier = 1; numberCasier <= 2; numberCasier++) {
            Casheir casheir = new Casheir(numberCasier);
            CashierWorker cashierWorker = new CashierWorker(manager, queue, casheir);
            Thread thread = new Thread(cashierWorker);
            threads.add(thread);
            thread.start();
        }


        while (manager.storeIsOpened()) {
            int count = RandomInt.random(0, 2);
            for (int i = 0; manager.storeIsOpened() && i < count; i++) {
                Customer customer = new Customer(++number);
                CustomerWorker customerThead = new CustomerWorker(customer, manager, queue);
                threads.add(customerThead);
                customerThead.start();
            }
            Timeout.sleep(1000);
        }

        //waiting
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new StoreException("Interrupted", e);
            }
        }

        System.out.println("Store closed");
    }
}
