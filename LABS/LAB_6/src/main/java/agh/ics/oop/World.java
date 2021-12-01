package agh.ics.oop;

import static java.lang.System.out;

public class World {
  public static void main(String[] args) {
    MoveDirection[] directions = new OptionsParser().parse(args);
//    IWorldMap map = new RectangularMap(10, 5);
    IWorldMap map = new GrassField(10);
    Vector2d v1 = new Vector2d(2, 2);
    Vector2d v2 = new Vector2d(3, 4);
    Vector2d[] positions = {v1, v2};
    IEngine engine = new SimulationEngine(directions, map, positions);
    out.println(map.toString());
    engine.run();
    out.println(map.toString());
  }
}