package edu.insightr.spellmonger.View;

import edu.insightr.spellmonger.Controller.C_SpellmongerApp;
import edu.insightr.spellmonger.Interfaces.IObservable;
import edu.insightr.spellmonger.Interfaces.IObserver;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by Triton on 27/11/2016.
 */
public class V_BoardCard_P1 implements IObserver {
    Image img;
    Image img2;
    Image img3;
    Image bear, eagle, heal, poison, wolf, shield;
    C_SpellmongerApp controller; // temporary solution
    String player1Name;
    int counter_round = 1;
    ArrayList<String> nameCard = new ArrayList<>();
    ArrayList<Image> imageCard = new ArrayList<>();
    ArrayList<Button> card = new ArrayList<>();


    public V_BoardCard_P1(Stage primaryStage, C_SpellmongerApp controller) {

        //set Image
        Image img = new Image(getClass().getResourceAsStream("/img.jpg"));
        Image img2 = new Image(getClass().getResourceAsStream("/img2.jpg"));
        Image img3 = new Image(getClass().getResourceAsStream("/img3.jpg"));
        Image bear = new Image(getClass().getResourceAsStream("/bear.png"));
        Image eagle = new Image(getClass().getResourceAsStream("/eagle.png"));
        Image heal = new Image(getClass().getResourceAsStream("/heal.png"));
        Image poison = new Image(getClass().getResourceAsStream("/poison.png"));
        Image shield = new Image(getClass().getResourceAsStream("/shield.png"));
        Image wolf = new Image(getClass().getResourceAsStream("/wolf.png"));


        this.img = img;
        this.img2 = img2;
        this.img3 = img3;
        this.bear = bear;
        this.eagle = eagle;
        this.heal = heal;
        this.poison = poison;
        this.shield = shield;
        this.wolf = wolf;
        this.controller = controller;
        this.player1Name = this.controller.getPlayerNames()[0];
    }


