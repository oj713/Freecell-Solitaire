package cs3500.freecell.model.pile;

import cs3500.freecell.model.card.ICard;

/**
 * Abstract base class for implementations of {@code IPile<ICard>}. Provides an implementation
 * of {@code move(int index, IPile<ICard>other)}.
 */
public abstract class AbstractPile implements IPile<ICard> {

  @Override
  public void move(int index, IPile<ICard> other) {
    ICard card = this.cardAt(index);
    if (other.canAddCard(card)) {
      this.removeCard(index);
      other.addCard(card);
    }
  }
}
