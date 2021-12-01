package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class Animal implements IMapElement {
  private MapDirection orientation = MapDirection.NORTH;
  private final IWorldMap map;
  private Vector2d position;
  private final List<IPositionChangeObserver> observers= new ArrayList<>();

  public Animal(IWorldMap map, Vector2d initialPosition) {
    this.map = map;
    this.position = initialPosition;
    this.addObserver((IPositionChangeObserver) map);
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

  void addObserver(IPositionChangeObserver observer){
    this.observers.add(observer);
  }

  void removeObserver(IPositionChangeObserver observer){
    this.observers.remove(observer);
  }

  void positionChanged(Vector2d oldPosition, Vector2d newPosition){
    for(IPositionChangeObserver observer:observers){
      observer.positionChanged(oldPosition, newPosition);
    }
  }

  protected MapDirection getOrientation() {
    return this.orientation;
  }

  protected void move(MoveDirection direction) {
    switch (direction) {
      case FORWARD -> {
        Vector2d position = this.position.add(this.orientation.toUnitVector());
        if (this.map.canMoveTo(position)) {
          this.positionChanged(this.position, position);
          this.position = position;
        }
      }
      case BACKWARD -> {
        Vector2d position = this.position.subtract(this.orientation.toUnitVector());
        if (this.map.canMoveTo(position)){
          this.positionChanged(this.position, position);
          this.position = position;
        }
      }
      case RIGHT -> this.orientation = this.orientation.next();
      case LEFT -> this.orientation = this.orientation.previous();
    }
  }
}
