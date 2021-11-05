# Freecell Solitaire

This is a text-based implementation of the game Freecell Solitaire. 

# How to Play: # 
The main() method that runs the game is contained in the src/cs3500/freecell/controller/Freecell.java file. 
Example screenshots of gameplay can be found in the gameplay-screenshots folder. 

Upon running, a text display of the current game state will print to the console. All piles will be displayed by name, with a list of its current contents (if any) printed beside its name. Piles are named with a letter signifying pile type (either O, F, or C for open, foundation, and cascade respectively) and a number. Cards are displayed as their value and suite. In order for a player to specify a move, they must enter the name of the source pile, the index (starting from 1) of the bottommost card they wish to move, and the destination pile separated by spaces (case sensitive). For example, "C5 5 C2" would move the 5th card and above from C5 to C2. An incorrect command will show an error message and the player can try again. All rules for moving cards between piles are the same as traditional freecell solitare (for rules reference: http://www.solitairecity.com/FreeCell.shtml) and illegal moves will prompt an error message. The player can enter "q" or "Q" at any time to quit the game, and if the player wins a "game over" message will display. 


# Design Details: #
This project was created as an assignment for CS3500: Object Oriented Design. It uses a Model-View-Controller design pattern. A game of freecell solitaire is represented as 3 lists of each of the pile types, where a pile is represented as a list of cards. The game was originally implemented to only allow single card moves, and was later updated to allow for multiple cards to be moved at a time. 

**View**
* FreecellView
  * This interface represents general operations needed for a Freecell view. These operations include rendering the board and displaying messages. 
* FreecellTextView
  * class representing a text-based view of Freecell Solitaire. 

**Controller**
* Freecell
  * runs the program.
* FreecellController
  * interface representing the operation to play a game of Freecell Solitaire. 
* SimpleFreecellController
  * implements freecell controller. Represents a game of Freecell as a model, input, and view. 



