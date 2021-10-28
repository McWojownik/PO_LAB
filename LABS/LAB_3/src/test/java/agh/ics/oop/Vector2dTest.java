package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {
  @Test
  public void checkString() {
    Vector2d v1 = new Vector2d(1, 2);
    assertEquals(v1.toString(), "(1,2)");
  }

  @Test
  public void precedes() {
    Vector2d v1 = new Vector2d(1, 2);
    Vector2d v2 = new Vector2d(2, 2);
    assertTrue(v1.precedes(v2));
    assertFalse(v2.precedes(v1));
  }

  @Test
  public void follows() {
    Vector2d v1 = new Vector2d(1, 2);
    Vector2d v2 = new Vector2d(2, 2);
    assertFalse(v1.follows(v2));
    assertTrue(v2.follows(v1));
  }

  @Test
  public void upperRight() {
    Vector2d v1 = new Vector2d(0, 2);
    Vector2d v2 = new Vector2d(2, 0);
    Vector2d v3 = new Vector2d(2, 2);
    assertEquals(v1.upperRight(v2), v3);
    assertEquals(v2.upperRight(v1), v3);
  }

  @Test
  public void lowerLeft() {
    Vector2d v1 = new Vector2d(0, 2);
    Vector2d v2 = new Vector2d(2, 1);
    Vector2d v3 = new Vector2d(0, 1);
    assertEquals(v1.lowerLeft(v2), v3);
    assertEquals(v2.lowerLeft(v1), v3);
  }

  @Test
  public void add() {
    Vector2d v1 = new Vector2d(0, 2);
    Vector2d v2 = new Vector2d(2, 1);
    Vector2d v3 = new Vector2d(2, 3);
    assertEquals(v1.add(v2), v3);
    assertEquals(v2.add(v1), v3);
  }

  @Test
  public void subtract() {
    Vector2d v1 = new Vector2d(0, 2);
    Vector2d v2 = new Vector2d(2, 1);
    Vector2d v3 = new Vector2d(-2, 1);
    Vector2d v4 = new Vector2d(2, -1);
    assertEquals(v1.subtract(v2), v3);
    assertEquals(v2.subtract(v1), v4);
  }

  @Test
  public void equals() {
    Vector2d v1 = new Vector2d(1, 2);
    Vector2d v2 = new Vector2d(2, 2);
    Vector2d v3 = new Vector2d(1, 2);
    assertFalse(v1.equals(v2));
    assertFalse(v1.equals(1));
    assertTrue(v1.equals(v3));
  }

  @Test
  public void opposite() {
    Vector2d v1 = new Vector2d(7, -5);
    Vector2d v2 = new Vector2d(-7, 5);
    assertEquals(v1.opposite(), v2);
  }
}
