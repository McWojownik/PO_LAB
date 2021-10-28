package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {
  @Test
  public void onlyMoves() {
    Animal a1 = new Animal();
    Vector2d v1 = new Vector2d(2, 2);
    assertTrue(a1.isAt(v1));
    assertEquals(MapDirection.NORTH, a1.getOrientation());
    a1.move(MoveDirection.RIGHT);
    assertEquals(MapDirection.EAST, a1.getOrientation());
    a1.move(MoveDirection.RIGHT);
    a1.move(MoveDirection.FORWARD);
    a1.move(MoveDirection.FORWARD);
    a1.move(MoveDirection.FORWARD);
    a1.move(MoveDirection.FORWARD);
    assertEquals(MapDirection.SOUTH, a1.getOrientation());
    assertTrue(a1.isAt(v1.subtract(new Vector2d(0, 2))));
  }

  @Test
  public void parserAndMoves() {
    String[] arr = {"f", "r", "VS", "f", "f", "l", "f", "b", "d", "b", "b", "b", "f", "l", "b"};
    OptionsParser parser = new OptionsParser();
    MoveDirection[] instructions = parser.parse(arr);
    MoveDirection[] instructions2 = {MoveDirection.FORWARD, MoveDirection.RIGHT, MoveDirection.FORWARD, MoveDirection.FORWARD,
        MoveDirection.LEFT, MoveDirection.FORWARD, MoveDirection.BACKWARD, MoveDirection.BACKWARD, MoveDirection.BACKWARD,
        MoveDirection.BACKWARD, MoveDirection.FORWARD, MoveDirection.LEFT, MoveDirection.BACKWARD};
    assertArrayEquals(instructions2, instructions);
    Animal a1 = new Animal();
    for (MoveDirection instruction : instructions) {
      a1.move(instruction);
    }
    assertEquals(MapDirection.WEST, a1.getOrientation());
    assertEquals(new Vector2d(4, 1), a1.getPosition());
  }

  @Test
  public void checkExit() {
    Animal a1 = new Animal();
    String[] arr = {"l", "f", "f", "f", "f", "f", "f"};
    OptionsParser parser = new OptionsParser();
    MoveDirection[] instructions = parser.parse(arr);
    for (MoveDirection instruction : instructions) {
      a1.move(instruction);
    }
    assertEquals(MapDirection.WEST, a1.getOrientation());
    assertTrue(a1.isAt(new Vector2d(0, 2)));
    arr = new String[]{"l", "f", "f", "f", "f", "f", "f", "r", "f", "f", "f", "f"};
    instructions = parser.parse(arr);
    Animal a2 = new Animal();
    for (MoveDirection instruction : instructions) {
      a2.move(instruction);
    }
    assertEquals(MapDirection.NORTH, a2.getOrientation());
    assertTrue(a2.isAt(new Vector2d(0, 4)));
    arr = new String[]{"b", "b", "b", "b", "b", "b"};
    instructions = parser.parse(arr);
    for (MoveDirection instruction : instructions) {
      a2.move(instruction);
    }
    assertEquals(MapDirection.NORTH, a2.getOrientation());
    assertTrue(a2.isAt(new Vector2d(0, 0)));
    arr = new String[]{"f", "f", "f", "f", "f", "r", "f", "f", "f", "f", "l", "f", "f", "f", "f"};
    instructions = parser.parse(arr);
    Animal a3 = new Animal();
    for (MoveDirection instruction : instructions) {
      a3.move(instruction);
    }
    assertEquals(MapDirection.NORTH, a2.getOrientation());
    assertTrue(a3.isAt(new Vector2d(4, 4)));
  }
}
