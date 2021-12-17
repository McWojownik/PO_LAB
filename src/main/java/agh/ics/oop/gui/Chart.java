package agh.ics.oop.gui;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class Chart {
  private final LineChart<Number, Number> lineChart;
  private final XYChart.Series<Number, Number> animalsSeries= new XYChart.Series<>();
  private final XYChart.Series<Number, Number> grassSeries= new XYChart.Series<>();
  private final XYChart.Series<Number, Number> energySeries= new XYChart.Series<>();
  private final XYChart.Series<Number, Number> lifetimeSeries= new XYChart.Series<>();
  private final XYChart.Series<Number, Number> childrenSeries= new XYChart.Series<>();


  public Chart() {
    NumberAxis xAxis = new NumberAxis();
    xAxis.setLabel("Era");
    NumberAxis yAxis = new NumberAxis();
    yAxis.setLabel("Stats");
    this.lineChart = new LineChart<>(xAxis, yAxis);
    this.lineChart.setAnimated(false);
    this.lineChart.setCreateSymbols(false);
    this.animalsSeries.setName("Animals living");
    this.grassSeries.setName("Grass on map");
    this.energySeries.setName("Average energy");
    this.lifetimeSeries.setName("Average lifetime");
    this.childrenSeries.setName("Average kids");
  }

  public LineChart<Number, Number> getChart() {
    return this.lineChart;
  }

  public void addData(int day, int animalLiving, int grassOnMap, int averageEnergy, int averageLifetime, int averageChildren) {
    // ZASTANOWIC SIE CZY NIE OBLICZAC WARTOSCI W ODPOWIEDNICH MOMENTACH, BO NP SREDNIA LICZBA DZIECI ZYJACYCH ZWIERZAT JEST OBLICZANA
    // JEST PO CALYM DNIU CZYLI GDY JUZ JAKIES ZWIERZETA SA MARTWE
    this.lineChart.getData().clear();
    this.animalsSeries.getData().add(new XYChart.Data<>(day, animalLiving));
    this.grassSeries.getData().add(new XYChart.Data<>(day, grassOnMap));
    this.energySeries.getData().add(new XYChart.Data<>(day, averageEnergy));
    this.lifetimeSeries.getData().add(new XYChart.Data<>(day, averageLifetime));
    this.childrenSeries.getData().add(new XYChart.Data<>(day, averageChildren));
    this.lineChart.getData().addAll(this.animalsSeries, this.grassSeries, this.energySeries, this.lifetimeSeries, this.childrenSeries);
  }
}
