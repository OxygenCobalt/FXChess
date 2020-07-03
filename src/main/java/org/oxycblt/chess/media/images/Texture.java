// Enum of textures used by TextureAtlas

package org.oxycblt.chess.media.images;

public enum Texture {

    CHESS_PIECES("/org/oxycblt/chess/textures/chesspieces.png");

    private final String path;

    Texture(final String path) {

        this.path = path;

    }

    public String getPath() {

        return path;

    }

}
