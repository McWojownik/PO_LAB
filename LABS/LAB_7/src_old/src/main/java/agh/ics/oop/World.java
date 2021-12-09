package agh.ics.oop;

import javafx.application.Application;

import static java.lang.System.out;

public class World {
  public static void main(String[] args) {
    try {
      Application.launch(App.class, args);
//      MoveDirection[] directions = new OptionsParser().parse(args);
////      IWorldMap map = new RectangularMap(10, 5);
//      IWorldMap map = new GrassField(10);
////      Vector2d v1 = new Vector2d(2, 2);
////      Vector2d v2 = new Vector2d(2, 4);
////      Vector2d v3 = new Vector2d(2, 3);
////      Vector2d[] positions = {v1, v2, v3};
//      Vector2d v1 = new Vector2d(2, 2);
//      Vector2d v2 = new Vector2d(3, 4);
//      Vector2d[] positions = {v1, v2};
//      IEngine engine = new SimulationEngine(directions, map, positions);
//      out.println(map.toString());
//      engine.run();
//      out.println(map.toString());
    } catch (IllegalArgumentException ex) {
      out.println(ex.getMessage());
    }
  }
}