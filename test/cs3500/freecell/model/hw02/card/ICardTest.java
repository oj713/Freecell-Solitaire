import cs3500.freecell.model.card.*;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the various methods of the ICard interface.
 */
public class ICardTest {
  Card aceOfSpades = new Card(CardSuite.SPADE, CardValue.ACE);
  Card twoOfSpades = new Card(CardSuite.SPADE, CardValue.TWO);
  Card twoOfDiamonds = new Card(CardSuite.DIAMOND, CardValue.TWO);
  Card fourOfDiamonds = new Card(CardSuite.DIAMOND, CardValue.FOUR);
  Card threeOfClubs = new Card(CardSuite.CLUB, CardValue.THREE);

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorBreaksForNullSuite() {
    new Card(null, CardValue.EIGHT);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorBreaksForNullValue() {
    new Card(CardSuite.CLUB, null);
  }

  @Test
  public void getValue() {
    assertEquals(aceOfSpades.getValue(), CardValue.ACE);
    assertEquals(fourOfDiamonds.getValue(), CardValue.FOUR);
  }

  @Test
  public void getSuite() {
    assertEquals(aceOfSpades.getSuite(), CardSuite.SPADE);
    assertEquals(fourOfDiamonds.getSuite(), CardSuite.DIAMOND);
  }

  @Test
  public void canPlayOnFoundation() {
    assertEquals(aceOfSpades.canPlayOnFoundation(null), true);
    assertEquals(aceOfSpades.canPlayOnFoundation(twoOfSpades), false);
    assertEquals(twoOfSpades.canPlayOnFoundation(aceOfSpades), true);
    assertEquals(threeOfClubs.canPlayOnFoundation(fourOfDiamonds), false);
  }

  @Test
  public void canPlayOnCascade() {
    assertEquals(twoOfDiamonds.canPlayOnCascade(null), true);
    assertEquals(twoOfDiamonds.canPlayOnCascade(threeOfClubs), true);
    assertEquals(aceOfSpades.canPlayOnCascade(twoOfDiamonds), true);
    assertEquals(aceOfSpades.canPlayOnCascade(twoOfSpades), false);
    assertEquals(threeOfClubs.canPlayOnCascade(fourOfDiamonds), true);
    assertEquals(threeOfClubs.canPlayOnCascade(fourOfDiamonds), true);
  }

  @Test
  public void toStringWorks() {
    assertEquals(aceOfSpades.toString(), "A♠");
    assertEquals(fourOfDiamonds.toString(), "4♦");
  }
}