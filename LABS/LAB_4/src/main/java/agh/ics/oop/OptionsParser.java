package agh.ics.oop;

import java.util.Arrays;

public class OptionsParser {
  protected MoveDirection[] parse(String[] arr) {
    MoveDirection[] instructions = new MoveDirection[arr.length];
    int index = 0;
    for (String s : arr) {
      switch (s) {
        case "f", "forward" -> instructions[index++] = MoveDirection.FORWARD;
        case "b", "backward" -> instructions[index++] = MoveDirection.BACKWARD;
        case "r", "right" -> instructions[index++] = MoveDirection.RIGHT;
        case "l", "left" -> instructions[index++] = MoveDirection.LEFT;
      }
    }
    instructions = Arrays.copyOfRange(instructions, 0, index);
    return instructions;
  }
}
//zad 10        PAMIEC  CZAS
//1) Tablica2D    -      +
//2) Lista        +      -