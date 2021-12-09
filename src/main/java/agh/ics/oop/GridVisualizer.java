package agh.ics.oop;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

public class GridVisualizer {
  private final IWorldMap map;
  private final GridPane grid;

  public GridVisualizer(IWorldMap map, GridPane grid) {
    this.map = map;
    this.grid = grid;
  }

  public void draw(Vector2d leftBottom, Vector2d rightTop) {
    this.drawHeader(leftBottom, rightTop);
    for (int i = rightTop.y; i >= leftBottom.y; i--) {
      this.grid.getRowConstraints().add(new RowConstraints(30));
      Label label = new Label(String.valueOf(i));
      this.grid.add(label, 0, rightTop.y - i + 1);
      GridPane.setHalignment(label, HPos.CENTER);
      for (int j = leftBottom.x; j <= rightTop.x + 1; j++) {
        if (j <= rightTop.x) {
          Text object = new Text(drawObject(new Vector2d(j, i)));
          this.grid.add(object, j - leftBottom.x + 1, rightTop.y - i + 1);
          GridPane.setHalignment(object, HPos.CENTER);
        }
      }
    }
  }

  private void drawHeader(Vector2d lowerLeft, Vector2d upperRight) {
    Label label = new Label(" y\\x ");
    this.grid.getRowConstraints().add(new RowConstraints(30));
    this.grid.getColumnConstraints().add(new ColumnConstraints(30));
    this.grid.add(label, 0, 0);
    GridPane.setHalignment(label, HPos.CENTER);
    for (int j = lowerLeft.x; j < upperRight.x + 1; j++) {
      label = new Label(String.valueOf(j));
      this.grid.add(label, j - lowerLeft.x + 1, 0);
      this.grid.getColumnConstraints().add(new ColumnConstraints(30));
      GridPane.setHalignment(label, HPos.CENTER);
    }
  }

  private String drawObject(Vector2d currentPosition) {
    if (this.map.isOccupied(currentPosition))
      return this.map.objectAt(currentPosition).toString();
    return " ";
  }

}
