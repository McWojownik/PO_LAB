package agh.ics.oop.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SettingsPanel {
  private final HBox settings;
  private final Button startButton;
  protected TextField width;
  protected TextField height;
  protected TextField startEnergy;
  protected TextField moveEnergy;
  protected TextField animalsAtStart;
  protected TextField plantEnergy;
  protected TextField jungleWidth;
  protected TextField jungleHeight;
  protected TextField speed;
  protected CheckBox magicBorderMap;
  protected CheckBox magicWrapMap;

  public SettingsPanel() {
    int textFieldSize = 100;
    int optionsSpace = 5;
    this.width = new TextField("11");
    this.width.setPrefWidth(textFieldSize);
    HBox widthBox = new HBox(new Text("WIDTH: "), this.width);
//    widthBox.setAlignment(Pos.CENTER);
    this.height = new TextField("11");
    this.height.setPrefWidth(textFieldSize);
    HBox heightBox = new HBox(new Text("HEIGHT: "), this.height);
//    heightBox.setAlignment(Pos.CENTER);
    this.startEnergy = new TextField("40");
    this.startEnergy.setPrefWidth(textFieldSize);
    HBox startEnergyBox = new HBox(new Text("START ENERGY: "), this.startEnergy);
//    startEnergyBox.setAlignment(Pos.CENTER);
    this.moveEnergy = new TextField("1");
    this.moveEnergy.setPrefWidth(textFieldSize);
    HBox moveEnergyBox = new HBox(new Text("MOVE ENERGY: "), this.moveEnergy);
//    moveEnergyBox.setAlignment(Pos.CENTER);
    this.animalsAtStart = new TextField("50");
    this.animalsAtStart.setPrefWidth(textFieldSize);
    HBox animalsAtStartBox = new HBox(new Text("NUMBER OF ANIMALS: "), this.animalsAtStart);
//    animalsAtStartBox.setAlignment(Pos.CENTER);
    this.plantEnergy = new TextField("20");
    this.plantEnergy.setPrefWidth(textFieldSize);
    HBox plantEnergyBox = new HBox(new Text("PLANT ENERGY: "), this.plantEnergy);
//    plantEnergyBox.setAlignment(Pos.CENTER);
    this.jungleWidth = new TextField("3");
    this.jungleWidth.setPrefWidth(textFieldSize);
    HBox jungleWidthBox = new HBox(new Text("JUNGLE WIDTH: "), this.jungleWidth);
//    jungleWidthBox.setAlignment(Pos.CENTER);
    this.jungleHeight = new TextField("3");
    this.jungleHeight.setPrefWidth(textFieldSize);
    HBox jungleHeightBox = new HBox(new Text("JUNGLE HEIGHT: "), this.jungleHeight);
//    jungleHeightBox.setAlignment(Pos.CENTER);
    this.speed = new TextField("100");
    this.speed.setPrefWidth(textFieldSize);
    HBox speedBox = new HBox(new Text("SPEED: "), this.speed);
//    speedBox.setAlignment(Pos.CENTER);
    this.magicBorderMap = new CheckBox("MAGIC BORDER MAP");
    HBox magicBorderMapBox = new HBox(this.magicBorderMap);
//    magicBorderMapBox.setAlignment(Pos.CENTER);
    this.magicWrapMap = new CheckBox("MAGIC WRAP MAP");
    HBox magicWrapMapBox = new HBox(this.magicWrapMap);
//    magicWrapMapBox.setAlignment(Pos.CENTER);
    this.startButton = new Button("START");
    HBox startButtonBox = new HBox(this.startButton);
    startButtonBox.setAlignment(Pos.CENTER);
    VBox settingHBox = new VBox(widthBox, heightBox, startEnergyBox, moveEnergyBox, animalsAtStartBox, plantEnergyBox, jungleWidthBox, jungleHeightBox, speedBox, magicBorderMapBox, magicWrapMapBox, startButtonBox);
    settingHBox.setSpacing(optionsSpace);
    settingHBox.setAlignment(Pos.CENTER);
    this.settings = new HBox(settingHBox);
    this.settings.setAlignment(Pos.CENTER);
  }

  public HBox getSettings() {
    return this.settings;
  }

  public Button getStartButton() {
    return this.startButton;
  }
}
