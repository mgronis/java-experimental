package aspects;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class CarAspect {

    @Pointcut("call(exchangables.Car.new())")
    public void constructorCall() {}

    @Before("constructorCall()")
    public void beforeNewCar() {
        System.out.println("Somebody wants a new car");
    }

    @After("constructorCall()")
    public void afterNewCall() {
        System.out.println("Somebody got a new car");
    }
}
