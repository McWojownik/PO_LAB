package agh.ics.oop;

import agh.ics.oop.gui.App;

import java.util.ArrayList;
import java.util.List;


public class SimulationEngine implements IEngine, Runnable {
  private MoveDirection[] moves = {};
  private final AbstractWorldMap map;
  private final List<Animal> animals = new ArrayList<>();
  protected final List<App> observers = new ArrayList<>();
  protected int moveDelay;

  public SimulationEngine(AbstractWorldMap map, int animalsAtStart) {
    this.map = map;
    this.putAnimals(this.createStartingPoints(animalsAtStart));
  }

//  public List<Animal> getAnimals() {
//    return this.animals;
//  }

  private List<Vector2d> createStartingPoints(int animalsAtStart) {
    List<Vector2d> startingPoints = new ArrayList<>();
    for (int i = 0; i < animalsAtStart; i++) {
      boolean foundPlace = false;
      while (!foundPlace) {
        int x = (int) ((Math.random() * (this.map.rightTop.x + 1)));
        int y = (int) ((Math.random() * (this.map.rightTop.y + 1)));
        boolean inside = false;
        for (Vector2d v : startingPoints) {
          if (v.x == x && v.y == y) {
            inside = true;
            break;
          }
        }
        if (!inside) {
          Vector2d vector = new Vector2d(x, y);
          startingPoints.add(vector);
          foundPlace = true;
        }
      }
    }
    return startingPoints;
  }

  private void putAnimals(List<Vector2d> startingPoints) {
    for (Vector2d vector : startingPoints) {
      Animal animal = new Animal(this.map, vector);
      if (this.map.place(animal))
        this.animals.add(animal);
    }
  }

  public void setMoves(String[] moves) {
    this.moves = new OptionsParser().parse(moves);
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
//    for (int i = 0; i < numberOfMoves; i++) {
    while (true) {
      try {
        Thread.sleep(this.moveDelay);
      } catch (InterruptedException e) {
        System.out.print("Something is wrong");
      }
//      animals.get(i % countAnimals).move(moves[i]);
      this.map.moveAllAnimals();
      this.map.generateDayGrass();
      for (App GUI : this.observers) {
        GUI.positionChanged();
      }
    }
//    }
  }
}
