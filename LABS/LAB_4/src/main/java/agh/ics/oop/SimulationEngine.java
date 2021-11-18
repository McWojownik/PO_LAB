package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class SimulationEngine implements IEngine {
  private final MoveDirection[] moves;
  private final RectangularMap map;
  private final List<Animal> animals;

  public SimulationEngine(MoveDirection[] moves, IWorldMap map, Vector2d[] startingPoints) {
    this.moves = moves;
    this.map = (RectangularMap) map;
    this.animals = new ArrayList<>();
    this.putAnimals(startingPoints);
  }

  public List<Animal> getAnimals(){
    return this.animals;
  }

  private void putAnimals(Vector2d[] startingPoints) {
    for (Vector2d vector : startingPoints) {
      Animal animal = new Animal(this.map, vector);
      if(this.map.place(animal)){
        animals.add(animal);
      }
    }
  }

  @Override
  public void run() {
    List<Animal> animals = this.animals;
    int countAnimals = animals.size();
    int numberOfMoves = this.moves.length;
//    System.out.println(countAnimals + " " + numberOfMoves);
//    out.println(map.toString());
    for (int i = 0; i < numberOfMoves; i++) {
      animals.get(i % countAnimals).move(moves[i]);
//      out.println(moves[i]);
//      out.println(this.map.toString());
    }
  }
}
