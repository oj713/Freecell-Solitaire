package cs3500.freecell.model.hw02.pile;

import cs3500.freecell.model.hw02.card.ICard;

/**
 * Represents functionality for interacting with a cascade pile in a
 * game of Freecell.
 */
public interface ICascadePile extends IPile<ICard> {
  /**
   * Adds this card to a cascade pile irregardless of whether the move is valid.
   * Provides a way to set up a game of Freecell with random Cascade Piles.
   * @param card the card to be added.
   */
  public void addUncheckedCard(ICard card);
}
