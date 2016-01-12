package exchangables;

public class BikeFactory {

    public Bike createBike(int gears) {
        return new Bike(gears);
    }
}
