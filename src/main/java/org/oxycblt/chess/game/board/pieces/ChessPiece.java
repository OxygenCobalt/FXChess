// Abstract chess piece

package org.oxycblt.chess.game.board.pieces;

import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import org.oxycblt.chess.media.images.TextureAtlas;
import org.oxycblt.chess.media.images.Texture;

public abstract class ChessPiece extends Pane {

    public final ChessType type;
    public final ChessType color;

    public int x;
    public int y;

    private final ImageView chessView;

    public ChessPiece(final ChessType type, final ChessType color, final int x, final int y) {

        this.type = type;
        this.color = color;

        // Chess piece color should not be black
        if (color != ChessType.BLACK && color != ChessType.WHITE) {

            throw new IllegalArgumentException("Chess color is not BLACK or WHITE");

        }

        setPrefSize(32, 32);
        relocate(x * 32, y * 32);

        // Add the correct chess piece image to use from TextureAtlas
        chessView = TextureAtlas.getTexture(

            Texture.CHESS_PIECES,
            type.getTextureCoordinate(),
            color.getTextureCoordinate()

        );

        getChildren().add(chessView);

    }

    private void onMove() {



    }

    abstract boolean validateMove();
    abstract void update();

}
