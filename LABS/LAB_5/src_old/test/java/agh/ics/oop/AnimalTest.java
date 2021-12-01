package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {
  @Test
  public void creatingAnimalOnMap() {
    IWorldMap map = new RectangularMap(10, 5);
    Vector2d v1 = new Vector2d(2, 2);
    Vector2d v2 = new Vector2d(2, 1);
    Animal a1 = new Animal(map, v1);
    map.place(a1);
    assertTrue(map.isOccupied(v1));
    Animal a2 = new Animal(map, v2);
    Animal a3 = new Animal(map, v1);
    map.place(a2);
    a1.move(MoveDirection.FORWARD);
    assertFalse(map.isOccupied(v1));
    map.place(a3);
    assertTrue(map.isOccupied(v1));
    assertEquals(a2, map.objectAt(v2));
    assertEquals(a1, map.objectAt(v1.add(new Vector2d(0, 1))));
  }

  @Test
  public void animalToString() {
    IWorldMap map = new RectangularMap(10, 5);
    Vector2d v1 = new Vector2d(2, 2);
    Animal a1 = new Animal(map, v1);
    assertEquals(MapDirection.NORTH, a1.getOrientation());
    assertEquals(MapDirection.NORTH.toString(), a1.getOrientation().toString());
    a1.move(MoveDirection.FORWARD);
    assertEquals(MapDirection.NORTH, a1.getOrientation());
    assertEquals(MapDirection.NORTH.toString(), a1.getOrientation().toString());
    a1.move(MoveDirection.LEFT);
    assertEquals(MapDirection.WEST, a1.getOrientation());
    assertEquals(MapDirection.WEST.toString(), a1.getOrientation().toString());
    a1.move(MoveDirection.RIGHT);
    a1.move(MoveDirection.RIGHT);
    assertEquals(MapDirection.EAST, a1.getOrientation());
    assertEquals(MapDirection.EAST.toString(), a1.getOrientation().toString());
    a1.move(MoveDirection.BACKWARD);
    assertEquals(MapDirection.EAST, a1.getOrientation());
    assertEquals(MapDirection.EAST.toString(), a1.getOrientation().toString());
    a1.move(MoveDirection.RIGHT);
    assertEquals(MapDirection.SOUTH, a1.getOrientation());
    assertEquals(MapDirection.SOUTH.toString(), a1.getOrientation().toString());
  }
}