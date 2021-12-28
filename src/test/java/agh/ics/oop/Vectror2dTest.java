package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vectror2dTest {
  @Test
  public void precedes() {
    Vector2d v1 = new Vector2d(1, 2);
    Vector2d v2 = new Vector2d(3, 5);
    assertTrue(v1.precedes(v2));
    assertFalse(v2.precedes(v1));
  }

  @Test
  public void follows() {
    Vector2d v1 = new Vector2d(1, 2);
    Vector2d v2 = new Vector2d(3, 5);
    assertTrue(v2.follows(v1));
    assertFalse(v1.follows(v2));
  }

  @Test
  public void equals() {
    Vector2d v1 = new Vector2d(1, 2);
    Vector2d v2 = new Vector2d(3, 5);
    Vector2d v3 = new Vector2d(1, 2);
    AbstractWorldMap map = new BorderMap(11, 11, 40, 1, 20, 3, 3, false);
    Genes genes = new Genes();
    Animal a1 = new Animal(map, v2, 40, genes);
    assertNotEquals(v1, v2);
    assertEquals(v1, v3);
    assertNotEquals(v1, a1);
  }
}
