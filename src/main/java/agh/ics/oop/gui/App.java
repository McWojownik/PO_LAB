package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Objects;

public class App extends Application {
  private final OptionsParser parser = new OptionsParser();
  private BorderMap borderMap;
  private WrapMap wrapMap;
  private SimulationEngine engineBorder;
  private SimulationEngine engineWrap;
  private GridVisualizer gridVisualizerBorder;
  private GridVisualizer gridVisualizerWrap;
  private final GridPane gridBorder = new GridPane();
  private final GridPane gridWrap = new GridPane();
  private final Text magicRemainsBorder = new Text();
  private final Text magicRemainsWrap = new Text();
  private final Chart lineChartBorder = new Chart();
  private final Chart lineChartWrap = new Chart();
  private final Text dominantGenotypeBorder = new Text("null");
  private final Text dominantGenotypeWrap = new Text("null");
  private final int spacing = 5;
  private int speed = 100;
  protected Animal observedAnimal = null;
  protected Text observedGenes = new Text("null");
  protected Text observedNumberOfKids = new Text("null");
  protected Text observedNumberOfDescendants = new Text("null");
  protected Text observedDays = new Text("null");

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setOnCloseRequest(windowEvent -> {
      Platform.exit();
      System.exit(0);
    });
    SettingsPanel settings = new SettingsPanel();
    Scene settingsScene = new Scene(settings.getSettings());
    primaryStage.setTitle("Settings");
    primaryStage.setScene(settingsScene);
    primaryStage.show();
    this.setScreenInTheMiddle(primaryStage);
    Button startButton = settings.getStartButton();
    startButton.setOnAction((e) -> {
      int width = this.parser.getInt(settings.width.getText());
      int height = this.parser.getInt(settings.height.getText());
      int startEnergy = this.parser.getInt(settings.startEnergy.getText());
      int moveEnergy = this.parser.getInt(settings.moveEnergy.getText());
      int animalsAtStart = this.parser.getInt(settings.animalsAtStart.getText());
      int plantEnergy = this.parser.getInt(settings.plantEnergy.getText());
      int jungleWidth = this.parser.getInt(settings.jungleWidth.getText());
      int jungleHeight = this.parser.getInt(settings.jungleHeight.getText());
      this.speed = this.parser.getInt(settings.speed.getText());
      boolean magicBorderMap = settings.magicBorderMap.isSelected();
      boolean magicWrapMap = settings.magicWrapMap.isSelected();
      this.borderMap = new BorderMap(width, height, startEnergy, moveEnergy, plantEnergy, jungleWidth, jungleHeight, magicBorderMap);
      this.wrapMap = new WrapMap(width, height, startEnergy, moveEnergy, plantEnergy, jungleWidth, jungleHeight, magicWrapMap);
      this.engineBorder = new SimulationEngine(this.borderMap, animalsAtStart);
      this.engineBorder.addObserver(this);
      this.engineBorder.setDayTimeChange(this.speed);
      this.engineWrap = new SimulationEngine(this.wrapMap, animalsAtStart);
      this.engineWrap.addObserver(this);
      this.engineWrap.setDayTimeChange(this.speed);
      lineChartBorder.addData(this.borderMap.getDay(), this.borderMap.getNumberOfAnimals(), this.borderMap.getNumberOfGrass(), this.borderMap.averageEnergy(), this.borderMap.averageLifetime(), this.borderMap.averageKids());
      lineChartWrap.addData(this.wrapMap.getDay(), this.wrapMap.getNumberOfAnimals(), this.wrapMap.getNumberOfGrass(), this.wrapMap.averageEnergy(), this.wrapMap.averageLifetime(), this.wrapMap.averageKids());

      // BORDER MAP ELEMENTS
      Button showDominantBorder = new Button("HIGHLIGHT");
      showDominantBorder.setVisible(false);
      showDominantBorder.setOnAction((e2) -> {
        this.showDominantClick(this.borderMap, this.gridBorder, this.gridVisualizerBorder);
      });
      TextField fileNameTextBorder = new TextField();
      fileNameTextBorder.setPromptText("Enter File Name");
      fileNameTextBorder.setVisible(false);
      Button saveToFileBorder = new Button("SAVE");
      saveToFileBorder.setVisible(false);
      saveToFileBorder.setOnAction((e2) -> {
        this.saveToFileClick(this.lineChartBorder, fileNameTextBorder);
      });
      Button stopStartBorder = new Button("CHANGE STATE");
      stopStartBorder.setOnAction((e2) -> {
        this.stopStartClick(this.engineBorder, this.borderMap, saveToFileBorder, showDominantBorder, fileNameTextBorder);
      });
      HBox buttons = new HBox(stopStartBorder, showDominantBorder, fileNameTextBorder, saveToFileBorder);
      buttons.setSpacing(this.spacing);
      this.magicRemainsBorder.setText(String.valueOf(this.borderMap.getMagicRemain()));
      HBox magicCount = new HBox(new Text("MAGIC REMAIN: "), this.magicRemainsBorder);
      VBox leftInfo = new VBox(buttons, magicCount, this.dominantGenotypeBorder);

      // BORDER MAP ELEMENTS
      Button showDominantWrap = new Button("HIGHLIGHT");
      showDominantWrap.setVisible(false);
      showDominantWrap.setOnAction((e2) -> {
        this.showDominantClick(this.wrapMap, this.gridWrap, this.gridVisualizerWrap);
      });
      TextField fileNameTextWrap = new TextField();
      fileNameTextWrap.setPromptText("Enter File Name");
      fileNameTextWrap.setVisible(false);
      Button saveToFileWrap = new Button("SAVE");
      saveToFileWrap.setVisible(false);
      saveToFileWrap.setOnAction((e2) -> {
        this.saveToFileClick(this.lineChartWrap, fileNameTextWrap);
      });
      Button stopStart2 = new Button("CHANGE STATE");
      stopStart2.setOnAction((e2) -> {
        this.stopStartClick(this.engineWrap, this.wrapMap, saveToFileWrap, showDominantWrap, fileNameTextWrap);
      });
      HBox buttons2 = new HBox(stopStart2, showDominantWrap, fileNameTextWrap, saveToFileWrap);
      buttons2.setSpacing(this.spacing);
      this.magicRemainsWrap.setText(String.valueOf(this.wrapMap.getMagicRemain()));
      HBox magicCount2 = new HBox(new Text("MAGIC REMAIN: "), this.magicRemainsWrap);
      VBox rightInfo = new VBox(buttons2, magicCount2, this.dominantGenotypeWrap);

      try {
        this.gridVisualizerBorder = new GridVisualizer(this.borderMap, this.gridBorder, this, this.engineBorder, (250-(int) (leftInfo.getHeight())) / height);
        this.gridVisualizerWrap = new GridVisualizer(this.wrapMap, this.gridWrap, this, this.engineWrap, (250-(int) (rightInfo.getHeight())) / height);
        this.gridVisualizerBorder.draw(this.borderMap.getLeftBottom(), this.borderMap.getRightTop());
        this.gridVisualizerWrap.draw(this.wrapMap.getLeftBottom(), this.wrapMap.getRightTop());
      } catch (FileNotFoundException ex) {
        ex.printStackTrace();
      }
      VBox left = new VBox(leftInfo, this.gridBorder);
      VBox right = new VBox(rightInfo, this.gridWrap);
      HBox mapsAndCharts = new HBox(left, this.lineChartBorder.getChart());
      HBox mapsAndCharts2 = new HBox(right, this.lineChartWrap.getChart());
      mapsAndCharts.setSpacing(this.spacing);
      mapsAndCharts2.setSpacing(this.spacing);
      VBox allMapsAndCharts = new VBox(mapsAndCharts, mapsAndCharts2);
      allMapsAndCharts.setSpacing(4 * this.spacing);
      HBox observedAnimalInfo = new HBox(new Text("OBSERVED ANIMAL INFO (TO BOTH MAPS): "));
      HBox observedGenesBox = new HBox(new Text("Genes: "), this.observedGenes);
      HBox observedDaysBox = new HBox(new Text("Era of death: "), this.observedDays);
      HBox observedKidsBox = new HBox(new Text("Number of kids alive: "), this.observedNumberOfKids);
      HBox observedDescendantsBox = new HBox(new Text("Number of descendants alive: "), this.observedNumberOfDescendants);
      VBox specificInfo = new VBox(observedAnimalInfo, observedGenesBox, observedDaysBox, observedKidsBox, observedDescendantsBox);
      specificInfo.setSpacing(this.spacing);
      VBox mainVBox = new VBox(allMapsAndCharts, specificInfo);
      mainVBox.setSpacing(4 * this.spacing);
      mainVBox.setAlignment(Pos.CENTER);
      HBox mainHBox = new HBox(mainVBox);
      mainHBox.setAlignment(Pos.CENTER);
      mainHBox.setPadding(new Insets(10, 10, 10, 10));
      Scene worldScene = new Scene(mainHBox);
      primaryStage.setScene(worldScene);
      this.setScreenInTheMiddle(primaryStage);
      primaryStage.setTitle("Darwin World");
      Thread thread = new Thread(this.engineBorder);
      thread.start();
      Thread thread2 = new Thread(this.engineWrap);
      thread2.start();
    });
  }

  public void positionChanged(AbstractWorldMap map) {
    Platform.runLater(() -> {
      try {
        if (map instanceof BorderMap)
          refreshAll(map, this.engineBorder, this.gridBorder, this.magicRemainsBorder, this.dominantGenotypeBorder, this.lineChartBorder, this.gridVisualizerBorder);
        else
          refreshAll(map, this.engineWrap, this.gridWrap, this.magicRemainsWrap, this.dominantGenotypeWrap, this.lineChartWrap, this.gridVisualizerWrap);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    });
  }

  private void setScreenInTheMiddle(Stage stage){
    Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
    stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
    stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
  }

  private void refreshAll(AbstractWorldMap map, SimulationEngine engine, GridPane grid, Text magicRemains, Text dominantGenotype, Chart lineChart, GridVisualizer gridVisualizer) throws FileNotFoundException {
    if (map.getAnimalsList().size() == 0)
      engine.removeObserver(this);
    grid.getChildren().clear();
    magicRemains.setText(String.valueOf(map.getMagicRemain()));
    Animal dominant = map.dominantGenotype();
    if (dominant != null)
      dominantGenotype.setText(Arrays.toString(dominant.getGenesArr()));
    else
      dominantGenotype.setText("null");
    this.refreshStatistics(map);
    lineChart.addData(map.getDay(), map.getNumberOfAnimals(), map.getNumberOfGrass(), map.averageEnergy(), map.averageLifetime(), map.averageKids());
    gridVisualizer.draw(map.getLeftBottom(), map.getRightTop());
  }

  private void saveToFileClick(Chart lineChart, TextField fileNameText) {
    String fileName = fileNameText.getText();
    if(Objects.equals(fileName, ""))
      fileName="data";
    FileManager fileManager = new FileManager(fileName);
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

  private void stopStartClick(SimulationEngine engine, AbstractWorldMap map, Button saveToFile, Button showDominant, TextField fileNameText) {
    engine.changeState();
    boolean visible = !engine.getIsRunning();
    saveToFile.setVisible(visible);
    showDominant.setVisible(visible);
    fileNameText.setVisible(visible);
    if (!visible)
      map.removeHighlightDominant();
  }

  private void removeStatisticsObservation() {
    this.borderMap.removeStatisticsObservation();
    this.wrapMap.removeStatisticsObservation();
  }

  private void refreshStatistics(AbstractWorldMap map) {
    if (this.observedAnimal != null && map.getIsObservedAnimalOnMap()) {
      int kids = this.observedAnimal.getNumberOfKidsObserved();
      if (kids > 0)
        this.observedNumberOfKids.setText(String.valueOf(kids));
      else
        this.observedNumberOfKids.setText("no kids");
      // IF YOU WANT ALIVE DESCENDANTS, THEN CHANGE COMMENT STYLE IN THESE LINES: 251-254
//      int descendants = map.countAllObservedAnimal();
//      if (descendants >= 0)
      int descendants = this.observedAnimal.getNumberOfDescendantsObserved();
      if (descendants > 0)
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
    map.setObservedAnimal(animal);
    animal.observeStatistics();
    this.refreshStatistics(map);
  }
}