package agh.ics.oop;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
  protected int width;
  protected int height;
  protected int startEnergy;
  protected int moveEnergy;
  protected int plantEnergy;
  protected int jungleRatio;
  protected Vector2d leftBottom;
  protected Vector2d rightTop;
  protected Map<Vector2d, LinkedList<Animal>> animalsHash = new LinkedHashMap<>();
  protected LinkedList<Animal> animalsList = new LinkedList<>();
  public final MapVisualizer visualizer = new MapVisualizer(this);
  public MapBoundary mapEnds = new MapBoundary(this);

  public Map<Vector2d, LinkedList<Animal>> getAnimalsHash() {
    return this.animalsHash;
  }

  public LinkedList<Animal> getAnimalsList() {
    return this.animalsList;
  }

  public Vector2d getLeftBottom() {
    return this.leftBottom;
  }

  public Vector2d getRightTop() {
    return this.rightTop;
  }

  public String toString(Vector2d leftBottom, Vector2d rightTop) {
    return visualizer.draw(leftBottom, rightTop);
  }

  public boolean place(Animal animal) {
    Vector2d animalPosition = animal.getPosition();
//    if (this.animals.get(animalPosition) != null)
//      throw new IllegalArgumentException("Position " + animalPosition.toString() + " is busy");
//    this.animals.put(animalPosition, animal);
    this.addAnimal(animal, animalPosition);
    this.animalsList.push(animal);
    return true;
  }

  public void addAnimal(Animal animal, Vector2d newPosition) {
//    Vector2d animalPosition = animal.getPosition();
    LinkedList<Animal> list = this.animalsHash.get(newPosition);
    if (list == null) {
      LinkedList<Animal> tmp = new LinkedList<>();
      tmp.add(animal);
      this.animalsHash.put(newPosition, tmp);
    } else {
      list.add(animal);
    }
  }

  public void removeAnimal(Animal animal) {
    Vector2d animalPosition = animal.getPosition();
    LinkedList<Animal> list = this.animalsHash.get(animalPosition);
    if (list != null) {
      if (list.size() > 0) {
        list.remove(animal);
        if (list.size() == 0) {
          this.animalsHash.remove(animalPosition);
        }
      }
    }
  }

  public boolean isOccupied(Vector2d position) {
    return this.animalsHash.get(position) != null;
  }

  public Object objectAt(Vector2d position) {
    LinkedList<Animal> list = this.animalsHash.get(position);
    if (list != null) {
      if (list.size() > 0) {
        return list.getFirst();
      } else { // NIE POWINNO SIE ZDARZYC
        this.animalsHash.remove(position);
      }
    }
    return null;
  }

  public boolean positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
//    Animal animal = this.animals.remove(oldPosition);
//    this.animals.put(newPosition, animal);
//    System.out.println("HERE");
    this.removeAnimal(animal);
    this.addAnimal(animal, newPosition);
    return true;
  }

  public void deleteAnimal(Animal animal) {
    this.animalsList.remove(animal);
    this.removeAnimal(animal);
//    Vector2d animalPosition = animal.getPosition();
//    this.animals.remove(animalPosition);
  }
}
