package cs3500.freecell.model.pile;

import cs3500.freecell.model.card.Card;
import cs3500.freecell.model.card.CardSuite;
import cs3500.freecell.model.card.CardValue;
import cs3500.freecell.model.card.ICard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the various functions of the IPile interface.
 */
public class IPileTest {
  IPile<ICard> f;
  IPile<ICard> o;
  ICascadePile sc;
  ICascadePile mc;
  ICascadePile mc2;
  Card aceOfSpades = new Card(CardSuite.SPADE, CardValue.ACE);
  Card twoOfDiamonds = new Card(CardSuite.DIAMOND, CardValue.TWO);
  Card fourOfDiamonds = new Card(CardSuite.DIAMOND, CardValue.FOUR);
  Card threeOfClubs = new Card(CardSuite.CLUB, CardValue.THREE);

  @Before
  public void setup() {
    f = new FoundationPile();
    o = new OpenPile();
    sc = new SimpleCascadePile();
    mc = new MultiMoveCascadePile();
    mc2 = new MultiMoveCascadePile();
  }

  @Test
  public void addValidCard() {
    f.addCard(aceOfSpades);
    assertEquals(f.cardAt(0), aceOfSpades);
    sc.addCard(threeOfClubs);
    assertEquals(sc.size(), 1);
    assertEquals(sc.cardAt(0), threeOfClubs);
  }

  @Test(expected = IllegalArgumentException.class)
  public void noNonAcesOnFoundation() {
    f.addCard(twoOfDiamonds);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidCardOnNonEmptyFoundation() {
    f.addCard(aceOfSpades);
    f.addCard(twoOfDiamonds);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullCardBad() {
    f.addCard(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void cantAddToFullOpenPile() {
    o.addCard(fourOfDiamonds);
    o.addCard(aceOfSpades);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidMoveOntoNonEmptyCascade() {
    sc.addCard(twoOfDiamonds);
    sc.addCard(fourOfDiamonds);
  }

  @Test
  public void removeCardWorks() {
    sc.addCard(aceOfSpades);
    assertEquals(sc.size(), 1);
    sc.removeCard(0);
    assertEquals(sc.size(), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidRemoveIndex() {
    sc.removeCard(0);
  }

  @Test
  public void cardAtWorks() {
    sc.addCard(twoOfDiamonds);
    sc.addCard(aceOfSpades);
    assertEquals(sc.cardAt(1), aceOfSpades);
    assertEquals(sc.cardAt(0), twoOfDiamonds);
  }

  @Test(expected = IllegalArgumentException.class)
  public void cardAtBreaksForInvalidIndex() {
    sc.cardAt(2);
  }

  @Test
  public void addUncheckedCardWorks() {
    sc.addUncheckedCard(aceOfSpades);
    assertEquals(sc.cardAt(0), aceOfSpades);
    sc.addUncheckedCard(fourOfDiamonds);
    assertEquals(sc.cardAt(1), fourOfDiamonds);
    mc.addUncheckedCard(aceOfSpades);
    assertEquals(mc.cardAt(0), aceOfSpades);
    mc.addUncheckedCard(fourOfDiamonds);
    assertEquals(mc.cardAt(1), fourOfDiamonds);
  }

  @Test
  public void singleMoveWorksForASimpleCase() {
    sc.addCard(aceOfSpades);
    sc.move(0, f);
    assertEquals(sc.size(), 0);
    assertEquals(f.cardAt(0), aceOfSpades);
  }

  @Test
  public void size() {
    assertEquals(sc.size(), 0);
    sc.addCard(aceOfSpades);
    assertEquals(sc.size(), 1);
  }

  // MULTI MOVE TESTS

  @Test(expected = IllegalArgumentException.class)
  public void multiMoveCantMoveMultipleCardsToFoundation() {
    mc.addCard(twoOfDiamonds);
    mc.addCard(aceOfSpades);
    mc.move(0, f);
  }

  @Test(expected = IllegalArgumentException.class)
  public void multiMoveCantMoveMultipleCardsToOpen() {
    mc.addCard(twoOfDiamonds);
    mc.addCard(aceOfSpades);
    mc.move(0, o);
  }

  @Test(expected = IllegalArgumentException.class)
  public void multiMoveCatchesHighIndex() {
    mc.addCard(twoOfDiamonds);
    mc.addCard(aceOfSpades);
    mc.move(2, mc2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void multiMoveCatchesLowIndex() {
    mc.addCard(twoOfDiamonds);
    mc.addCard(aceOfSpades);
    mc.move(-5, mc2);
  }

  @Test
  public void multiMoveWorksForMoveOntoNonEmptyPile() {
    mc.addCard(twoOfDiamonds);
    mc.addCard(aceOfSpades);
    mc2.addCard(threeOfClubs);
    mc.move(0, mc2);
    assertEquals(mc2.size(), 3);
    assertEquals(mc.size(), 0);
    assertEquals(mc2.cardAt(1), twoOfDiamonds);
  }

  @Test
  public void multiMoveWorksForMoveOntoEmptyPile() {
    mc.addCard(twoOfDiamonds);
    mc.addCard(aceOfSpades);
    mc.move(0, mc2);
    assertEquals(mc2.size(), 2);
    assertEquals(mc.size(), 0);
    assertEquals(mc2.cardAt(1), aceOfSpades);
  }

  @Test
  public void multiMoveCanStillMoveOneCard() {
    mc.addCard(aceOfSpades);
    mc.addUncheckedCard(twoOfDiamonds);
    mc.move(1, mc2);
    assertEquals(mc2.size(), 1);
    assertEquals(mc.size(), 1);
    mc.move(0, f);
    assertEquals(f.size(), 1);
    assertEquals(mc.size(), 0);
  }

  @Test
  public void multiMoveCanMovePartOfAPile() {
    mc.addCard(threeOfClubs);
    mc.addCard(twoOfDiamonds);
    mc.addCard(aceOfSpades);
    mc.move(1, mc2);
    assertEquals(mc2.size(), 2);
    assertEquals(mc.size(), 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void multiMoveCatchesInvalidBuild() {
    mc.addCard(twoOfDiamonds);
    mc.addUncheckedCard(fourOfDiamonds);
    mc2.addCard(threeOfClubs);
    mc.move(0, mc2);
  }
}