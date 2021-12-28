package agh.ics.oop;

import javafx.scene.chart.XYChart;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
  private final String fileName;

  public FileManager(String fileName) {
    this.fileName = fileName;
    try {
      File myObj = new File(fileName + ".csv");
      if (myObj.createNewFile()) {
        System.out.println("File created: " + myObj.getName());
      } else {
        System.out.println("File already exists.");
        this.clearContent();
      }
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  private void clearContent() throws IOException {
    new FileWriter(this.fileName + ".csv", false).close();
  }

  public void writeToFile(XYChart.Series<Number, Number>[] series) {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(this.fileName + ".csv", true));
      int[] tab = new int[series.length];
      int length = series[0].getData().size();
      for (int i = 0; i < length; i++) {
        StringBuilder str = new StringBuilder();
        if (i != 0)
          str.append("\n");
        str.append(i + 1);
        int index = 0;
        for (XYChart.Series<Number, Number> data : series) {
          str.append(",").append(data.getData().get(i).getYValue());
          tab[index] += data.getData().get(i).getYValue().intValue();
          index++;
        }
        writer.append(str);
      }
      StringBuilder str = new StringBuilder();
      str.append("\n").append("AVG");
      for (int number : tab)
        str.append(",").append(Math.round((double) number / length));
      writer.append(str);
      writer.close();
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}
