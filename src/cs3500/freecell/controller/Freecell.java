package cs3500.freecell.controller;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.FreecellModelCreator;
import cs3500.freecell.model.card.ICard;

import java.io.InputStreamReader;
import java.util.List;

/**
 * Plays a game of Freecell with the user. Prints to and receives output from the system.
 */
public class Freecell {
  /**
   * Plays a game of Freecell with the user.
   * @param args is unused input.
   */
  public static void main(String [] args) {
    FreecellModel<ICard> model = FreecellModelCreator.create(
        FreecellModelCreator.GameType.MULTIMOVE);

    List<ICard> deck = model.getDeck();
    try {
      new SimpleFreecellController(model,
          new InputStreamReader(System.in),
          System.out).playGame(deck, 8, 4, true);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
