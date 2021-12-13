package agh.ics.oop;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimulationEngineTest {
  @Test
  public void putAnimalsAndRun() {
//    String[] arr = {"f", "b", "r", "l", "f", "f", "SS", "AVAV", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
    String[] arr = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
    System.out.println(arr);
    MoveDirection[] directions = new OptionsParser().parse(arr);
    IWorldMap map = new RectangularMap(10, 5);
    Vector2d v1 = new Vector2d(2, 2);
    Vector2d v2 = new Vector2d(3, 4);
    Vector2d[] positions = {v1, v2, v2};
    SimulationEngine engine = new SimulationEngine(map, positions);
    engine.setMoves(arr);
    List<Animal> animals = engine.getAnimals();
    assertEquals(2, animals.size());
    engine.run();
    assertEquals(new Vector2d(2, 0), animals.get(0).getPosition());
    assertEquals(new Vector2d(3, 4), animals.get(1).getPosition());
    assertEquals(MapDirection.SOUTH, animals.get(0).getOrientation());
    assertEquals(MapDirection.NORTH, animals.get(1).getOrientation());
  }
}
