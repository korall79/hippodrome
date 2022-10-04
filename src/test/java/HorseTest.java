import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

public class HorseTest {

    @Test
    public void nullNameException() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 1));
        assertEquals("Name cannot be null.", e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t\t", "\n\n\n\n\n\n"})
    public void blankNameException(String name) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1, 1));
        assertEquals("Name cannot be blank.", e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1.0})
    public void blankSpeedException(Double speed) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse("null", speed, 1));
        assertEquals("Speed cannot be negative.", e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1.0})
    public void blankDistanceException(Double distance) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse("null", 1, distance));
        assertEquals("Distance cannot be negative.", e.getMessage());
    }

    @Test
    public void getName() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("qwerty", 1, 1);

//        Field name = Horse.class.getDeclaredField("name");
//        name.setAccessible(true);
//        String nameValue = (String) name.get(horse);
        assertEquals("qwerty", horse.getName());
    }

    @Test
    public void getSpeed() {
        double expectedSpeed = 443;
        Horse horse = new Horse("qwerty", expectedSpeed, 1);
        assertEquals(expectedSpeed, horse.getSpeed());
    }

    @Test
    public void getDistance() {
        double expectedDistance = 225;
        Horse horse = new Horse("qwerty", 1, expectedDistance);
        assertEquals(expectedDistance, horse.getDistance());
    }

    @Test
    public void zeroDistanceByDefault() {
        Horse horse = new Horse("qwerty", 1);
        assertEquals(0, horse.getDistance());
    }

    @Test
    public void moveUsesGetRandom(){
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)){
            new Horse("qwerty", 25, 87).move();
            mockedStatic.verify(()->Horse.getRandomDouble(0.2,0.9));

        }
    }
    @ParameterizedTest
    @ValueSource(doubles = {0.1,0.2,0.5,0.9,1.0,999.999,0.0})
    void move(double random){
        try(MockedStatic<Horse> mockedStatic =mockStatic(Horse.class) ){
            Horse horse =new Horse("qwerty", 25, 87);
            mockedStatic.when(()->Horse.getRandomDouble(0.2,0.9)).thenReturn(random);

            horse.move();

            assertEquals(87+25*random,horse.getDistance());
        }

    }

}
