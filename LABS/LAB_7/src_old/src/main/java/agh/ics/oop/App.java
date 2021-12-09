package agh.ics.oop;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import static java.lang.Integer.parseInt;
import static java.lang.System.out;

public class App extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    MoveDirection[] directions = new OptionsParser().parse(getParameters().getRaw().toArray(new String[0]));
//    IWorldMap map = new RectangularMap(10, 5);
//    b b r l f f
    IWorldMap map = new GrassField(10);
//    l r f f f f f f f f f f r r f f f f f f
//    Vector2d v1 = new Vector2d(2, 2);
//    Vector2d v2 = new Vector2d(2, 4);
//    Vector2d v3 = new Vector2d(2, 3);
//    Vector2d[] positions = {v1, v2, v3};
    showWindow(primaryStage, map);
    Vector2d v1 = new Vector2d(2, 2);
    Vector2d v2 = new Vector2d(3, 4);
    Vector2d[] positions = {v1, v2};
    IEngine engine = new SimulationEngine(directions, map, positions);
    out.println(map);
    engine.run();
    out.println(map);
  }

  public void showWindow(Stage primaryStage, IWorldMap map) {
    GridPane gridPane = new GridPane();
    gridPane.setGridLinesVisible(true);
    String strMap = map.toString();
//    int rows = this.getRows(strMap);
//    int columns = this.getColumns(strMap);
    String[] lines = getRows(strMap);
    out.println(lines.length + " " + lines[0].length());
    gridPane.getRowConstraints().add(new RowConstraints(20));
    gridPane.getColumnConstraints().add(new ColumnConstraints(20));
    Label label = new Label("y/x");
    GridPane.setHalignment(label, HPos.CENTER);
    gridPane.add(label, 0, 0, 1, 1);
    int numOfColumns = 0;
    for (int i = 2; i < lines.length - 1; i++) {
      gridPane.getRowConstraints().add(new RowConstraints(20));
      int used = 0;
      label = new Label(Integer.toString(lines.length - i - 2));
      GridPane.setHalignment(label, HPos.CENTER);
      gridPane.add(label, used++, i - 1, 1, 1);
      for (int j = 5; j < lines[0].length(); j++) {
        String letter = Character.toString(lines[i].charAt(j));
        if (!letter.equals("|")) {
          if (i == 2)
            gridPane.getColumnConstraints().add(new ColumnConstraints(20));
          label = new Label(letter);
          GridPane.setHalignment(label, HPos.CENTER);
          gridPane.add(label, used++, i - 1, 1, 1);
        }
      }
      numOfColumns = used;
    }
    String letter = Character.toString(lines[0].charAt(6));
    int number = parseInt(letter);
    if (Character.toString(lines[0].charAt(5)).equals("-"))
      number *= (-1);
    for (int i = 0; i < numOfColumns - 1; i++) {
      label = new Label(Integer.toString(number++));
      GridPane.setHalignment(label, HPos.CENTER);
      gridPane.add(label, i + 1, 0, 1, 1);
    }
    Scene scene = new Scene(gridPane, 20 * numOfColumns, 20 * (lines.length - 2));
    primaryStage.setTitle("WORLD");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public String[] getRows(String str) {
    return str.split("\r\n|\r|\n");
  }

//  public int getColumns(String str) {
//    String[] lines = str.split("\r\n|\r|\n");
//    return lines[0].length();
//  }
}
