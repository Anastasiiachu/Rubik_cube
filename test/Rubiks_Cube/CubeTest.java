package Rubiks_Cube;

import com.sun.org.apache.xpath.internal.operations.Equals;
import org.junit.Test;

import javax.print.attribute.standard.*;

import static org.junit.Assert.*;

public class CubeTest {
    Cube expected = new Cube();
    Cube actual = new Cube();
    @Test
    public void testTurnCubeVertical() {
        actual.turnCubeVertical(Direction.CLOCKWISE);
        actual.turnCubeVertical(Direction.CONTRACLOCKWISE);
        assertEquals(expected, actual);
    }
    @Test
    public void testFlipMiddleLine() {
        actual.sides[1].flip(Direction.CONTRACLOCKWISE);
        actual.sides[1].flip(Direction.CLOCKWISE);
        assertEquals(expected, actual);
    }
    @Test
    public void testTurnCubeHorizontal() {
        actual.turnCubeHorizontal(Direction.CLOCKWISE);
        actual.turnCubeHorizontal(Direction.CLOCKWISE);
        actual.turnCubeHorizontal(Direction.CLOCKWISE);
        actual.turnCubeHorizontal(Direction.CLOCKWISE);
        assertEquals(expected, actual);
    }
    @Test
    public void testTurnCubeBackwardForward() {
        actual.turnCubeForward();
        actual.turnCubeBackward();
        assertEquals(expected, actual);
    }
    @Test
    public void testFlipCubeAround() {
        actual.sides[2].flipCubeAround();
        actual.sides[2].flipCubeAround();
        actual.sides[2].flipCubeAround();
        System.out.println(actual);
        actual.sides[2].flipCubeAround();
        System.out.println(actual);
        assertEquals(expected, actual);
    }
    @Test
    public void testFlip() {
        actual.sides[0].flip(Direction.CLOCKWISE);
        actual.sides[3].flip(Direction.CONTRACLOCKWISE);
        actual.sides[3].flip(Direction.CLOCKWISE);
        actual.sides[0].flip(Direction.CONTRACLOCKWISE);
        assertEquals(expected, actual);
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void testException() {
        actual.sides[10].flip(Direction.CONTRACLOCKWISE);
    }
}