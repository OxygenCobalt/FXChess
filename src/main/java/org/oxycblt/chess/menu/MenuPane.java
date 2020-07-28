// Bar for the main buttons and the end screen

package org.oxycblt.chess.menu;

import javafx.scene.layout.Pane;

//import org.oxycblt.chess.model.ButtonType;
import org.oxycblt.chess.model.ChessType;
import org.oxycblt.chess.model.EndType;

public class MenuPane extends Pane {

    public MenuPane() {

        setPrefSize(276, 32);

    }

    // Change the turn indicator of the MenuPane
    public void changeTurn(final ChessType newColor) {



    }

    // Update the MenuPane of a game end
    public void onEnd(final ChessType winColor, final EndType type) {



    }

}
