package cs3500.freecell.model.hw02.card;

/**
 * Type for the four possible suites of a playing card
 * in Freecell: hearts, clubs, diamonds, and spades.
 */
public enum CardSuite {
  CLUB("♣"), DIAMOND("♦"), HEART("♥"), SPADE("♠");

  private final String value;

  /**
   * Constructor for a CardSuite object.
   * @param value the string used to represent the suite
   */
  private CardSuite(String value) {
    this.value = value;
  }

  /**
   * Determines whether the color of the given suite is opposite that of this suite.
   * Hearts and diamonds are red, while clubs and spades are black.
   * @param suite is the suite to which color will be compared against
   * @return true if the colors are different and false otherwise.
   */
  public boolean oppositeColor(CardSuite suite) {
    if (suite == null) {
      return false;
    }
    if (suite.equals(CardSuite.CLUB) || suite.equals(CardSuite.SPADE)) {
      return this.equals(CardSuite.HEART) || this.equals(CardSuite.DIAMOND);
    } else {
      return this.equals(CardSuite.CLUB) || this.equals(CardSuite.SPADE);
    }
  }

  @Override
  public String toString() {
    return this.value;
  }
}
