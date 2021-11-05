package cs3500.freecell;

import cs3500.freecell.controller.FreecellController;
import cs3500.freecell.controller.SimpleFreecellController;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.SimpleFreecellModel;
import cs3500.freecell.model.card.ICard;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests the {@code playGame()} function of the {@code FreecellController} interface.
 */
public class FreecellControllerTest {
  List<ICard> genDeck = new SimpleFreecellModel().getDeck();
  String quitMsg = "Game quit prematurely.";
  String invalidCuz = "Invalid move. Try again. Reason: ";
  SimpleFreecellModel model;
  String lotsOfPilesString = "F1:\nF2:\nF3:\nF4:\nO1:\nO2:\nC1: A♣\nC2: 2♣\nC3: 3♣\nC4: 4♣\nC5: 5♣"
      + "\nC6: 6♣\nC7: 7♣\nC8: 8♣\nC9: 9♣\nC10: 10♣\nC11: J♣\nC12: Q♣\nC13: K♣\nC14: A♦\nC15: 2♦\n"
      + "C16: 3♦\nC17: 4♦\nC18: 5♦\nC19: 6♦\nC20: 7♦\nC21: 8♦\nC22: 9♦\nC23: 10♦\nC24: J♦\n"
      + "C25: Q♦\nC26: K♦\nC27: A♥\nC28: 2♥\nC29: 3♥\nC30: 4♥\nC31: 5♥\nC32: 6♥\nC33: 7♥\nC34: 8♥"
      + "\nC35: 9♥\nC36: 10♥\nC37: J♥\nC38: Q♥\nC39: K♥\nC40: A♠\nC41: 2♠\nC42: 3♠\nC43: 4♠\n"
      + "C44: 5♠\nC45: 6♠\nC46: 7♠\nC47: 8♠\nC48: 9♠\nC49: 10♠\nC50: J♠\nC51: Q♠\nC52: K♠\n";
  String minParametersString = "F1:\nF2:\nF3:\nF4:\nO1:\n"
      + "C1: A♣, 5♣, 9♣, K♣, 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠\n"
      + "C2: 2♣, 6♣, 10♣, A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠\n"
      + "C3: 3♣, 7♣, J♣, 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠\n"
      + "C4: 4♣, 8♣, Q♣, 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠, K♠\n";

  @Before
  public void setup() {
    model = new SimpleFreecellModel();
  }

  @Test (expected = IllegalArgumentException.class)
  public void nullModelBreaksConstructor() {
    new SimpleFreecellController(null, new StringReader(""), new StringBuilder());
  }

  @Test (expected = IllegalArgumentException.class)
  public void nullReadableBreaksConstructor() {
    new SimpleFreecellController(model, null, new StringBuilder());
  }

  @Test (expected = IllegalArgumentException.class)
  public void nullAppendableBreaksConstructor() {
    new SimpleFreecellController(model, new StringReader(""), null);
  }

  @Test (expected = IllegalStateException.class)
  public void failToReadFromString() {
    new SimpleFreecellController(model, new StringReader(""),
        new StringBuilder()).playGame(genDeck, 4, 1, false);
  }

