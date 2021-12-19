package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

import static java.lang.System.out;

public class App extends Application {
  private BorderMap borderMap;
  private WrapMap wrapMap;
  private SimulationEngine engine;
  private SimulationEngine engine2;
  private GridVisualizer mapVisualizer;
  private GridVisualizer mapVisualizer2;
  private GridPane grid;
  private GridPane grid2;
  private final int speed = 100;
  private final Text text = new Text();
  private final Text text2 = new Text();
  private final Chart lineChart = new Chart();
  private final Chart lineChart2 = new Chart();
  protected Animal observedAnimal = null;
  protected Text observedGenes = new Text("null");
  protected Text observedNumberOfKids = new Text("null");
  protected Text observedNumberOfDescendants = new Text("null");
  protected Text observedDays = new Text("null");

  public App() {
  }

  public void init() {
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    this.grid = new GridPane();
    this.grid2 = new GridPane();
    SettingsPanel settings = new SettingsPanel();
    Scene scene = new Scene(settings.getSettings());
    primaryStage.setScene(scene);
    primaryStage.show();
    Button startButton = settings.getStartButton();
    startButton.setOnAction((e) -> {
      Vector2d leftBottom = new Vector2d(0, 0);
      Vector2d rightTop = new Vector2d(Integer.parseInt(settings.width.getText()) - 1, Integer.parseInt(settings.height.getText()) - 1);
      this.grid.getChildren().clear();
//      this.borderMap = new BorderMap(Integer.parseInt(settings.width.getText()), Integer.parseInt(settings.height.getText()), Integer.parseInt(settings.startEnergy.getText()), Integer.parseInt(settings.moveEnergy.getText()), Integer.parseInt(settings.animalsAtStart.getText()), Integer.parseInt(settings.plantEnergy.getText()), Integer.parseInt(settings.jungleRatio.getText()), settings.magicBorderMap.isSelected());
//      this.wrapMap = new WrapMap(Integer.parseInt(settings.width.getText()), Integer.parseInt(settings.height.getText()), Integer.parseInt(settings.startEnergy.getText()), Integer.parseInt(settings.moveEnergy.getText()), Integer.parseInt(settings.animalsAtStart.getText()), Integer.parseInt(settings.plantEnergy.getText()), Integer.parseInt(settings.jungleRatio.getText()), settings.magicWrapMap.isSelected());
      this.borderMap = new BorderMap(Integer.parseInt(settings.width.getText()), Integer.parseInt(settings.height.getText()), Integer.parseInt(settings.startEnergy.getText()), Integer.parseInt(settings.moveEnergy.getText()), Integer.parseInt(settings.animalsAtStart.getText()), Integer.parseInt(settings.plantEnergy.getText()), Integer.parseInt(settings.jungleWidth.getText()), Integer.parseInt(settings.jungleHeight.getText()), settings.magicBorderMap.isSelected());
      this.wrapMap = new WrapMap(Integer.parseInt(settings.width.getText()), Integer.parseInt(settings.height.getText()), Integer.parseInt(settings.startEnergy.getText()), Integer.parseInt(settings.moveEnergy.getText()), Integer.parseInt(settings.animalsAtStart.getText()), Integer.parseInt(settings.plantEnergy.getText()), Integer.parseInt(settings.jungleWidth.getText()), Integer.parseInt(settings.jungleHeight.getText()), settings.magicWrapMap.isSelected());
      this.mapVisualizer = new GridVisualizer(this.borderMap, this.grid, this);
      this.mapVisualizer2 = new GridVisualizer(this.wrapMap, this.grid2, this);
      try {
        this.engine = new SimulationEngine(this.borderMap, Integer.parseInt(settings.animalsAtStart.getText()));
        this.engine.addObserver(this);
        this.engine.setDayTimeChange(this.speed);
        this.engine2 = new SimulationEngine(this.wrapMap, Integer.parseInt(settings.animalsAtStart.getText()));
        this.engine2.addObserver(this);
        this.engine2.setDayTimeChange(this.speed);
        this.mapVisualizer.draw(leftBottom, rightTop);
        this.mapVisualizer2.draw(leftBottom, rightTop);
        Button stopStart = new Button("CHANGE");
        stopStart.setOnAction((e2) -> {
          this.engine.changeState();
        });
        this.text2.setText(String.valueOf(this.borderMap.getMagicRemain()));
        HBox magicCount = new HBox(new Text("MAGIC REMAIN: "), this.text);
        VBox left = new VBox(stopStart, magicCount, this.grid);
        Button stopStart2 = new Button("CHANGE");
        stopStart2.setOnAction((e2) -> {
          this.engine2.changeState();
        });
        this.text2.setText(String.valueOf(this.wrapMap.getMagicRemain()));
        HBox magicCount2 = new HBox(new Text("MAGIC REMAIN: "), this.text2);
        VBox right = new VBox(stopStart2, magicCount2, this.grid2);
        HBox mapsAndCharts = new HBox(this.lineChart.getChart(), left, right, this.lineChart2.getChart());
        mapsAndCharts.setSpacing(5);
        HBox observedGenesBox = new HBox(new Text("Genes: "), this.observedGenes);
        HBox observedDaysBox = new HBox(new Text("Days survived: "), this.observedDays);
        HBox observedKidsBox = new HBox(new Text("Number of kids: "), this.observedNumberOfKids);
        HBox observedDescendantsBox = new HBox(new Text("Number of descendants: "), this.observedNumberOfDescendants);
        VBox specificInfo = new VBox(observedGenesBox, observedDaysBox, observedKidsBox, observedDescendantsBox);
        specificInfo.setSpacing(5);
        VBox mainBox = new VBox(mapsAndCharts, specificInfo);
        mainBox.setSpacing(5);
        Scene scene2 = new Scene(mainBox);
        primaryStage.setScene(scene2);
        primaryStage.show();
        Thread thread = new Thread(this.engine);
        thread.start();
        Thread thread2 = new Thread(this.engine2);
        thread2.start();
      } catch (FileNotFoundException ex) {
        ex.printStackTrace();
      }
    });
  }

