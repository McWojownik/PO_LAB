package agh.ics.oop;

public enum MapDirection {
  NORTH,
  SOUTH,
  WEST,
  EAST;

  public String toString(){
    String message = switch(this){
      case NORTH -> "POLNOC";
      case SOUTH -> "POLUDNIE";
      case WEST -> "ZACHOD";
      case EAST -> "WSCHOD";
    };
    return message;
  }
}
