// Texture loading for the spritesheets in resources

package org.oxycblt.chess.media.images;

import java.util.HashMap;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;

public final class TextureAtlas {

    private TextureAtlas() {

        // Not called

    }

    // Default dimensions for textures
    private static final int DEF_WIDTH = 32;
    private static final int DEF_HEIGHT = 32;

    private static HashMap<Texture, Image> loadedImages = new HashMap<Texture, Image>();

    // Get a texture based on the simple X/Y coords [E.G 3X 7Y] and the default size
    public static ImageView getTexture(final Texture tex, final int x, final int y) {

        ImageView view = new ImageView(loadFullTexture(tex));

        view.setViewport(new Rectangle2D(
            x * DEF_WIDTH, y * DEF_HEIGHT, DEF_WIDTH, DEF_HEIGHT
        ));

        return view;

    }

    // Get a texture using the simple X/Y coords & a custom size
    public static ImageView getTexture(final Texture tex,
                                       final int x, final int y,
                                       final int w, final int h) {

        ImageView view = new ImageView(loadFullTexture(tex));

        view.setViewport(new Rectangle2D(
            x * w, y * h, w, h
        ));

        return view;

    }

    // Get full Image of all the textures of the specified sheet
    private static Image loadFullTexture(final Texture tex) {

        // Check if image is loaded, load it if not
        if (!loadedImages.containsKey(tex)) {

            loadedImages.put(
                tex,
                new Image(TextureAtlas.class.getResource(tex.getPath()).toString())
            );

        }

        return loadedImages.get(tex);

    }

}
