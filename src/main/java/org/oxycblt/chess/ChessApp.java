// Main FXChess window

package org.oxycblt.chess;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import org.oxycblt.chess.game.ChessScene;

// TODO:
// REWORK THE HELL OUT OF THE PROMOTION MENU
// Spin off the SelectRects in BoardPane, ChessPiece, and PromotionMenu into a /ui/ object
// Add a ChessPiece factory to get rid of the hacky ways youve done promotion & generation
// Maybe probably possible spin off the board mouse function into multiple parts
// Make the color validation a builtin of chesstype

public class ChessApp extends Application {

    public void start(final Stage primaryStage) {

        primaryStage.setTitle("FXChess");
        primaryStage.setResizable(false);

        primaryStage.setScene(new ChessScene(new Group()));
        primaryStage.show();

    }

    public static void run(final String[] args) {

        launch();

    }

}
