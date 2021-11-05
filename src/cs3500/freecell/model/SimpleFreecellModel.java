package cs3500.freecell.model;

import cs3500.freecell.model.card.Card;
import cs3500.freecell.model.card.CardSuite;
import cs3500.freecell.model.card.CardValue;
import cs3500.freecell.model.card.ICard;
import cs3500.freecell.model.pile.*;
import cs3500.freecell.model.pile.pileInfo.PileType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Represents a model of the game Freecell as lists of each pile in the game, the card within them,
 * and whether or not the game has been started.
 */
public class SimpleFreecellModel implements FreecellModel<ICard> {
  private ArrayList<OpenPile> openPiles;
  /*
  This code is different than the original implementation. The type for cascadePiles is now
  ArrayList<ICascadePile> rather than ArrayList<SimpleCascadePile> so that
  MultiMoveFreecellModel can initialize cascadePiles to use MultiMoveCascadePile instead.
   */
  private ArrayList<IPile<ICard>> cascadePiles;
  private ArrayList<FoundationPile> foundationPiles;
  private boolean gameStarted;

  /**
   * Constructs a SimpleFreecellModel object.
   */
  public SimpleFreecellModel() {
    openPiles = new ArrayList<OpenPile>();
    cascadePiles = new ArrayList<IPile<ICard>>();
    foundationPiles = new ArrayList<FoundationPile>();
    gameStarted = false;
  }

  @Override
  public List<ICard> getDeck() {
    List<ICard> deck = new ArrayList<ICard>(52);
    for (CardSuite suite : CardSuite.values()) {
      for (CardValue value : CardValue.values()) {
        deck.add(new Card(suite, value));
      }
    }
    return deck;
  }

  @Override
  public void startGame(List<ICard> deck, int numCascadePiles, int numOpenPiles, boolean shuffle)
      throws IllegalArgumentException {
    checkValidDeck(deck);
    if (shuffle) {
      deck = shuffle(deck);
    }
    if (numCascadePiles < 4 || numOpenPiles < 1) {
      throw new IllegalArgumentException("Must have at least 4 cascade piles and 1 open pile");
    }
    foundationPiles = new ArrayList<FoundationPile>(Arrays.asList(new FoundationPile(),
        new FoundationPile(), new FoundationPile(), new FoundationPile()));
    openPiles = new ArrayList<OpenPile>(numOpenPiles);

    /*
    The below line of code has been modified from its original. Instead of creating
    cascadePiles inside of startGame(), this class now delegates the task to a helper function
    that can be overridden by MultiMoveFreecellModel.
     */
    cascadePiles = this.generateCascadePiles(numCascadePiles);

    for (int j = 0; j < numOpenPiles; j++) {
      openPiles.add(new OpenPile());
    }
    for (int k = 0; k < 52; k++) {
      cascadePiles.get(k % numCascadePiles).addCard(deck.get(k));
    }
    gameStarted = true;
  }

