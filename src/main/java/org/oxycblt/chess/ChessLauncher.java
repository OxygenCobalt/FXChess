// Launcher for ChessApp

package org.oxycblt.chess;

public final class ChessLauncher {

    // TODO:

    // StatBar stuff:
    // Remove top StatBar, reduce it down to just a top one.
    // Remove names, just have the result on the top.
    // Add Reset/Draw buttons.

    // Other:

    //    - Fix Checking your own king issue?
    //    - Add sounds
    //    - Merge EntityList w/ChessList since it wont be used in other places?

    private ChessLauncher() {

        // Not called

    }

    public static void main(final String[] args) {

        ChessApp.run(args);

    }

}
