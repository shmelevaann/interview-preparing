package main.java.ru.chiffa.lesson1.task2;

public class LightWeightCar extends Car implements Moveable {
    @Override
    void open() {
        System.out.println("Car is open");
    }

    @Override
    public void move() {
        System.out.println("Car is moving");
    }

}
