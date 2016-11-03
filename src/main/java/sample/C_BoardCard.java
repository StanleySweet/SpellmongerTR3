package sample;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Created by antho on 19/10/2016.
 * do for get children of V_boardCard, and for example do the same thing for all button "card"
 */
public class C_BoardCard {

    public static Button[] CreateCard(int n, Image img) {
        Button tab[]= new Button[n];
        for (int i = 0; i < n; i++) {
          tab[i] = new Button("", new ImageView(img));
        }
        return tab;
    }
}


