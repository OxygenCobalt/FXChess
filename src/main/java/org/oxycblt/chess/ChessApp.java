// Main FXChess window

package org.oxycblt.chess;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;

import org.oxycblt.chess.media.Texture;
import org.oxycblt.chess.media.TextureAtlas;

import org.oxycblt.chess.media.AudioLoader;

public class ChessApp extends Application {

    public void start(final Stage primaryStage) {

        AudioLoader.init();

        primaryStage.setTitle("FXChess");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(
            TextureAtlas.loadFullTexture(Texture.ICON)
        );

        primaryStage.setScene(new ChessScene(new Group()));
        primaryStage.show();

    }

    public static void run(final String[] args) {

        launch();

    }

}
