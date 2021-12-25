package agh.ics.oop.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {
  private final Image image;
  private final int size;

  public GuiElementBox(String pictureName, int size) throws FileNotFoundException {
    this.image = new Image(new FileInputStream("src/main/resources/" + pictureName));
    this.size = size;
  }

  public ImageView getImage() {
    ImageView imageView = new ImageView(this.image);
    imageView.setFitWidth(this.size);
    imageView.setFitHeight(this.size);
    return imageView;
  }
}
