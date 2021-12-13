package agh.ics.oop;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver {
  private final SortedSet<Vector2d> setX = new TreeSet<>(new Comparator<Vector2d>() {
    public int compare(Vector2d v1, Vector2d v2) {
      if (v1.x - v2.x == 0) {
        if (v1.y - v2.y == 0) {
          if (v1 == v2)
            return 0;
          return -1;
        }
        return v1.y - v2.y;
      }
      return v1.x - v2.x;
    }
  });
  private final SortedSet<Vector2d> setY = new TreeSet<>((v1, v2) -> {
    if (v1.y - v2.y == 0) {
      if (v1.x - v2.x == 0) {
        if (v1.equals(v2))
          return 0;
        return -1;
      }
      return v1.x - v2.x;
    }
    return v1.y - v2.y;
  });
//  private IWorldMap map;

  public MapBoundary(IWorldMap map) {
//    this.map = map;
  }

  protected boolean addElement(Vector2d v) {
    setX.add(v);
    setY.add(v);
    return true;
  }

  public Vector2d getLeftBottom() {
    Vector2d vXmin = setX.first();
    Vector2d vYmin = setY.first();
    return new Vector2d(vXmin.x, vYmin.y);
  }

  public Vector2d getRightTop() {
    Vector2d vXmax = setX.last();
    Vector2d vYmax = setY.last();
    return new Vector2d(vXmax.x, vYmax.y);
  }

  @Override
  public boolean positionChanged(Vector2d oldPosition, Vector2d newPosition) {
    setX.remove(oldPosition);
    setY.remove(oldPosition);
    setX.add(newPosition);
    setY.add(newPosition);
    return true;
  }
}
