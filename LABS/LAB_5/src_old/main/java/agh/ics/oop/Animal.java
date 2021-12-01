package agh.ics.oop;

public class Animal implements IMapElement {
  private MapDirection orientation = MapDirection.NORTH;
  private final IWorldMap map;
  private Vector2d position;

  public Animal(IWorldMap map, Vector2d initialPosition) {
    this.map = map;
    this.position = initialPosition;
  }

  @Override
  public String toString() {
    return this.orientation.toString();
  }

  @Override
  public Vector2d getPosition() {
    return this.position;
  }

  @Override
  public boolean isAt(Vector2d position) {
    return this.position.equals(position);
  }

  protected MapDirection getOrientation() {
    return this.orientation;
  }

  protected void move(MoveDirection direction) {
    switch (direction) {
      case FORWARD -> {
        Vector2d position = this.position.add(this.orientation.toUnitVector());
        if (this.map.canMoveTo(position))
          this.position = position;
      }
      case BACKWARD -> {
        Vector2d position = this.position.subtract(this.orientation.toUnitVector());
        if (this.map.canMoveTo(position))
          this.position = position;
      }
      case RIGHT -> this.orientation = this.orientation.next();
      case LEFT -> this.orientation = this.orientation.previous();
    }
  }
}
