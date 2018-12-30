# chess
An interactive chess game built in java

![alt text](https://github.com/NeoWu1216/chess/blob/master/example_board.JPG)

## Demo:
- You can view the full demo [here](https://github.com/NeoWu1216/chess/blob/master/src/tests/resources/Manual%20Test%20Plan.pdf) 

## Features:
- Support multiple board configurations (with a special mode which replaces bishop and rook with princess and empress)
- During the game, user can click a piece in the board and click a valid position in board to move it (according to [rules of chess](https://en.wikipedia.org/wiki/Rules_of_chess))
- All valid moves of given piece are suggested with color, and special conditions (check, checkmate) are prompted below the board
- User can restart, resign or undo a move
- At end of each game, user score will be updated accordingly (win: 1-0, resign: 1-0, draw: 0.5-0.5)
- Support player options including friends or a configurable AI
- A [global configuration file](https://github.com/NeoWu1216/chess/blob/master/src/main/java/com/chess/gui/Preferences.java) containing fonts, key bindings for undo/restart, or AI of any level

## Launch:
- To start the game, simply run [Launcher.java](https://github.com/NeoWu1216/chess/blob/master/src/main/java/com/chess/Launcher.java)
