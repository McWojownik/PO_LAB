package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class RectangularMap implements IWorldMap {
  private final int width;
  private final int height;
  private final List<Animal> animals = new ArrayList<>();
  private final MapVisualizer visualizer = new MapVisualizer(this);

  public RectangularMap(int width, int height) {
    this.width = width;
    this.height = height;
  }

  public List<Animal> getAnimals() {
    return this.animals;
  }

  public String toString() {
    return visualizer.draw(new Vector2d(0, 0), new Vector2d(this.width - 1, this.height - 1));
  }

  @Override
  public boolean canMoveTo(Vector2d position) {
    return position.y < this.height && position.y >= 0 && position.x < this.width && position.x >= 0 && !isOccupied(position);
  }

  @Override
  public boolean place(Animal animal) {
    if (!isOccupied(animal.position)) {
      this.animals.add(animal);
      return true;
    }
    return false;
  }

  @Override
  public boolean isOccupied(Vector2d position) {
    if (position.y >= this.height || position.y < 0 || position.x >= this.width || position.x < 0) {
      return true;
    }
    for (Animal animal : this.animals) {
      if (animal.isAt(position))
        return true;
    }
    return false;
  }

  @Override
  public Object objectAt(Vector2d position) {
    for (Animal animal : this.animals) {
      if (animal.isAt(position))
        return animal;
    }
    return null;
  }
}
