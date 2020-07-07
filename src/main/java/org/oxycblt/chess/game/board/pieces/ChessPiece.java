// Base class for all chess pieces

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

    protected ChessType type;
    protected ChessType color;
    protected ChessList list;

    protected int x = 0;
    protected int y = 0;

    protected int xDist = 0;
    protected int yDist = 0;

    protected boolean hasMoved = false;
    protected boolean moveIsValid = false;

    private ImageView chessView;
    private Rectangle selectRect;

    public ChessPiece(final ChessList list,
                      final ChessType type,
                      final ChessType color,
                      final int x, final int y) {

        setPrefSize(32, 32);
        relocate(x * 32, y * 32);

        this.type = type;
        this.color = color;
        this.x = x;
        this.y = y;

        this.list = list;
        this.list.addEntity(this);

        // Color should not be anything other than BLACK or WHITE
        if (color != ChessType.BLACK && color != ChessType.WHITE) {

            throw new IllegalArgumentException("Chess color is not BLACK or WHITE");

        }

        // Add the correct chess piece image to use from TextureAtlas
        chessView = TextureAtlas.getTexture(

            Texture.CHESS_PIECES,
            type.getTextureCoordinate(),
            color.getTextureCoordinate()

        );

        getChildren().add(chessView);

    }

    // Calculate the distance the from the current position to the new position.
    protected void calculateDistance(final int targetX, final int targetY) {

        xDist = x - targetX;
        yDist = y - targetY;

    }

    // Select/Deselect a chess piece
    public void setSelected(final boolean selected) {

        if (selected) {

            // Create selection rectangle if not already created
            if (selectRect == null) {

                selectRect = new Rectangle(32, 32);
                selectRect.setFill(Color.TRANSPARENT);
                selectRect.setStroke(Color.valueOf(color.toString()));
                selectRect.setStrokeType(StrokeType.INSIDE);
                selectRect.setStrokeWidth(3);

            }

            getChildren().add(selectRect);

        } else {

            getChildren().remove(selectRect);

        }

    }

    // Confirm a move
    protected void doMove(final int targetX, final int targetY, final ChessPiece toKill) {

        x = targetX;
        y = targetY;

        hasMoved = true;

        relocate(x * 32, y * 32);
        setSelected(false);

        if (toKill != null) {

            list.removeEntity(toKill);

        }

        list.removeEntity(list.findChessPiece(ChessType.inverseOf(color), x, y));
        list.pushChange(this);

    }

    // Validate a move, confirm a move, and update a piece of the last
    // changed piece, all are unique for every implementation for chesspiece
    public abstract void validateMove(int targetX, int targetY);
    public abstract void confirmMove(int targetX, int targetY);
    public abstract void update(ChessPiece changedPiece);

    // Return true if all characteristics of piece are correct
    public boolean isMatching(final ChessType matchColor,
                               final int matchX,
                               final int matchY) {

        if (matchColor != ChessType.BLACK && matchColor != ChessType.WHITE) {

            throw new IllegalArgumentException("Chess color is not BLACK or WHITE");

        }

        return matchColor == color && matchX == x && matchY == y;

    }

    // Getters
    public ChessType getType() {

        return type;

    }

    public ChessType getColor() {

        return color;

    }

    public int getX() {

        return x;

    }

    public int getY() {

        return y;

    }

    public int getYDist() {

        return yDist;

    }

    public boolean getValid() {

        return moveIsValid;

    }

}
