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
import java.util.Arrays;

public class App extends Application {
  private BorderMap borderMap;
  private WrapMap wrapMap;
  private SimulationEngine engine;
  private SimulationEngine engine2;
  private GridVisualizer gridVisualizer;
  private GridVisualizer gridVisualizer2;
  private final GridPane grid = new GridPane();
  private final GridPane grid2 = new GridPane();
  private final Text text = new Text();
  private final Text text2 = new Text();
  private final Chart lineChart = new Chart();
  private final Chart lineChart2 = new Chart();
  private final Text dominantGenotype = new Text("null");
  private final Text dominantGenotype2 = new Text("null");
  private final int spacing = 5;
  private int speed = 100;
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
    SettingsPanel settings = new SettingsPanel();
    Scene settingsScene = new Scene(settings.getSettings());
    primaryStage.setScene(settingsScene);
    primaryStage.show();
    Button startButton = settings.getStartButton();
    startButton.setOnAction((e) -> {
      this.speed = Integer.parseInt(settings.speed.getText());
      this.borderMap = new BorderMap(Integer.parseInt(settings.width.getText()), Integer.parseInt(settings.height.getText()), Integer.parseInt(settings.startEnergy.getText()), Integer.parseInt(settings.moveEnergy.getText()), Integer.parseInt(settings.plantEnergy.getText()), Integer.parseInt(settings.jungleWidth.getText()), Integer.parseInt(settings.jungleHeight.getText()), settings.magicBorderMap.isSelected());
      this.wrapMap = new WrapMap(Integer.parseInt(settings.width.getText()), Integer.parseInt(settings.height.getText()), Integer.parseInt(settings.startEnergy.getText()), Integer.parseInt(settings.moveEnergy.getText()), Integer.parseInt(settings.plantEnergy.getText()), Integer.parseInt(settings.jungleWidth.getText()), Integer.parseInt(settings.jungleHeight.getText()), settings.magicWrapMap.isSelected());
      this.engine = new SimulationEngine(this.borderMap, Integer.parseInt(settings.animalsAtStart.getText()));
      this.engine.addObserver(this);
      this.engine.setDayTimeChange(this.speed);
      this.engine2 = new SimulationEngine(this.wrapMap, Integer.parseInt(settings.animalsAtStart.getText()));
      this.engine2.addObserver(this);
      this.engine2.setDayTimeChange(this.speed);
      try {
        this.gridVisualizer = new GridVisualizer(this.borderMap, this.grid, this, this.engine, 200 / Integer.parseInt(settings.height.getText()));
        this.gridVisualizer2 = new GridVisualizer(this.wrapMap, this.grid2, this, this.engine2, 200 / Integer.parseInt(settings.height.getText()));
        this.gridVisualizer.draw(this.borderMap.getLeftBottom(), this.borderMap.getRightTop());
        this.gridVisualizer2.draw(this.wrapMap.getLeftBottom(), this.wrapMap.getRightTop());
      } catch (FileNotFoundException ex) {
        ex.printStackTrace();
      }
      Button saveToFile = new Button("SAVE");
      saveToFile.setVisible(false);
      saveToFile.setOnAction((e2) -> {
        this.saveToFileClick(this.lineChart);
      });
      Button showDominant = new Button("HIGHLIGHT");
      showDominant.setVisible(false);
      showDominant.setOnAction((e2) -> {
        this.showDominantClick(this.borderMap, this.grid, this.gridVisualizer);
      });
      Button stopStart = new Button("CHANGE STATE");
      stopStart.setOnAction((e2) -> {
        this.stopStartClick(this.engine, this.borderMap, saveToFile, showDominant);
      });
      HBox buttons = new HBox(stopStart, saveToFile, showDominant);
      buttons.setSpacing(this.spacing);
      this.text.setText(String.valueOf(this.borderMap.getMagicRemain()));
      HBox magicCount = new HBox(new Text("MAGIC REMAIN: "), this.text);
      VBox left = new VBox(buttons, magicCount, this.dominantGenotype, this.grid);
      Button saveToFile2 = new Button("SAVE");
      saveToFile2.setVisible(false);
      saveToFile2.setOnAction((e2) -> {
        this.saveToFileClick(this.lineChart2);
      });
      Button showDominant2 = new Button("HIGHLIGHT");
      showDominant2.setVisible(false);
      showDominant2.setOnAction((e2) -> {
        this.showDominantClick(this.wrapMap, this.grid2, this.gridVisualizer2);
      });
      Button stopStart2 = new Button("CHANGE STATE");
      stopStart2.setOnAction((e2) -> {
        this.stopStartClick(this.engine2, this.wrapMap, saveToFile2, showDominant2);
      });
      HBox buttons2 = new HBox(stopStart2, saveToFile2, showDominant2);
      buttons2.setSpacing(this.spacing);
      this.text2.setText(String.valueOf(this.wrapMap.getMagicRemain()));
      HBox magicCount2 = new HBox(new Text("MAGIC REMAIN: "), this.text2);
      VBox right = new VBox(buttons2, magicCount2, this.dominantGenotype2, this.grid2);
      HBox mapsAndCharts = new HBox(left, this.lineChart.getChart());
      HBox mapsAndCharts2 = new HBox(right, this.lineChart2.getChart());
      mapsAndCharts.setSpacing(this.spacing);
      mapsAndCharts2.setSpacing(this.spacing);
      VBox allMapsAndCharts = new VBox(mapsAndCharts, mapsAndCharts2);
      allMapsAndCharts.setSpacing(4 * this.spacing);
      HBox observedGenesBox = new HBox(new Text("Genes: "), this.observedGenes);
      HBox observedDaysBox = new HBox(new Text("Era of death: "), this.observedDays);
      HBox observedKidsBox = new HBox(new Text("Number of kids total: "), this.observedNumberOfKids);
      HBox observedDescendantsBox = new HBox(new Text("Number of descendants alive: "), this.observedNumberOfDescendants);
//        HBox dominantGenotypeBox = new HBox(new Text("Dominant genotype: "), this.dominantGenotype);
//        VBox specificInfo = new VBox(observedGenesBox, observedDaysBox, observedKidsBox, observedDescendantsBox, dominantGenotypeBox);
      VBox specificInfo = new VBox(observedGenesBox, observedDaysBox, observedKidsBox, observedDescendantsBox);
      specificInfo.setSpacing(this.spacing);
      VBox mainBox = new VBox(allMapsAndCharts, specificInfo);
      mainBox.setSpacing(this.spacing);
      Scene worldScene = new Scene(mainBox, 800, 660);
      primaryStage.setScene(worldScene);
      primaryStage.show();
      Thread thread = new Thread(this.engine);
      thread.start();
      Thread thread2 = new Thread(this.engine2);
      thread2.start();
    });
  }

  public void positionChanged(AbstractWorldMap map) {
    Platform.runLater(() -> {
      try {
        if (map instanceof BorderMap)
          refreshAll(map, this.engine, this.grid, this.text, this.dominantGenotype, this.lineChart, this.gridVisualizer);
        else
          refreshAll(map, this.engine2, this.grid2, this.text2, this.dominantGenotype2, this.lineChart2, this.gridVisualizer2);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    });
  }

  private void refreshAll(AbstractWorldMap map, SimulationEngine engine, GridPane grid, Text text, Text dominantGenotype, Chart lineChart, GridVisualizer gridVisualizer) throws FileNotFoundException {
    if (map.getAnimalsList().size() == 0)
      engine.removeObserver(this);
    grid.getChildren().clear();
    text.setText(String.valueOf(map.getMagicRemain()));
    Animal dominant = map.dominantGenotype();
    if (dominant != null)
      dominantGenotype.setText(Arrays.toString(dominant.getGenesArr()));
    else
      dominantGenotype.setText("null");
    this.refreshStatistics(map);
    lineChart.addData(map.getDay(), map.getNumberOfAnimals(), map.getNumberOfGrass(), map.averageEnergy(), map.averageLifetime(), map.averageKids());
    gridVisualizer.draw(map.getLeftBottom(), map.getRightTop());
  }

  private void saveToFileClick(Chart lineChart) {
    FileManager fileManager = new FileManager("data");
    fileManager.writeToFile(lineChart.getSeries());
  }

  private void showDominantClick(AbstractWorldMap map, GridPane grid, GridVisualizer gridVisualizer) {
    if (!map.getAnimalsHightlighted()) {
      if (map.getAnimalsList().size() > 0)
        map.highlightDominant();
    } else
      map.removeHighlightDominant();
    grid.getChildren().clear();
    try {
      gridVisualizer.draw(map.getLeftBottom(), map.getRightTop());
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    }
  }

  private void stopStartClick(SimulationEngine engine, AbstractWorldMap map, Button saveToFile, Button showDominant) {
    engine.changeState();
    boolean visible = !engine.getIsRunning();
    saveToFile.setVisible(visible);
    showDominant.setVisible(visible);
    if (!visible)
      map.removeHighlightDominant();
  }

  private void removeStatisticsObservation() {
    this.borderMap.removeStatisticsObservation();
    this.wrapMap.removeStatisticsObservation();
  }

  private void refreshStatistics(AbstractWorldMap map) {
    if (this.observedAnimal != null && map.getIsObservedAnimalOnMap()) {
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

  public void changeObservedAnimal(Animal animal, AbstractWorldMap map) {
    this.observedGenes.setText(Arrays.toString(animal.getGenesArr()));
    this.removeStatisticsObservation();
    this.observedAnimal = animal;
    animal.observeStatistics();
    this.refreshStatistics(map);
  }
}