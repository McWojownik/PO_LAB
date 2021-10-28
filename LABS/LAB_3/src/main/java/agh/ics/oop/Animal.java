package agh.ics.oop;

public class Animal {
  private MapDirection orientation = MapDirection.NORTH;
  private Vector2d position = new Vector2d(2, 2);
  private final Vector2d start = new Vector2d(0, 0);
  private final Vector2d end = new Vector2d(4, 4);

  public String toString() {
    return this.orientation.toString() + " " + this.position.toString();
  }

  protected MapDirection getOrientation(){
    return this.orientation;
  }

  protected Vector2d getPosition(){
    return this.position;
  }

  protected boolean isAt(Vector2d position) {
    return this.position.equals(position);
  }

  protected void move(MoveDirection direction) {
    switch (direction) {
      case FORWARD -> {
        Vector2d position = this.position.add(this.orientation.toUnitVector());
        if (position.follows(this.start) && position.precedes(this.end))
          this.position = position;
      }
      case BACKWARD -> {
        Vector2d position = this.position.subtract(this.orientation.toUnitVector());
        if (position.follows(this.start) && position.precedes(this.end))
          this.position = position;
      }
      case RIGHT -> this.orientation = this.orientation.next();
      case LEFT -> this.orientation = this.orientation.previous();
    }
  }
}
