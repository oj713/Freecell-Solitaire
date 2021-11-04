package cs3500.freecell.model.hw02.card;

import cs3500.freecell.model.hw02.card.CardSuite;
import cs3500.freecell.model.hw02.card.CardValue;

/**
 * This the interface of a card model, that specifies the basic functions of a playing card in
 * a game of Freecell. These functions include determining the values of a card and whether
 * it can be played in specific scenarios.
 */
public interface ICard {
  /**
   * Retrieves the value of a card.
   * @return the value of the card.
   */
  CardValue getValue();

  /**
   * Retrieves the suite of a card.
   * @return the suite of the card.
   */
  CardSuite getSuite();

  /**
   * Determines if this card be played on a foundation pile given the current top card of the pile.
   * @param cardBelow is the current top card of the pile
   * @return whether the current card can be played.
   */
  boolean canPlayOnFoundation(ICard cardBelow);

  /**
   * Determines if this card be played on a cascade pile given the current top card of the pile.
   * @param cardBelow is the current top card of the pile
   * @return whether the current card can be played.
   */
  boolean canPlayOnCascade(ICard cardBelow);
}
