package agh.ics.oop;

public class RectangularMap extends AbstractWorldMap {
  private final int width;
  private final int height;

  public RectangularMap(int width, int height) {
    super();
    this.width = width;
    this.height = height;
  }

  public String toString() {
    return super.toString(new Vector2d(0, 0), new Vector2d(this.width - 1, this.height - 1));
  }

  @Override
  public boolean canMoveTo(Vector2d position) {
    return position.y < this.height && position.y >= 0 && position.x < this.width && position.x >= 0;
  }

  @Override
  public boolean place(Animal animal) {
    Vector2d animalPosition = animal.getPosition();
    if (!canMoveTo(animalPosition))
      return false;
    return super.place(animal);
  }

  @Override
  public boolean isOccupied(Vector2d position) {
    if (!canMoveTo(position))
      return true;
    return super.isOccupiedABS(position);
  }

  @Override
  public Object objectAt(Vector2d position) {
    return super.objectAt(position);
  }
}
