package cs3500.freecell.model.hw02;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.pile.pileInfo.PileType;
import cs3500.freecell.model.SimpleFreecellModel;
import cs3500.freecell.model.card.Card;
import cs3500.freecell.model.card.CardSuite;
import cs3500.freecell.model.card.CardValue;
import cs3500.freecell.model.card.ICard;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Class to test SimpleFreecellModel.
 */
public class SimpleFreecellModelTest {
  private FreecellModel<ICard> basic;
  private FreecellModel<ICard> unstartedGame;
  private FreecellModel<ICard> minParameters;
  private FreecellModel<ICard> lotsOfPiles;
  Card aceOfClubs = new Card(CardSuite.CLUB, CardValue.ACE);

  @Before
  public void setUp() {
    basic = new SimpleFreecellModel();
    unstartedGame = new SimpleFreecellModel();
    minParameters = new SimpleFreecellModel();
    lotsOfPiles = new SimpleFreecellModel();

    basic.startGame(basic.getDeck(), 8, 2, false);
    minParameters.startGame(basic.getDeck(), 4, 1, false);
    lotsOfPiles.startGame(basic.getDeck(), 53, 4, false);
  }

  @Test
  public void getDeckIsTheRightSizeAndHasCorrectFirstAndLastCards() {
    assertEquals(basic.getDeck().size(), 52);
    assertEquals(basic.getDeck().get(0), aceOfClubs);
    assertEquals(basic.getDeck().get(51), new Card(CardSuite.SPADE, CardValue.KING));
  }

  // tests for startGame

  @Test
  public void testingThatStartGameDealsTheRightNumberOfCardsToTheRightNumOfPiles() {
    assertEquals(basic.getNumCascadePiles(), 8);
    assertEquals(basic.getNumOpenPiles(), 2);
    assertEquals(basic.getNumCardsInFoundationPile(3), 0);
    assertEquals(basic.getNumCardsInCascadePile(3), 7);
    assertEquals(basic.getNumCardsInOpenPile(0), 0);
    assertEquals(basic.getNumCardsInCascadePile(4), 6);
    assertEquals(minParameters.getNumCascadePiles(), 4);
    assertEquals(minParameters.getNumOpenPiles(), 1);
    assertEquals(lotsOfPiles.getNumCardsInCascadePile(0), 1);
    assertEquals(lotsOfPiles.getNumCardsInCascadePile(52), 0);
  }

  @Test
  public void testingThatStartGameDealsRoundRobinFashion() {
    assertEquals(basic.getCascadeCardAt(0, 0), aceOfClubs);
    assertEquals(basic.getCascadeCardAt(3, 6),
        new Card(CardSuite.SPADE, CardValue.KING));
    assertEquals(minParameters.getCascadeCardAt(1, 0),
        new Card(CardSuite.CLUB, CardValue.TWO));
    assertEquals(minParameters.getCascadeCardAt(0, 1),
        new Card(CardSuite.CLUB, CardValue.FIVE));
  }

  @Test
  public void testingThatStartGameShufflesTheDeck() {
    basic.startGame(basic.getDeck(), 8, 2, true);
    //note: there is a small chance that even if shuffled the first three cards might stay the
    //same (1 / 140608), so this test may fail.
    assertEquals((basic.getCascadeCardAt(0, 0) != aceOfClubs)
                         && (basic.getCascadeCardAt(1, 0)
                            != new Card(CardSuite.CLUB, CardValue.TWO))
                         && (basic.getCascadeCardAt(2, 0)
                            != new Card(CardSuite.CLUB, CardValue.THREE)),
                  true);
  }

