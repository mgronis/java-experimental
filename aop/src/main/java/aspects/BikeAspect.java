package aspects;

import exchangables.Bike;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class BikeAspect {

    @Around(value = "call(exchangables.Bike exchangables.BikeFactory.createBike(int)) && args(gears)", argNames = "pjp,gears")
    public Object createBike(ProceedingJoinPoint pjp, int gears) {
        return new Bike(gears * 2);
    }

}
