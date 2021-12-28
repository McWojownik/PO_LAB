package agh.ics.oop;

import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
  protected int width;
  protected int height;
  protected int startEnergy;
  protected int moveEnergy;
  protected int plantEnergy;
  protected int jungleWidth;
  protected int jungleHeight;
  protected boolean isMagical;
  protected int magicRemain = 0;
  protected Vector2d leftBottom;
  protected Vector2d rightTop;
  protected Map<Vector2d, Grass> grassHash = new LinkedHashMap<>();
  protected Map<Vector2d, LinkedList<Animal>> animalsHash = new LinkedHashMap<>();
  protected LinkedList<Animal> animalsList = new LinkedList<>();
  protected Vector2d jungleLeftBottom;
  protected Vector2d jungleRightTop;
  protected int day = 0;
  protected int deadAnimalsCount = 0;
  protected int deadAnimalsDays = 0;
  protected boolean animalsHighlighted = false;
  protected boolean isObservedAnimalOnMap = false;
  protected Animal observedAnimal = null;

  public AbstractWorldMap(int width, int height, int startEnergy, int moveEnergy, int plantEnergy, int jungleWidth, int jungleHeight, boolean isMagical) {
    this.width = width;
    this.height = height;
    this.startEnergy = startEnergy;
    this.moveEnergy = moveEnergy;
    this.plantEnergy = plantEnergy;
    this.jungleWidth = jungleWidth;
    this.jungleHeight = jungleHeight;
    this.isMagical = isMagical;
    if (isMagical) this.magicRemain = 3;
    this.leftBottom = new Vector2d(0, 0);
    this.rightTop = new Vector2d(this.width - 1, this.height - 1);
    this.generateJungle();
  }

  public Vector2d getLeftBottom() {
    return this.leftBottom;
  }

  public Vector2d getRightTop() {
    return this.rightTop;
  }

  public Map<Vector2d, LinkedList<Animal>> getAnimalsHash() {
    return this.animalsHash;
  }

  public LinkedList<Animal> getAnimalsList() {
    return this.animalsList;
  }

  public int getMagicRemain() {
    return this.magicRemain;
  }

  public int getDay() {
    return this.day;
  }

  public int getNumberOfAnimals() {
    return this.animalsList.size();
  }

  public int getNumberOfGrass() {
    return this.grassHash.size();
  }

  public boolean getAnimalsHightlighted() {
    return this.animalsHighlighted;
  }

  public boolean getIsObservedAnimalOnMap() {
    return this.isObservedAnimalOnMap;
  }

  public void setObservedAnimal(Animal animal) {
    this.observedAnimal = animal;
  }

  public int averageEnergy() {
    if (this.animalsList.size() == 0) return 0;
    int total = 0;
    for (Animal animal : this.animalsList) {
      total += animal.energy;
    }
    return total / this.animalsList.size();
  }

  public int averageLifetime() {
    if (this.deadAnimalsCount == 0) return 0;
    return this.deadAnimalsDays / this.deadAnimalsCount;
  }

  public int averageKids() {
    if (this.animalsList.size() == 0) return 0;
    int total = 0;
    for (Animal animal : this.animalsList) {
      total += animal.numberOfKidsTotal;
    }
    return total / this.animalsList.size();
  }

  @Override
  public boolean canMoveTo(Vector2d position) {
    return position.precedes(this.rightTop) && position.follows(this.leftBottom);
  }

  @Override
  public void place(Animal animal) {
    Vector2d animalPosition = animal.getPosition();
    this.addAnimal(animal, animalPosition);
    this.animalsList.push(animal);
  }

  @Override
  public boolean isOccupied(Vector2d position) {
    if (this.animalsHash.get(position) != null) return true;
    return this.grassOnField(position);
  }

  @Override
  public Object objectAt(Vector2d position) {
    LinkedList<Animal> list = this.animalsHash.get(position);
    if (list != null) {
      if (list.size() > 0) {
        return list.getFirst();
      } else { // SHOULD NOT HAPPEN
        this.animalsHash.remove(position);
        throw new IllegalArgumentException("Position " + position.toString() + " shouldn't be in hashMap");
      }
    }
    return this.getGrassOnField(position);
  }

  @Override
  public Animal getStrongestAnimalOnField(Vector2d position) {
    LinkedList<Animal> animalsAtPosition = this.animalsHash.get(position);
    if (animalsAtPosition != null) {
      if (animalsAtPosition.size() > 0) {
        Animal animal = animalsAtPosition.getFirst();
        for (int i = 1; i < animalsAtPosition.size(); i++) {
          Animal possiblyStronger = animalsAtPosition.get(i);
          if (animal.energy < possiblyStronger.energy) animal = possiblyStronger;
        }
        return animal;
      } else { // SHOULD NOT HAPPEN
        this.animalsHash.remove(position);
        throw new IllegalArgumentException("Position " + position.toString() + " shouldn't be in hashMap");
      }
    }
    return null;
  }

  @Override
  public String getGrassColorOnField(Vector2d position) {
    if (this.isJungle(position)) return "004500";
    return "f7f774";
  }

  @Override
  public void clearDeadAnimals() {
    LinkedList<Animal> animalsList = this.getAnimalsList();
    for (int i = animalsList.size() - 1; i >= 0; i--) {
      Animal animal = animalsList.get(i);
      if (animal.isDead()) {
        // IF YOU WANT ALIVE KIDS, THEN CHANGE COMMENT STYLE IN THESE LINES: 180-185
//        if (animal.isUnderObservation) {
//          if (animal.mother != null)
//            animal.mother.numberOfKidsObserved--;
//          if (animal.father != null)
//            animal.father.numberOfKidsObserved--;
//        }
        this.deadAnimalsDays += animal.daysAlive;
        this.deadAnimalsCount++;
        animal.setEraDied(this.day);
        animal.removeObserver(this);
        this.deleteAnimal(animal);
      }
    }
  }

  @Override
  public void moveAllAnimals() {
    LinkedList<Animal> animalsList = this.getAnimalsList();
//    System.out.println(animalsList.size());
//    Map<Vector2d, LinkedList<Animal>> animalsHash = super.getAnimalsHash();
//    AtomicInteger total = new AtomicInteger();
//    animalsHash.forEach((k, v) -> total.addAndGet(v.size()));
//    System.out.println(total);
    for (Animal animal : animalsList) {
      MoveDirection nextMove = animal.getNextAnimalMove();
      animal.move(nextMove);
    }
  }

  @Override
  public void allAnimalsEat() {
    Map<Vector2d, LinkedList<Animal>> animalsHash = this.getAnimalsHash();
    animalsHash.forEach((Vector2d vector, LinkedList<Animal> animalsAtPosition) -> {
      if (animalsAtPosition.size() > 0) {
        Grass grass = this.getGrassOnField(vector);
        if (grass != null) {
          Animal animalChoosen = animalsAtPosition.getFirst();
          int count = 1;
          for (int i = 1; i < animalsAtPosition.size(); i++) {
            Animal animal = animalsAtPosition.get(i);
            if (animalChoosen.energy < animal.energy) {
              animalChoosen = animal;
              count = 1;
            } else if (animalChoosen.energy == animal.energy) count++;
          }
          if (count == 1) animalChoosen.energy += this.plantEnergy;
          else {
            for (Animal animal : animalsAtPosition) {
              if (animalChoosen.energy == animal.energy) animal.energy += this.plantEnergy / count;
            }
          }
          this.grassHash.remove(vector);
        }
      }
    });
  }

  @Override
  public void animalsCopulation() {
    Map<Vector2d, LinkedList<Animal>> animalsHash = this.getAnimalsHash();
    animalsHash.forEach((Vector2d vector, LinkedList<Animal> animalsAtPosition) -> {
      if (animalsAtPosition.size() > 1) {
        int count = 0;
        for (Animal animal : animalsAtPosition) {
          if (animal.canCopulate()) count++;
          if (count > 1) break;
        }
        if (count > 1) {
          Animal mother = animalsAtPosition.getFirst();
          for (int i = 1; i < animalsAtPosition.size(); i++) {
            Animal animal = animalsAtPosition.get(i);
            if (mother.energy < animal.energy) mother = animal;
          }
          Animal father = animalsAtPosition.getFirst();
          if (mother.equals(father)) father = animalsAtPosition.get(1);
          for (int i = 1; i < animalsAtPosition.size(); i++) {
            Animal animal = animalsAtPosition.get(i);
            if (father.energy < animal.energy && !animal.equals(mother)) father = animal;
          }
          Genes genes = new Genes(mother, father);
          int motherEnergy = mother.energy / 4;
          int fatherEnergy = father.energy / 4;
          mother.energy -= motherEnergy;
          father.energy -= fatherEnergy;
          mother.numberOfKidsTotal++;
          father.numberOfKidsTotal++;
          int childEnergy = motherEnergy + fatherEnergy;
          Animal child = new Animal(this, vector, childEnergy, genes);
          if (mother.isUnderObservation || father.isUnderObservation) {
            child.isUnderObservation = true;
            if (this.observedAnimal != null)
              this.observedAnimal.numberOfDescendantsObserved++;
            if (mother.isUnderObservation) mother.numberOfKidsObserved++;
            if (father.isUnderObservation) father.numberOfKidsObserved++;
          }
          child.mother = mother;
          child.father = father;
//          System.out.println(mother.getPosition().toString() + " " + father.getPosition().toString() + " COPULATE ");
//          System.out.println(animal.getPosition().toString() + " " + child.getPosition() + " " + child.energy + " CHILD ");
          this.place(child);
        }
      }
    });
  }

  @Override
  public void generateDayGrass() {
    this.generateJungleGrass();
    this.generateOutJungleGrass();
  }

  public void addAnimal(Animal animal, Vector2d newPosition) {
    LinkedList<Animal> list = this.animalsHash.get(newPosition);
    if (list == null) {
      LinkedList<Animal> animalLinkedList = new LinkedList<>();
      animalLinkedList.add(animal);
      this.animalsHash.put(newPosition, animalLinkedList);
    } else list.add(animal);
  }

  public void removeAnimalFromHashMap(Animal animal) {
    Vector2d animalPosition = animal.getPosition();
    LinkedList<Animal> list = this.animalsHash.get(animalPosition);
    if (list != null) {
      if (list.size() > 0) {
        list.remove(animal);
        if (list.size() == 0) this.animalsHash.remove(animalPosition);
      } else this.animalsHash.remove(animalPosition);
    }
  }

  public void deleteAnimal(Animal animal) {
    this.animalsList.remove(animal);
    this.removeAnimalFromHashMap(animal);
  }

  public boolean isJungle(Vector2d position) {
    return position.precedes(this.jungleRightTop) && position.follows(this.jungleLeftBottom);
  }

  private boolean grassOnField(Vector2d position) {
    return this.grassHash.get(position) != null;
  }

  private Grass getGrassOnField(Vector2d position) {
    return this.grassHash.get(position);
  }

  public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
    this.removeAnimalFromHashMap(animal);
    this.addAnimal(animal, newPosition);
  }

  private void generateJungle() {
    int middleWidth = this.width / 2;
    int middleHeight = this.height / 2;
    int middleJungleWidth = this.jungleWidth / 2;
    int middleJungleHeight = this.jungleHeight / 2;
    int xLeft = middleWidth - middleJungleWidth;
    int yLeft = middleHeight - middleJungleHeight;
    int xRight = xLeft + this.jungleWidth - 1;
    int yRight = yLeft + this.jungleHeight - 1;
    this.jungleLeftBottom = new Vector2d(xLeft, yLeft);
    this.jungleRightTop = new Vector2d(xRight, yRight);
  }

  private void generateJungleGrass() {
    int numberOfAttemps = (this.jungleRightTop.x - this.jungleLeftBottom.x + 1) * (this.jungleRightTop.y - this.jungleLeftBottom.y + 1);
    for (int i = 0; i < numberOfAttemps; i++) {
      int x = (int) ((Math.random() * (this.jungleRightTop.x - this.jungleLeftBottom.x + 1)) + this.jungleLeftBottom.x);
      int y = (int) ((Math.random() * (this.jungleRightTop.y - this.jungleLeftBottom.y + 1)) + this.jungleLeftBottom.y);
      Vector2d vector = new Vector2d(x, y);
      if (!this.isOccupied(vector)) {
        Grass grass = new Grass(vector);
        this.grassHash.put(vector, grass);
        break;
      }
    }
  }

  private void generateOutJungleGrass() {
    int numberOfAttemps = (this.rightTop.x + 1) * (this.rightTop.y + 1);
    for (int i = 0; i < numberOfAttemps; i++) {
      int x = (int) ((Math.random() * (this.rightTop.x + 1)));
      int y = (int) ((Math.random() * (this.rightTop.y + 1)));
      Vector2d vector = new Vector2d(x, y);
      if (!this.isJungle(vector) && !this.isOccupied(vector)) {
        Grass grass = new Grass(vector);
        this.grassHash.put(vector, grass);
        break;
      }
    }
  }

  public void createMagicalAnimals(int numberOfAnimals) {
    if (this.isMagical && this.magicRemain > 0) {
      int total = this.animalsList.size();
      if (total <= numberOfAnimals) {
        if (total > 0) {
          for (int i = 0; i < numberOfAnimals; i++) {
            Vector2d vector = this.emptyPosition();
            if (vector != null) {
              int index = (int) (Math.random() * total);
              Animal animal = this.animalsList.get(index);
              int[] animalGenes = animal.getGenesArr();
              Genes genes = new Genes(animalGenes);
              Animal copy = new Animal(this, vector, this.startEnergy, genes);
              this.place(copy);
            }
          }
        } else {
          for (int i = 0; i < numberOfAnimals; i++) {
            Vector2d vector = this.emptyPosition();
            if (vector != null) {
              Genes genes = new Genes();
              Animal animal = new Animal(this, vector, this.startEnergy, genes);
              this.place(animal);
            }
          }
        }
        this.magicRemain--;
      }
    }
  }

  public Vector2d emptyPosition() {
    int numberOfTries = this.width * this.height;
    while (numberOfTries > 0) {
      int x = (int) (Math.random() * this.width);
      int y = (int) (Math.random() * this.height);
      Vector2d vector = new Vector2d(x, y);
      if (this.animalsHash.get(vector) == null) return vector;
      numberOfTries--;
    }
    return null;
  }

  public void nextDay() {
    this.day++;
    for (Animal animal : this.animalsList) {
      animal.liveAnotherDay();
    }
  }

  public void removeStatisticsObservation() {
    this.isObservedAnimalOnMap = false;
    LinkedList<Animal> animalsList = this.getAnimalsList();
    for (Animal animal : animalsList) {
      animal.isUnderObservation = false;
      animal.numberOfKidsObserved = 0;
    }
  }

  public int countAllObservedAnimal() {
    LinkedList<Animal> animalsList = this.getAnimalsList();
    int count = 0;
    for (Animal animal : animalsList) {
      if (animal.isUnderObservation) count++;
    }
    return count - 1;
  }

  public Animal dominantGenotype() {
    LinkedList<Animal> animalsList = this.getAnimalsList();
    if (animalsList.size() == 0) return null;
    try {
      Animal animal = animalsList.getFirst();
      int best = -1;
      for (Animal animalToCompare : animalsList) {
        int count = 0;
        for (Animal nextAnimal : animalsList) {
          if (animalToCompare.checkIfIdenticalGenes(nextAnimal)) count++;
        }
        if (count > best) {
          best = count;
          animal = animalToCompare;
        }
      }
      return animal;
    } catch (ConcurrentModificationException ex) {
      throw new ConcurrentModificationException("Something wrong while searching for dominant genotype");
    }
  }

  public void highlightDominant() {
    this.animalsHighlighted = true;
    int[] genes = this.dominantGenotype().getGenesArr();
    if (genes != null) {
      LinkedList<Animal> animalsList = this.getAnimalsList();
      for (Animal animal : animalsList) {
        int[] animalGens = animal.getGenesArr();
        boolean highlight = true;
        for (int i = 0; i < 32; i++) {
          if (genes[i] != animalGens[i]) {
            highlight = false;
            break;
          }
        }
        if (highlight) animal.setHighlight(true);
      }
    }
  }

  public void removeHighlightDominant() {
    this.animalsHighlighted = false;
    LinkedList<Animal> animalsList = this.getAnimalsList();
    for (Animal animal : animalsList) {
      animal.setHighlight(false);
    }
  }

  public boolean checkIfHighlightedOnField(Vector2d position) {
    Map<Vector2d, LinkedList<Animal>> animalsHash = this.getAnimalsHash();
    LinkedList<Animal> animals = animalsHash.get(position);
    for (Animal animal : animals) {
      if (animal.getHighlight()) return true;
    }
    return false;
  }
}