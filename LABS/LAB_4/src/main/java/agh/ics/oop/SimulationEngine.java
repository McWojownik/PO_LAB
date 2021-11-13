package agh.ics.oop;

import java.util.List;

import static java.lang.System.out;

public class SimulationEngine implements IEngine{
  private final MoveDirection[] moves;
  public IWorldMap map;
  private final Vector2d[] startingPoints;

  public SimulationEngine(MoveDirection[] moves, IWorldMap map, Vector2d[] startingPoints){
    this.moves=moves;
    this.map=map;
    this.startingPoints=startingPoints;
    this.putAnimals();
  }

  private void putAnimals(){
    for(Vector2d vector: this.startingPoints){
      Animal animal = new Animal(this.map, vector);
      this.map.place(animal);
    }
  }

  @Override
  public void run() {
    List<Animal> animals = this.map.getAnimals();
    int countAnimals = animals.size();
    int numberOfMoves = this.moves.length;
//    System.out.println(countAnimals + " " + numberOfMoves);
//    out.println(map.toString());
    for(int i=0;i<numberOfMoves;i++){
      animals.get(i%countAnimals).move(moves[i]);
//      out.println(moves[i]);
//      out.println(this.map.toString());
    }
  }
}
