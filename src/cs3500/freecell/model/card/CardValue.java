package cs3500.freecell.model.card;

/**
 * Represents all the possible values of a card in a game
 * of Freecell: Ace, 2, 3, 4, 5, 6, 7, 8, 9, 10, Jack, Queen, and King.
 */
public enum CardValue {
  ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8),
  NINE(9), TEN(10), JACK(11), QUEEN(12), KING(13);

  private final int value;

  /**
   * Constructs a CardValue object.
   * @param value the integer value of a card.
   */
  private CardValue(int value) {
    this.value = value;
  }

  /**
   * Determines whether this card value is one higher than that of the given card value.
   * The value of Ace is 1, Jack is 11, Queen is 12, and King is 13.
   * @param value  the card value against which this card value is compared.
   * @return  true if this card value is one less than the given value and false otherwise.
   */
  public boolean oneLessThan(CardValue value) {
    if (value == null) {
      return false;
    } else {
      return this.value == (value.value - 1);
    }
  }

  @Override
  public String toString() {
    switch (this.value) {
      case(1):
        return "A";
      case(11):
        return "J";
      case(12):
        return "Q";
      case(13):
        return "K";
      default:
        return Integer.toString(value);
    }
  }
}
