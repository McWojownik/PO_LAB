package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

// DO ZROBIENIA KIEDYS LAB_5_ZAD_12

public class GrassField extends AbstractWorldMap {
  protected List<Grass> grass= new ArrayList<>();;
  private Vector2d leftBottom = new Vector2d(0, 0);
  private Vector2d rightTop = new Vector2d(0, 0);

  public GrassField(int n) {
    super();
    this.generateGrass(n);
  }

  public String toString() {
    return super.toString(leftBottom, rightTop);
  }

  private void generateGrass(int n) {
    for (int i = 0; i < n; i++) {
      boolean found = false;
      while (!found) {
        int x = (int) (Math.random() * Math.sqrt(10 * n));
        int y = (int) (Math.random() * Math.sqrt(10 * n));
        Vector2d v = new Vector2d(x, y);
        if (!grassOnField(v)) {
          found = true;
          if (this.grass.size() != 0)
            this.updateMapEnd(v);
          else {
            this.leftBottom = v;
            this.rightTop = v;
          }
          this.grass.add(new Grass(v));
        }
      }
    }
  }

  public void updateMapEnd(Vector2d v) {
    int miniX = this.leftBottom.x;
    int miniY = this.leftBottom.y;
    int maxiX = this.rightTop.x;
    int maxiY = this.rightTop.y;
    miniX = Math.min(miniX, v.x);
    miniY = Math.min(miniY, v.y);
    maxiX = Math.max(maxiX, v.x);
    maxiY = Math.max(maxiY, v.y);
    this.leftBottom = new Vector2d(miniX, miniY);
    this.rightTop = new Vector2d(maxiX, maxiY);
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
    if (super.isOccupiedABS(position))
      return false;
    this.updateMapEnd(position);
    return true;
  }

  @Override
  public boolean place(Animal animal) {
    if (super.place(animal)) {
      this.updateMapEnd(animal.getPosition());
      return true;
    }
    return false;
  }

  @Override
  public boolean isOccupied(Vector2d position) {
    if (super.isOccupiedABS(position))
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
}

// WCZESNIEJSZA MAPA NIE DZIEDZICZACA Z AbstractWorldMap
//package agh.ics.oop;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class GrassField implements IWorldMap {
//  protected List<Animal> animals;
//  protected List<Grass> grass;
//  private Vector2d leftDown = new Vector2d(0, 0);
//  private Vector2d rightTop = new Vector2d(0, 0);
//  private final MapVisualizer vizualizer = new MapVisualizer(this);
//
//  public GrassField(int n) {
//    this.animals = new ArrayList<>();
//    this.grass = new ArrayList<>();
//    this.generateGrass(n);
//  }
//
//  private void generateGrass(int n) {
//    for (int i = 0; i < n; i++) {
//      boolean found = false;
//      while (!found) {
//        int x = (int) (Math.random() * Math.sqrt(10 * n));
//        int y = (int) (Math.random() * Math.sqrt(10 * n));
//        Vector2d v = new Vector2d(x, y);
//        if (canPutGrass(v)) {
//          found = true;
//          if (this.grass.size() != 0)
//            this.updateMapEnd(v);
//          else {
//            this.leftDown = v;
//            this.rightTop = v;
//          }
//          this.grass.add(new Grass(v));
//        }
//      }
//    }
//  }
//
//  private void updateMapEnd(Vector2d v) {
//    int miniX = this.leftDown.x;
//    int miniY = this.leftDown.y;
//    int maxiX = this.rightTop.x;
//    int maxiY = this.rightTop.y;
//    miniX = Math.min(miniX, v.x);
//    miniY = Math.min(miniY, v.y);
//    maxiX = Math.max(maxiX, v.x);
//    maxiY = Math.max(maxiY, v.y);
//    this.leftDown = new Vector2d(miniX, miniY);
//    this.rightTop = new Vector2d(maxiX, maxiY);
////    out.println(this.leftDown.toString() + " " + this.rightTop.toString());
//  }
//
//  private boolean canPutGrass(Vector2d v) {
//    for (Grass grass : this.grass) {
//      Vector2d posGrass = grass.getPosition();
//      if (posGrass.x == v.x && posGrass.y == v.y) {
//        return false;
//      }
//    }
//    return true;
//  }
//
//  public String toString() {
//    return vizualizer.draw(leftDown, rightTop);
//  }
//
//  @Override
//  public boolean canMoveTo(Vector2d position) {
//    for (Animal animal : this.animals) {
//      Vector2d animalPosition = animal.getPosition();
//      if (animalPosition.x == position.x && animalPosition.y == position.y) {
//        return false;
//      }
//    }
//    this.updateMapEnd(position);
//    return true;
//  }
//
//  @Override
//  public boolean place(Animal animal) {
//    Vector2d animalPosition = animal.getPosition();
//    for (Animal a : this.animals) {
//      Vector2d aPosition = a.getPosition();
//      if (animalPosition.x == aPosition.x && animalPosition.y == aPosition.y) {
//        return false;
//      }
//    }
//    this.updateMapEnd(animalPosition);
//    this.animals.add(animal);
//    return true;
//  }
//
//  @Override
//  public boolean isOccupied(Vector2d position) {
//    for (Animal animal : this.animals) {
//      Vector2d animalPosition = animal.getPosition();
//      if (animalPosition.x == position.x && animalPosition.y == position.y) {
//        return true;
//      }
//    }
//    for (Grass grass : this.grass) {
//      Vector2d animalPosition = grass.getPosition();
//      if (animalPosition.x == position.x && animalPosition.y == position.y) {
//        return true;
//      }
//    }
//    return false;
//  }
//
//  @Override
//  public Object objectAt(Vector2d position) {
//    for (Animal animal : this.animals) {
//      Vector2d animalPosition = animal.getPosition();
//      if (animalPosition.x == position.x && animalPosition.y == position.y) {
//        return animal;
//      }
//    }
//    for (Grass grass : this.grass) {
//      Vector2d animalPosition = grass.getPosition();
//      if (animalPosition.x == position.x && animalPosition.y == position.y) {
//        return grass;
//      }
//    }
//    return null;
//  }
//}