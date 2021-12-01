package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

abstract class AbstractWorldMap implements IWorldMap {
  protected List<Animal> animals;
  private final MapVisualizer visualizer = new MapVisualizer(this);

  public AbstractWorldMap() {
    this.animals = new ArrayList<>();
  }

  public String toString(Vector2d leftBottom, Vector2d rightTop) {
    return visualizer.draw(leftBottom, rightTop);
  }

  public boolean place(Animal animal) {
    Vector2d animalPosition = animal.getPosition();
    for (Animal animal2 : this.animals) {
      if (animal2.isAt(animalPosition))
        return false;
    }
    this.animals.add(animal);
    return true;
  }

  public boolean isOccupied(Vector2d position) {
    for (Animal animal : this.animals) {
      if (animal.isAt(position))
        return true;
    }
    return false;
  }

  public Object objectAt(Vector2d position) {
    for (Animal animal : this.animals) {
      if (animal.isAt(position))
        return animal;
    }
    return null;
  }
}
