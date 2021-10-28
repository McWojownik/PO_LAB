package agh.ics.oop;

import static java.lang.System.out;

public class World {
  public static void main(String[] args) {
    Animal a1 = new Animal();
    out.println(a1.toString());
    Vector2d v4 = new Vector2d(2, 2);
    out.println(a1.isAt(v4));
    OptionsParser parser = new OptionsParser();
    String[] arr = {"f", "r", "VS", "f", "f", "l", "f", "b", "d", "b", "b", "b", "f", "l", "b"};
    MoveDirection[] instructions = parser.parse(arr);
    out.println(instructions.length);
    for (MoveDirection instruction : instructions) {
      a1.move(instruction);
      out.println(instruction.toString() + " " + a1.toString());
    }
  }
}