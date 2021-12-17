package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
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

  public App() {

  }

  public void init() {
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    this.grid = new GridPane();
    this.grid2 = new GridPane();
    TextField width = new TextField("11");
    HBox widthBox = new HBox(new Text("WIDTH: "), width);
    TextField height = new TextField("11");
    HBox heightBox = new HBox(new Text("HEIGHT: "), height);
    TextField startEnergy = new TextField("400");
    HBox startEnergyBox = new HBox(new Text("START ENERGY: "), startEnergy);
    TextField moveEnergy = new TextField("10");
    HBox moveEnergyBox = new HBox(new Text("MOVE ENERGY: "), moveEnergy);
    TextField animalsAtStart = new TextField("10");
    HBox animalsAtStartBox = new HBox(new Text("NUMBER OF ANIMALS: "), animalsAtStart);
    TextField plantEnergy = new TextField("100");
    HBox plantEnergyBox = new HBox(new Text("PLANT ENERGY: "), plantEnergy);
    TextField jungleRatio = new TextField("20");
    HBox jungleRatioBox = new HBox(new Text("JUNGLE RATIO: "), jungleRatio);
    CheckBox magicBorderMap = new CheckBox("MAGIC BORDER MAP");
    HBox magicBorderMapBox = new HBox(magicBorderMap);
    CheckBox magicWrapMap = new CheckBox("MAGIC WRAP MAP");
    HBox magicWrapMapBox = new HBox(magicWrapMap);
    Button startButton = new Button("START");
    VBox settings = new VBox(widthBox, heightBox, startEnergyBox, moveEnergyBox, animalsAtStartBox, plantEnergyBox, jungleRatioBox, magicBorderMapBox, magicWrapMapBox, startButton);
    Scene scene = new Scene(settings);
    primaryStage.setScene(scene);
    primaryStage.show();
    startButton.setOnAction((e) -> {
      this.grid.getChildren().clear();
      this.borderMap = new BorderMap(Integer.parseInt(width.getText()), Integer.parseInt(height.getText()), Integer.parseInt(startEnergy.getText()), Integer.parseInt(moveEnergy.getText()), Integer.parseInt(animalsAtStart.getText()), Integer.parseInt(plantEnergy.getText()), Integer.parseInt(jungleRatio.getText()), magicBorderMap.isSelected());
      this.wrapMap = new WrapMap(Integer.parseInt(width.getText()), Integer.parseInt(height.getText()), Integer.parseInt(startEnergy.getText()), Integer.parseInt(moveEnergy.getText()), Integer.parseInt(animalsAtStart.getText()), Integer.parseInt(plantEnergy.getText()), Integer.parseInt(jungleRatio.getText()), magicWrapMap.isSelected());
      this.mapVisualizer = new GridVisualizer(this.borderMap, this.grid);
      this.mapVisualizer2 = new GridVisualizer(this.wrapMap, this.grid2);
      Vector2d leftBottom = new Vector2d(0, 0);
      Vector2d rightTop = new Vector2d(Integer.parseInt(width.getText()) - 1, Integer.parseInt(height.getText()) - 1);
//      Vector2d leftBottom2 = new Vector2d(0, 0);
//      Vector2d rightTop2 = new Vector2d(Integer.parseInt(width.getText()) , Integer.parseInt(height.getText()) );
      try {
        this.engine = new SimulationEngine(this.borderMap, Integer.parseInt(animalsAtStart.getText()));
        this.engine.addObserver(this);
        this.engine.setDayTimeChange(this.speed);
        this.engine2 = new SimulationEngine(this.wrapMap, Integer.parseInt(animalsAtStart.getText()));
        this.engine2.addObserver(this);
        this.engine2.setDayTimeChange(this.speed);
        this.mapVisualizer.draw(leftBottom, rightTop);
        this.mapVisualizer2.draw(leftBottom, rightTop);
        Button stopStart = new Button("CHANGE");
        stopStart.setOnAction((e2) -> {
          this.engine.changeState();
        });
        this.text2.setText(String.valueOf(this.borderMap.getMaxMagicAvailable()));
        HBox magicCount = new HBox(new Text("MAGIC REMAIN: "), this.text);
        VBox left = new VBox(stopStart, magicCount, this.grid);
        Button stopStart2 = new Button("CHANGE");
        stopStart2.setOnAction((e2) -> {
          this.engine2.changeState();
        });
        this.text2.setText(String.valueOf(this.wrapMap.getMaxMagicAvailable()));
        HBox magicCount2 = new HBox(new Text("MAGIC REMAIN: "), this.text2);
        VBox right = new VBox(stopStart2, magicCount2, this.grid2);
        HBox mainBox = new HBox(left, right);
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
          this.text.setText(String.valueOf(map.getMaxMagicAvailable()));
          this.mapVisualizer.draw(this.borderMap.getLeftBottom(), this.borderMap.getRightTop());
        }
//      else if (map instanceof WrapMap) {
        else {
          this.grid2.getChildren().clear();
          this.text2.setText(String.valueOf(map.getMaxMagicAvailable()));
          this.mapVisualizer2.draw(this.wrapMap.getLeftBottom(), this.wrapMap.getRightTop());
        }
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    });
  }
}