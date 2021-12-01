package agh.ics.oop;

import java.util.LinkedHashMap;
import java.util.Map;

abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
  protected Map<Vector2d, Animal> animals = new LinkedHashMap<>();
  private final MapVisualizer visualizer = new MapVisualizer(this);

  public AbstractWorldMap() {}

  public String toString(Vector2d leftBottom, Vector2d rightTop) {
    return visualizer.draw(leftBottom, rightTop);
  }

  public boolean place(Animal animal) {
    Vector2d animalPosition = animal.getPosition();
    if(this.animals.get(animalPosition)!=null)
      return false;
    this.animals.put(animalPosition, animal);
    return true;
  }

  public boolean isOccupied(Vector2d position) {
    return this.animals.get(position) != null;
  }

  public Object objectAt(Vector2d position) {
    if(this.animals.get(position)!=null)
      return this.animals.get(position);
    return null;
  }

  public boolean positionChanged(Vector2d oldPosition, Vector2d newPosition) {
    Animal animal = this.animals.remove(oldPosition);
    this.animals.put(newPosition, animal);
    return true;
  }
}
