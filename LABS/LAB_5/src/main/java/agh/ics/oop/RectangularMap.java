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
    return position.y < this.height && position.y >= 0 && position.x < this.width && position.x >= 0 && !isOccupied(position);
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
    return super.isOccupiedABS(position);
  }

  @Override
  public Object objectAt(Vector2d position) {
    return super.objectAt(position);
  }
}

// WCZESNIEJSZA MAPA NIE DZIEDZICZACA Z AbstractWorldMap
//package agh.ics.oop;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class RectangularMap implements IWorldMap {
//  private final int width;
//  private final int height;
//  private final List<Animal> animals;
//  private final MapVisualizer visualizer = new MapVisualizer(this);
//
//  public RectangularMap(int width, int height) {
//    this.width = width;
//    this.height = height;
//    this.animals = new ArrayList<>();
//  }
//
//  public String toString() {
//    return visualizer.draw(new Vector2d(0, 0), new Vector2d(this.width - 1, this.height - 1));
//  }
//
//  @Override
//  public boolean canMoveTo(Vector2d position) {
//    return position.y < this.height && position.y >= 0 && position.x < this.width && position.x >= 0 && !isOccupied(position);
//  }
//
//  @Override
//  public boolean place(Animal animal) {
//    if (!isOccupied(animal.getPosition())) {
//      this.animals.add(animal);
//      return true;
//    }
//    return false;
//  }
//
//  @Override
//  public boolean isOccupied(Vector2d position) {
//    if (position.y >= this.height || position.y < 0 || position.x >= this.width || position.x < 0) {
//      return true;
//    }
//    for (Animal animal : this.animals) {
//      if (animal.isAt(position))
//        return true;
//    }
//    return false;
//  }
//
//  @Override
//  public Object objectAt(Vector2d position) {
//    for (Animal animal : this.animals) {
//      if (animal.isAt(position))
//        return animal;
//    }
//    return null;
//  }
//}