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
import org.oxycblt.chess.game.board.pieces.Rook;
import org.oxycblt.chess.game.board.pieces.Knight;
import org.oxycblt.chess.game.board.pieces.Bishop;
import org.oxycblt.chess.game.board.pieces.Queen;
import org.oxycblt.chess.game.board.pieces.King;
import org.oxycblt.chess.game.board.pieces.ChessPiece;
import org.oxycblt.chess.game.board.ui.PromotionMenu;
import org.oxycblt.chess.game.board.ui.PromotionEndListener;
import org.oxycblt.chess.entity.EntityRemovalListener;

public class BoardPane extends Pane {

    private ChessList pieces;
    private Rectangle2D mouseRect;

    private ChessPiece selectedPiece = null;
    private ChessPiece promotedPiece = null;
    private Rectangle selectRect = null;
    private PromotionMenu promotionMenu = null;

    private ChessType turn = ChessType.WHITE;

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
        setOnMouseDragged(mouseHoverHandler);
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

    PromotionEndListener confirmListener = newType -> {

        // Remove the original pawn set to be promoted, and replace
        // it with the type that was chosen by the menu

        // TODO: Jesus christ man just make a ChessFactory or something
        switch (newType) {

            case ROOK: getChildren().add(
                new Rook(pieces,
                         promotedPiece.getColor(),
                         promotedPiece.getX(),
                         promotedPiece.getY()
                )); break;
            case KNIGHT: getChildren().add(
                new Knight(pieces,
                           promotedPiece.getColor(),
                           promotedPiece.getX(),
                           promotedPiece.getY()
                )); break;
            case BISHOP: getChildren().add(
                new Bishop(pieces,
                           promotedPiece.getColor(),
                           promotedPiece.getX(),
                           promotedPiece.getY()
                )); break;
            case QUEEN: getChildren().add(
                new Queen(pieces,
                          promotedPiece.getColor(),
                          promotedPiece.getX(),
                          promotedPiece.getY()
                )); break;

        }

        pieces.removeEntity(promotedPiece);
        promotedPiece = null;

        // Also end the players turn, something that didnt originally happen
        // when the move was originally confirmed
        turn = ChessType.inverseOf(turn);

    };

    EventHandler<MouseEvent> mouseClickHandler = event -> {

        MouseButton button = event.getButton();

        // Left mouse button to select a chess piece/confirm chess piece movement,
        // no selections can occur when a pawn is being promoted.
        if (button == MouseButton.PRIMARY && promotedPiece == null) {

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

                    if (selectedPiece.validateMove(simpleX, simpleY)) {

                        selectedPiece.confirmMove(simpleX, simpleY);

                        /*
                        | If a pawn has just been promoted however, disable the entire board
                        | and add a listener to the piece to wait until the promotion process
                        | is done, dont change the turn if this happens.
                        */
                        if (selectedPiece.getType() == ChessType.PAWN) {

                            if (((Pawn) selectedPiece).getPromoted()) {

                                promotedPiece = selectedPiece;

                                if (promotionMenu == null) {

                                    promotionMenu = new PromotionMenu(
                                        confirmListener,
                                        promotedPiece.getColor(),
                                        promotedPiece.getX(), promotedPiece.getY()
                                    );

                                } else {

                                    promotionMenu.show(
                                        promotedPiece.getColor(),
                                        promotedPiece.getX(), promotedPiece.getY()
                                    );

                                }

                                getChildren().add(promotionMenu);

                            }

                        }

                        selectedPiece = null;

                        getChildren().remove(selectRect);

                        if (promotedPiece == null) {

                            turn = ChessType.inverseOf(turn);

                        }

                    }

                }

            }

        // Right mouse button is used to deselect a chess piece, also removing the selection rect
        } else if (button == MouseButton.SECONDARY
               && selectedPiece != null
               && promotedPiece == null) {

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

                    // If so, update the selection rect
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

    // Update the simple coordinates from the mouse coords
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
        if (selectedPiece.validateMove(simpleX, simpleY)) {

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

        // TODO: Readd once the game itself is complete
        /*
        if (new Random().nextBoolean()) {

            turn = ChessType.WHITE;

        } else {

            turn = ChessType.BLACK;

        }
        */

        // Iterate through the 2 colors possible, BLACK or WHITE
        for (int cY = 0; cY < 2; cY++) {

            ChessType color = cY == 0 ? ChessType.BLACK : ChessType.WHITE;

            // Then, generate the row of pawns,
            // changing the Y value depending on the color
            for (int pX = 0; pX < 8; pX++) {

                getChildren().add(
                    new Pawn(pieces, color, pX, 1 + (cY * 5))
                );

            }

            // Then generate the back row of special pieces, using the typical order of
            // ROOK, KNIGHT, BISHOP, QUEEN, KING, BISHOP, ROOK, KNIGHT
            for (int cX = 0; cX < 8; cX++) {

                switch (ChessType.PIECE_ORDER[cX]) {

                    case ROOK: getChildren().add(new Rook(pieces, color, cX, cY * 7)); break;
                    case KNIGHT: getChildren().add(new Knight(pieces, color, cX, cY * 7)); break;
                    case BISHOP: getChildren().add(new Bishop(pieces, color, cX, cY * 7)); break;
                    case QUEEN: getChildren().add(new Queen(pieces, color, cX, cY * 7)); break;
                    case KING: getChildren().add(new King(pieces, color, cX, cY * 7)); break;

                }

            }

        }

    }

}
