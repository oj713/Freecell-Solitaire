package cs3500.freecell;

/**
 * An interaction with the user consists of some input to send the program
 * and some output to expect.  We represent it as an object that takes in two
 * StringBuilders and produces the intended effects on them
 */
public interface Interaction {
  /**
   * applies this interaction to the input and output.
   * @param in the input
   * @param out the output
   */
  void apply(StringBuilder in, StringBuilder out);
}
