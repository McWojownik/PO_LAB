package agh.ics.oop;

public interface IWorldMap {
  boolean canMoveTo(Vector2d position);

  void place(Animal animal);

  boolean isOccupied(Vector2d position);

  Object objectAt(Vector2d position);

  Animal getStrongestAnimalOnField(Vector2d position);

  String getGrassColorOnField(Vector2d position);

  boolean canMoveBeyondMap();

  Vector2d moveBeyondMapVector(Vector2d oldPosition);

  void clearDeadAnimals();

  void moveAllAnimals();

  void allAnimalsEat();

  void animalsCopulation();

  void generateDayGrass();
}