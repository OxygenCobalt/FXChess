// Launcher for ChessApp

package org.oxycblt.chess;

public final class ChessLauncher {

    // TODO:

    // StatBar stuff:
    //    - Add text editing to TextLoader to lower the amount of created ImageViews

    //    - Rewrite alot of the game-end stuff to work with statbar
    //        - Rework Draws to only give the option, not for it to be mandatory
    //    - Update text sub-object to use that game-end stuff

    //    - Rework resetbutton to work on statbars
    //    - Add Draw/Forfiet buttons

    // Title Screen:

    //    - Add a basic title screen with the logo

    //    - Add semi-configuration class that statbars read from to get values
    //    - Create textfields for the config values [Not sure if to use custom font or not]
    //       - Player 1 name
    //       - Player 2 name

    //    - Add a return button to StatBar that goes back to the title screen

    // Other:

    //    - Fix Checking your own king issue?
    //    - Merge EntityList w/ChessList since it wont be used in other places?

    private ChessLauncher() {

        // Not called

    }

    public static void main(final String[] args) {

        ChessApp.run(args);

    }

}
