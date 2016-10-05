package edu.insightr.spellmonger;


// refair algo tri
// drop
// player methode implement
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Last Modification by Tara 26/09/2016
 * Class that simulates a card game (currently with 2 virtual players) :
 * <p>
 * There are currently 2 types of card that can be drawn by the player : Creatures and Rituals
 * Each card have an effect on the player or on its opponent
 * <p>
 * There are currently 3 different creatures (Beast) that deals damages to its opponent :
 * Eagle deals 1 damage
 * Wolf deals 2 damages
 * Bear deals 3 damages
 * <p>
 * There are currently 2 different rituals : curse and blessing
 * Curse deals 3 damages
 * Blessing restore 3 life points
 * <p>
 * Each player begins with 20 life points
 * <p>
 * The first player who has no life points loose the game
 *
 * @author Tara Zhong
 * @author Thomas RAIMBAULT
 * @author Paul Bezault
 */
public class SpellmongerApp {
    private static final Logger logger = Logger.getLogger(SpellmongerApp.class);
    private Player playerA, playerB, currentPlayer, opponent, winner;
    private boolean onePlayerDead;
    private int currentCardNumber, roundCounter, maxNumberOfCard;
    private List<PlayCard> cardPool;
    private List<PlayCard> graveyard;


    /**
     * @return Opponent Player
     */
    Player getOpponent(){
        return opponent ;
    }

    /**
     * @return Current Player
     */
    Player getCurrentPlayer(){
        return currentPlayer ;
    }


    /**
     * Constructor of the class
     *
     * @param playerA : {@code String} Name of Player A
     * @param playerB : {@code int} Name of the Player B
     *                Last Modified by : Tara
     */
    private SpellmongerApp(Player playerA, Player playerB, int maxNumberOfCard) {
        this.maxNumberOfCard = maxNumberOfCard;
        this.onePlayerDead = false;
        this.playerA = playerA;
        this.playerB = playerB;
        this.currentPlayer = this.playerA;
        this.opponent = this.playerB;
        this.currentCardNumber = 0;
        this.roundCounter = 1;
        this.winner = null;
        this.cardPool = new ArrayList<>(this.maxNumberOfCard);
        this.graveyard = new ArrayList<>();
        final ArrayList<PlayCard> cardList;
        cardList = new ArrayList<>(Arrays.asList(
                new Beast("Bear", 3),
                new Beast("Wolf", 2),
                new Beast("Eagle", 1),
                new Ritual("Curse", 3, false),
                new Ritual("Blessing", -3, true)
        ));

        // Use the DeckCreator class to fill and shuffle the cards deck
        DeckCreator.fillCardPool(cardPool, cardList, maxNumberOfCard);
    }


    /**
     * Says when all cards have been played.
     *
     * @return true if the game can continue
     */
    private boolean isThereAnyCardLeft() {
        return !(this.currentCardNumber == this.maxNumberOfCard);//drop ?
    }

    /**
     * Ritual already played must be add to graveyard
     *
     * @param used_card : used_card must be add to graveyard
     */
    private void discard(PlayCard used_card){
        graveyard.add(used_card);
        logger.info(used_card.getName() + " added to graveyard ");
    }

    /**
     * Deals the damages from the creatures of the current player
     */
    private void creaturesAttack() {

        ArrayList<PlayCard> beastsList = currentPlayer.getCreatures();
        int totalDamages = 0;
        for (PlayCard beast : beastsList) {
            totalDamages += beast.getDamage();
        }
        logger.info("The beasts of " + this.currentPlayer.getName() + " deal " + totalDamages + " damages to " + this.opponent.getName());
        opponent.inflictDamages(totalDamages);
    }

    /**
     * Launches the game
     */
    private void play() {
        while (!onePlayerDead) {
            if (!isThereAnyCardLeft()) {
                logger.info("\n");
                logger.info("******************************");
                logger.info("No more cards in the CardPool - End of the game");
                logger.info("******************************");
                break;
            }

            logger.info("\n");
            logger.info("***** ROUND " + roundCounter);
            currentPlayer.Drawcard(cardPool);
            currentPlayer.Playcard(this,currentPlayer.getHand());
            creaturesAttack();
            logger.info(opponent.getName() + " has " + opponent.getLifePoints() + " life points and " + currentPlayer.getName() + " has " + currentPlayer.getLifePoints() + " life points ");

            if (opponent.isDead()) {
                winner = currentPlayer;
                onePlayerDead = true;
            }

            nextTurn();
        }

        if (isThereAnyCardLeft()) {
            logger.info("\n");
            logger.info("******************************");
            logger.info("THE WINNER IS " + winner.getName() + " !!!");
            logger.info("******************************");
            logger.info("Beasts controlled by " + playerA.getName());
            logger.info(playerA.getCreatures());
            logger.info("Beasts controlled by " + playerB.getName());
            logger.info(playerB.getCreatures());
            logger.info("Graveyard : " + graveyard);
        }
    }

    /**
     * Switch players and increment turns and cardNumbers
     */
    private void nextTurn() {
        if (playerA.equals(currentPlayer)) {
            currentPlayer = playerB;
            opponent = playerA;
            logger.info("Graveyard : " + graveyard);
        } else {
            currentPlayer = playerA;
            opponent = playerB;
        }
        ++currentCardNumber;
        ++roundCounter;
    }

    /**
     * Draw A Card
     * Return the card (PlayCard type) of the current card number in the card Pool
     *
     * @return {@code PlayCard} of the card drawn on the card Pool
     */
    private PlayCard drawACard() {
        return this.cardPool.get(this.currentCardNumber);
    }


    public static void main(String[] args) {
        final int lifePoints = 20;
        final int maxNumberOfCards = 70;

        // We create the application
        SpellmongerApp app = new SpellmongerApp(new Player("Alice", lifePoints), new Player("Bob", lifePoints), maxNumberOfCards);

        // We start the game
        app.play();
    }
}