// Abstract chess piece

package org.oxycblt.chess.game.board.pieces;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;
import org.oxycblt.chess.media.images.TextureAtlas;
import org.oxycblt.chess.media.images.Texture;

public abstract class ChessPiece extends Pane {

    public final ChessType type;
    public final ChessType color;

    public int x;
    public int y;

    private boolean isSelected;
    private ArrayList<ChessPiece> list;

    private ImageView chessView;

    public ChessPiece(final ArrayList<ChessPiece> list,
                      final ChessType type,
                      final ChessType color,
                      final int x, final int y) {

        list.add(this);
        this.list = list;

        this.type = type;
        this.color = color;

        // Chess piece color should not be black
        if (color != ChessType.BLACK && color != ChessType.WHITE) {

            throw new IllegalArgumentException("Chess color is not BLACK or WHITE");

        }

        setPrefSize(32, 32);
        relocate(x * 32, y * 32);
        setOnMouseClicked(mouseClickHandler);

        // Add the correct chess piece image to use from TextureAtlas
        chessView = TextureAtlas.getTexture(

            Texture.CHESS_PIECES,
            type.getTextureCoordinate(),
            color.getTextureCoordinate()

        );

        getChildren().add(chessView);

    }

    EventHandler<MouseEvent> mouseClickHandler = event -> {

        MouseButton button = event.getButton();

        if (button == MouseButton.PRIMARY && !isSelected) {

            isSelected = true;

            System.out.println(list.size());

        }

    };

    private void onMove() {



    }

    abstract boolean validateMove();
    abstract void update();

    // Remove any loaded media
    public void destroy() {

        getChildren().remove(chessView);

        chessView = null;

    }

}
