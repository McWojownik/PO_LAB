package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.FileNotFoundException;

public class GridVisualizer {
  private final IWorldMap map;
  private final GridPane grid;
  private final int boxSize = 30;

  public GridVisualizer(IWorldMap map, GridPane grid) {
    this.map = map;
    this.grid = grid;
  }

  public void draw(Vector2d leftBottom, Vector2d rightTop) throws FileNotFoundException {
    this.drawHeader(leftBottom, rightTop);
    for (int i = rightTop.y; i >= leftBottom.y; i--) {
//      this.grid.getRowConstraints().add(new RowConstraints(40));
      Label label = new Label(String.valueOf(i));
      this.grid.add(label, 0, rightTop.y - i + 1);
      GridPane.setHalignment(label, HPos.CENTER);
      for (int j = leftBottom.x; j <= rightTop.x + 1; j++) {
        if (j <= rightTop.x) {
          VBox box = new VBox();
          box.setPrefWidth(this.boxSize);
          box.setPrefHeight(this.boxSize);
          Vector2d vector = new Vector2d(j, i);
          String c = this.map.getGrassColorOnField(vector);
          box.setStyle("-fx-background-color: #" + c + ";");
          IMapElement obj = (IMapElement) this.getObject(vector);
          if (obj != null) {
            Circle circle = new Circle((int) (this.boxSize/2), (int) (this.boxSize/2), (int) (this.boxSize/2));
            if (obj instanceof Animal) {
              String color = this.map.getStrongestAnimalColorOnField(vector);
              circle.setFill(Color.web("#" + color));
//              circle.setStyle("-fx-background-color: #" + color + ";");
            } else {
//              String color = this.map.getGrassColorOnField(vector);
//              box.setStyle("-fx-background-color: #" + color + ";");
//              GuiElementBox el = new GuiElementBox(obj.getImageSource());
//              box.getChildren().add(el.getImage());
              circle.setFill(Color.web("#6dad51"));
//              circle.setStyle("-fx-background-color: #6dad51;");
            }
//            GuiElementBox el = new GuiElementBox(obj.getImageSource());
//            box.getChildren().add(el.getImage());
            box.getChildren().add(circle);
          }
//          box.setAlignment(Pos.CENTER);
          this.grid.add(box, j - leftBottom.x + 1, rightTop.y - i + 1);
        }
      }
    }
  }

  private void drawHeader(Vector2d lowerLeft, Vector2d upperRight) {
    Label label = new Label(" y\\x ");
//    this.grid.getRowConstraints().add(new RowConstraints(40));
//    this.grid.getColumnConstraints().add(new ColumnConstraints(40));
    label.setPrefWidth(40);
    label.setPrefHeight(40);
    this.grid.add(label, 0, 0);
    GridPane.setHalignment(label, HPos.CENTER);
    for (int j = lowerLeft.x; j < upperRight.x + 1; j++) {
      label = new Label(String.valueOf(j));
      label.setPrefWidth(this.boxSize);
      label.setPrefHeight(this.boxSize);
      this.grid.add(label, j - lowerLeft.x + 1, 0);
//      this.grid.getColumnConstraints().add(new ColumnConstraints(40));
      GridPane.setHalignment(label, HPos.CENTER);
    }
  }

  private Object getObject(Vector2d currentPosition) {
    return this.map.objectAt(currentPosition);
  }
}
