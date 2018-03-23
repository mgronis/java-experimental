package mapper;

import domain.AnOtherPerson;
import domain.Person;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class PersonMapper {

    private static final BoundMapperFacade<Person, AnOtherPerson> MAPPER_FACADE;

    static {
        final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(Person.class, AnOtherPerson.class)
                .byDefault()
                .register();

        MAPPER_FACADE = mapperFactory.getMapperFacade(Person.class, AnOtherPerson.class);
    }

    public static AnOtherPerson map(Person person){
        return MAPPER_FACADE.map(person);
    }

}
