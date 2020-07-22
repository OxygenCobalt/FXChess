// Base class for all chess pieces

package org.oxycblt.chess.game.board.pieces;

import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;

import org.oxycblt.chess.game.ChessType;
import org.oxycblt.chess.game.board.ChessList;
import org.oxycblt.chess.game.board.ui.SelectionRect;
import org.oxycblt.chess.game.board.animation.FadeAnimation;
import org.oxycblt.chess.game.board.animation.RecallAnimation;

import org.oxycblt.chess.media.images.Texture;
import org.oxycblt.chess.media.images.TextureAtlas;

public abstract class ChessPiece extends Pane {

    protected final ChessType type;
    protected final ChessType color;
    protected final ChessList list;

    private final int originX;
    private final int originY;

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
    private RecallAnimation recallAnim;
    private FadeAnimation fadeAnim;

    public ChessPiece(final ChessList list,
                      final ChessType type,
                      final ChessType color,
                      final int x, final int y) {

        setPrefSize(32, 32);
        relocate(x * 32, y * 32);

        this.type = type;
        this.color = color;
        this.originX = x;
        this.originY = y;
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
    // Returns true if a blocking piece is found, false if none are found
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

            // Make sure that any chess pieces at the end aren't counted.
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
        isSelected = false;

        selectRect.hideNoAnim();
        relocate(targetX * 32, targetY * 32);

        if (toKill != null) {

            list.removeEntity(toKill);

        }

        x = targetX;
        y = targetY;

        list.pushChange(this);

    }

    /*
    | Validate a move, confirm a move, and update a piece of the last changed piece, all are
    | unique for every implementation for chess piece
    */
    public abstract boolean validateMove(int targetX, int targetY);
    public abstract void confirmMove(int targetX, int targetY);
    public abstract void update(ChessPiece changedPiece);

    // Select/Deselect a chess piece
    public void setSelected(final boolean selected) {

        if (isSelected != selected) {

            if (selected) {

                toFront();

                /*
                | Reset the translate coordinates and confirm the pieces current position to
                | prevent weird positioning bugs if a piece is selected after being recalled
                | to its original position
                */
                setTranslateX(0);
                setTranslateY(0);
                relocate(x * 32, y * 32);

                // Create selection rectangle if not already created
                if (selectRect == null) {

                    selectRect = new SelectionRect(color, 0, 0);

                    getChildren().add(selectRect);

                } else {

                    selectRect.show(color);

                }

            } else {

                selectRect.hide();

            }

            isSelected = selected;

        }

    }

    // Reset a chess piece
    public void reset() {

        if (hasMoved) {

            x = originX;
            y = originY;

            hasMoved = false;

            recall();

        }

        setSelected(false);

    }

    // Reset a piece while re-adding it to the main list of chesspieces
    public void unkill() {

        reset();

        if (fadeAnim == null) {

            fadeAnim = new FadeAnimation(this);

        }

        fadeAnim.fadeIn();
        list.addEntity(this);

    }

    // Return a chess piece to its original location
    public void recall() {

        if (recallAnim == null) {

            recallAnim = new RecallAnimation(this);

        }

        recallAnim.start((int) getLayoutX(), (int) getLayoutY(), x * 32, y * 32);

        setSelected(false);

    }

    // Find if a chess piece is at a certain position
    // Returns true if yes, false if no
    public boolean isAt(final int atX, final int atY) {

        return atX == x && atY == y;

    }

    // Relocate the interal view, used by CheckAnimation
    public void offsetView(final int offset) {

        chessView.setLayoutX(chessView.getLayoutX() - offset);

    }

    // Getters
    public ChessType getType() {

        return type;

    }

    public ChessType getColor() {

        return color;

    }

    public int getOriginX() {

        return originX;

    }

    public int getOriginY() {

        return originY;

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
