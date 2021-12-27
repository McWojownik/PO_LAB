package agh.ics.oop;

public class BorderMap extends AbstractWorldMap {
  public BorderMap(int width, int height, int startEnergy, int moveEnergy, int plantEnergy, int jungleWidth, int jungleHeight, boolean isMagical) {
    super(width, height, startEnergy, moveEnergy, plantEnergy, jungleWidth, jungleHeight, isMagical);
  }

  @Override
  public boolean canMoveBeyondMap() {
    return false;
  }

  @Override
  public Vector2d moveBeyondMapVector(Vector2d oldPosition) {
    return new Vector2d(0, 0);
  }
}