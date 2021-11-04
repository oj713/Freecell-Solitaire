package cs3500.freecell.model.hw02.pile;

import cs3500.freecell.model.hw02.card.ICard;

import java.util.ArrayList;

/**
 * Provides implementation for a cascade pile that allows for multicard moves.
 */
public class MultiMoveCascadePile implements ICascadePile {
  private final SimpleCascadePile pile;

  public MultiMoveCascadePile() {
    this.pile = new SimpleCascadePile();
  }

  @Override
  public void addCard(ICard card) throws IllegalArgumentException {
    pile.addCard(card);
  }

  @Override
  public void removeCard(int index) throws IllegalArgumentException {
    pile.removeCard(index);
  }

  @Override
  public ICard cardAt(int index) throws IllegalArgumentException {
    return pile.cardAt(index);
  }

  @Override
  public void move(int index, IPile<ICard> other) {
    if (!(other instanceof MultiMoveCascadePile)) {
      pile.move(index, other);
    } else {
      if (index < 0 || index >= pile.size()) {
        throw new IllegalArgumentException("Invalid card index.");
      }
      ArrayList<ICard> movingCards = new ArrayList<>();
      for (int i = index; i < pile.size(); i++) {
        if (i != index && !pile.cardAt(i).canPlayOnCascade(pile.cardAt(i - 1))) {
          throw new IllegalArgumentException("Illegal build for moving multiple cards.");
        }
        movingCards.add(pile.cardAt(i));
      }
      for (ICard card : movingCards) {
        other.addCard(card);
      }
      for (int i = pile.size() - 1; i >= index; i -= 1) {
        this.removeCard(i);
      }
    }
  }

  @Override
  public int size() {
    return pile.size();
  }

  @Override
  public void addUncheckedCard(ICard card) {
    pile.addUncheckedCard(card);
  }
}
