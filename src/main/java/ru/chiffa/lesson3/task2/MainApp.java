package main.java.ru.chiffa.lesson3.task2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainApp {
    public static void main(String[] args) {
        Counter counter = new Counter();
        Lock lock = new ReentrantLock();
        Thread thread1 = new CounterThread(counter, lock);
        Thread thread2 = new CounterThread(counter, lock);
        Thread thread3 = new CounterThread(counter, lock);

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
