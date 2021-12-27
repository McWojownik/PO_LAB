package agh.ics.oop;

public class OptionsParser {

  public MoveDirection numberToMoveDirection(int direction) {
    return switch (direction) {
      case 0 -> MoveDirection.FORWARD;
      case 1 -> MoveDirection.FORWARD_RIGHT;
      case 2 -> MoveDirection.RIGHT;
      case 3 -> MoveDirection.BACKWARD_RIGHT;
      case 4 -> MoveDirection.BACKWARD;
      case 5 -> MoveDirection.BACKWARD_LEFT;
      case 6 -> MoveDirection.LEFT;
      case 7 -> MoveDirection.FORWARD_LEFT;
      default -> throw new IllegalArgumentException(direction + " is not legal move direction");
    };
  }

  public MapDirection numberToMapDirection(int direction) {
    return switch (direction) {
      case 0 -> MapDirection.NORTH;
      case 1 -> MapDirection.NORTH_EAST;
      case 2 -> MapDirection.EAST;
      case 3 -> MapDirection.SOUTH_EAST;
      case 4 -> MapDirection.SOUTH;
      case 5 -> MapDirection.SOUTH_WEST;
      case 6 -> MapDirection.WEST;
      case 7 -> MapDirection.NORTH_WEST;
      default -> throw new IllegalArgumentException(direction + " is not legal map direction");
    };
  }

  public int getInt(String toParse) {
    return Integer.parseInt(toParse);
  }
}