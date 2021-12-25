package agh.ics.oop;

import javafx.scene.chart.XYChart;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
  public FileManager(String fileName) {
    try {
      File myObj = new File(fileName + ".csv");
      if (myObj.createNewFile()) {
        System.out.println("File created: " + myObj.getName());
      } else {
        System.out.println("File already exists.");
        this.clearContent(fileName);
      }
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  private void clearContent(String fileName) throws IOException {
    new FileWriter(fileName + ".csv", false).close();
  }

  public void writeToFile(XYChart.Series<Number, Number>[] series) {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter("data.csv", true));
      for (int i = 0; i < series[0].getData().size(); i++) {
        StringBuilder str = new StringBuilder();
        str.append("\n").append(i + 1);
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
