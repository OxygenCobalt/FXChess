// Abstract chess piece

package org.oxycblt.chess.game.board.pieces;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import org.oxycblt.chess.game.ChessType;
import org.oxycblt.chess.game.board.ChessList;
import org.oxycblt.chess.media.images.Texture;
import org.oxycblt.chess.media.images.TextureAtlas;

public abstract class ChessPiece extends Pane {

    public final ChessType type;
    public final ChessType color;
    private ChessList list;

    public int x;
    public int y;

    private ImageView chessView;
    private Rectangle selectRect;

    public ChessPiece(final ChessList list,
                      final ChessType type,
                      final ChessType color,
                      final int x, final int y) {

        this.type = type;
        this.color = color;
        this.x = x;
        this.y = y;

        this.list = list;
        list.addEntity(this);

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

    public void setSelected(final boolean selected) {

        if (selected) {

            // Create selection rectangle if not already created
            if (selectRect == null) {

                selectRect = new Rectangle(32, 32);
                selectRect.setFill(Color.TRANSPARENT);
                selectRect.setStroke(Color.valueOf(color.toString()));
                selectRect.setStrokeType(StrokeType.INSIDE);
                selectRect.setStrokeWidth(2);

            }

            getChildren().add(selectRect);

        } else {

            getChildren().remove(selectRect);

        }

    }

    public void confirmMove(final int targetX, final int targetY) {

        System.out.println("Confirming move");

        setSelected(false);

    }

    public abstract boolean validateMove();
    public abstract void update();

    // Return true if all characteristics of piece are correct
    public boolean isMatching(final ChessType matchColor,
                               final int matchX,
                               final int matchY) {

        if (matchColor != ChessType.BLACK && matchColor != ChessType.WHITE) {

            throw new IllegalArgumentException("Chess color is not BLACK or WHITE");

        }

        return matchColor == color && matchX == x && matchY == y;

    }

}
