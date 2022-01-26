package main.java.ru.chiffa.lesson3.task1;

public class MainApp {
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new SequenceThread("1");
        Thread thread2 = new SequenceThread("2");
        Thread thread3 = new SequenceThread("3");
        Thread thread4 = new SequenceThread("4");
        Thread thread5 = new SequenceThread("5");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
    }
}
