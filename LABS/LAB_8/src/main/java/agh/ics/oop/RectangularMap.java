package agh.ics.oop;

public class RectangularMap extends AbstractWorldMap {
  private final int width;
  private final int height;
  protected final Vector2d leftBottom;
  protected final Vector2d rightTop;

  public RectangularMap(int width, int height) {
    super();
    this.width = width;
    this.height = height;
    this.leftBottom=new Vector2d(0, 0);
    this.rightTop=new Vector2d(this.width - 1, this.height - 1);
    this.mapEnds.addElement(this.leftBottom);
    this.mapEnds.addElement(this.rightTop);
  }

  public String toString() {
    return super.toString(this.leftBottom, this.rightTop);
  }

  @Override
  public boolean canMoveTo(Vector2d position) {
//    return position.y < this.height && position.y >= 0 && position.x < this.width && position.x >= 0 && !isOccupied(position);
    if (position.y >= this.height || position.y < 0 || position.x >= this.width || position.x < 0)
      throw new IllegalArgumentException("You want to place animal beyond map");
    return !isOccupied(position);
  }

  @Override
  public boolean place(Animal animal) {
    try {
      Vector2d animalPosition = animal.getPosition();
      canMoveTo(animalPosition);
      super.place(animal);
      return true;
    } catch (IllegalArgumentException ex) {
      System.out.println(ex.getMessage());
      return false;
    }
  }

}