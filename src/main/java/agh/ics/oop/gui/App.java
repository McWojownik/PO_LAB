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

import static java.lang.System.out;

public class App extends Application {
  private BorderMap borderMap;
  private WrapMap wrapMap;
  private SimulationEngine engine;
  private SimulationEngine engine2;
  private GridVisualizer gridVisualizer;
  private GridVisualizer gridVisualizer2;
  private GridPane grid;
  private GridPane grid2;
  private int speed = 100;
  private final Text text = new Text();
  private final Text text2 = new Text();
  private final Chart lineChart = new Chart();
  private final Chart lineChart2 = new Chart();
  protected Animal observedAnimal = null;
  protected Text observedGenes = new Text("null");
  protected Text observedNumberOfKids = new Text("null");
  protected Text observedNumberOfDescendants = new Text("null");
  protected Text observedDays = new Text("null");
  protected Text dominantGenotype = new Text("null");
  protected Text dominantGenotype2 = new Text("null");

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
      try {
        this.speed = Integer.parseInt(settings.speed.getText());
//        Vector2d leftBottom = new Vector2d(0, 0);
//        Vector2d rightTop = new Vector2d(Integer.parseInt(settings.width.getText()) - 1, Integer.parseInt(settings.height.getText()) - 1);
        this.grid.getChildren().clear();
//      this.borderMap = new BorderMap(Integer.parseInt(settings.width.getText()), Integer.parseInt(settings.height.getText()), Integer.parseInt(settings.startEnergy.getText()), Integer.parseInt(settings.moveEnergy.getText()), Integer.parseInt(settings.animalsAtStart.getText()), Integer.parseInt(settings.plantEnergy.getText()), Integer.parseInt(settings.jungleRatio.getText()), settings.magicBorderMap.isSelected());
//      this.wrapMap = new WrapMap(Integer.parseInt(settings.width.getText()), Integer.parseInt(settings.height.getText()), Integer.parseInt(settings.startEnergy.getText()), Integer.parseInt(settings.moveEnergy.getText()), Integer.parseInt(settings.animalsAtStart.getText()), Integer.parseInt(settings.plantEnergy.getText()), Integer.parseInt(settings.jungleRatio.getText()), settings.magicWrapMap.isSelected());
        this.borderMap = new BorderMap(Integer.parseInt(settings.width.getText()), Integer.parseInt(settings.height.getText()), Integer.parseInt(settings.startEnergy.getText()), Integer.parseInt(settings.moveEnergy.getText()), Integer.parseInt(settings.animalsAtStart.getText()), Integer.parseInt(settings.plantEnergy.getText()), Integer.parseInt(settings.jungleWidth.getText()), Integer.parseInt(settings.jungleHeight.getText()), settings.magicBorderMap.isSelected());
        this.wrapMap = new WrapMap(Integer.parseInt(settings.width.getText()), Integer.parseInt(settings.height.getText()), Integer.parseInt(settings.startEnergy.getText()), Integer.parseInt(settings.moveEnergy.getText()), Integer.parseInt(settings.animalsAtStart.getText()), Integer.parseInt(settings.plantEnergy.getText()), Integer.parseInt(settings.jungleWidth.getText()), Integer.parseInt(settings.jungleHeight.getText()), settings.magicWrapMap.isSelected());
        this.engine = new SimulationEngine(this.borderMap, Integer.parseInt(settings.animalsAtStart.getText()));
        this.engine.addObserver(this);
        this.engine.setDayTimeChange(this.speed);
        this.engine2 = new SimulationEngine(this.wrapMap, Integer.parseInt(settings.animalsAtStart.getText()));
        this.engine2.addObserver(this);
        this.engine2.setDayTimeChange(this.speed);
        this.gridVisualizer = new GridVisualizer(this.borderMap, this.grid, this, this.engine, 200 / Integer.parseInt(settings.height.getText()));
        this.gridVisualizer2 = new GridVisualizer(this.wrapMap, this.grid2, this, this.engine2, 200 / Integer.parseInt(settings.height.getText()));
        this.gridVisualizer.draw(this.borderMap.getLeftBottom(), this.borderMap.getRightTop());
        this.gridVisualizer2.draw(this.wrapMap.getLeftBottom(), this.wrapMap.getRightTop());
        Button stopStart = new Button("CHANGE STATE");
        Button saveToFile = new Button("SAVE");
        saveToFile.setVisible(false);
        saveToFile.setOnAction((e2) -> {
          FileManager fileManager = new FileManager("data");
          fileManager.writeToFile(this.lineChart.getSeries());
        });
        Button showDominant = new Button("HIGHLIGHT");
        showDominant.setVisible(false);
        showDominant.setOnAction((e2) -> {
          if (!this.borderMap.getAnimalsHightlighted()) {
            if (this.borderMap.getAnimalsList().size() > 0)
              this.borderMap.highlightDominant();
          } else
            this.borderMap.removeHighlightDominant();
          this.grid.getChildren().clear();
          try {
            this.gridVisualizer.draw(this.borderMap.getLeftBottom(), this.borderMap.getRightTop());
          } catch (FileNotFoundException ex) {
            ex.printStackTrace();
          }
        });
        stopStart.setOnAction((e2) -> {
          this.engine.changeState();
          if (!this.engine.getIsRunning()) {
            saveToFile.setVisible(true);
            showDominant.setVisible(true);
          } else {
            saveToFile.setVisible(false);
            showDominant.setVisible(false);
            this.borderMap.removeHighlightDominant();
          }
        });
        HBox buttons = new HBox(stopStart, saveToFile, showDominant);
        buttons.setSpacing(5);
        this.text2.setText(String.valueOf(this.borderMap.getMagicRemain()));
        HBox magicCount = new HBox(new Text("MAGIC REMAIN: "), this.text);
        VBox left = new VBox(buttons, magicCount, this.dominantGenotype, this.grid);
        Button stopStart2 = new Button("CHANGE STATE");
        Button saveToFile2 = new Button("SAVE");
        saveToFile2.setVisible(false);
        saveToFile2.setOnAction((e2) -> {
          FileManager fileManager = new FileManager("data");
          fileManager.writeToFile(this.lineChart2.getSeries());
        });
        Button showDominant2 = new Button("HIGHLIGHT");
        showDominant2.setVisible(false);
        showDominant2.setOnAction((e2) -> {
          if (!this.wrapMap.getAnimalsHightlighted()) {
            if (this.wrapMap.getAnimalsList().size() > 0)
              this.wrapMap.highlightDominant();
          } else
            this.wrapMap.removeHighlightDominant();
          this.grid2.getChildren().clear();
          try {
            this.gridVisualizer2.draw(this.wrapMap.getLeftBottom(), this.wrapMap.getRightTop());
          } catch (FileNotFoundException ex) {
            ex.printStackTrace();
          }
        });
        stopStart2.setOnAction((e2) -> {
          this.engine2.changeState();
          if (!this.engine2.getIsRunning()) {
            saveToFile2.setVisible(true);
            showDominant2.setVisible(true);
          } else {
            saveToFile2.setVisible(false);
            showDominant2.setVisible(false);
            this.wrapMap.removeHighlightDominant();
          }
//          if (!this.engine2.getIsRunning()) {
//            this.wrapMap.highlightDominant();
//            try {
//              this.gridVisualizer2.draw(this.wrapMap.getLeftBottom(), this.wrapMap.getRightTop());
//            } catch (FileNotFoundException ex) {
//              ex.printStackTrace();
//            }
//          } else {
//            this.wrapMap.removeHighlightDominant();
//          }
        });
        HBox buttons2 = new HBox(stopStart2, saveToFile2, showDominant2);
        buttons2.setSpacing(5);
        this.text2.setText(String.valueOf(this.wrapMap.getMagicRemain()));
        HBox magicCount2 = new HBox(new Text("MAGIC REMAIN: "), this.text2);
        VBox right = new VBox(buttons2, magicCount2, this.dominantGenotype2, this.grid2);
        HBox mapsAndCharts = new HBox(left, this.lineChart.getChart());
        HBox mapsAndCharts2 = new HBox(right, this.lineChart2.getChart());
        mapsAndCharts.setSpacing(5);
        mapsAndCharts2.setSpacing(5);
        VBox allMapsAndCharts = new VBox(mapsAndCharts, mapsAndCharts2);
        allMapsAndCharts.setSpacing(20);
        HBox observedGenesBox = new HBox(new Text("Genes: "), this.observedGenes);
        HBox observedDaysBox = new HBox(new Text("Era of death: "), this.observedDays);
        HBox observedKidsBox = new HBox(new Text("Number of kids total: "), this.observedNumberOfKids);
        HBox observedDescendantsBox = new HBox(new Text("Number of descendants alive: "), this.observedNumberOfDescendants);
//        HBox dominantGenotypeBox = new HBox(new Text("Dominant genotype: "), this.dominantGenotype);
//        VBox specificInfo = new VBox(observedGenesBox, observedDaysBox, observedKidsBox, observedDescendantsBox, dominantGenotypeBox);
        VBox specificInfo = new VBox(observedGenesBox, observedDaysBox, observedKidsBox, observedDescendantsBox);
        specificInfo.setSpacing(5);
        VBox mainBox = new VBox(allMapsAndCharts, specificInfo);
        mainBox.setSpacing(5);
        Scene scene2 = new Scene(mainBox, 800, 660);
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
          if (map.getAnimalsList().size() == 0)
            this.engine.removeObserver(this);
          this.grid.getChildren().clear();
          this.text.setText(String.valueOf(map.getMagicRemain()));
          Animal dominant = map.dominantGenotype();
          if (dominant != null)
            this.dominantGenotype.setText(Arrays.toString(dominant.getGenesArr()));
          else
            this.dominantGenotype.setText("null");
          this.refreshStatistics(map);
          this.lineChart.addData(map.getDay(), map.getNumberOfAnimals(), map.getNumberOfGrass(), map.averageEnergy(), map.averageLifetime(), map.averageKids());
          this.gridVisualizer.draw(this.borderMap.getLeftBottom(), this.borderMap.getRightTop());
        } else {
          if (map.getAnimalsList().size() == 0)
            this.engine2.removeObserver(this);
          this.grid2.getChildren().clear();
          this.text2.setText(String.valueOf(map.getMagicRemain()));
          Animal dominant = map.dominantGenotype();
          if (dominant != null)
            this.dominantGenotype2.setText(Arrays.toString(dominant.getGenesArr()));
          else
            this.dominantGenotype2.setText("null");
          this.refreshStatistics(map);
          this.lineChart2.addData(map.getDay(), map.getNumberOfAnimals(), map.getNumberOfGrass(), map.averageEnergy(), map.averageLifetime(), map.averageKids());
          this.gridVisualizer2.draw(this.wrapMap.getLeftBottom(), this.wrapMap.getRightTop());
        }
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    });
  }

  private void removeStatisticsObservation() {
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

  public void changeObservedAnimal(Animal animal, AbstractWorldMap map) {
    this.observedGenes.setText(Arrays.toString(animal.getGenesArr()));
    this.removeStatisticsObservation();
//    this.map.removeStatisticsObservation();
    this.observedAnimal = animal;
    animal.observeStatistics();
    this.refreshStatistics(map);
  }
}