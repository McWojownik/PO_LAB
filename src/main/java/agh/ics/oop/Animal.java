package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class Animal implements IMapElement {
  private MapDirection orientation;
  private final AbstractWorldMap map;
  private Vector2d position;
  private final List<IPositionChangeObserver> observers = new ArrayList<>();
  //  private int[] genes = {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 4, 4, 4, 4, 4, 4, 5, 5, 6, 6, 7, 7, 7, 7};
  private final Genes genes;
  protected int energy;
  protected int daysAlive = 0;
  protected int numberOfKidsTotal = 0;
  protected int numberOfKidsObserved = 0;
  protected boolean isUnderObservation = false;
  private int eraDied = 0;
  private boolean highlight = false;

  public Animal(AbstractWorldMap map, Vector2d initialPosition, int startEnergy, Genes genes) {
    this.map = map;
    this.energy = startEnergy;
    this.position = initialPosition;
    this.orientation = new OptionsParser().numberToMapDirection((int) (Math.random() * 8));
    this.genes = genes;
    this.addObserver(map);
  }

  @Override
  public String toString() {
    return this.orientation.toString();
  }

  @Override
  public Vector2d getPosition() {
    return this.position;
  }

  public MoveDirection getNextAnimalMove() {
    return new OptionsParser().numberToMoveDirection(this.getGenesArr()[(int) (Math.random() * 32)]);
  }

  public boolean isDead() {
    return this.energy <= 0;
  }

  public boolean canCopulate() {
    return this.energy >= this.map.startEnergy / 2;
  }

  public int[] getGenesArr() {
    return this.genes.getAnimalGenes();
  }

  public void liveAnotherDay() {
    this.daysAlive++;
  }

  public int getEraDied() {
    return this.eraDied;
  }

  public void setEraDied(int day) {
    this.eraDied = day;
  }

  public boolean getHighlight() {
    return this.highlight;
  }

  public void setHighlight(boolean change) {
    this.highlight = change;
  }

  public void addObserver(IPositionChangeObserver observer) {
    this.observers.add(observer);
  }

  public void removeObserver(IPositionChangeObserver observer) {
    this.observers.remove(observer);
  }

  public void observeStatistics() {
    this.isUnderObservation = true;
    this.numberOfKidsObserved = 0;
    this.map.isObservedAnimalOnMap = true;
  }

  public boolean checkIfIdenticalGenes(Animal animal) {
    return this.genes.checkIfIdenticalGenes(animal.getGenesArr());
  }

  boolean positionChanged(Vector2d oldPosition, Vector2d newPosition) {
    for (IPositionChangeObserver observer : observers) {
      observer.positionChanged(oldPosition, newPosition, this); // observer.positionChanged(oldPosition, newPosition) zwraca na ten moment zawsze true
    }
    return true;
  }

  protected void move(MoveDirection direction) {
    switch (direction) {
      case FORWARD -> {
        Vector2d position = this.position.add(this.orientation.toUnitVector());
        realMove(position);
      }
      case BACKWARD -> {
        Vector2d position = this.position.subtract(this.orientation.toUnitVector());
        realMove(position);
      }
      case FORWARD_RIGHT -> {
        this.orientation = this.orientation.next();
      }
      case RIGHT -> {
        this.orientation = this.orientation.next();
        this.orientation = this.orientation.next();
      }
      case BACKWARD_RIGHT -> {
        this.orientation = this.orientation.next();
        this.orientation = this.orientation.next();
        this.orientation = this.orientation.next();
      }
      case FORWARD_LEFT -> {
        this.orientation = this.orientation.previous();
      }
      case LEFT -> {
        this.orientation = this.orientation.previous();
        this.orientation = this.orientation.previous();
      }
      case BACKWARD_LEFT -> {
        this.orientation = this.orientation.previous();
        this.orientation = this.orientation.previous();
        this.orientation = this.orientation.previous();
      }
    }
    this.energy -= this.map.moveEnergy;
  }

  private void realMove(Vector2d position) {
    if (this.map.canMoveTo(position)) {
      if (this.positionChanged(this.position, position))
        this.position = position;
    } else {
      if (this.map.canMoveBeyondMap()) {
        Vector2d vector = this.map.moveBeyondMapVector(position);
        position = this.position.add(vector);
        if (this.positionChanged(this.position, position))
          this.position = position;
      }
    }
  }

  public String getAnimalColor() {
    int energy = this.energy;
    if (energy < 0.4 * this.map.startEnergy)
      return "e6cd17";
    if (energy < 0.8 * this.map.startEnergy)
      return "cf7632";
    if (energy < 1.2 * this.map.startEnergy)
      return "fa6b37";
    if (energy < 1.6 * this.map.startEnergy)
      return "ed4d13";
    if (energy < 2 * this.map.startEnergy)
      return "ff3b3b";
    if (energy < 4 * this.map.startEnergy)
      return "ff0000";
    if (energy < 8 * this.map.startEnergy)
      return "870000";
    return "e8138f";
  }

  public int getNumberOfKidsObserved() {
    return this.numberOfKidsObserved;
  }
}
