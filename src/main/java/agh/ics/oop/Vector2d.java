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
}