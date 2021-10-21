package agh.ics.oop;

import static java.lang.System.out;

public class World {
  public static void main(String[] args) {
    Direction[] d = convert(args);
    run(d);
  }

  public static Direction[] convert(String[] args) {
    int n = args.length;
    Direction[] d = new Direction[n];
    for (int i = 0; i < n; i++) {
      d[i] = switch (args[i]) {
        case "f" -> Direction.FORWARD;
        case "b" -> Direction.BACKWARD;
        case "r" -> Direction.RIGHT;
        case "l" -> Direction.LEFT;
        default -> Direction.NOT_KNOWN;
      };
    }
    return d;
  }

  public static void run(Direction[] d) {
    for (Direction dirk : d)
      out.print(dirk + "\n");
  }
}