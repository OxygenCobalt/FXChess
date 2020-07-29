// Enum of audio files used by AudioLoader

package org.oxycblt.chess.media;

public enum Audio {

    /*
    All sounds sourced from FreeSound.

    "Placing chess pieces on chess board.wav" by BiancaBothaPure
    https://freesound.org/people/BiancaBothaPure/sounds/437484/

    "dragslide1.mp3" by ChaosEntertainment
    https://freesound.org/people/ChaosEntertainment/sounds/396705/
    */

    MOVE("/org/oxycblt/chess/audio/chess_place.wav"),
    RESET("/org/oxycblt/chess/audio/chess_slide.wav");

    public static final Audio[] AUDIO_LIST = new Audio[]{
        MOVE, RESET
    };

    private final String path;

    Audio(final String path) {

        this.path = path;

    }

    public String getPath() {

        return path;

    }

}
