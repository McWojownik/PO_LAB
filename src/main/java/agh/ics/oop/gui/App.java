package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
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
  private SimulationEngine engine;
  private SimulationEngine engine2;
  private GridVisualizer gridVisualizer;
  private GridVisualizer gridVisualizer2;
  private final GridPane grid = new GridPane();
  private final GridPane grid2 = new GridPane();
  private final Text magicRemains = new Text();
  private final Text magicRemains2 = new Text();
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
      this.engine = new SimulationEngine(this.borderMap, animalsAtStart);
      this.engine.addObserver(this);
      this.engine.setDayTimeChange(this.speed);
      this.engine2 = new SimulationEngine(this.wrapMap, animalsAtStart);
      this.engine2.addObserver(this);
      this.engine2.setDayTimeChange(this.speed);

      Button showDominant = new Button("HIGHLIGHT");
      showDominant.setVisible(false);
      showDominant.setOnAction((e2) -> {
        this.showDominantClick(this.borderMap, this.grid, this.gridVisualizer);
      });
      TextField fileNameText = new TextField();
      fileNameText.setPromptText("Enter File Name");
      fileNameText.setVisible(false);
      Button saveToFile = new Button("SAVE");
      saveToFile.setVisible(false);
      saveToFile.setOnAction((e2) -> {
        this.saveToFileClick(this.lineChart, fileNameText);
      });
      Button stopStart = new Button("CHANGE STATE");
      stopStart.setOnAction((e2) -> {
        this.stopStartClick(this.engine, this.borderMap, saveToFile, showDominant, fileNameText);
      });
      HBox buttons = new HBox(stopStart, showDominant, fileNameText, saveToFile);
      buttons.setSpacing(this.spacing);
      this.magicRemains.setText(String.valueOf(this.borderMap.getMagicRemain()));
      HBox magicCount = new HBox(new Text("MAGIC REMAIN: "), this.magicRemains);
      VBox leftInfo = new VBox(buttons, magicCount, this.dominantGenotype);

      TextField fileNameText2 = new TextField();
      fileNameText2.setPromptText("Enter File Name");
      fileNameText2.setVisible(false);
      Button saveToFile2 = new Button("SAVE");
      saveToFile2.setVisible(false);
      saveToFile2.setOnAction((e2) -> {
        this.saveToFileClick(this.lineChart2, fileNameText2);
      });
      Button showDominant2 = new Button("HIGHLIGHT");
      showDominant2.setVisible(false);
      showDominant2.setOnAction((e2) -> {
        this.showDominantClick(this.wrapMap, this.grid2, this.gridVisualizer2);
      });
      Button stopStart2 = new Button("CHANGE STATE");
      stopStart2.setOnAction((e2) -> {
        this.stopStartClick(this.engine2, this.wrapMap, saveToFile2, showDominant2, fileNameText2);
      });
      HBox buttons2 = new HBox(stopStart2, showDominant2, fileNameText2, saveToFile2);
      buttons2.setSpacing(this.spacing);
      this.magicRemains2.setText(String.valueOf(this.wrapMap.getMagicRemain()));
      HBox magicCount2 = new HBox(new Text("MAGIC REMAIN: "), this.magicRemains2);
      VBox rightInfo = new VBox(buttons2, magicCount2, this.dominantGenotype2);

      try {
        this.gridVisualizer = new GridVisualizer(this.borderMap, this.grid, this, this.engine, (250-(int) (leftInfo.getHeight())) / height);
        this.gridVisualizer2 = new GridVisualizer(this.wrapMap, this.grid2, this, this.engine2, (250-(int) (rightInfo.getHeight())) / height);
        this.gridVisualizer.draw(this.borderMap.getLeftBottom(), this.borderMap.getRightTop());
        this.gridVisualizer2.draw(this.wrapMap.getLeftBottom(), this.wrapMap.getRightTop());
      } catch (FileNotFoundException ex) {
        ex.printStackTrace();
      }
      VBox left = new VBox(leftInfo, this.grid);
      VBox right = new VBox(rightInfo, this.grid2);
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
      VBox specificInfo = new VBox(observedGenesBox, observedDaysBox, observedKidsBox, observedDescendantsBox);
      specificInfo.setSpacing(this.spacing);
      VBox mainVBox = new VBox(allMapsAndCharts, specificInfo);
      mainVBox.setSpacing(4 * this.spacing);
      mainVBox.setAlignment(Pos.CENTER);
      HBox mainHBox = new HBox(mainVBox);
      mainHBox.setAlignment(Pos.CENTER);
      Scene worldScene = new Scene(mainHBox);
      primaryStage.setScene(worldScene);
      this.setScreenInTheMiddle(primaryStage);
      primaryStage.setTitle("Darwin World");
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
          refreshAll(map, this.engine, this.grid, this.magicRemains, this.dominantGenotype, this.lineChart, this.gridVisualizer);
        else
          refreshAll(map, this.engine2, this.grid2, this.magicRemains2, this.dominantGenotype2, this.lineChart2, this.gridVisualizer2);
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
      this.observedNumberOfKids.setText(String.valueOf(this.observedAnimal.getNumberOfKidsObserved()));
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