  @Test (expected = IllegalStateException.class)
  public void failToWriteToAppendable() {
    new SimpleFreecellController(model, new StringReader("q"),
        new BogusAppendable()).playGame(genDeck, 4, 1, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullDeckBreaksPlayGame() {
    new SimpleFreecellController(model, new StringReader(""),
        new StringBuilder()).playGame(null, 4, 1, false);
  }

  @Test
  public void gameCorrectlyInitializesWithoutErrors() {
    assertTrue(testPlayGame(model, genDeck, 52, 2, false,
        prints(lotsOfPilesString), inputs("q 7 O1"), prints(quitMsg)));
    assertTrue(testPlayGame(model, genDeck, 4, 1, false,
        prints(minParametersString), inputs("C4 Q 99"), prints(quitMsg)));
    assertTrue(testPlayGame(model, genDeck, 4, 1, false,
        prints(minParametersString), inputs("C4 5 q"), prints(quitMsg)));
  }

  @Test
  public void gameCorrectlyRecognizesQuit() {
    assertTrue(testPlayGame(model, genDeck, 52, 2, false,
        prints(lotsOfPilesString), inputs("q"), prints(quitMsg)));
    assertTrue(testPlayGame(model, genDeck, 4, 1, false,
        prints(minParametersString), inputs("C4 Q 99"), prints(quitMsg)));
  }

  @Test
  public void wontStartGameForInvalidParams() {
    assertTrue(testPlayGame(model, genDeck, 3, 2, false,
        prints("Could not start game.")));
    assertTrue(testPlayGame(model, genDeck, 4, 0, false,
        prints("Could not start game.")));
    List<ICard> badDeck = new SimpleFreecellModel().getDeck();
    badDeck.remove(3);
    assertTrue(testPlayGame(model, badDeck, 4, 2,
        false, prints("Could not start game.")));
  }

  @Test
  public void perfectGameResultsInGameOver() {
    StringBuilder fullGameInputs = new StringBuilder();
    for (int i = 0; i < 52; i ++) {
      String command = String.format("C%d 1 F%d\n", i + 1, (i / 13 + 1));
      fullGameInputs.append(command);
    }
    StringReader input = new StringReader(fullGameInputs.toString());
    StringBuilder output = new StringBuilder();

    FreecellController<ICard> controller =
        new SimpleFreecellController(model, input, output);
    controller.playGame(genDeck, 52, 2, false);

    assertEquals(output.substring(output.length() - 10), "Game over.");
  }

  @Test
  public void imperfectGameResultsInGameOver() {
    StringBuilder fullGameInputs = new StringBuilder();
    for (int i = 0; i < 52; i ++) {
      String command = String.format("C%d 1 F%d\n", i + 1, (i / 13 + 1));
      String command2 = String.format("C%d 1 F7\n", i + 1, (i / 13 + 1));
      fullGameInputs.append(command);
      fullGameInputs.append(command2);
    }
    StringReader input = new StringReader(fullGameInputs.toString());
    StringBuilder output = new StringBuilder();

    FreecellController<ICard> controller =
        new SimpleFreecellController(model, input, output);
    controller.playGame(genDeck, 52, 2, false);

    assertEquals(output.substring(output.length() - 10), "Game over.");
  }

  @Test
  public void validMovesWorkCorrectly() {
    assertTrue(testPlayGame(model, genDeck, 52, 2, false,
        prints(lotsOfPilesString), inputs("C1 1 F1"), prints(
            "F1: A♣\nF2:\nF3:\nF4:\nO1:\nO2:\nC1:\nC2: 2♣\nC3: 3♣\nC4: 4♣\nC5: 5♣"
                + "\nC6: 6♣\nC7: 7♣\nC8: 8♣\nC9: 9♣\nC10: 10♣\nC11: J♣\nC12: Q♣\nC13: K♣\nC14: "
                + "A♦\nC15: 2♦\nC16: 3♦\nC17: 4♦\nC18: 5♦\nC19: 6♦\nC20: 7♦\nC21: 8♦\nC22: 9♦"
                + "\nC23: 10♦\nC24: J♦\nC25: Q♦\nC26: K♦\nC27: A♥\nC28: 2♥\nC29: 3♥\nC30: 4♥\nC31:"
                + " 5♥\nC32: 6♥\nC33: 7♥\nC34: 8♥\nC35: 9♥\nC36: 10♥\nC37: J♥\nC38: Q♥\nC39: K♥"
                + "\nC40: A♠\nC41: 2♠\nC42: 3♠\nC43: 4♠\nC44: 5♠\nC45: 6♠\nC46: 7♠\nC47: 8♠\nC48:"
                + " 9♠\nC49: 10♠\nC50: J♠\nC51: Q♠\nC52: K♠\n"), inputs("q"), prints(quitMsg)));
    assertTrue(testPlayGame(model, genDeck, 4, 1, false,
        prints(minParametersString),
        inputs("C4 13 O1"), prints("F1:\nF2:\nF3:\nF4:\nO1: K♠\n"
            + "C1: A♣, 5♣, 9♣, K♣, 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠\n"
            + "C2: 2♣, 6♣, 10♣, A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠\n"
            + "C3: 3♣, 7♣, J♣, 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠\n"
            + "C4: 4♣, 8♣, Q♣, 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠\n"),
        inputs("C4 4 Q"), prints(quitMsg)));
  }

  @Test
  public void doesntBunchInputs() {
    assertTrue(testPlayGame(model, genDeck, 4, 1, false,
        prints(minParametersString), inputs("Fw 4 C4 O1 13 O1"),
        prints("Invalid source pile notation. Try again.\n"),
        prints("Invalid source pile notation. Try again.\n"),
        prints("Invalid card index. Try again.\n"),
        prints("F1:\nF2:\nF3:\nF4:\nO1: K♠\n"
            + "C1: A♣, 5♣, 9♣, K♣, 4♦, 8♦, Q♦, 3♥, 7♥, J♥, 2♠, 6♠, 10♠\n"
            + "C2: 2♣, 6♣, 10♣, A♦, 5♦, 9♦, K♦, 4♥, 8♥, Q♥, 3♠, 7♠, J♠\n"
            + "C3: 3♣, 7♣, J♣, 2♦, 6♦, 10♦, A♥, 5♥, 9♥, K♥, 4♠, 8♠, Q♠\n"
            + "C4: 4♣, 8♣, Q♣, 3♦, 7♦, J♦, 2♥, 6♥, 10♥, A♠, 5♠, 9♠\n"),
        inputs("C4 4 Q"), prints(quitMsg)));
  }

  @Test
  public void correctlyNotifiesInvalidMoves() {
    assertTrue(testPlayGame(model, genDeck, 52, 2, false,
        prints(lotsOfPilesString),
        inputs("C1 1 C2"), prints(invalidCuz + "Cannot place this card onto this pile.\n"),
        prints(lotsOfPilesString), inputs("C2 1 F1"),
        prints(invalidCuz + "Cannot add a non-Ace card to empty foundation pile.\n"),
        prints(lotsOfPilesString),
        inputs("C1 2 C2"), prints(invalidCuz + "Invalid card index.\n"),
        prints(lotsOfPilesString),
        inputs("C1 11 C2"), prints(invalidCuz + "Invalid card index.\n"),
        prints(lotsOfPilesString),
        inputs("C1 1 C91"), prints(invalidCuz + "Invalid index\n"),
        prints(lotsOfPilesString),
        inputs("C57 1 C4"), prints(invalidCuz + "Invalid index\n"),
        prints(lotsOfPilesString),
        inputs("Q"), prints(quitMsg)));
  }

  /**
   * Streamlines testing the playGame method with given inputs and outputs.
   * @param model is the model used to construct a controller
   * @param deck the starting deck for a controller
   * @param numCascades the number of cascade piles in the game
   * @param numOpens the number of open piles in the game
   * @param shuffle whether or not the deck should be shuffled
   * @param interactions a list of interactions for the game (inputs and outputs)
   */
  private boolean testPlayGame(FreecellModel<ICard> model, List<ICard> deck, int numCascades, int
      numOpens, boolean shuffle, Interaction... interactions) {
    StringBuilder fakeUserInput = new StringBuilder();
    StringBuilder expectedOutput = new StringBuilder();

    for (Interaction interaction : interactions) {
      interaction.apply(fakeUserInput, expectedOutput);
    }

    StringReader input = new StringReader(fakeUserInput.toString());
    StringBuilder actualOutput = new StringBuilder();

    FreecellController<ICard> controller =
        new SimpleFreecellController(model, input, actualOutput);
    controller.playGame(deck, numCascades, numOpens, shuffle);

    return expectedOutput.toString().equals(actualOutput.toString());
  }

  static Interaction prints(String line) {
    return (input, output) -> {
      output.append(line);
    };
  }

  static Interaction inputs(String in) {
    return (input, output) -> {
      input.append(in).append("\n");
    };
  }
}