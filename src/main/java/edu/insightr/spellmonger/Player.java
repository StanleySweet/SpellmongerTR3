package edu.insightr.spellmonger;

import javax.rmi.CORBA.Util;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yasmeen on 28/09/2016.
 * Defines a player
 */
class Player {

    private final String name;
    private int lifePoints;
    private ArrayList<PlayCard> playerCreatures;
    private PlayCard cardInHand ;
//    private ArrayList<PlayCard> playerHand;

    /**
     * Constructor
     * @param name       of the player
     * @param lifePoints of this player
     */
    Player(String name, int lifePoints) {
        this.name = name;
        this.lifePoints = lifePoints;
        this.playerCreatures = new ArrayList<>();
//        this.playerHand = new ArrayList<>();
    }

    /**
     * put first card of the provide cardPool into the player's hand
     * remove the first PlayCard of cardPool as well
     */
    void Drawcard(List<PlayCard> cardPool){

        this.cardInHand = cardPool.get(0);
        cardPool.remove(0);

    }

    /**
     * Returns the name of the player
     * @return the name (String)
     */
     PlayCard getHand(){
         return this.cardInHand;
     }

    void Playcard(SpellmongerApp app,PlayCard card){

        card.activate(app);

    }

    String getName() {
        return this.name;
    }

    /**
     * Returns the life points of the player
     * @return the life points (Integer)
     */
    int getLifePoints() {
        return this.lifePoints;
    }

    /**
     * Return the state of life of the player
     * @return wether the player is alive
     */
    boolean isDead(){return this.lifePoints <= 0;}

    /**
     * Adds a creature in the Beast list of the player
     * @param card : the Beast to put
     */
    boolean addCreature(PlayCard card) {
        return this.playerCreatures.add(card);
    }

    /**
     * Returns all the creatures
     * @return a list containing the creatures of the player
     */
    ArrayList<PlayCard> getCreatures() {
        // To make sure one can not modify our list freely, we return a clone
        ArrayList<PlayCard> clone = new ArrayList<>(this.playerCreatures.size());
        clone.addAll(this.playerCreatures);
        return clone;
    }

    /**
     * Deals some damage to the player
     * @param damage : Damage to inflict
     */
    void inflictDamages(int damage) {
        this.lifePoints -= damage;
    }


//    /**
//     * Adds a card to the hand of the player
//     * @param card : the card to be added
//     */
//    void addCard(PlayCard card){
//        this.playerHand.add(card);
//    }
//
//    /**
//     * Adds a card of the hand of the player
//     * @param card : the card to be removed
//     */
//    void removeCard(PlayCard card){
//        this.playerHand.remove(card);
//    }

}
