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
      for (int i = 0; i < series[0].getData().size(); i++) {
        StringBuilder str = new StringBuilder();
        if (i != 0)
          str.append("\n");
        str.append(i + 1);
        for (XYChart.Series<Number, Number> data : series) {
          str.append(",").append(data.getData().get(i).getYValue());
        }
        writer.append(String.valueOf(str));
      }
      writer.close();
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}
