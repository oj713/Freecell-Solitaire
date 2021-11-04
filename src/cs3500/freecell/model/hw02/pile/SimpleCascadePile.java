package cs3500.freecell.model.hw02.pile;

import cs3500.freecell.model.hw02.card.ICard;

import java.util.ArrayList;

/**
 * Represents a cascade pile in a game of Freecell. This class extends {@code AbstractPile} and
 * provides implementation of the methods from {@code IPile<ICard>}. Cascade piles can
 * have any number of cards and a
 * new card can be added if it 1 less and has an opposite suit than the top card on the pile.
 */
public class SimpleCascadePile extends AbstractPile implements ICascadePile {
  protected final ArrayList<ICard> cascadePile;

  /**
   * Constructs a cascade pile object.
   */
  public SimpleCascadePile() {
    cascadePile = new ArrayList<ICard>();
  }

  @Override
  public boolean canAddCard(ICard card) {
    if (card == null) {
      throw new IllegalArgumentException("Cannot add a null card.");
    }
    if (cascadePile.isEmpty()) {
      return true;
    } else {
      ICard priorCard = cascadePile.get(cascadePile.size() - 1);
      if (!card.canPlayOnCascade(priorCard)) {
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
    cascadePile.add(card);
  }

  @Override
  public void removeCard(int index) {
    if (index != cascadePile.size() - 1) {
      throw new IllegalArgumentException("Invalid card index.");
    } else {
      cascadePile.remove(index);
    }
  }

  @Override
  public ICard cardAt(int index) {
    if (index < 0 || index >= cascadePile.size()) {
      throw new IllegalArgumentException("Invalid card index.");
    }
    return cascadePile.get(index);
  }

  @Override
  public int size() {
    return cascadePile.size();
  }

  @Override
  public void addUncheckedCard(ICard card) {
    cascadePile.add(card);
  }
}
