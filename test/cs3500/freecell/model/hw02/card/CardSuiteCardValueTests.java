package cs3500.freecell.model.hw02.card;

import cs3500.freecell.model.card.CardSuite;
import cs3500.freecell.model.card.CardValue;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the functionality of the public methods for CardSuite and CardValue.
 */
public class CardSuiteCardValueTests {
  @Test
  public void oppositeColor() {
    assertEquals(CardSuite.CLUB.oppositeColor(null), false);
    assertEquals(CardSuite.CLUB.oppositeColor(CardSuite.CLUB), false);
    assertEquals(CardSuite.CLUB.oppositeColor(CardSuite.SPADE), false);
    assertEquals(CardSuite.CLUB.oppositeColor(CardSuite.DIAMOND), true);
    assertEquals(CardSuite.DIAMOND.oppositeColor(CardSuite.SPADE), true);
  }

  @Test
  public void oneLessThan() {
    assertEquals(CardValue.TWO.oneLessThan(null), false);
    assertEquals(CardValue.TWO.oneLessThan(CardValue.THREE), true);
    assertEquals(CardValue.TWO.oneLessThan(CardValue.FOUR), false);
  }

  @Test
  public void testToString() {
    assertEquals(CardValue.ACE.toString(), "A");
    assertEquals(CardValue.KING.toString(), "K");
    assertEquals(CardValue.TWO.toString(), "2");
    assertEquals(CardSuite.CLUB.toString(), "♣");
  }
}