// Text loader for the custom text spritesheet

package org.oxycblt.chess.media.text;

import javafx.scene.image.ImageView;

import org.oxycblt.chess.model.ChessType;

import org.oxycblt.chess.media.images.Texture;
import org.oxycblt.chess.media.images.TextureAtlas;

public final class TextLoader {

    private TextLoader() {

        // Not called.

    }

    private static final String VALID_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-:,!?()[]";

    private static char[] chars;
    private static char[] newChars;

    private static int index = 0;

    // Create a piece of text
    public static ImageView[] createText(final String text, final ChessType color,
                                         final int x, final int y) {

        ImageView[] views = new ImageView[text.length()];

        // There are only uppercase letters in the font texture, so change the array to fit that.
        chars = text.toUpperCase().toCharArray();

        for (int i = 0; i < views.length; i++) {

            /*
            | Add the corresponding texture for each character in the string, if the character
            | is not valid, load a blank texture. The latter is actually a bug, but it does what
            | I want it to do so I dont really mind.
            */
            index = VALID_CHARS.indexOf(chars[i]);

            views[i] = TextureAtlas.getTexture(
                Texture.TEXT,
                index % 9, (index / 9) + (color.getCoordinate() * 5),
                8, 10, (i * 9 + x), y
            );

        }

        return views;

    }

    // Edit a piece of text, only works with evenly sized strings because im lazy.
    public static ImageView[] editText(final String oldText, final String newText,
                                     final ChessType color, final int x, final int y,
                                     final ImageView... img) {

        ImageView[] views = new ImageView[oldText.length()];

        chars = oldText.toUpperCase().toCharArray();
        newChars = newText.toUpperCase().toCharArray();

        for (int i = 0; i < views.length; i++) {

            if (chars[i] == newChars[i]) {

                views[i] = img[i];

            } else {

                views[i] = TextureAtlas.getTexture(
                    Texture.TEXT,
                    index % 9, (index / 9) + (color.getCoordinate() * 5),
                    8, 10, (i * 9 + x), y
                );

            }

        }

        return views;

    }

}
