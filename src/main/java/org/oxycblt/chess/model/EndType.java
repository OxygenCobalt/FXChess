// Enum for the ways a game can end

package org.oxycblt.chess.model;

public enum EndType {

    CHECKMATE("Won", "Lost"),
    RESIGN("Won", "Resigned"),
    TIME("Won", "Out of time"),
    DRAW("Draw", "Draw");

    private final String winnerText;
    private final String gooberText; // Don't ask

    EndType(final String winnerText, final String gooberText) {

        this.winnerText = winnerText;
        this.gooberText = gooberText;

    }

    // Get the winning StatBar's text
    public String getWinnerText() {

        return winnerText;

    }

    // Get the losing StatBars text
    public String getGooberText() {

        return gooberText;

    }

}
