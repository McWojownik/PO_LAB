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
import java.util.Arrays;

public class GridVisualizer {
  private final AbstractWorldMap map;
  private final GridPane grid;
  private final App app;
  private final SimulationEngine engine;
  private int boxSize = 5;
  private final GuiElementBox guiElementBox; // GRASS

  public GridVisualizer(AbstractWorldMap map, GridPane grid, App app, SimulationEngine engine, int boxSize) throws FileNotFoundException {
    this.map = map;
    this.grid = grid;
    this.app = app;
    this.engine = engine;
    this.boxSize = boxSize;
    this.guiElementBox = new GuiElementBox("grass.png", boxSize);
  }

  public void draw(Vector2d leftBottom, Vector2d rightTop) throws FileNotFoundException {
//    this.drawHeader(leftBottom, rightTop);
    for (int i = rightTop.y; i >= leftBottom.y; i--) {
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
//            IF YOU WANT TO CHANGE GRASS IMG JUST TO COLOR OR VICE VERSA CHANGE COMMENT STYLE IN THESE LINES: 48, 50, 67, 69, 70, 72
//            Circle circle = new Circle((int) (this.boxSize / 2), (int) (this.boxSize / 2), (int) (this.boxSize / 2));
            if (obj instanceof Animal) {
              Circle circle = new Circle((int) (this.boxSize / 2), (int) (this.boxSize / 2), (int) (this.boxSize / 2));
              Animal strongest = this.map.getStrongestAnimalOnField(vector);
              String strColor = "#2235e3";
              if (!this.map.getAnimalsHightlighted()) {
                String color = strongest.getAnimalColor();
                strColor = "#"+color;
              } else {
                if (!this.map.checkIfHighlightedOnField(vector)) {
                  String color = strongest.getAnimalColor();
                  strColor = "#"+color;
                }
              }
              circle.setFill(Color.web(strColor));
              box.setOnMouseClicked((e) -> {
                if (!this.engine.getIsRunning())
                  this.app.changeObservedAnimal(strongest, this.map);
              });
              box.getChildren().add(circle);
            } else {
              box.getChildren().add(this.guiElementBox.getImage());
//              circle.setFill(Color.web("#6dad51"));
            }
//            box.getChildren().add(circle);
          }
          this.grid.add(box, j - leftBottom.x + 1, rightTop.y - i + 1);
        }
      }
    }
  }

  private void drawHeader(Vector2d lowerLeft, Vector2d upperRight) {
    Label label = new Label(" y\\x ");
    label.setPrefWidth(40);
    label.setPrefHeight(40);
    this.grid.add(label, 0, 0);
    GridPane.setHalignment(label, HPos.CENTER);
    for (int j = lowerLeft.x; j < upperRight.x + 1; j++) {
      label = new Label(String.valueOf(j));
      label.setPrefWidth(this.boxSize);
      label.setPrefHeight(this.boxSize);
      this.grid.add(label, j - lowerLeft.x + 1, 0);
      GridPane.setHalignment(label, HPos.CENTER);
    }
    for (int i = upperRight.y; i >= lowerLeft.y; i--) {
      label = new Label(String.valueOf(i));
      this.grid.add(label, 0, upperRight.y - i + 1);
      GridPane.setHalignment(label, HPos.CENTER);
    }
  }

  private Object getObject(Vector2d currentPosition) {
    return this.map.objectAt(currentPosition);
  }
}
