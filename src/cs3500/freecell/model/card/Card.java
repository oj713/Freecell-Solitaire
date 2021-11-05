package cs3500.freecell.model.card;

import java.util.Objects;

/**
 * Represents value and suit information for playing cards.
 */
public class Card implements ICard {
  private final CardSuite suite;
  private final CardValue value;

  /**
   * Constructs an {@code Card} object.
   * @param suite     the suite of the card
   * @param value     the value of the card
   */
  public Card(CardSuite suite, CardValue value) throws IllegalArgumentException {
    if (suite == null || value == null) {
      throw new IllegalArgumentException("Card suite and value must be non-null");
    }
    this.suite = suite;
    this.value = value;
  }

  @Override
  public CardValue getValue() {
    return this.value;
  }

  @Override
  public CardSuite getSuite() {
    return this.suite;
  }

  @Override
  public boolean canPlayOnFoundation(ICard cardBelow) {
    if (cardBelow == null) {
      return this.value.equals(CardValue.ACE);
    } else {
      return this.suite.equals(cardBelow.getSuite())
          && cardBelow.getValue().oneLessThan(this.value);
    }
  }

  @Override
  public boolean canPlayOnCascade(ICard cardBelow) {
    if (cardBelow == null) {
      return true;
    } else {
      return this.value.oneLessThan(cardBelow.getValue())
          && this.suite.oppositeColor(cardBelow.getSuite());
    }
  }

  @Override
  public String toString() {
    return this.value.toString() + this.suite.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Card card = (Card) o;
    return suite == card.suite && value == card.value;
  }

  @Override
  public int hashCode() {
    return Objects.hash(suite, value);
  }
}