  @Test
  public void testStartGameResetsPlay() {
    basic.move(PileType.CASCADE, 0, 6, PileType.OPEN, 0);
    basic.startGame(basic.getDeck(), 8, 2, false);
    assertEquals(basic.getNumCardsInOpenPile(0), 0);
    assertEquals(basic.getNumCardsInCascadePile(0), 7);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameCatchesInvalidDeckWrongSize() {
    basic.startGame(new ArrayList<ICard>(Arrays.asList(new Card(CardSuite.CLUB, CardValue.THREE))),
        4, 5, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameCatchesInvalidDeckDuplicateCard() {
    List<ICard> badDeck = basic.getDeck();
    badDeck.remove(0);
    badDeck.add(new Card(CardSuite.CLUB, CardValue.THREE));
    basic.startGame(badDeck, 4, 5, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameWontStartGameWithLessThanFourCascadePiles() {
    basic.startGame(basic.getDeck(), 3, 4, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameBreaksForLessThanOneOpenPiles() {
    basic.startGame(basic.getDeck(), 4, 0, true);
  }

  // tests for Move

  @Test
  public void testingValidMovesOnOpenPile() {
    basic.move(PileType.CASCADE, 0, 6, PileType.OPEN, 0);
    assertEquals(basic.getNumCardsInOpenPile(0), 1);
    assertEquals(basic.getNumCardsInCascadePile(0), 6);
    basic.move(PileType.OPEN, 0, 0, PileType.OPEN, 1);
    assertEquals(basic.getNumCardsInOpenPile(0), 0);
    assertEquals(basic.getOpenCardAt(1), new Card(CardSuite.SPADE, CardValue.TEN));
  }

  @Test(expected = IllegalArgumentException.class)
  public void illegalMoveOntoFullOpenPile() {
    basic.move(PileType.CASCADE, 0, 6, PileType.OPEN, 0);
    basic.move(PileType.CASCADE, 0, 5, PileType.OPEN, 0);
  }

  @Test
  public void validMovesOntoEmptyFoundation() {
    basic.move(PileType.CASCADE, 7, 5, PileType.OPEN, 0);
    basic.move(PileType.CASCADE, 7, 4, PileType.FOUNDATION, 1);
    assertEquals(basic.getNumCardsInFoundationPile(1), 1);
    assertEquals(basic.getNumCardsInCascadePile(7), 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void illegalMoveOntoEmptyFoundation() {
    basic.move(PileType.CASCADE, 0, 6, PileType.FOUNDATION, 2);
  }

  @Test
  public void validMoveOntoNonemptyFoundation() {
    lotsOfPiles.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    lotsOfPiles.move(PileType.CASCADE, 1, 0, PileType.FOUNDATION, 0);
    assertEquals(lotsOfPiles.getFoundationCardAt(0, 1),
        new Card(CardSuite.CLUB, CardValue.TWO));
    assertEquals(lotsOfPiles.getNumCardsInCascadePile(1), 0);
    assertEquals(lotsOfPiles.getCascadeCardAt(39, 0),
        new Card(CardSuite.SPADE, CardValue.ACE));
    lotsOfPiles.move(PileType.CASCADE, 39, 0,
        PileType.FOUNDATION, 3);
    assertEquals(lotsOfPiles.getFoundationCardAt(3, 0),
        new Card(CardSuite.SPADE, CardValue.ACE));
  }

  @Test(expected = IllegalArgumentException.class)
  public void illegalMoveOntoFoundationWrongSuit() {
    lotsOfPiles.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    lotsOfPiles.move(
        PileType.CASCADE, 14, 0, PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void illegalMoveOntoFoundationWrongValue() {
    lotsOfPiles.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    lotsOfPiles.move(
        PileType.CASCADE, 3, 0, PileType.FOUNDATION, 0);
  }

  @Test
  public void legalMovesOntoCascadePileEmptyAndNonEmpty() {
    lotsOfPiles.move(PileType.CASCADE, 2,0, PileType.CASCADE, 52);
    assertEquals(lotsOfPiles.getCascadeCardAt(52, 0),
        new Card(CardSuite.CLUB, CardValue.THREE));
    assertEquals(lotsOfPiles.getNumCardsInCascadePile(2), 0);
    lotsOfPiles.move(PileType.CASCADE, 0, 0, PileType.CASCADE, 14);
    assertEquals(lotsOfPiles.getNumCardsInCascadePile(14), 2);
    assertEquals(lotsOfPiles.getNumCardsInCascadePile(0), 0);
    lotsOfPiles.move(PileType.CASCADE, 15, 0, PileType.CASCADE, 3);
    assertEquals(lotsOfPiles.getNumCardsInCascadePile(3), 2);
    assertEquals(lotsOfPiles.getNumCardsInCascadePile(15), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void illegalMoveOnCascadePileRedOnRed() {
    lotsOfPiles.move(PileType.CASCADE, 14, 0, PileType.CASCADE, 28);
  }

  @Test(expected = IllegalArgumentException.class)
  public void illegalMoveOnCascadePileBlackOnBlack() {
    lotsOfPiles.move(PileType.CASCADE, 0, 0, PileType.CASCADE, 40);
  }

  @Test(expected = IllegalArgumentException.class)
  public void illegalMoveOnCascadeInvalidValue() {
    lotsOfPiles.move(PileType.CASCADE, 0, 0, PileType.CASCADE, 17);
  }

  @Test(expected = IllegalArgumentException.class)
  public void illegalMoveToOutOfBoundPile() {
    basic.move(PileType.CASCADE, 0, 6, PileType.OPEN, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void illegalMoveFromOutOfBoundPile() {
    basic.move(PileType.CASCADE, -2, 6, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void illegalMoveFromOutOfBoundCardIndex() {
    basic.move(PileType.CASCADE, 0, 7, PileType.OPEN, 1);
  }

  @Test(expected = IllegalStateException.class)
  public void moveBeforeGameStartIsBad() {
    unstartedGame.move(PileType.CASCADE, 3, 2, PileType.FOUNDATION, 2);
  }

  @Test
  public void isGameOverWorksCorrectly() {
    assertEquals(unstartedGame.isGameOver(), false);
    assertEquals(basic.isGameOver(), false);
    lotsOfPiles.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    assertEquals(lotsOfPiles.isGameOver(), false);
    for (int i = 1; i < 52; i ++) {
      lotsOfPiles.move(PileType.CASCADE, i, 0, PileType.FOUNDATION, i / 13);
    }
    assertEquals(lotsOfPiles.isGameOver(), true);
  }

  @Test
  public void getNumCardsInFoundationPileWorks() {
    assertEquals(lotsOfPiles.getNumCardsInFoundationPile(0), 0);
    lotsOfPiles.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    assertEquals(lotsOfPiles.getNumCardsInFoundationPile(0), 1);
    assertEquals(lotsOfPiles.getNumCardsInFoundationPile(1), 0);
  }

  @Test(expected = IllegalStateException.class)
  public void getNumCardsInFoundationPileBreaksForUnstartedGame() {
    unstartedGame.getNumCardsInFoundationPile(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getNumCardsInFoundationPileBreaksForTooHighIndex() {
    basic.getNumCardsInFoundationPile(9);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getNumCardsInFoundationPileBreaksForTooLowIndex() {
    basic.getNumCardsInFoundationPile(-1);
  }

  @Test
  public void getNumCascadePiles() {
    assertEquals(basic.getNumCascadePiles(), 8);
    assertEquals(lotsOfPiles.getNumCascadePiles(), 53);
    assertEquals(unstartedGame.getNumCascadePiles(), -1);
  }

  @Test
  public void getNumCardsInCascadePileWorks() {
    assertEquals(lotsOfPiles.getNumCardsInCascadePile(0), 1);
    assertEquals(lotsOfPiles.getNumCardsInCascadePile(52), 0);
  }

  @Test(expected = IllegalStateException.class)
  public void getNumCardsInCascadePileBreaksForUnstartedGame() {
    unstartedGame.getNumCardsInCascadePile(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getNumCardsInCascadePileBreaksForTooHighIndex() {
    basic.getNumCardsInCascadePile(9);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getNumCardsInCascadePileBreaksForTooLowIndex() {
    basic.getNumCardsInCascadePile(-1);
  }

  @Test
  public void getNumCardsInOpenPileWorks() {
    assertEquals(lotsOfPiles.getNumCardsInOpenPile(0), 0);
    lotsOfPiles.move(PileType.CASCADE, 0, 0, PileType.OPEN, 0);
    assertEquals(lotsOfPiles.getNumCardsInOpenPile(0), 1);
    assertEquals(lotsOfPiles.getNumCardsInOpenPile(1), 0);
  }

  @Test(expected = IllegalStateException.class)
  public void getNumCardsInOpenPileBreaksForUnstartedGame() {
    unstartedGame.getNumCardsInOpenPile(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getNumCardsInOpenPileBreaksForTooHighIndex() {
    basic.getNumCardsInOpenPile(9);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getNumCardsInOpenPileBreaksForTooLowIndex() {
    basic.getNumCardsInOpenPile(-1);
  }

  @Test
  public void getNumOpenPiles() {
    assertEquals(basic.getNumOpenPiles(), 2);
    assertEquals(lotsOfPiles.getNumOpenPiles(), 4);
    assertEquals(unstartedGame.getNumOpenPiles(), -1);
  }

  @Test
  public void getFoundationCardAtWorks() {
    lotsOfPiles.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    lotsOfPiles.move(PileType.CASCADE, 1, 0, PileType.FOUNDATION, 0);
    assertEquals(lotsOfPiles.getFoundationCardAt(0, 0), aceOfClubs);
    assertEquals(lotsOfPiles.getFoundationCardAt(0, 1),
        new Card(CardSuite.CLUB, CardValue.TWO));
  }

  @Test(expected = IllegalStateException.class)
  public void getFoundationCardAtBreaksForUnstartedGame() {
    unstartedGame.getFoundationCardAt(0, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getFoundationCardAtBreaksForOutOfBoundsPile() {
    basic.getFoundationCardAt(-2, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getFoundationCardAtBreaksForOutOfBoundsCard() {
    basic.getFoundationCardAt(0, 0);
  }

  @Test
  public void getCascadeCardAtWorks() {
    assertEquals(basic.getCascadeCardAt(0, 0), aceOfClubs);
    assertEquals(basic.getCascadeCardAt(2, 3),
        new Card(CardSuite.HEART, CardValue.ACE));
  }

  @Test(expected = IllegalStateException.class)
  public void getCascadeCardAtBreaksForUnstartedGame() {
    unstartedGame.getCascadeCardAt(0, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCascadeCardAtBreaksForOutOfBoundsPile() {
    basic.getCascadeCardAt(9, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCascadeCardAtBreaksForOutOfBoundsCard() {
    basic.getCascadeCardAt(0, 7);
  }

  @Test
  public void getOpenCardAtWorks() {
    lotsOfPiles.move(PileType.CASCADE, 0, 0, PileType.OPEN, 0);
    lotsOfPiles.move(PileType.CASCADE, 1, 0, PileType.OPEN, 1);
    assertEquals(lotsOfPiles.getOpenCardAt(0), aceOfClubs);
    assertEquals(lotsOfPiles.getOpenCardAt(1),
        new Card(CardSuite.CLUB, CardValue.TWO));
    assertEquals(minParameters.getOpenCardAt(0), null);
  }

  @Test(expected = IllegalStateException.class)
  public void getOpenCardAtBreaksForUnstartedGame() {
    unstartedGame.getOpenCardAt(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getOpenCardAtBreaksForOutOfBoundsPile() {
    basic.getOpenCardAt(5);
  }
}