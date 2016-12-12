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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Defines the Player view
 * Created by Triton on 27/11/2016.
 */
public class V_BoardCard_P2 implements IObserver {
    private final Image img3;
    private final Image bear;
    private final Image eagle;
    private final Image heal;
    private final Image poison;
    private final Image wolf;
    private final Image shield;
    private final C_SpellmongerApp controller; // temporary solution
    private final Label actiontarget = new Label();
    private final int id_player;
    private final int id_opponent;
    private Stage V_BoardCard_P2;
    private String name_current, name_opponent;
    private ArrayList<String> cardNames;
    private ArrayList<Label> card_opponent;
    private ArrayList<Button> cards_current;
    private Button graveyardP1, graveyardP2;
    private int round;
    private String playedCard, opponentCard;
    private int points_opponent, points_current;


    public V_BoardCard_P2(C_SpellmongerApp controller, int player_id) {

        this.img3 = new Image(getClass().getResourceAsStream("/img3.jpg"));
        this.bear = new Image(getClass().getResourceAsStream("/bear.png"));
        this.eagle = new Image(getClass().getResourceAsStream("/eagle.png"));
        this.heal = new Image(getClass().getResourceAsStream("/heal.png"));
        this.poison = new Image(getClass().getResourceAsStream("/poison.png"));
        this.shield = new Image(getClass().getResourceAsStream("/shield.png"));
        this.wolf = new Image(getClass().getResourceAsStream("/wolf.png"));

        this.controller = controller;
        // nameP1 and P2 can be deleted
        this.id_player = player_id;
        if (id_player == 0) id_opponent = 1;
        else id_opponent = 0;

        this.name_current = controller.getPlayerNames()[id_player];
        this.name_opponent = controller.getPlayerNames()[id_opponent];

        this.points_current = 20;
        this.points_opponent = 20;
        this.opponentCard = "";
        this.playedCard = "";

        this.cardNames = controller.get3Cards(id_player);
        this.round = 1;
        this.V_BoardCard_P2 = new Stage();


    }


