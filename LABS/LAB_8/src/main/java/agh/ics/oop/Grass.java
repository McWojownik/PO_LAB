package agh.ics.oop;

public class Grass implements IMapElement {
  private final Vector2d position;

  public Grass(Vector2d initialPosition) {
    this.position = initialPosition;
  }

  @Override
  public String toString() {
    return "*";
  }

  @Override
  public Vector2d getPosition() {
    return this.position;
  }

  @Override
  public boolean isAt(Vector2d position) {
    return this.position.equals(position);
  }

  @Override
  public String getImageSource() {
    return "grass.png";
  }
}
