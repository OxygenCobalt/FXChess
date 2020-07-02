// Main UselessChess window

package org.oxycblt.chess;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;

public class ChessApp extends Application {

    public void start(final Stage primaryStage) {

        // Configure given stage
        primaryStage.setTitle("UselessChess");
        primaryStage.setResizable(false);

        primaryStage.setScene(new Scene(new Group(), 500, 500));
        primaryStage.show();

    }

    public static void run(final String[] args) {

        launch();

    }

}
