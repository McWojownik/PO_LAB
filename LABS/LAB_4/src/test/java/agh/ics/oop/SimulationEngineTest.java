package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimulationEngineTest {
  @Test
  public void putAnimals() {
    String[] arr = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
    MoveDirection[] directions = new OptionsParser().parse(arr);
    IWorldMap map = new RectangularMap(10, 5);
    Vector2d v1 = new Vector2d(2, 2);
    Vector2d v2 = new Vector2d(3, 4);
    Vector2d[] positions = {v1, v2, v2};
    IEngine engine = new SimulationEngine(directions, map, positions);
    assertEquals(2, map.getAnimals().size());
    engine.run();
    assertEquals(new Vector2d(2,0), map.getAnimals().get(0).getPosition());
    assertEquals(new Vector2d(3,4), map.getAnimals().get(1).getPosition());
    assertEquals(MapDirection.SOUTH, map.getAnimals().get(0).getOrientation());
    assertEquals(MapDirection.NORTH, map.getAnimals().get(1).getOrientation());
  }
}
