package cs3500.freecell.model.hw02.pile;

import cs3500.freecell.model.hw02.card.ICard;

/**
 * Represents simple functions for interacting with a pile in a game of Freecell.
 * @param <T> represents the class used for playing cards in this pile.
 */
public interface IPile<T> {

  /**
   * Adds the given card to this pile if valid.
   * @param card the card to be added.
   * @throws IllegalArgumentException if the card cannot be added to this pile.
   */
  public void addCard(T card) throws IllegalArgumentException;

  /**
   * Removes the card at the given index of this pile.
   * @param index the index of the card to be removed
   * @throws IllegalArgumentException if the index is invalid or the card cannot be removed.
   */
  public void removeCard(int index) throws IllegalArgumentException;

  /**
   * Returns the card at the given index of this pile.
   * @param index the index of the card to be returned.
   * @return the card at the given index.
   * @throws IllegalArgumentException if the index is invalid.
   */
  public ICard cardAt(int index) throws IllegalArgumentException;

  /**
   * Moves the card at the given index of this pile to the given other pile.
   * @param index the index of the card to be moved.
   * @param other the pile to which the card will be moved.
   * @throws IllegalArgumentException if the move is invalid.
   */
  public void move(int index, IPile<T> other) throws IllegalArgumentException;

  /**
   * Returns the number of cards in this pile.
   * @return the number of cards in the pile.
   */
  public int size();
}
