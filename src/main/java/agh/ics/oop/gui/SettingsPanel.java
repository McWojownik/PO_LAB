package agh.ics.oop.gui;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SettingsPanel {
  private final VBox settings;
  private final Button startButton;
  protected TextField width;
  protected TextField height;
  protected TextField startEnergy;
  protected TextField moveEnergy;
  protected TextField animalsAtStart;
  protected TextField plantEnergy;
  protected TextField jungleRatio;
  protected CheckBox magicBorderMap;
  protected CheckBox magicWrapMap;

  public SettingsPanel(){
    this.width = new TextField("11");
    HBox widthBox = new HBox(new Text("WIDTH: "), width);
    this.height = new TextField("11");
    HBox heightBox = new HBox(new Text("HEIGHT: "), height);
    this.startEnergy = new TextField("40");
    HBox startEnergyBox = new HBox(new Text("START ENERGY: "), startEnergy);
    this.moveEnergy = new TextField("1");
    HBox moveEnergyBox = new HBox(new Text("MOVE ENERGY: "), moveEnergy);
    this.animalsAtStart = new TextField("10");
    HBox animalsAtStartBox = new HBox(new Text("NUMBER OF ANIMALS: "), animalsAtStart);
    this.plantEnergy = new TextField("10");
    HBox plantEnergyBox = new HBox(new Text("PLANT ENERGY: "), plantEnergy);
    this.jungleRatio = new TextField("20");
    HBox jungleRatioBox = new HBox(new Text("JUNGLE RATIO: "), jungleRatio);
    this.magicBorderMap = new CheckBox("MAGIC BORDER MAP");
    HBox magicBorderMapBox = new HBox(magicBorderMap);
    this.magicWrapMap = new CheckBox("MAGIC WRAP MAP");
    HBox magicWrapMapBox = new HBox(magicWrapMap);
    this.startButton = new Button("START");
    this.settings = new VBox(widthBox, heightBox, startEnergyBox, moveEnergyBox, animalsAtStartBox, plantEnergyBox, jungleRatioBox, magicBorderMapBox, magicWrapMapBox, startButton);
  }

  public VBox getSettings() {
    return this.settings;
  }

  public Button getStartButton(){
    return this.startButton;
  }
}
