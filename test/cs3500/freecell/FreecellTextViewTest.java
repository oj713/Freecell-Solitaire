package cs3500.freecell;

import cs3500.freecell.model.FreecellModelState;
import cs3500.freecell.model.pile.pileInfo.PileType;
import cs3500.freecell.model.SimpleFreecellModel;
import cs3500.freecell.model.card.ICard;
import cs3500.freecell.view.FreecellTextView;
import cs3500.freecell.view.FreecellView;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * A class to test the functionality of FreecellTextView.
 */
public class FreecellTextViewTest {
  FreecellModelState<ICard> unstartedGame;
  FreecellModelState<ICard> minParameters;
  FreecellModelState<ICard> startedGame;
  FreecellView unstartedView;
  FreecellView gameBeginning;
  FreecellView midGame;
  StringBuilder out;

  @Before
  public void setup() {
    out = new StringBuilder();
    this.unstartedGame = new SimpleFreecellModel();
    SimpleFreecellModel minParameters = new SimpleFreecellModel();
    minParameters.startGame(minParameters.getDeck(), 4, 1, false);
    this.minParameters = minParameters;
    SimpleFreecellModel startedGame = new SimpleFreecellModel();
    startedGame.startGame(startedGame.getDeck(), 7, 2, false);
    startedGame.move(PileType.CASCADE, 4, 6, PileType.OPEN, 1);
    startedGame.move(PileType.CASCADE, 5, 6, PileType.OPEN, 0);
    startedGame.move(PileType.CASCADE, 4, 5, PileType.FOUNDATION, 2);
    startedGame.move(PileType.CASCADE, 5, 5, PileType.FOUNDATION, 2);
    this.startedGame = startedGame;

    unstartedView = new FreecellTextView(unstartedGame);
    gameBeginning = new FreecellTextView(minParameters);
    midGame = new FreecellTextView(startedGame, out);
  }

  @Test(expected = IllegalArgumentException.class)
  public void givenModelCannotBeNull() {
    new FreecellTextView(null, new StringBuilder());
  }

  @Test(expected = IllegalArgumentException.class)
  public void givenAppendableCannotBeNull() {
    new FreecellTextView(new SimpleFreecellModel(), null);
  }

  @Test
  public void toStringWorksForUnstartedNewAndMidGameModels() {
    assertEquals(unstartedView.toString(), "");
    assertEquals(gameBeginning.toString(), "F1:\nF2:\nF3:\nF4:\nO1:\n"
        + "C1: A♣, 5♣, 9♣, K♣, 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠\n"
        + "C2: 2♣, 6♣, 10♣, A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠\n"
        + "C3: 3♣, 7♣, J♣, 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠\n"
        + "C4: 4♣, 8♣, Q♣, 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠");
    assertEquals(midGame.toString(), "F1:\nF2:\nF3: A♠, 2♠\nF4:\nO1: 9♠\nO2: 8♠\n"
        + "C1: A♣, 8♣, 2♦, 9♦, 3♥, 10♥, 4♠, J♠\n"
        + "C2: 2♣, 9♣, 3♦, 10♦, 4♥, J♥, 5♠, Q♠\n"
        + "C3: 3♣, 10♣, 4♦, J♦, 5♥, Q♥, 6♠, K♠\n"
        + "C4: 4♣, J♣, 5♦, Q♦, 6♥, K♥, 7♠\n"
        + "C5: 5♣, Q♣, 6♦, K♦, 7♥\n"
        + "C6: 6♣, K♣, 7♦, A♥, 8♥\n"
        + "C7: 7♣, A♦, 8♦, 2♥, 9♥, 3♠, 10♠");
  }

  @Test
  public void renderGameWorks() throws IOException {
    midGame.renderBoard();
    assertEquals(out.toString(), "F1:\nF2:\nF3: A♠, 2♠\nF4:\nO1: 9♠\nO2: 8♠\n"
        + "C1: A♣, 8♣, 2♦, 9♦, 3♥, 10♥, 4♠, J♠\n"
        + "C2: 2♣, 9♣, 3♦, 10♦, 4♥, J♥, 5♠, Q♠\n"
        + "C3: 3♣, 10♣, 4♦, J♦, 5♥, Q♥, 6♠, K♠\n"
        + "C4: 4♣, J♣, 5♦, Q♦, 6♥, K♥, 7♠\n"
        + "C5: 5♣, Q♣, 6♦, K♦, 7♥\n"
        + "C6: 6♣, K♣, 7♦, A♥, 8♥\n"
        + "C7: 7♣, A♦, 8♦, 2♥, 9♥, 3♠, 10♠");
  }

  @Test
  public void renderMessageWorks() throws IOException {
    midGame.renderMessage("hello");
    assertEquals(out.toString(), "hello");
  }
}