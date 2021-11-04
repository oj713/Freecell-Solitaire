package cs3500.freecell.model.hw02.pile;

import cs3500.freecell.model.hw02.card.CardValue;
import cs3500.freecell.model.hw02.card.ICard;

import java.util.ArrayList;

/**
 * Represents a cascade pile in a game of Freecell. This class extends {@code AbstractPile} and
 * provides implementation of the methods from {@code IPile<ICard>}. Foundation piles can have up
 * to thirteen cards, which will be ordered from Ace - King in one suit.
 */
public class FoundationPile extends AbstractPile {
  private final ArrayList<ICard> foundationPile;

  /**
   * Constructs a FoundationPile object.
   */
  public FoundationPile() {
    foundationPile = new ArrayList<ICard>();
  }

  @Override
  public boolean canAddCard(ICard card) {
    if (card == null) {
      throw new IllegalArgumentException("Cannot add a null card.");
    }
    if (foundationPile.isEmpty()) {
      if (card.getValue().equals(CardValue.ACE)) {
        return true;
      } else {
        throw new IllegalArgumentException("Cannot add a non-Ace card to empty foundation pile.");
      }
    } else {
      ICard priorCard = foundationPile.get(foundationPile.size() - 1);
      if (!card.canPlayOnFoundation(priorCard)) {
        throw new IllegalArgumentException("Cannot place this card onto this pile.");
      }
    }
    return true;
  }

  @Override
  public void addCard(ICard card) {
    if (card == null) {
      throw new IllegalArgumentException("Cannot add a null card.");
    }
    foundationPile.add(card);
  }

  @Override
  public void removeCard(int index) {
    if (index != foundationPile.size() - 1) {
      throw new IllegalArgumentException("Invalid card index.");
    } else {
      foundationPile.remove(index);
    }
  }

  @Override
  public ICard cardAt(int index) {
    if (index < 0 || index >= foundationPile.size()) {
      throw new IllegalArgumentException("Invalid card index");
    }
    return foundationPile.get(index);
  }

  @Override
  public int size() {
    return foundationPile.size();
  }
}