  public void positionChanged(AbstractWorldMap map) {
    Platform.runLater(() -> {
      try {
        if (map instanceof BorderMap) {
          this.grid.getChildren().clear();
          this.text.setText(String.valueOf(map.getMagicRemain()));
          this.refreshStatistics(map);
          this.lineChart.addData(map.getDay(), map.getNumberOfAnimals(), map.getNumberOfGrass(), map.averageEnergy(), map.averageLifetime(), map.averageKids());
          this.mapVisualizer.draw(this.borderMap.getLeftBottom(), this.borderMap.getRightTop());
        }
//      else if (map instanceof WrapMap) {
        else {
          this.grid2.getChildren().clear();
          this.text2.setText(String.valueOf(map.getMagicRemain()));
          this.refreshStatistics(map);
          this.lineChart2.addData(map.getDay(), map.getNumberOfAnimals(), map.getNumberOfGrass(), map.averageEnergy(), map.averageLifetime(), map.averageKids());
          this.mapVisualizer2.draw(this.wrapMap.getLeftBottom(), this.wrapMap.getRightTop());
        }
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    });
  }

  public void removeStatisticsObservation() {
    this.borderMap.removeStatisticsObservation();
    this.wrapMap.removeStatisticsObservation();
  }

  private void refreshStatistics(AbstractWorldMap map) {
    if (this.observedAnimal != null && map.isObservedAnimalOnMap) {
      this.observedNumberOfKids.setText(String.valueOf(this.observedAnimal.getNumberOfKids2()));
      int descendants = map.countAllObservedAnimal();
      if (descendants >= 0)
        this.observedNumberOfDescendants.setText(String.valueOf(descendants));
      else
        this.observedNumberOfDescendants.setText("no descendants");
      if (!this.observedAnimal.isDead())
        this.observedDays.setText("still alive");
      else
        this.observedDays.setText(String.valueOf(this.observedAnimal.getEraDied()));
    }
  }
}