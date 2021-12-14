package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;

public class GridVisualizer {
  private final IWorldMap map;
  private final GridPane grid;

  public GridVisualizer(IWorldMap map, GridPane grid) {
    this.map = map;
    this.grid = grid;
  }

  public void draw(Vector2d leftBottom, Vector2d rightTop) throws FileNotFoundException {
    this.drawHeader(leftBottom, rightTop);
    for (int i = rightTop.y; i >= leftBottom.y; i--) {
      this.grid.getRowConstraints().add(new RowConstraints(40));
      Label label = new Label(String.valueOf(i));
      this.grid.add(label, 0, rightTop.y - i + 1);
      GridPane.setHalignment(label, HPos.CENTER);
      for (int j = leftBottom.x; j <= rightTop.x + 1; j++) {
        if (j <= rightTop.x) {
          VBox box = new VBox();
          Vector2d vector = new Vector2d(j, i);
          IMapElement obj = (IMapElement) this.getObject(vector);
          if (this.map.isJungle(vector))
            box.setStyle("-fx-background-color: #004500;");
          else
            box.setStyle("-fx-background-color: #00FF00;");
          if (obj != null) {
            GuiElementBox el = new GuiElementBox(obj.getImageSource());
            box.getChildren().add(el.getImage());
          }
          box.setAlignment(Pos.CENTER);
          this.grid.add(box, j - leftBottom.x + 1, rightTop.y - i + 1);
        }
      }
    }
  }

  private void drawHeader(Vector2d lowerLeft, Vector2d upperRight) {
    Label label = new Label(" y\\x ");
    this.grid.getRowConstraints().add(new RowConstraints(40));
    this.grid.getColumnConstraints().add(new ColumnConstraints(40));
    this.grid.add(label, 0, 0);
    GridPane.setHalignment(label, HPos.CENTER);
    for (int j = lowerLeft.x; j < upperRight.x + 1; j++) {
      label = new Label(String.valueOf(j));
      this.grid.add(label, j - lowerLeft.x + 1, 0);
      this.grid.getColumnConstraints().add(new ColumnConstraints(40));
      GridPane.setHalignment(label, HPos.CENTER);
    }
  }

  private Object getObject(Vector2d currentPosition) {
    return this.map.objectAt(currentPosition);
  }
}
