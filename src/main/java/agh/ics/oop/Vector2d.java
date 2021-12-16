package agh.ics.oop;

import java.util.Objects;

public class Vector2d {
  public final int x;
  public final int y;

  public Vector2d(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y);
  }

  public String toString() {
    return "(" + this.x + "," + this.y + ")";
  }

  protected boolean precedes(Vector2d other) {
    return this.x <= other.x && this.y <= other.y;
  }

  protected boolean follows(Vector2d other) {
    return this.x >= other.x && this.y >= other.y;
  }

//  protected Vector2d upperRight(Vector2d other) {
//    int maxiX = this.x;
//    int maxiY = this.y;
//    if (maxiX < other.x)
//      maxiX = other.x;
//    if (maxiY < other.y)
//      maxiY = other.y;
//    return new Vector2d(maxiX, maxiY);
//  }
//
//  protected Vector2d lowerLeft(Vector2d other) {
//    int miniX = this.x;
//    int miniY = this.y;
//    if (miniX > other.x)
//      miniX = other.x;
//    if (miniY > other.y)
//      miniY = other.y;
//    return new Vector2d(miniX, miniY);
//  }

  protected Vector2d add(Vector2d other) {
    return new Vector2d(this.x + other.x, this.y + other.y);
  }

  protected Vector2d subtract(Vector2d other) {
    return new Vector2d(this.x - other.x, this.y - other.y);
  }

  public boolean equals(Object other) {
    if (!(other instanceof Vector2d that))
      return false;
    return this.x == that.x && this.y == that.y;
  }

//  protected Vector2d opposite() {
//    return new Vector2d(-this.x, -this.y);
//  }
}