package main.java.ru.chiffa.lesson3.task1;

//плюсы - потоки будут выводиться гарантированно последовательно, лаконичное применение
//минусы - при каждом нотифай все потоки будятся и делают проверку
public class SequenceThread extends Thread {
    private static final Monitor COUNTER = new Monitor();
    private static final Monitor CURRENT = new Monitor();
    private int count;
    private final String text;


    public SequenceThread(String text) {
        synchronized (COUNTER) {
            count = COUNTER.getCount();
            COUNTER.countUp();
        }
        this.text = text;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            synchronized (CURRENT) {
                while (CURRENT.getCount() != count) {
                    try {
                        CURRENT.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(text);
                CURRENT.setCount(CURRENT.getCount() == COUNTER.getCount() - 1 ? 0 : CURRENT.getCount() + 1);
                CURRENT.notifyAll();
            }
        }
    }
}
