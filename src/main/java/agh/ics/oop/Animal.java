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

  public Animal(AbstractWorldMap map, Vector2d initialPosition, int startEnergy, Genes genes) {
    this.map = map;
    this.energy = startEnergy;
    this.position = initialPosition;
    this.orientation = new OptionsParser().numberToMapDirection((int) (Math.random()*8));
    this.genes = genes;
    this.addObserver(map);
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

  public int[] getGenesArr(){
    return this.genes.getAnimalGenes();
  }

  @Override
  public String toString() {
    return this.orientation.toString();
  }

  @Override
  public Vector2d getPosition() {
    return this.position;
  }

  @Override
  public boolean isAt(Vector2d position) {
    return this.position.equals(position);
  }

  @Override
  public String getImageSource() {
    return "up.png";
//    return switch (this.toString()) {
//      case "N" -> "up.png";
//      case "E" -> "right.png";
//      case "S" -> "down.png";
//      case "W" -> "left.png";
//      default -> "";
//    };
  }

  public void addObserver(IPositionChangeObserver observer) {
    this.observers.add(observer);
  }

  public void removeObserver(IPositionChangeObserver observer) {
    this.observers.remove(observer);
  }

  boolean positionChanged(Vector2d oldPosition, Vector2d newPosition) {
    for (IPositionChangeObserver observer : observers) {
      observer.positionChanged(oldPosition, newPosition, this); // observer.positionChanged(oldPosition, newPosition) zwraca na ten moment zawsze true
    }
    return true;
  }

  protected MapDirection getOrientation() {
    return this.orientation;
  }

  protected void move(MoveDirection direction) {
    switch (direction) {
      case FORWARD -> {
        Vector2d position = this.position.add(this.orientation.toUnitVector());
        if (this.map.canMoveTo(position)) {
          if (this.positionChanged(this.position, position)) {
            this.position = position;
          }
        }
      }
      case BACKWARD -> {
        Vector2d position = this.position.subtract(this.orientation.toUnitVector());
        if (this.map.canMoveTo(position)) {
          if (this.positionChanged(this.position, position)) {
            this.position = position;
          }
        }
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
}
