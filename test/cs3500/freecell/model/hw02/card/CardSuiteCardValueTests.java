package cs3500.freecell.model.hw02.card;

import cs3500.freecell.model.card.CardSuite;
import cs3500.freecell.model.card.CardValue;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the functionality of the public methods for CardSuite and CardValue.
 */
public class CardSuiteCardValueTests {
  @Test
  public void oppositeColor() {
    assertFalse(CardSuite.CLUB.oppositeColor(null));
    assertFalse(CardSuite.CLUB.oppositeColor(CardSuite.CLUB));
    assertFalse(CardSuite.CLUB.oppositeColor(CardSuite.SPADE));
    assertTrue(CardSuite.CLUB.oppositeColor(CardSuite.DIAMOND));
    assertTrue(CardSuite.DIAMOND.oppositeColor(CardSuite.SPADE));
  }

  @Test
  public void oneLessThan() {
    assertFalse(CardValue.TWO.oneLessThan(null));
    assertTrue(CardValue.TWO.oneLessThan(CardValue.THREE));
    assertFalse(CardValue.TWO.oneLessThan(CardValue.FOUR));
  }

  @Test
  public void testToString() {
    assertEquals(CardValue.ACE.toString(), "A");
    assertEquals(CardValue.KING.toString(), "K");
    assertEquals(CardValue.TWO.toString(), "2");
    assertEquals(CardSuite.CLUB.toString(), "♣");
  }
}