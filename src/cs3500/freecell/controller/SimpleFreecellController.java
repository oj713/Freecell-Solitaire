package cs3500.freecell.controller;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.pile.pileInfo.IPileInfo;
import cs3500.freecell.model.pile.pileInfo.PileType;
import cs3500.freecell.model.pile.pileInfo.SimplePileInfo;
import cs3500.freecell.model.card.ICard;
import cs3500.freecell.view.FreecellTextView;
import cs3500.freecell.view.FreecellView;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

/**
 * Models a simple implementation of {@code FreecellController<ICard>} with {@code FreecellModel},
 * input source, and {@code FreecellView} to create output.
 */
public class SimpleFreecellController implements FreecellController<ICard> {
  private final FreecellModel<ICard> model;
  private final Readable rd;
  private final FreecellView view;

  /**
   * Constructs a SimpleFreecellController object.
   * @param model is the model for the controller
   * @param rd is the Readable object for the controller, representing input.
   * @param ap is the Appendable object for the controller, representing output.
   * @throws IllegalArgumentException if any argument is null.
   */
  public SimpleFreecellController(FreecellModel<ICard> model, Readable rd, Appendable ap)
      throws IllegalArgumentException {
    if (model == null || rd == null || ap == null) {
      throw new IllegalArgumentException("No null values allowed");
    }
    this.model = model;
    this.rd = rd;
    this.view = new FreecellTextView(model, ap);
  }

  @Override
  public void playGame(List<ICard> deck, int numCascades, int numOpens, boolean shuffle)
      throws IllegalStateException, IllegalArgumentException {
    if (deck == null) {
      throw new IllegalArgumentException("Cannot use null deck.");
    }
    try {
      model.startGame(deck, numCascades, numOpens, shuffle);
    } catch (IllegalArgumentException e) {
      this.renderMessage("Could not start game.");
      return;
    }
    Scanner scanner = new Scanner(this.rd);

    while (!model.isGameOver()) {
      this.renderBoard();

      String command;
      try {
        command = scanner.nextLine();
      } catch (NoSuchElementException e) {
        throw new IllegalStateException("Reading from Readable failed.");
      }

      Scanner commandScanner = new Scanner(command);

      Optional<IPileInfo> fromPile = this.getPileInfoFrom(commandScanner, "source pile");
      if (this.isQuit(fromPile)) {
        return;
      }
      Optional<Integer> cardIndex = this.getIndexFrom(commandScanner);
      if (this.isQuit(cardIndex)) {
        return;
      }
      Optional<IPileInfo> toPile = this.getPileInfoFrom(commandScanner, "destination pile");
      if (this.isQuit(toPile)) {
        return;
      }

      try {
        model.move(fromPile.get().getPileType(),
                   fromPile.get().getPileIndex(),
                   cardIndex.get(),
                   toPile.get().getPileType(),
                   toPile.get().getPileIndex());
      } catch (IllegalArgumentException e) {
        this.renderMessage("Invalid move. Try again. Reason: " + e.getMessage() + "\n");
      }
    }
    this.renderBoard();
    this.renderMessage("Game over.");
  }

  /**
   * Determines whether it's time to quit the game and if so, sends a message saying thus.
   * @param optional is the object determining whether its time to quit the game.
   * @return whether its time to quit the game or not.
   */
  private boolean isQuit(Optional optional) {
    if (optional.isEmpty()) {
      this.renderMessage("Game quit prematurely.");
      return true;
    } else {
      return false;
    }
  }

  /**
   * Parses scanner input until an acceptable string is provided, and converts
   * the string into pile information.
   * @param scanner the scanner from which pile information will be procured.
   * @param pileName the name of the pile used in producing messages
   * @return an {@code IPileInfo} object representing pile information parsed from the scanner,
   *     or an empty object if the user indicates they want to quit.
   * @throws IllegalStateException if reading from the scanner fails.
   */
  private Optional<IPileInfo> getPileInfoFrom(Scanner scanner, String pileName)
      throws IllegalStateException {
    String pileInfo;
    try {
      pileInfo = scanner.next();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("Reading from Readable failed.");
    }

    if (pileInfo.equals("Q") || pileInfo.equals("q")) {
      return Optional.empty();
    } else {
      Optional<PileType> pileType = getTypeFrom(pileInfo.charAt(0));
      Optional<Integer> pileIndex = getNumFrom(pileInfo.substring(1));

      if (pileType.isEmpty() || pileIndex.isEmpty()) {
        this.renderMessage("Invalid " + pileName + " notation. Try again.\n");
        return getPileInfoFrom(scanner, pileName);
      } else {
        return Optional.of(new SimplePileInfo(pileType.get(), pileIndex.get()));
      }
    }
  }

  /**
   * Returns a valid piletype from the given character, or asks and retrieves a new entry
   *     if the given character is invalid.
   * @param piletype is the character from which piletype is retrieved.
   * @return a piletype based off the given input if valid, and a new input otherwise.
   */
  private static Optional<PileType> getTypeFrom(char piletype) {
    switch (piletype) {
      case('C'):
        return Optional.of(PileType.CASCADE);
      case('O'):
        return Optional.of(PileType.OPEN);
      case('F'):
        return Optional.of(PileType.FOUNDATION);
      default:
        return Optional.empty();
    }
  }

  /**
   * Parses scanner input until an acceptable string is provided, and converts
   * the string into card index information.
   * @param scanner the scanner from which card index information will be procured.
   * @return a card index value, or an empty object if the user indicates they want to quit.
   * @throws IllegalStateException if reading from the scanner fails.
   */
  private Optional<Integer> getIndexFrom(Scanner scanner) throws IllegalStateException {
    String index;
    try {
      index = scanner.next();
    } catch (Exception e) {
      throw new IllegalStateException("Reading from Readable failed.");
    }

    if (index.equals("Q") || index.equals("q")) {
      return Optional.empty();
    } else {
      Optional<Integer> cardIndex = getNumFrom(index);
      if (cardIndex.isEmpty()) {
        this.renderMessage("Invalid card index. Try again.\n");
        return this.getIndexFrom(scanner);
      } else {
        return cardIndex;
      }
    }
  }

  /**
   * Parses an integer value from the given string, or asks for re-entry if given string
   * does not represent a valid integer value.
   * @param value the string to be parsed.
   * @return a valid string as an integer.
   */
  private static Optional<Integer> getNumFrom(String value) {
    try {
      Optional<Integer> val = Optional.of(Integer.parseInt(value) - 1);
      if (val.get() < 0) {
        return Optional.empty();
      } else {
        return val;
      }
    } catch (NumberFormatException e) {
      return Optional.empty();
    }
  }

  /**
   * Render the board to the provided data destination. The board should be rendered exactly
   * in the format produced by the toString method above
   * @throws IllegalStateException if transmission of the board to the
   *      provided data destination fails
   */
  private void renderBoard() throws IllegalStateException {
    try {
      this.view.renderBoard();
      this.view.renderMessage("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Writing to appendable failed");
    }
  }

  /**
   * Render a specific message to the provided data destination.
   * @param message the message to be transmitted
   * @throws IllegalStateException if transmission of the board to the
   *      provided data destination fails
   */
  private void renderMessage(String message) throws IllegalStateException {
    try {
      this.view.renderMessage(message);
    } catch (IOException e) {
      throw new IllegalStateException("Writing to appendable failed");
    }
  }
}
