package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

// DO ZROBIENIA KIEDYS LAB_5_ZAD_12

public class GrassField extends AbstractWorldMap {
  protected List<Grass> grass = new ArrayList<>();
  private final MapBoundary mapEnds = new MapBoundary(this);
  private Vector2d leftBottom;
  private Vector2d rightTop;

  public GrassField(int n) {
    super();
    this.generateGrass(n);
  }

  public String toString() {
    return super.toString(this.leftBottom, this.rightTop);
  }

  private void generateGrass(int n) {
    for (int i = 0; i < n; i++) {
      boolean found = false;
      while (!found) {
        int x = (int) (Math.random() * Math.sqrt(10 * n));
        int y = (int) (Math.random() * Math.sqrt(10 * n));
        Vector2d v = new Vector2d(x, y);
        if (!grassOnField(v)) {
          found = true;
          this.mapEnds.addElement(v);
          this.grass.add(new Grass(v));
        }
      }
    }
    this.leftBottom = this.mapEnds.updateMapEndMIN();
    this.rightTop = this.mapEnds.updateMapEndMAX();
  }

  private boolean grassOnField(Vector2d position) {
    for (Grass grass : this.grass) {
      if (grass.isAt(position))
        return true;
    }
    return false;
  }

  @Override
  public boolean canMoveTo(Vector2d position) {
    return !super.isOccupied(position);
  }

  @Override
  public boolean place(Animal animal) {
    super.place(animal);
    this.mapEnds.addElement(animal.getPosition());
    this.leftBottom = this.mapEnds.updateMapEndMIN();
    this.rightTop = this.mapEnds.updateMapEndMAX();
    return true;
  }

  @Override
  public boolean isOccupied(Vector2d position) {
    if (super.isOccupied(position))
      return true;
    return grassOnField(position);
  }

  @Override
  public Object objectAt(Vector2d position) {
    Object animal = super.objectAt(position);
    if (animal != null)
      return animal;
    for (Grass grass : this.grass) {
      if (grass.isAt(position))
        return grass;
    }
    return null;
  }

  public boolean positionChanged(Vector2d oldPosition, Vector2d newPosition) {
    this.mapEnds.positionChanged(oldPosition, newPosition);
    this.leftBottom = this.mapEnds.updateMapEndMIN();
    this.rightTop = this.mapEnds.updateMapEndMAX();
    return super.positionChanged(oldPosition, newPosition);
  }
}