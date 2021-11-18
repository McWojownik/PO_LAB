package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {
  @Test
  public void creatingAnimal() {
    IWorldMap map = new RectangularMap(10, 5);
    Vector2d v1 = new Vector2d(2, 2);
    Animal a1 = new Animal(map, v1);
    map.place(a1);
    assertEquals(1, map.getAnimals().size());
    Vector2d v2 = new Vector2d(2, 1);
    Animal a2 = new Animal(map, v2);
    Animal a3 = new Animal(map, v1);
    map.place(a2);
    assertEquals(2, map.getAnimals().size());
    map.place(a3);
    assertEquals(2, map.getAnimals().size());
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