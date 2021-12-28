package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapDirectionTest {
  @Test
  public void next() {
    assertEquals(MapDirection.NORTH.next(), MapDirection.NORTH_EAST);
    assertEquals(MapDirection.SOUTH_EAST.next(), MapDirection.SOUTH);
    assertEquals(MapDirection.WEST.next(), MapDirection.NORTH_WEST);
  }

  @Test
  public void previous() {
    assertEquals(MapDirection.NORTH.previous(), MapDirection.NORTH_WEST);
    assertEquals(MapDirection.SOUTH_EAST.previous(), MapDirection.EAST);
    assertEquals(MapDirection.WEST.previous(), MapDirection.SOUTH_WEST);
  }
}
