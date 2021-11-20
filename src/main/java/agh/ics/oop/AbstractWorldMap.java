package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

abstract class AbstractWorldMap implements IWorldMap {
  protected List<Animal> animals;
  private final MapVisualizer visualizer = new MapVisualizer(this);

  public AbstractWorldMap(){
    this.animals = new ArrayList<>();
  }

  public String toString(Vector2d leftBottom, Vector2d rightTop) {
    return visualizer.draw(leftBottom, rightTop);
  }

  public boolean place(Animal animal) {
    Vector2d animalPosition = animal.getPosition();
//    PODCZAS WYWOLANIA this.isOccupied WYWOLYWANA BYLA METODA Z KLASY DZIEDZICZACEJ GrassField, KTORA BLOKOWALA POSTAWIENIE ZWIERZAKA NA POZYCJI
//    animalPosition, GDYZ BYLA ZAJETA PRZEZ KEPKE TRAWY, DLATEGO W TEJ KLASIE METODA this.isOccupiedABS MA KONCOWKE "ABS"
    if (this.isOccupiedABS(animalPosition))
      return false;
    this.animals.add(animal);
    return true;
//    EWENTUALNIE MOZNA WYKOWAC PONIZSZY KOD, A Z NAZWY METODY this.isOccupiedabs USUNAC KONCOWKE "ABS"
//    for(Animal a: this.animals){
//      if (a.isAt(animalPosition))
//        return false;
//    }
//    this.animals.add(animal);
//    return true;
  }

  public boolean isOccupiedABS(Vector2d position){
    for(Animal animal: this.animals){
      if (animal.isAt(position))
        return true;
    }
    return false;
  }

  public Object objectAt(Vector2d position){
    for (Animal animal : this.animals) {
      if (animal.isAt(position))
        return animal;
    }
    return null;
  }
}
