package agh.ics.oop;

import static java.lang.System.out;

public class World {
  public static void main(String[] args) {
    Vector2d pos1 = new Vector2d(1, 2);
    Vector2d pos2 = new Vector2d(12, 22);
    Vector2d pos3 = new Vector2d(0, 3);
    Vector2d pos4 = new Vector2d(0, 0);
    out.println(pos1.toString());
    out.println(pos1.precedes(pos2));
    out.println(pos1.follows(pos2));
    out.println(pos4.upperRight(pos3).toString());
    out.println(pos4.lowerLeft(pos3).toString());
    out.println(pos2.add(pos1).toString());
    out.println(pos1.subtract(pos2).toString());
    out.println(pos3.equals(pos1));
    out.println(pos3.opposite().toString());
    Vector2d v1 = new Vector2d(0, 0);
    Vector2d v2 = new Vector2d(1, 1);
    Vector2d v3 = new Vector2d(1, 2);
    out.println(v1.follows(v2));
    Vector2d position1 = new Vector2d(1,2);
    out.println(position1);
    Vector2d position2 = new Vector2d(-2,1);
    out.println(position2);
    out.println(position1.add(position2));
    MapDirection direction = MapDirection.EAST;
    out.println(direction.toString());
  }
}