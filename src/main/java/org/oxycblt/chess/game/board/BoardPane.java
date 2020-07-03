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

public class BoardPane extends Pane {

    private ChessList pieces;

    private Rectangle2D mouseRect;
    private ChessPiece selectedPiece;
    private Rectangle selectRect;
    private boolean selectionIsValid;

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

        pieces = new ChessList();
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
        // Right mouse button to deselect a chess piece
        if (button == MouseButton.PRIMARY) {

            // Normalize the pointer so that only
            // the pointer positions in the rectangle
            // are used
            int x = (int) (event.getSceneX() - getLayoutX());
            int y = (int) (event.getSceneY() - getLayoutY());

            if (x > 0 && x < getPrefWidth() && y > 0 && y < getPrefHeight()) {

                // Find a chess piece that matches the coordinates
                // --- TODO ---
                // and the current player turn,
                // ------------
                // and select that if there is one

                int simpleX = x / 32;
                int simpleY = y / 32;

                ChessPiece piece = pieces.findEntity(ChessType.WHITE, simpleX, simpleY);

                if (piece != null && piece != selectedPiece) {

                    // If no piece is selected, select the one clicked.
                    // If a piece is selected, deselect that one and select the one clicked.
                    if (selectedPiece == null) {

                        piece.setSelected(true);
                        selectedPiece = piece;

                        updateSelectionRect(simpleX, simpleY);

                        getChildren().add(selectRect);

                    } else {

                        selectedPiece.setSelected(false);
                        piece.setSelected(true);

                        selectedPiece = piece;

                    }

                // If clicked again while on an empty square with a piece selected,
                // then confirm the move as long as its valid, do nothing if it isnt.
                } else if (piece == null && selectedPiece != null) {

                    if (selectionIsValid) {

                        selectedPiece.confirmMove(simpleX, simpleY);

                        selectedPiece = null;

                        getChildren().remove(selectRect);

                    }

                }

            }

        } else if (button == MouseButton.SECONDARY && selectedPiece != null) {

            selectedPiece.setSelected(false);

            selectedPiece = null;

            getChildren().remove(selectRect);

        }

    };

    EventHandler<MouseEvent> mouseHoverHandler = event -> {

        // Ignore if nothing is selected
        if (selectedPiece != null) {

            int x = (int) (event.getSceneX() - getLayoutX());
            int y = (int) (event.getSceneY() - getLayoutY());

            if (x > 0 && x < getPrefWidth() && y > 0 && y < getPrefHeight()) {

                int simpleX = x / 32;
                int simpleY = y / 32;

                // Check if the mouse pointer has actually meaningfully changed
                // E.G it went from one square to another
                if (simpleX != cacheSimpleX || simpleY != cacheSimpleY) {

                    updateSelectionRect(simpleX, simpleY);

                    cacheSimpleX = simpleX;
                    cacheSimpleY = simpleY;

                }

            }

        }

    };

    private void updateSelectionRect(final int simpleX, final int simpleY) {

       // Add the selection rectangle now that a chess piece
       // has been selected, creating it if its not created already
        if (selectRect == null) {

            selectRect = new Rectangle(32, 32);
            selectRect.setFill(Color.TRANSPARENT);
            selectRect.setStrokeType(StrokeType.INSIDE);
            selectRect.setStrokeWidth(2);
        }

        // If the move is valid, show the selection color respective
        // -- TODO --
        // to the current player turn
        // ----------
        // Otherwise, mark it as invalid w/a red color

        selectionIsValid = selectedPiece.validateMove(simpleX, simpleY);

        if (selectionIsValid) {

            selectRect.setStroke(Color.valueOf("WHITE"));

        } else {

            selectRect.setStroke(Color.RED);

        }

        // If theres a piece of the same color where the selection box should be, dont show it.
        if (pieces.findEntity(ChessType.WHITE, simpleX, simpleY) != null) {

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

    private void generateChessPieces() {

        Pawn pawn1 = new Pawn(pieces, ChessType.BLACK, 0, 1);
        Pawn pawn2 = new Pawn(pieces, ChessType.WHITE, 0, 6);
        Pawn pawn3 = new Pawn(pieces, ChessType.WHITE, 1, 6);

        getChildren().addAll(pawn1, pawn2, pawn3);

    }

}
