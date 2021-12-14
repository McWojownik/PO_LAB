package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

import static java.lang.System.out;

public class App extends Application {
  private AbstractWorldMap map;
  private SimulationEngine engine;
  private GridVisualizer mapVisualizer;
  private GridPane grid;

  public App() {
  }

  public void init() {
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    this.grid = new GridPane();
    TextField width = new TextField("11");
    HBox widthBox = new HBox(new Text("WIDTH: "), width);
    TextField height = new TextField("11");
    HBox heightBox = new HBox(new Text("HEIGHT: "), height);
    TextField startEnergy = new TextField("400");
    HBox startEnergyBox = new HBox(new Text("START ENERGY: "), startEnergy);
    TextField moveEnergy = new TextField("20");
    HBox moveEnergyBox = new HBox(new Text("MOVE ENERGY: "), moveEnergy);
    TextField animalsAtStart = new TextField("2");
    HBox animalsAtStartBox = new HBox(new Text("NUMBER OF ANIMALS: "), animalsAtStart);
    TextField plantEnergy = new TextField("100");
    HBox plantEnergyBox = new HBox(new Text("PLANT ENERGY: "), plantEnergy);
    TextField jungleRatio = new TextField("20");
    HBox jungleRatioBox = new HBox(new Text("JUNGLE RATIO: "), jungleRatio);
    Button startButton = new Button("START");
    VBox settings = new VBox(widthBox, heightBox, startEnergyBox, moveEnergyBox, animalsAtStartBox, plantEnergyBox, jungleRatioBox, startButton);
    Scene scene = new Scene(settings, 400, 400);
    primaryStage.setScene(scene);
    primaryStage.show();
    startButton.setOnAction((e) -> {
      this.map = new GrassField(Integer.parseInt(width.getText()),Integer.parseInt(height.getText()),Integer.parseInt(startEnergy.getText()),Integer.parseInt(moveEnergy.getText()),Integer.parseInt(animalsAtStart.getText()),Integer.parseInt(plantEnergy.getText()),Integer.parseInt(jungleRatio.getText()));
      this.grid.getChildren().clear();
      this.mapVisualizer = new GridVisualizer(this.map, this.grid);
//      this.grid.setGridLinesVisible(true);
      Vector2d leftBottom = new Vector2d(0,0);
      Vector2d rightTop = new Vector2d(Integer.parseInt(width.getText())-1,Integer.parseInt(height.getText())-1);
      try {
        this.engine = new SimulationEngine(this.map, Integer.parseInt(animalsAtStart.getText()));
        this.engine.addObserver(this);
        this.engine.setDayTimeChange(600);
        this.mapVisualizer.draw(leftBottom, rightTop);
        Scene scene2 = new Scene(this.grid);
        primaryStage.setScene(scene2);
        primaryStage.show();
        Thread thread = new Thread(this.engine);
        thread.start();
      } catch (FileNotFoundException ex) {
        ex.printStackTrace();
      }
    });
  }

  public void positionChanged() {
    Platform.runLater(() -> {
      this.grid.getChildren().clear();
      try {
        this.mapVisualizer.draw(this.map.getLeftBottom(), this.map.getRightTop());
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    });
  }
}