package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

import static java.lang.System.out;

public class App extends Application {
  private final AbstractWorldMap map;
  private SimulationEngine engine;
  private GridVisualizer mapVisualizer;
  private GridPane grid;
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
//      MoveDirection[] directions = new OptionsParser().parse(getParameters().getRaw().toArray(new String[0]));
      Vector2d v1 = new Vector2d(2, 2);
      Vector2d v2 = new Vector2d(3, 4);
      Vector2d[] positions = {v1, v2};
      this.engine = new SimulationEngine(this.map, positions);
      this.engine.addObserver(this);
      this.engine.setDayTimeChange(600);
    } catch (IllegalArgumentException ex) {
      out.println(ex.getMessage());
    }
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    this.grid = new GridPane();
    Button start = new Button("Start");
    TextField arguments = new TextField();
    HBox hbox = new HBox(start, arguments);
    this.grid.setGridLinesVisible(true);
    VBox vbox = new VBox(hbox, this.grid);
    this.mapVisualizer = new GridVisualizer(this.map, this.grid);
    Vector2d leftBottom = this.map.mapEnds.getLeftBottom();
    Vector2d rightTop = this.map.mapEnds.getRightTop();
    this.mapVisualizer.draw(leftBottom, rightTop);
    Scene scene = new Scene(vbox, 600, 600);
    primaryStage.setScene(scene);
    primaryStage.show();
    start.setOnAction((e) -> {
      engine.setMoves(arguments.getText().split(" "));
      arguments.clear();
      Thread thread = new Thread(this.engine);
      thread.start();
    });
  }

  public void positionChanged() {
    Platform.runLater(() -> {
      this.grid.getChildren().clear();
      Vector2d leftBottom = this.map.mapEnds.getLeftBottom();
      Vector2d rightTop = this.map.mapEnds.getRightTop();
      try {
        this.mapVisualizer.draw(leftBottom, rightTop);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    });
  }
}