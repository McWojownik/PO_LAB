//package agh.ics.oop;
//
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.atomic.AtomicInteger;
//
//public class GrassField extends AbstractWorldMap {
//  protected List<Grass> grass = new ArrayList<>();
//  private Vector2d jungleLeftBottom;
//  private Vector2d jungleRightTop;
////  private MapBoundary mapEnds = new MapBoundary(this);
//
//  public GrassField(int width, int height, int startEnergy, int moveEnergy, int animalsAtStart, int plantEnergy, int jungleRatio) {
//    super();
//    this.width = width;
//    this.height = height;
//    this.startEnergy = startEnergy;
//    this.moveEnergy = moveEnergy;
//    this.plantEnergy = plantEnergy;
//    this.jungleRatio = jungleRatio;
//    this.leftBottom = new Vector2d(0, 0);
//    this.rightTop = new Vector2d(this.width - 1, this.height - 1);
////    this.generateAnimals(animalsAtStart);
//    this.generateJungle();
////    this.generateDayGrass();
//  }
//
//  public String toString() {
//    return super.toString(this.leftBottom, this.rightTop);
//  }
//
//  public void generateJungle() {
//    int middleWidth = this.width / 2;
//    int middleHeight = this.height / 2;
//    this.jungleLeftBottom = new Vector2d(middleWidth - 1, middleHeight - 1);
//    this.jungleRightTop = new Vector2d(middleWidth + 1, middleHeight + 1);
//  }
//
//  public void generateDayGrass() {
//    this.generateJungleGrass();
//    this.generateOutJungleGrass();
//  }
//
//  @Override
//  public void clearDeadAnimals() {
//    LinkedList<Animal> animalsList = super.getAnimalsList();
//    for (int i = animalsList.size() - 1; i >= 0; i--) {
//      Animal animal = animalsList.get(i);
//      if (animal.isDead()) {
//        animal.removeObserver(this);
//        this.deleteAnimal(animal);
//      }
//    }
//  }
//
//  @Override
//  public void allAnimalsEat() {
//    Map<Vector2d, LinkedList<Animal>> animalsHash = super.getAnimalsHash();
////    AtomicInteger total = new AtomicInteger();
//    animalsHash.forEach((Vector2d vector, LinkedList<Animal> animalsAtPosition) -> {
//      if (animalsAtPosition.size() > 0) {
//        Grass grass = this.getGrassOnField(vector);
//        if (grass != null) {
//          Animal animalChoosen = animalsAtPosition.getFirst();
//          int count = 1;
//          for (int i = 1; i < animalsAtPosition.size(); i++) {
//            Animal animal = animalsAtPosition.get(i);
//            if (animalChoosen.energy < animal.energy) {
//              animalChoosen = animal;
//              count = 1;
//            } else if (animalChoosen.energy == animal.energy) {
//              count++;
//            }
//          }
//          if (count == 1) {
//            animalChoosen.energy += this.plantEnergy;
//          } else {
//            for (Animal animal : animalsAtPosition) {
//              if (animalChoosen.energy == animal.energy) {
//                animal.energy += this.plantEnergy / count;
//              }
//            }
//          }
////          System.out.println(g.getPosition().toString() + " zjedzone przez " + count);
//          this.grass.remove(grass);
//        }
//      }
//    });
//  }
//
//  @Override
//  public void animalsCopulation() {
//    Map<Vector2d, LinkedList<Animal>> animalsHash = super.getAnimalsHash();
//    animalsHash.forEach((Vector2d vector, LinkedList<Animal> animalsAtPosition) -> {
//      if (animalsAtPosition.size() > 1) {
//        int count = 0;
//        for (Animal animal : animalsAtPosition) {
//          if (animal.canCopulate())
//            count++;
//        }
//        if (count > 1) {
//          Animal mother = animalsAtPosition.getFirst();
//          for (int i = 1; i < animalsAtPosition.size(); i++) {
//            Animal animal = animalsAtPosition.get(i);
//            if (mother.energy < animal.energy) {
//              mother = animal;
//            }
//          }
//          Animal father = animalsAtPosition.getFirst();
//          if (mother.equals(father))
//            father = animalsAtPosition.get(1);
//          for (int i = 1; i < animalsAtPosition.size(); i++) {
//            Animal animal = animalsAtPosition.get(i);
//            if (father.energy < animal.energy && !animal.equals(mother)) {
//              father = animal;
//            }
//          }
//          Genes genes = new Genes(mother, father);
//          int energy1 = mother.energy / 4;
//          int energy2 = father.energy / 4;
//          mother.energy -= energy1;
//          father.energy -= energy2;
//          int childEnergy = energy1 + energy2;
//          Animal animal = new Animal(this, vector, childEnergy, genes);
////          System.out.println(mother.getPosition().toString() + " " + father.getPosition().toString() + " COPULATE ");
////          System.out.println(animal.getPosition().toString() + " " + animal.getPosition() + " " + animal.energy + " CHILD ");
//          this.place(animal);
//        }
//      }
//    });
//  }
//
//  @Override
//  public String getGrassColorOnField(Vector2d position) {
//    if (this.isJungle(position))
//      return "004500";
//    return "00FF00";
//  }
//
//  public void moveAllAnimals() {
//    LinkedList<Animal> animalsList = super.getAnimalsList();
////    Map<Vector2d, LinkedList<Animal>> animalsHash = super.getAnimalsHash();
////    AtomicInteger total = new AtomicInteger();
////    animalsHash.forEach((k, v) -> total.addAndGet(v.size()));
////    System.out.println(total);
//    for (Animal animal : animalsList) {
//      MoveDirection nextMove = animal.getNextAnimalMove();
//      animal.move(nextMove);
//    }
//  }
//
//  private void generateJungleGrass() {
////    System.out.println(isFieldEmpty);
////    System.out.println(this.jungleLeftBottom.x);
////    System.out.println((int) (0.99*(this.jungleRightTop.x-this.jungleLeftBottom.x+1))+this.jungleLeftBottom.x);
////    System.out.println(this.jungleLeftBottom.y);
////    System.out.println((int) (0.99*(this.jungleRightTop.y-this.jungleLeftBottom.y+1))+this.jungleLeftBottom.y);
//    int numberOfAttemps = (this.jungleRightTop.x - this.jungleLeftBottom.x + 1) * (this.jungleRightTop.y - this.jungleLeftBottom.y + 1);
//    for (int i = 0; i < numberOfAttemps; i++) {
//      int x = (int) ((Math.random() * (this.jungleRightTop.x - this.jungleLeftBottom.x + 1)) + this.jungleLeftBottom.x);
//      int y = (int) ((Math.random() * (this.jungleRightTop.y - this.jungleLeftBottom.y + 1)) + this.jungleLeftBottom.y);
//      Vector2d vector = new Vector2d(x, y);
//      if (!this.isOccupied(vector)) {
//        this.grass.add(new Grass(vector));
//        break;
//      }
//    }
//  }
//
//  private void generateOutJungleGrass() {
////    System.out.println((int) (0.99*(this.rightTop.x+1)));
////    System.out.println((int) (0.01*(this.rightTop.x+1)));
////    System.out.println((int) (0.99*(this.rightTop.y+1)));
////    System.out.println((int) (0.01*(this.rightTop.y+1)));
//    int numberOfAttemps = (this.rightTop.x + 1) * (this.rightTop.y + 1);
//    for (int i = 0; i < numberOfAttemps; i++) {
//      int x = (int) ((Math.random() * (this.rightTop.x + 1)));
//      int y = (int) ((Math.random() * (this.rightTop.y + 1)));
//      Vector2d vector = new Vector2d(x, y);
//      if (!this.isJungle(vector) && !this.isOccupied(vector)) {
//        this.grass.add(new Grass(vector));
//        break;
//      }
//    }
//  }
//
//  private boolean grassOnField(Vector2d position) {
//    for (Grass grass : this.grass) {
//      if (grass.isAt(position))
//        return true;
//    }
//    return false;
//  }
//
//  private Grass getGrassOnField(Vector2d position) {
//    for (Grass grass : this.grass) {
//      if (grass.isAt(position))
//        return grass;
//    }
//    return null;
//  }
//
//
//  @Override
//  public boolean canMoveTo(Vector2d position) {
//    return position.precedes(this.rightTop) && position.follows(this.leftBottom);
//  }
//
//  @Override
//  public boolean place(Animal animal) {
//    super.place(animal);
//    return true;
//  }
//
//  @Override
//  public boolean isOccupied(Vector2d position) {
//    if (super.isOccupied(position))
//      return true;
//    return grassOnField(position);
//  }
//
//  @Override
//  public Object objectAt(Vector2d position) {
//    Object animal = super.objectAt(position);
//    if (animal != null)
//      return animal;
//    for (Grass grass : this.grass) {
//      if (grass.isAt(position))
//        return grass;
//    }
//    return null;
//  }
//
//  @Override
//  public boolean isJungle(Vector2d position) {
//    if (position.precedes(this.jungleRightTop) && position.follows(this.jungleLeftBottom)) {
//      return true;
//    }
//    return false;
//  }
//
//  public boolean positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
//    return super.positionChanged(oldPosition, newPosition, animal);
//  }
//}