// Main FXChess window

package org.oxycblt.chess;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import org.oxycblt.chess.game.ChessScene;

// TODO:
// Maybe probably possible spin off the board mouse function into multiple parts

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