    public void display() {

        V_BoardCard_P2 = presentation(V_BoardCard_P2);
        try {
            if (id_player == 0) V_BoardCard_P2.setX(900.0);
            else V_BoardCard_P2.setX(300.0);
            V_BoardCard_P2.show();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public void setVisible(boolean setVisible) {
        if (setVisible) V_BoardCard_P2.show();
        else V_BoardCard_P2.hide();
    }

    private Stage presentation(Stage board) {

        Scene scene;
        BorderPane layout = new BorderPane();
        GridPane gridpane = new GridPane();
        HBox bottomBox = new HBox();

        card_opponent = new ArrayList<>();
        cards_current = new ArrayList<>();

        Button graveyardP1 = new Button();
        Button graveyardP2 = new Button();
        Button btnCenterP1 = new Button();
        Button btnCenterP2 = new Button();
        Button btnPlay = new Button("Play");
        Label labelP1 = new Label(name_current);
        Label labelP2 = new Label(name_opponent);


        // Set id for stylesheet
        bottomBox.setId("mabox");
        actiontarget.setId("monLabel");
        btnCenterP1.setId("playCard");
        btnCenterP2.setId("playCard");
        graveyardP1.setId("playCard");
        graveyardP2.setId("playCard");
        this.graveyardP1 = graveyardP1;
        this.graveyardP2 = graveyardP2;

        actiontarget.setText(this.name_current + " : " + points_current + "  " + this.name_opponent + " : " + points_opponent + " ");
        actiontarget.setText(playedCard + "   " + opponentCard);
        board.getIcons().add(new Image("/logo_esilv.png"));
        board.setTitle(controller.getPlayerNames()[id_player]); // Display the player name

        // Name of player

        labelP1.setFont(Font.font("Cambria", 32));
        labelP2.setFont(Font.font("Cambria", 32));


        // Set cards_current for player 1

        for (int i = 0; i < 3; i++) {
            Label card_opp = new Label();
            card_opp.setGraphic(new ImageView(img3));
            card_opp.setId("reverseCard");
            card_opponent.add(card_opp);
        }


        // Get cards_current for player 2

        cardNames = controller.get3Cards(id_player);


        for (String cardName : cardNames) {
            Button button = new Button();
            button.setGraphic(new ImageView((getImage(cardName))));
            button.setId("playCard");
            cards_current.add(button);
        }

        for (int i = 0; i < cards_current.size(); i++) {
            setCardOnAction(cards_current.get(i), btnCenterP2, i);
        }

        // Set button play
        btnPlay.setOnAction(e -> SetCardPlayOnAction(btnCenterP1, btnCenterP2));


        // disposition of elements on the board

        layout.setRight(btnPlay);
        BorderPane.setAlignment(btnPlay, Pos.TOP_RIGHT);

        bottomBox.getChildren().add(actiontarget);
        layout.setBottom(bottomBox);
        BorderPane.setAlignment(bottomBox, Pos.BOTTOM_CENTER);


        // Dispose the cards_current on the board
        gridpane.add(card_opponent.get(0), 1, 1);
        gridpane.add(card_opponent.get(1), 2, 1);
        gridpane.add(card_opponent.get(2), 3, 1);
        gridpane.add(graveyardP1, 1, 2);
        gridpane.add(btnCenterP1, 2, 2);
        gridpane.add(graveyardP2, 1, 3);
        gridpane.add(btnCenterP2, 2, 3);
        gridpane.add(cards_current.get(0), 1, 4);
        gridpane.add(cards_current.get(1), 2, 4);
        gridpane.add(cards_current.get(2), 3, 4);

        gridpane.setVgap(5.0);
        gridpane.setHgap(5.0);
        layout.setCenter(gridpane);
        BorderPane.setAlignment(gridpane, Pos.CENTER);

        if (!("".equals(this.opponentCard))) {
            btnCenterP1.setGraphic(new ImageView(img3));
        }

        scene = new Scene(layout);
        scene.getStylesheets().add("style.css");
        board.setScene(scene);

        return board;

    }

    private Image getImage(String cardName) {
        Image image;
        switch (cardName) {
            case "Bear":
                image = bear;
                break;
            case "Wolf":
                image = wolf;
                break;
            case "Eagle":
                image = eagle;
                break;
            case "Shield":
                image = shield;
                break;
            case "Poison":
                image = poison;
                break;
            case "Heal":
                image = heal;
                break;
            default:
                image = null;
                break;
        }
        return image;
    }

    private void setCardOnAction(Button card, Button destination, int i) {
        card.setOnAction(e -> {

            opponentCard = null;
            // If player clicks on an empty cards_current, get this cards_current back on its place
            if (card.getGraphic() == null) {
                card.setGraphic(destination.getGraphic());
                destination.setGraphic(null);
                playedCard = "";
            } else {

                // If a cards_current is already on the board, get this cards_current back on its place
                if (destination.getGraphic() != null) {
                    int position = 0;
                    for (int j = 0; j < cardNames.size(); j++) {
                        if (playedCard.equals(cardNames.get(j))) position = j;
                    }
                    this.cards_current.get(position).setGraphic(destination.getGraphic());
                }

                destination.setGraphic(card.getGraphic());
                playedCard = cardNames.get(i);
                controller.setPlayedCardNames(playedCard, id_player);

                card.setGraphic(null);
            }
        });
    }


    //Function when button play pressed : transfers cards_current only on both field to their respective Graveyard
    private void SetCardPlayOnAction(Button btn_centerP1, Button btn_centerP2) {

        if (btn_centerP2.getGraphic() != null) {
            //graveyardP1.setGraphic(btn_centerP1.getGraphic());
            //graveyardP2.setGraphic(btn_centerP2.getGraphic());
            btn_centerP1.setGraphic(null);
            btn_centerP2.setGraphic(null);
            this.opponentCard = controller.getOpponentCard(id_player);
            this.points_current = controller.getPlayerAPoints();
            this.points_opponent = controller.getPlayerBPoints();

            // Getting the position of playedCard
            int position = 0;
            for (int j = 0; j < cardNames.size(); j++) {
                if (playedCard.equals(cardNames.get(j))) position = j;
            }
            this.controller.getCardPlayerFromView(id_player, position);


            //if (playedCard != null && opponentCard != null) {
            //    this.setVisible(true);
            //    /*playedCard=null;
            //    opponentCard=null;*/
            //}
            //if (opponentCard != null) {
            //    graveyardP1.setGraphic(new ImageView(getImage(opponentCard)));
            //}


        } else V_Utilities.getInstance().AlertBox("Invalid", "\n Please choose a Card \n");

        // Have to be moved to controller
        /*if (round % 3 == 0) {
            cardNames = controller.get3Cards(id_player);

            for (int i = 0; i < cards_current.size(); i++) {
                cards_current.get(i).setGraphic(new ImageView((getImage(cardNames.get(i)))));
            }
            for (Label aCard_opponent : card_opponent) {
                aCard_opponent.setGraphic(new ImageView(img3));
            }
        }
        actiontarget.setText("player: " + this.playedCard + " " + points_current + " ||  opponent: " + this.opponentCard + "  " + points_opponent);
        round++;*/
    }


    private void updatePlayerName() {
        this.name_current = controller.getPlayerNames()[id_player];
        this.name_opponent = controller.getPlayerNames()[id_opponent];
    }

    private void updateLifePoints() {
        //this.points_current = controller.getPlayerAPoints();
        //this.points_opponent = controller.getPlayerBPoints();
    }

    private void updateCards() {
        // OPPONENT

        // first, empty all cards
        for (Label card : card_opponent) {
            card.setGraphic(null);
        }

        // then show as many cards as the opponent has
        int nbCardOpponent = this.controller.getNbCardOpponent(id_opponent);
        int i = 1;
        for (Label card : card_opponent) {
            if (i <= nbCardOpponent) {
                card.setGraphic(new ImageView(img3));
                i++;
            }
        }

        // update current
        // first, empty all cards
        for (Button card : cards_current) {
            card.setGraphic(null);
        }

        // then show the cards in hand
        cardNames = controller.getCards(id_player);
        for (i = 0; i < cardNames.size(); i++) {
            cards_current.get(i).setGraphic(new ImageView((getImage(cardNames.get(i)))));
        }
    }

    private void updateGraveyards() {

        // update the graveyard of P1
        String cardName = controller.getLastCardNameInGraveyard(id_opponent);
        if (!cardName.equals("")) this.graveyardP1.setGraphic(new ImageView((getImage(cardName))));

        // update the graveyard of P2
        cardName = controller.getLastCardNameInGraveyard(id_player);
        if (!cardName.equals("")) this.graveyardP2.setGraphic(new ImageView((getImage(cardName))));


    }

    /**
     * Function that update the view (INCOMPLETE)
     */
    @Override
    public void update(IObservable o) {
        if (o instanceof C_SpellmongerApp) {
            // That's probably a bug.
            // C_SpellmongerApp controller = (C_SpellmongerApp) o;
            updatePlayerName();//  and update data for view
            updateLifePoints();
            updateCards();
            updateGraveyards();

            // For example controller.getNames and update data for view
        }

    }


}


