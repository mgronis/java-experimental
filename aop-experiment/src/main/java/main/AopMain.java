package main;

import exchangables.Bike;
import exchangables.BikeFactory;
import exchangables.Car;

/**
 * Add -javaagent:build/javaagent/aspectjweaver-1.8.7.jar and $MODULE_DIR$ to idea run target
 */
public class AopMain {

    public static void main(String[] args) {
        System.out.println("Lets start experimenting");

        new Car().describe();

        System.out.println("A bike has " + new BikeFactory().createBike(3).getGears() + " gears");

    }

}
