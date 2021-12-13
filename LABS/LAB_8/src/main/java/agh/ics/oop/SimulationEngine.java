package agh.ics.oop;
import agh.ics.oop.gui.App;

import java.util.ArrayList;
import java.util.List;


public class SimulationEngine implements IEngine, Runnable {
  private MoveDirection[] moves = {};
  private final IWorldMap map;
  private final List<Animal> animals;
  protected final List<App> observers = new ArrayList<>();
  protected int moveDelay;

  public SimulationEngine(IWorldMap map, Vector2d[] startingPoints) {
    this.map = map;
    this.animals = new ArrayList<>();
    this.putAnimals(startingPoints);
  }

  public List<Animal> getAnimals() {
    return this.animals;
  }

  private void putAnimals(Vector2d[] startingPoints) {
    for (Vector2d vector : startingPoints) {
      Animal animal = new Animal(this.map, vector);
      if (this.map.place(animal))
        this.animals.add(animal);
    }
  }

  public void setMoves(String[] moves) {
    this.moves = new OptionsParser().parse(moves);
  }

  public void setMoves2(MoveDirection[] moves) {
    this.moves = moves;
  }

  public void addObserver(App gui) {
    observers.add(gui);
  }

  public void setDayTimeChange(int time) {
    this.moveDelay = time;
  }

  @Override
  public void run() {
    int countAnimals = this.animals.size();
    int numberOfMoves = this.moves.length;
    for (int i = 0; i < numberOfMoves; i++) {
      try {
        Thread.sleep(this.moveDelay);
      } catch (InterruptedException e) {
        System.out.print("Something is wrong");
      }
      animals.get(i % countAnimals).move(moves[i]);
      for (App GUI : this.observers) {
        GUI.positionChanged();
      }
    }
  }
}
