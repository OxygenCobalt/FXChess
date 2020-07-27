// Pane that houses the chess pieces and handles their interactions

package org.oxycblt.chess.board;

import java.util.Random;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;

import org.oxycblt.chess.model.EndType;
import org.oxycblt.chess.model.ChessType;

import org.oxycblt.chess.board.pieces.ChessPiece;
import org.oxycblt.chess.board.pieces.ChessFactory;

//import org.oxycblt.chess.board.ui.ResetButton;
import org.oxycblt.chess.board.ui.ResetListener;
import org.oxycblt.chess.board.ui.PromotionMenu;
import org.oxycblt.chess.board.ui.PromotionEndListener;

import org.oxycblt.chess.stats.StatPane;

import org.oxycblt.chess.media.images.Texture;
import org.oxycblt.chess.media.images.TextureAtlas;

import org.oxycblt.chess.entity.EntityChangeListener;

public class BoardPane extends Pane implements EntityChangeListener<ChessPiece> {

    private ChessList pieces;
    private ChessFactory factory;

    private StatPane stats;

    private ChessPiece selectedPiece = null;
    private ChessPiece promotedPiece = null;

    private PromotionMenu promotionMenu = null;

    private ChessType turn = ChessType.WHITE;

    private int moves = 0;

    private int mouseX = 0;
    private int mouseY = 0;
    private int simpleX = 0;
    private int simpleY = 0;
    private int selX = 0;
    private int selY = 0;

    private Random rand;

    private boolean isDisabled = false;

    public BoardPane(final StatPane stats) {

        // W/H/X/Y are static
        relocate(10, 59);
        setPrefSize(256, 256);
        setOnMouseDragged(dragHandler);
        setOnMousePressed(pressHandler);
        setOnMouseReleased(releaseHandler);

        // Add a reference to BoardPane to the given StatPane so that the two panes can
        // communicate with eachother without ChessScene as an intermediary.
        stats.addBoard(this);
        this.stats = stats;

        rand = new Random();
        pieces = new ChessList(this);
        factory = new ChessFactory(pieces, this);

        getChildren().addAll(
            //new ResetButton(resetListener), To be readded later
            TextureAtlas.getTexture(Texture.BORDER, 0, 0, 268, 268, -6, -6)
        );

        generateCheckerBoard();
        generateChessPieces();

    }

    // --- MOUSE/MOVE MANAGEMENT ---

    // Drag start
    private EventHandler<MouseEvent> pressHandler = event -> {

        if (event.getButton() == MouseButton.PRIMARY && !isDisabled) {

            normalizePointer(event);

            if (validateXY()) {

                updateSimpleXY();

                selectedPiece = pieces.findChessPiece(turn, simpleX, simpleY);

                if (selectedPiece != null) {

                    selectedPiece.setSelected(true);

                    /*
                    | The position of the mouse relative to the square its in is stored to make
                    | sure that the chess piece is relocated based on the mouse position, not the
                    | top left corner of the chess piece.
                    */
                    selX = mouseX % 32;
                    selY = mouseY % 32;

                }

            }

        }

    };

    // Drag update
    private EventHandler<MouseEvent> dragHandler = event -> {

        // Ignore if nothing is selected
        if (selectedPiece != null) {

            normalizePointer(event);

            /*
            | Move the selected piece to the new location of the pointer as long as its still valid.
            | These are separated so that one axis can still be moved through if the other is
            | invalid.
            */
            if (validateDragX()) {

                selectedPiece.setLayoutX(mouseX - selX);

            } else {

                /*
                | If the drag location isn't valid, check if the mouse pointer is completely out of
                | bounds, and default to the location it should be at if so to prevent a bug where
                | the piece wont be in the right location if the mouse pointer moves too fast.
                */
                if (mouseX < 0) {

                    selectedPiece.setLayoutX(0);

                } else if (mouseX > 256) {

                    selectedPiece.setLayoutX(224);

                }

            }

            if (validateDragY()) {

                selectedPiece.setLayoutY(mouseY - selY);

            } else {

                if (mouseY < 0) {

                    selectedPiece.setLayoutY(0);

                } else if (mouseY > 256) {

                    selectedPiece.setLayoutY(224);

                }

            }

        }

    };

    // Drag confirmation
    // TODO: Do move confirmation using chesspiece rect, not mouse pointer
    private EventHandler<MouseEvent> releaseHandler = event -> {

        if (selectedPiece != null) {

            normalizePointer(event);

            /*
            | Confirm the move if the X/Y coordinates are valid, the move itself is valid, there
            | are no pieces of the same color at the destination, and if the piece has meaningfully
            | moved from its starting location.
            */
            if (validateXY() || validateDrag()) {

                updateSimpleXY();

                if (pieces.findChessPiece(turn, simpleX, simpleY) == null
                &&  selectedPiece.validateMove(simpleX, simpleY)
                && !selectedPiece.isAt(simpleX, simpleY)) {

                    doMove();

                    return;

                }

            }

            // Otherwise, recall the piece to its original location.
            selectedPiece.recall();

            selectedPiece = null;

        }

    };

    // Normalize a mouse pointer so that the coordinates are solely within the bounds of BoardPane
    private void normalizePointer(final MouseEvent event) {

        mouseX = (int) (event.getSceneX() - getLayoutX());
        mouseY = (int) (event.getSceneY() - getLayoutY());

    }