  /**
   * Returns a new ArrayList to represent cascade piles for a game of Freecell.
   * @param numCascadePiles is the number of cascade piles.
   * @return an array list representing cascade piles.
   */
  protected ArrayList<IPile<ICard>> generateCascadePiles(int numCascadePiles) {
    ArrayList<IPile<ICard>> newCascadePiles = new ArrayList<>(numCascadePiles);

    for (int i = 0; i < numCascadePiles; i++) {
      newCascadePiles.add(new SimpleCascadePile());
    }
    return newCascadePiles;
  }

  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
                   int destPileNumber) throws IllegalArgumentException, IllegalStateException {
    this.checkGameStart();
    if (source == null || destination == null) {
      throw new IllegalArgumentException("Source and destination cannot be null.");
    }
    IPile<ICard> sourcePile = getPile(source, pileNumber);
    IPile<ICard> destPile = getPile(destination, destPileNumber);
    /*
    The below if statement has been added and is a modification to the move function.
    It throws an exception if too many cards are moved at once. This modification was
    necessary because the extended MultiMoveFreecellModel class can override the
    getMaxCardsCanMove() function to properly enforce max card moves in its model.
     */
    if (sourcePile.size() - cardIndex > this.getMaxCardsCanMove()) {
      throw new IllegalArgumentException("Cannot currently move this many cards at once.");
    }
    sourcePile.move(cardIndex, destPile);
  }

  /**
   * Returns the maximum number of cards that can currently be moved at once in a game of Freecell.
   * @return the max number of moveable cards.
   */
  protected int getMaxCardsCanMove() {
    return 1;
  }

  /**
   * Retrieves the freecell pile corresponding to the given pile type and pile number.
   * @param source      the pile type of the pile to be retrieved
   * @param pileNumber  the index of the pile to be retrieved
   * @return  a pile based on the given input.
   * @throws IllegalStateException if given piletype does not exist.
   */
  private IPile<ICard> getPile(PileType source, int pileNumber) throws IllegalStateException {
    switch (source) {
      case CASCADE:
        checkInBound(pileNumber, this.getNumCascadePiles());
        return cascadePiles.get(pileNumber);
      case OPEN:
        checkInBound(pileNumber, this.getNumOpenPiles());
        return openPiles.get(pileNumber);
      case FOUNDATION:
        checkInBound(pileNumber, 4);
        return foundationPiles.get(pileNumber);
      default:
        throw new IllegalStateException("Should not get here");
    }
  }

  @Override
  public boolean isGameOver() {
    if (gameStarted) {
      for (FoundationPile pile : foundationPiles) {
        if (pile.size() != 13) {
          return false;
        }
      }
      return true;
    } else {
      return false;
    }
  }

  @Override
  public int getNumCardsInFoundationPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    this.checkGameStart();
    checkInBound(index, 4);
    return foundationPiles.get(index).size();
  }

  @Override
  public int getNumCascadePiles() {
    if (gameStarted) {
      return cascadePiles.size();
    } else {
      return -1;
    }
  }

  @Override
  public int getNumCardsInCascadePile(int index)
      throws IllegalArgumentException, IllegalStateException {
    this.checkGameStart();
    checkInBound(index, this.getNumCascadePiles());
    return cascadePiles.get(index).size();
  }

  @Override
  public int getNumCardsInOpenPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    this.checkGameStart();
    checkInBound(index, this.getNumOpenPiles());
    return openPiles.get(index).size();
  }

  @Override
  public int getNumOpenPiles() {
    if (gameStarted) {
      return openPiles.size();
    } else {
      return -1;
    }
  }

  @Override
  public ICard getFoundationCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    this.checkGameStart();
    checkInBound(pileIndex, 4);
    return foundationPiles.get(pileIndex).cardAt(cardIndex);
  }

  @Override
  public ICard getCascadeCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    this.checkGameStart();
    checkInBound(pileIndex, this.getNumCascadePiles());
    return cascadePiles.get(pileIndex).cardAt(cardIndex);
  }

  @Override
  public ICard getOpenCardAt(int pileIndex)
      throws IllegalArgumentException, IllegalStateException {
    this.checkGameStart();
    checkInBound(pileIndex, this.getNumOpenPiles());
    return openPiles.get(pileIndex).cardAt(0);
  }

  /**
   * Throws an exception if the given deck of cards is invalid. A deck is invalid if it
   * has one or more of the following flaws:
   * <ul><li>It does not have 52 cards</li>
   * <li>It has duplicate cards</li></ul>
   *
   * @param deck the deck of cards for which to determine validity
   * @throws IllegalArgumentException if the deck is invalid
   */
  private static void checkValidDeck(List<ICard> deck) throws IllegalArgumentException {
    if (deck.size() != 52) {
      throw new IllegalArgumentException("Deck wrong size");
    }
    ArrayList<ICard> deck2 = new ArrayList<ICard>(52);
    for (ICard card: deck) {
      if (deck2.contains(card)) {
        throw new IllegalArgumentException("Invalid card in deck");
      }
      deck2.add(card);
    }
  }

  /**
   * Shuffles a deck of cards.
   * @param deck  the deck of cards to be shuffled
   * @return   the shuffled deck of cards.
   */
  private static List<ICard> shuffle(List<ICard> deck) {
    List<ICard> shuffledDeck = new ArrayList<ICard>(52);
    for (int i = 0; i < 52; i++) {
      shuffledDeck.add(deck.remove(new Random().nextInt(deck.size())));
    }
    return shuffledDeck;
  }

  /**
   * Throws an exception if the game has not yet started.
   * @throws IllegalStateException if the game has not started.
   */
  private void checkGameStart() throws IllegalStateException {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started");
    }
  }

  /**
   * Throws an exception if the provided index isn't within an appropriate range between 0
   * and upperBound.
   * @param index the index for which we are checking validity
   * @param upperBound  the acceptable upperbound for the index (exclusive)
   * @throws IllegalArgumentException if the index isn't within 0 and upperBound
   */
  private static void checkInBound(int index, int upperBound) throws IllegalArgumentException {
    if (index < 0 || index >= upperBound) {
      throw new IllegalArgumentException("Invalid index");
    }
  }
}
