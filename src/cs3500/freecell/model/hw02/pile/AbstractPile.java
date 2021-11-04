package cs3500.freecell.model.hw02.pile;

import cs3500.freecell.model.hw02.card.ICard;

/**
 * Abstract base class for implementations of {@code IPile<ICard>}. Provides an implementation
 * of {@code move(int index, IPile<ICard>other)}.
 */
public abstract class AbstractPile implements IPile<ICard> {

  @Override
  public void move(int index, IPile<ICard> other) {
    ICard card = this.cardAt(index);
    other.addCard(card);
    this.removeCard(index);
  }
}