    public void display() {
        Stage V_BoardCard_P1 = new Stage();
        V_BoardCard_P1 = presentation(V_BoardCard_P1);

        try {
            V_BoardCard_P1.show();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public Stage presentation(Stage board) {


        board.getIcons().add(new Image("/logo_esilv.png"));
        board.setTitle("V_BoardCard_P1");

        //Set 2 graveyard
        Button graveyardP1 = new Button();
        graveyardP1.setGraphic(new ImageView(img2));
        Button graveyardP2 = new Button();
        graveyardP2.setGraphic(new ImageView(img2));

        // Sets button position to the middle
        Button btnCenterP1 = new Button();
        Button btnCenterP2 = new Button();
        Button btnPlay = new Button("Play");

        // Name of player
        Label Player1 = new Label(controller.getPlayerNames()[0]);
        Label Player2 = new Label(controller.getPlayerNames()[1]);
        Player1.setFont(Font.font("Cambria", 32));
        Player2.setFont(Font.font("Cambria", 32));

        //Card From View Manager filling the Board
        int n = 3;
        Button[] card_P2 = V_Utilities.CreateCardArray(n, img3);

        Image image1 = null;

        nameCard = this.controller.get3Cards(player1Name);

        //card.add(playerCards);
        HBox topMenu = new HBox();
        HBox botMenu = new HBox();
        VBox leftMenu = new VBox();
        VBox rightMenu = new VBox();
        VBox centerMenu = new VBox();

        // Sets Hbox for both top and bottom
        Button card1 = new Button();
        Button card2 = new Button();
        Button card3 = new Button();
        card.add(card1);
        card.add(card2);
        card.add(card3);


        for (int i = 0; i < nameCard.size(); i++) {
            //  card.add(new Button());
            //  String nameCard= this.controller.getImagePath().get(i); // Have the first path for the card 1 of the current player

            switch (nameCard.get(i)) {
                case "Bear":
                    image1 = bear;
                    break;
                case "Wolf":
                    image1 = wolf;
                    break;
                case "Eagle":
                    image1 = eagle;
                    break;
                case "Shield":
                    image1 = shield;
                    break;
                case "Poison":
                    image1 = poison;
                    break;
                case "Heal":
                    image1 = heal;
                    break;
                default:
                    break;
            }
            imageCard.add(image1);
        }
        for (int i = 0; i < n; i++) {
            card.get(i).setGraphic(new ImageView(imageCard.get(i)));
            botMenu.getChildren().addAll(card.get(i));
        }

/*
        if (this.controller.getRoundCounter() == 3) {

            for (int i = 0; i < n ; i++) {
                card.get(i).setGraphic(new ImageView(imageCard.get(i+3)));
                botMenu.getChildren().addAll(card.get(i));
            }
        }
        */

        botMenu.getChildren().addAll(Player1);

        topMenu.getChildren().addAll(card_P2);
        topMenu.getChildren().addAll(Player2);

        for (int i = 0; i < card.size(); i++) {
            V_Utilities.SetCardOnAction(card.get(i), btnCenterP1);
        }

        for (int i = 0; i < n; i++) {
            V_Utilities.SetCardOnAction(card_P2[i], btnCenterP2);
        }

        //add menubar plus cards
        VBox vbox_items = new VBox();
        vbox_items.getChildren().addAll(topMenu);

        //Layout setup
        BorderPane layout = new BorderPane();


        layout.setTop(vbox_items);
        BorderPane.setAlignment(vbox_items, Pos.TOP_CENTER);

        layout.setBottom(botMenu);
        BorderPane.setAlignment(botMenu, Pos.BOTTOM_CENTER);

        leftMenu.getChildren().addAll(graveyardP2, graveyardP1);
        layout.setLeft(leftMenu);
        BorderPane.setAlignment(leftMenu, Pos.CENTER_LEFT);

        rightMenu.getChildren().addAll(btnPlay);
        layout.setRight(rightMenu);
        BorderPane.setAlignment(rightMenu, Pos.CENTER_RIGHT);

        centerMenu.getChildren().addAll(btnCenterP2, btnCenterP1);
        layout.setCenter(centerMenu);
        BorderPane.setAlignment(centerMenu, Pos.CENTER);


        //Set button play
        btnPlay.setOnAction(e -> SetCardPlayOnAction(btnCenterP1, btnCenterP2, graveyardP1, graveyardP2));
        // V_BoardCard_P2.primaryStage.setScene(new Scene(new Button("my second window")));

        Scene scene = new Scene(layout);
        board.setScene(scene);

        return board;

    }


    //Function when button play pressed : tranfers cards only on both field to their Graveyard respective
    public void SetCardPlayOnAction(Button btn_centerP1, Button btn_centerP2, Button graveyardP1, Button graveyardP2) {

        // if(this.controller.getRoundCounter()==3){ V_BoardCard_P1.close();controller.displayBoard(); }
        if (this.controller.getRoundCounter() % 3 == 0) {
            for (int i = 0; i < card.size(); i++) {
                card.get(i).setGraphic(new ImageView(imageCard.get(i)));
            }
        }
        if (btn_centerP1.getGraphic() != null && btn_centerP2.getGraphic() != null) {
            graveyardP1.setGraphic(btn_centerP1.getGraphic());
            graveyardP2.setGraphic(btn_centerP2.getGraphic());
            btn_centerP1.setGraphic(null);
            btn_centerP2.setGraphic(null);
            controller.playTurn();


            // controller.nothing();
        } else V_Utilities.AlertBox("Invalid", "\n Please Card on both Field \n");
    }


    public void updatePlayerName() {
        this.player1Name = controller.getPlayerNames()[0];
    }

    /**
     * Function that update the view (INCOMPLETE)
     */
    @Override
    public void update(IObservable o) {
        if (o instanceof C_SpellmongerApp) {
            C_SpellmongerApp controller = (C_SpellmongerApp) o;
            updatePlayerName();//  and update data for view
        }

    }
}