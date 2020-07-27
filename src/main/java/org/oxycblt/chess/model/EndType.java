// Enum for the ways a game can end

package org.oxycblt.chess.model;

public enum EndType {

    CHECKMATE(" (Won)", " (Lost)"),
    RESIGN(" (Won)", " (Resigned)"),
    TIME(" (Won)", " (No Time)"),
    DRAW(" (Draw)", " (Draw)");

    private final String winText;
    private final String loseText;

    EndType(final String winText, final String loseText) {

        this.winText = winText;
        this.loseText = loseText;

    }

    // Get the winning StatBar's text
    public String getWinText() {

        return winText;

    }

    // Get the losing StatBars text
    public String getLoseText() {

        return loseText;

    }

}
