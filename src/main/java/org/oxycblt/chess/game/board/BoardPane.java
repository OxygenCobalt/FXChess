// Pane that houses the chess pieces and handles their interactions

package org.oxycblt.chess.game.board;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;
import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import org.oxycblt.chess.game.ChessType;
import org.oxycblt.chess.game.board.pieces.Pawn;
import org.oxycblt.chess.game.board.pieces.ChessPiece;
import org.oxycblt.chess.entity.EntityRemovalListener;

public class BoardPane extends Pane {

    private ChessList pieces;
    private Rectangle2D mouseRect;

    private ChessPiece selectedPiece = null;
    private Rectangle selectRect = null;

    private ChessType turn = ChessType.BLACK;

    private int mouseX = 0;
    private int mouseY = 0;

    private int simpleX = 0;
    private int simpleY = 0;
    private int cacheSimpleX = -1;
    private int cacheSimpleY = -1;

    public BoardPane() {

        // W/H/X/Y are static
        relocate(33, 49);
        setPrefSize(256, 256);
        setOnMouseClicked(mouseClickHandler);
        setOnMouseMoved(mouseHoverHandler);
        setStyle(
              "-fx-border-style: solid outside;"
            + "-fx-border-width: 6px;"
            + "-fx-border-color: #8F8F8F"
        );

        pieces = new ChessList(chessRemovalListener);
        mouseRect = new Rectangle2D(
            getLayoutX(),
            getLayoutY(),
            getPrefWidth(),
            getPrefHeight()
        );

        generateCheckerBoard();
        generateChessPieces();

    }

    EventHandler<MouseEvent> mouseClickHandler = event -> {

        MouseButton button = event.getButton();

        // Left mouse button to select a chess piece/confirm chess piece movement
        if (button == MouseButton.PRIMARY) {

            normalizePointer(event);

            if (validateXY()) {

                // Find a chess piece that matches the coordinates and the
                // current player turn, and select that if there is one

                updateSimpleXY();

                ChessPiece piece = pieces.findChessPiece(turn, simpleX, simpleY);

                if (piece != null && piece != selectedPiece) {

                    // If no piece is selected, select the one clicked
                    // and add the selection square to the pane.
                    if (selectedPiece == null) {

                        selectedPiece = piece;
                        selectedPiece.setSelected(true);
                        selectedPiece.validateMove(simpleX, simpleY);

                        updateSelectionRect();

                        getChildren().add(selectRect);

                    // If a piece is already selected, deselect that one and select the one clicked.
                    } else {

                        selectedPiece.setSelected(false);
                        piece.setSelected(true);

                        selectedPiece = piece;

                    }

                // If clicked again while on an empty square with a piece selected,
                // confirm the move as long as its valid, deselecting it and changing the turn
                } else if (piece == null && selectedPiece != null) {

                    if (selectedPiece.getValid()) {

                        selectedPiece.confirmMove(simpleX, simpleY);

                        selectedPiece = null;

                        getChildren().remove(selectRect);

                        turn = ChessType.inverseOf(turn);

                    }

                }

            }

        // Right mouse button to deselect a chess piece, also removing the selection rect
        } else if (button == MouseButton.SECONDARY && selectedPiece != null) {

            selectedPiece.setSelected(false);

            selectedPiece = null;

            getChildren().remove(selectRect);

        }

    };

    EventHandler<MouseEvent> mouseHoverHandler = event -> {

        // Ignore if nothing is selected
        if (selectedPiece != null) {

            normalizePointer(event);

            if (validateXY()) {

                updateSimpleXY();

                // Check if the mouse pointer has actually meaningfully changed
                // E.G it went from one square to another
                if (simpleX != cacheSimpleX || simpleY != cacheSimpleY) {

                    // If so, check if these new coordinates are valid or not
                    selectedPiece.validateMove(simpleX, simpleY);

                    updateSelectionRect();

                    cacheSimpleX = simpleX;
                    cacheSimpleY = simpleY;

                }

            }

        }

    };

    EntityRemovalListener<ChessPiece> chessRemovalListener = removed -> {

        getChildren().remove(removed);

    };

    // Normalize a mouse pointer so that the coordinates are solely within the bounds of BoardPane
    private void normalizePointer(final MouseEvent event) {

        mouseX = (int) (event.getSceneX() - getLayoutX());
        mouseY = (int) (event.getSceneY() - getLayoutY());

    }

    // Validate that the X/Y coordinates are not out of bounds
    private boolean validateXY() {

        return mouseX > 0 && mouseX < getPrefWidth()
            && mouseY > 0 && mouseY < getPrefHeight();

    }

    private void updateSimpleXY() {

        simpleX = mouseX / 32;
        simpleY = mouseY / 32;

    }

    // Update the selection rects position/color
    private void updateSelectionRect() {

       // Create the selection rectangle if it hasnt already been added
        if (selectRect == null) {

            selectRect = new Rectangle(32, 32);
            selectRect.setFill(Color.TRANSPARENT);
            selectRect.setStrokeType(StrokeType.INSIDE);
            selectRect.setStrokeWidth(3);

        }

        // If the move is valid, show the selection color respective to the
        // current player turn, otherwise mark it as invalid w/a red color
        if (selectedPiece.getValid()) {

            selectRect.setStroke(Color.valueOf(turn.toString()));

        } else {

            selectRect.setStroke(Color.RED);

        }

        // If theres a piece of the same color where the selection box should be, dont show it.
        if (pieces.findChessPiece(turn, simpleX, simpleY) != null) {

            selectRect.setStroke(Color.TRANSPARENT);

        }

        selectRect.relocate(simpleX * 32, simpleY * 32);

    }

    // Generate the decorative checker rectangles
    private void generateCheckerBoard() {

        Rectangle checkerRect;

        for (int x = 0; x <= 224; x += 32) {

            for (int y = 0; y <= 224; y += 32) {

                checkerRect = new Rectangle(x, y, 32, 32);

                // If the added simple coordinates [E.G 4X + 7Y]
                // is divisible by two, than that checker must be
                // a lighter one. Otherwise it needs to be a dark one
                if (((x / 32) + (y / 32)) % 2 == 0) {

                     checkerRect.setFill(Color.web("9F9F9F"));

                } else {

                    checkerRect.setFill(Color.web("5F5F5F"));

                }

                getChildren().add(checkerRect);

            }

        }

    }

    // Generate the chess pieces
    private void generateChessPieces() {

        // First, randomly determine which turn will be first.

        // TODO: Readd once the board fully generates
        /*
        if (new Random().nextBoolean()) {

            turn = ChessType.WHITE;

        } else {

            turn = ChessType.BLACK;

        }
        */

        Pawn pawn1 = new Pawn(pieces, ChessType.BLACK, 0, 1);
        Pawn pawn2 = new Pawn(pieces, ChessType.BLACK, 1, 1);
        Pawn pawn3 = new Pawn(pieces, ChessType.WHITE, 0, 6);
        Pawn pawn4 = new Pawn(pieces, ChessType.WHITE, 1, 6);

        getChildren().addAll(pawn1, pawn2, pawn3, pawn4);

    }

}
