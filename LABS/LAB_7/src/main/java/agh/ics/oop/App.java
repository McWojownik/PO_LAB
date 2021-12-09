package agh.ics.oop;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import static java.lang.System.out;

public class App extends Application {
  private final AbstractWorldMap map;
//  private RectangularMap map;
//  private GrassField map;

  public App() {
//    this.map = new RectangularMap(10, 5);
//    b b r l f f
    this.map = new GrassField(10);
//    l r f f f f f f f f f f r r f f f f f f
  }

  public void init() {
    try {
      MoveDirection[] directions = new OptionsParser().parse(getParameters().getRaw().toArray(new String[0]));
//    Vector2d v1 = new Vector2d(2, 2);
//    Vector2d v2 = new Vector2d(2, 4);
//    Vector2d v3 = new Vector2d(2, 3);
//    Vector2d[] positions = {v1, v2, v3};
      Vector2d v1 = new Vector2d(2, 2);
      Vector2d v2 = new Vector2d(3, 4);
      Vector2d[] positions = {v1, v2};
      IEngine engine = new SimulationEngine(directions, this.map, positions);
      out.println(this.map);
      engine.run();
      out.println(this.map);
    } catch (IllegalArgumentException ex) {
      out.println(ex.getMessage());
    }
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    GridPane grid = new GridPane();
    GridVisualizer mapVisualizer = new GridVisualizer(this.map, grid);
    Vector2d leftBottom = this.map.mapEnds.getLeftBottom();
    Vector2d rightTop = this.map.mapEnds.getRightTop();
    mapVisualizer.draw(leftBottom, rightTop);
    grid.setGridLinesVisible(true);
    grid.setAlignment(Pos.CENTER);
    Scene scene = new Scene(grid, 30 * (rightTop.x - leftBottom.x + 2) + 30, 30 * (rightTop.y - leftBottom.y + 2) + 30);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}


//package agh.ics.oop;
//
//    import javafx.application.Application;
//    import javafx.geometry.Pos;
//    import javafx.scene.Scene;
//    import javafx.scene.layout.GridPane;
//    import javafx.stage.Stage;
//
//    import static java.lang.System.out;
//
//public class App extends Application {
//  @Override
//  public void start(Stage primaryStage) throws Exception {
//    MoveDirection[] directions = new OptionsParser().parse(getParameters().getRaw().toArray(new String[0]));
////    RectangularMap map = new RectangularMap(10, 5);
////    b b r l f f
//    GrassField map = new GrassField(10);
////    l r f f f f f f f f f f r r f f f f f f
////    Vector2d v1 = new Vector2d(2, 2);
////    Vector2d v2 = new Vector2d(2, 4);
////    Vector2d v3 = new Vector2d(2, 3);
////    Vector2d[] positions = {v1, v2, v3};
//    GridPane grid = new GridPane();
//    GridVisualizer mapVisualizer = new GridVisualizer(map, grid);
//    Vector2d leftBottom = map.mapEnds.getLeftBottom();
//    Vector2d rightTop = map.mapEnds.getRightTop();
//    mapVisualizer.draw(leftBottom, rightTop);
//    grid.setGridLinesVisible(true);
//    grid.setAlignment(Pos.CENTER);
//    Scene scene = new Scene(grid, 30 * (rightTop.x - leftBottom.x + 2) + 30, 30 * (rightTop.y - leftBottom.y + 2) + 30);
//    primaryStage.setScene(scene);
//    primaryStage.show();
//
//    Vector2d v1 = new Vector2d(2, 2);
//    Vector2d v2 = new Vector2d(3, 4);
//    Vector2d[] positions = {v1, v2};
//    IEngine engine = new SimulationEngine(directions, map, positions);
//    out.println(map);
//    engine.run();
//    out.println(map);
//  }
//}