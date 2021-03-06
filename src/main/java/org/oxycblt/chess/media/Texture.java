// Enum of textures used by TextureAtlas

package org.oxycblt.chess.media;

public enum Texture {

    CHESS_PIECES("/org/oxycblt/chess/textures/chesspieces.png"),
    END_SCREEN("/org/oxycblt/chess/textures/endscreens.png"),
    BORDER("/org/oxycblt/chess/textures/border.png"),
    RESET("/org/oxycblt/chess/textures/reset.png"),
    ICON("/org/oxycblt/chess/textures/icon.png");

    private final String path;

    Texture(final String path) {

        this.path = path;

    }

    // Get the file path of a Texture
    public String getPath() {

        return path;

    }

}
