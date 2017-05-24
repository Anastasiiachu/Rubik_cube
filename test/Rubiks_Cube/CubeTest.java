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
        actual.getSide(Sides.FORWARD).flip(Direction.CONTRACLOCKWISE);
        actual.getSide(Sides.FORWARD).flip(Direction.CLOCKWISE);
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
        actual.getSide(Sides.RIGHT).flipCubeAround();
        actual.getSide(Sides.RIGHT).flipCubeAround();
        actual.getSide(Sides.RIGHT).flipCubeAround();
        actual.getSide(Sides.RIGHT).flipCubeAround();
        assertEquals(expected, actual);
    }
    @Test
    public void testFlip() {
        actual.getSide(Sides.TOP).flip(Direction.CLOCKWISE);
        actual.getSide(Sides.BACKWARD).flip(Direction.CONTRACLOCKWISE);
        actual.getSide(Sides.BACKWARD).flip(Direction.CLOCKWISE);
        actual.getSide(Sides.TOP).flip(Direction.CONTRACLOCKWISE);
        assertEquals(expected, actual);
    }
    @Test
    public void testCube() {
        actual.getSide(Sides.RIGHT).flip(Direction.CONTRACLOCKWISE);
        actual.turnCubeForward();
        actual.turnCubeBackward();
        actual.getSide(Sides.RIGHT).flip(Direction.CLOCKWISE);
        assertEquals(expected, actual);
    }
    @Test
    public void testC() {
        expected.getSide(Sides.TOP).flip(Direction.CLOCKWISE);
        actual.getSide(Sides.TOP).flip(Direction.CONTRACLOCKWISE);
        actual.getSide(Sides.TOP).flip(Direction.CONTRACLOCKWISE);
        actual.getSide(Sides.TOP).flip(Direction.CONTRACLOCKWISE);
        assertEquals(expected, actual);
    }
    @Test
    public void testA() {
        expected.turnCubeBackward();
        expected.getSide(Sides.BOTTOM).flipMiddleLine(true);
        System.out.println(expected);
        actual.turnCubeForward();
        actual.turnCubeForward();
        actual.turnCubeForward();
        actual.getSide(Sides.BOTTOM).flipMiddleLine(true);
        System.out.println(actual);
        assertEquals(expected, actual);
    }
    @Test
    public void testB() {
        expected.getSide(Sides.BACKWARD).flipCubeAround();
        System.out.println(expected);
        actual.turnCubeVertical(Direction.CONTRACLOCKWISE);
        System.out.println(actual);
        assertEquals(expected, actual);
    }
}