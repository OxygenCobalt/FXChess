// Base class for all chess pieces

package org.oxycblt.chess.game.board.pieces;

import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;

import javafx.util.Duration;
import javafx.scene.shape.Path;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.animation.PathTransition;

import org.oxycblt.chess.game.ChessType;
import org.oxycblt.chess.game.board.ChessList;
import org.oxycblt.chess.game.board.ui.SelectionRect;

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

    protected int iterX = 0;
    protected int iterY = 0;

    protected boolean hasMoved = false;
    protected boolean isSelected = false;

    private ImageView chessView;
    private SelectionRect selectRect;
    private PathTransition recallAnim;

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

        ChessType.validateColor(color);

        // Add the correct chess piece image to use from TextureAtlas
        chessView = TextureAtlas.getTexture(
            Texture.CHESS_PIECES,
            type.getCoordinate(),
            color.getCoordinate()
        );

        getChildren().add(chessView);

    }

    // Calculate the distance the from the current position to the new position.
    protected void calculateDistance(final int targetX, final int targetY) {

        xDist = targetX - x;
        yDist = targetY - y;

    }

    // Search for any pieces that may be blocking the path to a destination coordinate
    protected boolean findBlockingPieces(final int targetX, final int targetY) {

        iterX = x;
        iterY = y;

        while (iterX != targetX || iterY != targetY) {

            if (iterX > targetX) {

                iterX--;

            } else if (iterX < targetX) {

                iterX++;

            }

            if (iterY > targetY) {

                iterY--;

            } else if (iterY < targetY) {

                iterY++;

            }

            // Make sure that any chess pieces at the end arent
            // counted. The if statement is redundant but oh well
            if (iterX != targetX || iterY != targetY) {

                if (list.findChessPiece(iterX, iterY) != null) {

                    return true;

                }

            }

        }

        return false;

    }

    // Confirm a move
    protected void doMove(final int targetX, final int targetY, final ChessPiece toKill) {

        hasMoved = true;

        setSelected(false);
        relocate(targetX * 32, targetY * 32);

        if (toKill != null) {

            list.removeEntity(toKill);

        }

        x = targetX;
        y = targetY;

        list.pushChange(this);

    }

    // Validate a move, confirm a move, and update a piece of the last
    // changed piece, all are unique for every implementation for chesspiece
    public abstract boolean validateMove(int targetX, int targetY);
    public abstract void confirmMove(int targetX, int targetY);
    public abstract void update(ChessPiece changedPiece);

    // Select/Deselect a chess piece
    public void setSelected(final boolean selected) {

        if (isSelected != selected) {

            if (selected) {

                toFront();

                /*
                | Reset the translate coordinates and confirm the pieces current
                | position to prevent weird positioning bugs if a piece is selected
                | after being recalled to its original position
                */
                setTranslateX(0);
                setTranslateY(0);
                relocate(x * 32, y * 32);

                // Create selection rectangle if not already created
                if (selectRect == null) {

                    selectRect = new SelectionRect(color, 0, 0);

                }

                getChildren().add(selectRect);

            } else {

                getChildren().remove(selectRect);

            }

            isSelected = selected;

        }

    }

    // Return a chess piece to its original location
    public void recall(final int mouseX, final int mouseY) {

        setSelected(false);

        /*
        | Set up the recall animation if not already, and then recalulate the path to be taken
        | based on the distance from the original position of the piece and the current mouse
        | position.
        */
        if (recallAnim == null) {

            recallAnim = new PathTransition();
            recallAnim.setDuration(Duration.seconds(0.3));
            recallAnim.setNode(this);

        }

        recallAnim.setPath(
            new Path(
                new MoveTo(16, 16),
                new LineTo(
                    (((x * 32) + 16) - mouseX),
                    (((y * 32) + 16) - mouseY)
                )
            )
        );

        recallAnim.play();

    }

    // Find if a chess piece is at a certain position
    public boolean isAt(final int atX, final int atY) {

        return atX == x && atY == y;

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

    public boolean getMoved() {

        return hasMoved;

    }

}
