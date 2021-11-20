package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class GrassField extends AbstractWorldMap {
  protected List<Grass> grasses;
  private Vector2d leftBottom = new Vector2d(0, 0);
  private Vector2d rightTop = new Vector2d(0, 0);

  public GrassField(int n) {
    super();
    this.grasses = new ArrayList<>();
    this.generateGrass(n);
  }

  public String toString() {
    return super.toString(leftBottom, rightTop);
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
          if (this.grasses.size() != 0)
            this.updateMapEnd(v);
          else {
            this.leftBottom = v;
            this.rightTop = v;
          }
          this.grasses.add(new Grass(v));
        }
      }
    }
  }

  public void updateMapEnd(Vector2d v) {
    int miniX = this.leftBottom.x;
    int miniY = this.leftBottom.y;
    int maxiX = this.rightTop.x;
    int maxiY = this.rightTop.y;
    miniX = Math.min(miniX, v.x);
    miniY = Math.min(miniY, v.y);
    maxiX = Math.max(maxiX, v.x);
    maxiY = Math.max(maxiY, v.y);
    this.leftBottom = new Vector2d(miniX, miniY);
    this.rightTop = new Vector2d(maxiX, maxiY);
  }

  private boolean grassOnField(Vector2d position) {
    for (Grass grass : this.grasses) {
      if (grass.isAt(position))
        return true;
    }
    return false;
  }

  @Override
  public boolean canMoveTo(Vector2d position) {
    if (super.isOccupiedABS(position))
      return false;
    this.updateMapEnd(position);
    return true;
  }

  @Override
  public boolean place(Animal animal) {
    if (super.place(animal)) {
      this.updateMapEnd(animal.getPosition());
      return true;
    }
    return false;
  }

  @Override
  public boolean isOccupied(Vector2d position) {
    if (super.isOccupiedABS(position))
      return true;
    return grassOnField(position);
  }

  @Override
  public Object objectAt(Vector2d position) {
    Object animal = super.objectAt(position);
    if (animal != null)
      return animal;
    for (Grass grass : this.grasses) {
      if (grass.isAt(position))
        return grass;
    }
    return null;
  }
}