    // Validate that the X/Y coordinates are not out of bounds
    // Returns true if valid, false if not
    private boolean validateXY() {

        return mouseX > 0 && mouseX < getPrefWidth()
            && mouseY > 0 && mouseY < getPrefHeight();

    }

    // Validate that the currently dragged piece is still in the bounds of BoardPane
    // Returns true if valid, false if not
    private boolean validateDrag() {

        return validateDragX() && validateDragY();

    }

    // Sub-components of validateDrag() that are also called
    private boolean validateDragX() {

        return (32 - selX) + mouseX >= 32 && (32 - selX) + mouseX <= 256;

    }

    private boolean validateDragY() {

        return (32 - selY) + mouseY >= 32 && (32 - selY) + mouseY <= 256;

    }

    // Update the simple coordinates from the mouse coords
    private void updateSimpleXY() {

        simpleX = mouseX / 32;
        simpleY = mouseY / 32;

    }

    // --- MOVE MANAGEMENT ---

    // Confirm a move and run its logic
    private void doMove() {

        selectedPiece.confirmMove(simpleX, simpleY);

        /*
        | If a pawn has just been promoted however by reaching the end of the board,
        | disable the entire board and add a listener to the piece to wait until the
        | promotion process is done, don't change the turn if this happens.
        */
        if (selectedPiece.getType() == ChessType.PAWN) {

            if (selectedPiece.getY() == 0 || selectedPiece.getY() == 7) {

                promotedPiece = selectedPiece;
                isDisabled = true;

                if (promotionMenu == null) {

                    promotionMenu = new PromotionMenu(
                        promotionListener,
                        promotedPiece.getColor(),
                        promotedPiece.getX(), promotedPiece.getY()
                    );

                    getChildren().add(promotionMenu);

                } else {

                    promotionMenu.show(
                        promotedPiece.getColor(),
                        promotedPiece.getX(), promotedPiece.getY()
                    );

                }

            }

        }

        if (promotedPiece == null) {

            changeTurn();

        }

        moves++;

        if (moves == 30) {

            enableDraw();

            // Stub: Draw should enable once 30 moves pass.

        }

        selectedPiece = null;

    }

    // --- GAME EVENT LISTENERS ---

    // Confirmation for promotion
    private PromotionEndListener promotionListener = newType -> {

        /*
        | Remove the original pawn set to be promoted, and replace it with the type that was
        | chosen by the menu. Also end the players turn, as that didn't happen when the pawn
        | was originally moved.
        */
        factory.setColor(turn);
        factory.promote(promotedPiece.getX(), promotedPiece.getY(), newType);

        pieces.addPromotedPiece(pieces.findChessPiece(promotedPiece.getX(), promotedPiece.getY()));

        promotedPiece = null;
        isDisabled = false;

        changeTurn();

    };

    // Confirmation for reset
    private ResetListener resetListener = () -> {

        // Dont reset if *nothing* has actually happened
        if (moves != 0) {

            /*
            | Reset the chess pieces/references to the chess pieces, clear any specific values, and
            | hide any menus.
            */
            factory.replaceKilled();
            pieces.resetAll();

            if (promotionMenu != null) {

                promotionMenu.hide();

            }

            isDisabled = false;

            selectedPiece = null;
            promotedPiece = null;

            randomizeTurn();

        }

    };

    // Game ending
    public void onEnd(final ChessType winColor, final EndType type) {

        stats.onEnd(winColor, type);

        // Disable the game, and hide the promotion menu, in the case that causes issues.
        isDisabled = true;
        promotedPiece = null;

        if (promotionMenu != null) {

            promotionMenu.hide();

        }

    }

    public void enableDraw() {

        // Stub: Draw should enable after 30 moves

    }

    // --- ENTITY MANAGEMENT ---

    // Generate the decorative checker rectangles
    private void generateCheckerBoard() {

        Rectangle checkerRect;

        for (int x = 0; x <= 224; x += 32) {

            for (int y = 0; y <= 224; y += 32) {

                checkerRect = new Rectangle(x, y, 32, 32);

                /*
                | If the added simple coordinates [E.G 4X + 7Y] is divisible by two, than that
                | checker must be a lighter one. Otherwise it must be a dark one.
                */
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

        // Randomly determine which turn will be first.
        randomizeTurn();

        // Generate each color's pieces
        factory.setColor(ChessType.BLACK);
        factory.genHomeRow(0);
        factory.genPawnRow(1);

        factory.setColor(ChessType.WHITE);
        factory.genPawnRow(6);
        factory.genHomeRow(7);

    }

    // Randomize the turn
    public void randomizeTurn() {

        if (rand.nextBoolean()) {

            turn = ChessType.WHITE;

        } else {

            turn = ChessType.BLACK;

        }

        stats.changeTurn(turn);

    }

    // Change the turn
    public void changeTurn() {

        turn = ChessType.inverseOf(turn);

        stats.changeTurn(turn);

    }

    // Entity addition
    public void onAdded(final ChessPiece added) {

        getChildren().add(added);

    }

    // Entity removal
    public void onRemoved(final ChessPiece removed) {

        getChildren().remove(removed);

    }

    public void addStats(final StatPane newStats) {

        stats = newStats;

    }

}
