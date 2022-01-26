package main.java.ru.chiffa.lesson3.task2;

import java.util.concurrent.locks.Lock;

public class CounterThread extends Thread {
    private final Counter counter;
    private final Lock lock;

    public CounterThread(Counter counter, Lock lock) {
        this.counter = counter;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                lock.lock();
                counter.countUp();
            } finally {
                lock.unlock();
            }
        }
    }
}
