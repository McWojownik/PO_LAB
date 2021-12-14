package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GrassField extends AbstractWorldMap {
  protected List<Grass> grass = new ArrayList<>();
  private int width;
  private int height;
  private int startEnergy;
  private int moveEnergy;
  private int plantEnergy;
  private int jungleRatio;
  private Vector2d jungleLeftBottom;
  private Vector2d jungleRightTop;
//  private MapBoundary mapEnds = new MapBoundary(this);

  public GrassField(int width, int height, int startEnergy, int moveEnergy, int animalsAtStart, int plantEnergy, int jungleRatio) {
    super();
    this.width = width;
    this.height = height;
    this.startEnergy = startEnergy;
    this.moveEnergy = moveEnergy;
    this.plantEnergy = plantEnergy;
    this.jungleRatio = jungleRatio;
    this.leftBottom = new Vector2d(0, 0);
    this.rightTop = new Vector2d(this.width - 1, this.height - 1);
//    this.generateAnimals(animalsAtStart);
    this.generateJungle();
//    this.generateDayGrass();
  }

  public Vector2d getLeftBottom() {
    return this.leftBottom;
  }

  public Vector2d getRightTop() {
    return this.rightTop;
  }

  public String toString() {
    return super.toString(this.leftBottom, this.rightTop);
  }

  public void generateJungle() {
    int middleWidth = this.width / 2;
    int middleHeight = this.height / 2;
    this.jungleLeftBottom = new Vector2d(middleWidth - 1, middleHeight - 1);
    this.jungleRightTop = new Vector2d(middleWidth + 1, middleHeight + 1);
  }

  public void generateDayGrass() {
    this.generateJungleGrass();
    this.generateOutJungleGrass();
  }

  public void moveAllAnimals() {
    Map<Vector2d, Animal> animals = super.getAnimals();
    for (Animal animal : animals.values()) {
      // TU SKOŃCZYŁEM
      System.out.println(animal.toString() + " " + animal.getPosition());
    }
  }

  private void generateJungleGrass() {
    boolean isFieldEmpty = false;
    for (int i = this.jungleLeftBottom.x; i <= this.jungleRightTop.x; i++) {
      for (int j = this.jungleLeftBottom.y; j <= this.jungleRightTop.y; j++) {
        Vector2d vector = new Vector2d(i, j);
        if (!this.isOccupied(vector)) {
          isFieldEmpty = true;
          break;
        }
      }
      if (isFieldEmpty)
        break;
    }
//    System.out.println(isFieldEmpty);
//    System.out.println(this.jungleLeftBottom.x);
//    System.out.println((int) (0.99*(this.jungleRightTop.x-this.jungleLeftBottom.x+1))+this.jungleLeftBottom.x);
//    System.out.println(this.jungleLeftBottom.y);
//    System.out.println((int) (0.99*(this.jungleRightTop.y-this.jungleLeftBottom.y+1))+this.jungleLeftBottom.y);
    if (isFieldEmpty) {
      boolean found = false;
      while (!found) {
        int x = (int) ((Math.random() * (this.jungleRightTop.x - this.jungleLeftBottom.x + 1)) + this.jungleLeftBottom.x);
        int y = (int) ((Math.random() * (this.jungleRightTop.y - this.jungleLeftBottom.y + 1)) + this.jungleLeftBottom.y);
        Vector2d vector = new Vector2d(x, y);
        if (!this.isOccupied(vector)) {
          found = true;
          this.grass.add(new Grass(vector));
        }
      }
    }
  }

  private void generateOutJungleGrass() {
    boolean isFieldEmpty = false;
    for (int i = 0; i <= this.rightTop.x; i++) {
      for (int j = 0; j <= this.rightTop.y; j++) {
        Vector2d vector = new Vector2d(i, j);
        if (!this.isJungle(vector) && !this.isOccupied(vector)) {
          isFieldEmpty = true;
          break;
        }
      }
      if (isFieldEmpty)
        break;
    }
//    System.out.println((int) (0.99*(this.rightTop.x+1)));
//    System.out.println((int) (0.01*(this.rightTop.x+1)));
//    System.out.println((int) (0.99*(this.rightTop.y+1)));
//    System.out.println((int) (0.01*(this.rightTop.y+1)));
    if (isFieldEmpty) {
      boolean found = false;
      while (!found) {
        int x = (int) ((Math.random() * (this.rightTop.x + 1)));
        int y = (int) ((Math.random() * (this.rightTop.y + 1)));
        Vector2d vector = new Vector2d(x, y);
        if (!this.isJungle(vector) && !this.isOccupied(vector)) {
          found = true;
          this.grass.add(new Grass(vector));
        }
      }
    }
  }

  private boolean grassOnField(Vector2d position) {
    for (Grass grass : this.grass) {
      if (grass.isAt(position))
        return true;
    }
    return false;
  }


  @Override
  public boolean canMoveTo(Vector2d position) {
    return position.precedes(this.rightTop) && position.follows(this.leftBottom);
  }

  @Override
  public boolean place(Animal animal) {
    super.place(animal);
    return true;
  }

  @Override
  public boolean isOccupied(Vector2d position) {
    if (super.isOccupied(position))
      return true;
    return grassOnField(position);
  }

  @Override
  public Object objectAt(Vector2d position) {
    Object animal = super.objectAt(position);
    if (animal != null)
      return animal;
    for (Grass grass : this.grass) {
      if (grass.isAt(position))
        return grass;
    }
    return null;
  }

  @Override
  public boolean isJungle(Vector2d position) {
    if (position.precedes(this.jungleRightTop) && position.follows(this.jungleLeftBottom)) {
      return true;
    }
    return false;
  }

  public boolean positionChanged(Vector2d oldPosition, Vector2d newPosition) {
    return super.positionChanged(oldPosition, newPosition);
  }
}