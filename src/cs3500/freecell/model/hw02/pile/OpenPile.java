package cs3500.freecell.model.hw02.pile;

import cs3500.freecell.model.hw02.card.ICard;
import java.util.Optional;

/**
 * Represents a cascade pile in a game of Freecell. This class extends {@code AbstractPile} and
 * provides implementation of the methods fom {@code IPile<ICard>}. Open piles can only have one
 * card, which can be anything.
 */
public class OpenPile extends AbstractPile {
  private Optional<ICard> card;

  /**
   * Constructs an OpenPile object.
   */
  public OpenPile() {
    card = Optional.empty();
  }

  @Override
  public void addCard(ICard card) {
    if (card == null) {
      throw new IllegalArgumentException("Cannot add a null card");
    }
    if (this.card.isPresent()) {
      throw new IllegalArgumentException("Pile is already full.");
    } else {
      this.card = this.card.of(card);
    }
  }

  @Override
  public void removeCard(int index) {
    if (index != 0 || this.card.isEmpty()) {
      throw new IllegalArgumentException("Invalid card index.");
    } else {
      ICard card = this.card.get();
      this.card = Optional.empty();
    }
  }

  @Override
  public ICard cardAt(int index) {
    if (index != 0) {
      throw new IllegalArgumentException("Invalid card index.");
    } else {
      if (this.card.isEmpty()) {
        return null;
      } else {
        return this.card.get();
      }
    }
  }

  @Override
  public int size() {
    if (this.card.isPresent()) {
      return 1;
    } else {
      return 0;
    }
  }
}
