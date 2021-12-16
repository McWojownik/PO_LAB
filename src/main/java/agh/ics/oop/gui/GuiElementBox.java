//package agh.ics.oop.gui;
//
//import javafx.scene.control.Label;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//
//public class GuiElementBox {
//  private Image image;
//  private ImageView imageView;
//
//  public GuiElementBox(String pictureName) throws FileNotFoundException {
//    this.image = new Image(new FileInputStream("src/main/resources/" + pictureName));
//    this.imageView = new ImageView(this.image);
//    imageView.setFitWidth(20);
//    imageView.setFitHeight(20);
//  }
//
//  public Label addDescription(String text) {
//    return new Label(text);
//  }
//
//  public ImageView getImage() {
//    return this.imageView;
//  }
//}
