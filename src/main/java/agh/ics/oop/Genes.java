package agh.ics.oop;

import java.util.Arrays;

public class Genes {
  private final int[] animalGenes;

  public Genes() {
    this.animalGenes = this.randomGenes();
  }

  public Genes(int[] copiedGenes) {
    this.animalGenes = this.copyGenes(copiedGenes);
  }

  public Genes(Animal mother, Animal father) {
    this.animalGenes = this.parentGenes(mother, father);
  }

  public int[] getAnimalGenes() {
    return this.animalGenes;
  }

  private int[] randomGenes() {
    int[] genes = new int[32];
    for (int i = 0; i < 32; i++) {
      genes[i] = (int) (Math.random() * 8);
    }
    Arrays.sort(genes);
    return genes;
  }

  private int[] copyGenes(int[] copiedGenes) {
    int[] genes = new int[32];
    for (int i = 0; i < 32; i++) {
      genes[i] = copiedGenes[i];
    }
    Arrays.sort(genes);
    return genes;
  }

  private int[] parentGenes(Animal mother, Animal father) {
    Animal stronger = mother;
    Animal weaker = father;
    if (mother.energy < father.energy) {
      stronger = father;
      weaker = mother;
    }
    double percentage = (double) (stronger.energy) / ((double) (father.energy) + (double) (mother.energy));
    double strongerAmountDouble = 32 * percentage;
    int strongerAmountInt = (int) strongerAmountDouble;
    int strongerLeft = (int) (Math.random() * 2); // 0 - SILNIEJSZY DAJE Z PRAWEJ, 1 - SILNIEJSZY DAJE Z LEWEJ
    int[] strongerGens = stronger.getGenesArr();
    int[] weakerGens = weaker.getGenesArr();
    int[] genes = new int[32];
    if (strongerLeft == 1) {
      for (int i = 0; i < 32; i++) {
        if (i < strongerAmountInt) {
          genes[i] = strongerGens[i];
        } else {
          genes[i] = weakerGens[i];
        }
      }
    } else {
      for (int i = 0; i < 32; i++) {
        if (i < 32 - strongerAmountInt) {
          genes[i] = weakerGens[i];
        } else {
          genes[i] = strongerGens[i];
        }
      }
    }
    Arrays.sort(genes);
    return genes;
  }

  public boolean checkIfIdenticalGenes(int[] genes){
    for (int i = 0; i < 32; i++) {
      if(this.animalGenes[i]!=genes[i]){
        return false;
      }
    }
    return true;
  }
}
