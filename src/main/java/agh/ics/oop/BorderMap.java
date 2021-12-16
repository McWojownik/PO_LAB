package agh.ics.oop;

public class BorderMap extends AbstractWorldMap {
  public BorderMap(int width, int height, int startEnergy, int moveEnergy, int animalsAtStart, int plantEnergy, int jungleRatio) {
    super(width, height, startEnergy, moveEnergy, animalsAtStart, plantEnergy, jungleRatio);
  }

  public boolean canMoveBeyondMap() {
    return false;
  }

  public Vector2d moveBeyondMapVector(Vector2d oldPosition) {
    return new Vector2d(0, 0);
  }
}