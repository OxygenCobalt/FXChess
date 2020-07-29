// Class for AudioClips

package org.oxycblt.chess.media;

import java.util.HashMap;

import javafx.scene.media.AudioClip;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

public final class AudioLoader {

    private static HashMap<Audio, AudioClip> clips;

    private AudioLoader() {

        // Not called.

    }

    /*
    | Load all the sounds initially on a separate thread to decrease
    | startup times and avoid long freezes when the clips are first played
    */
    public static void init() {

        Task<HashMap<Audio, AudioClip>> load = new Task<HashMap<Audio, AudioClip>>() {

            @Override
            protected HashMap<Audio, AudioClip> call() throws Exception {

                HashMap<Audio, AudioClip> clipMap = new HashMap<Audio, AudioClip>();

                for (Audio audio : Audio.AUDIO_LIST) {

                    // Create a new AudioClip with the path stored in the Audio enum.
                    AudioClip clip = new AudioClip(
                        Audio.class.getResource(audio.getPath()).toString()
                    );

                    /*
                    | Stop the clip [Which actually loads it for some reason] and put
                    | it into the dictionary of audio values.
                    */
                    clip.stop();

                    clipMap.put(audio, clip);

                }

                return clipMap;

            }

        };

        load.addEventHandler(

            WorkerStateEvent.WORKER_STATE_SUCCEEDED,

            /*
            | Once the task is done, assign the created HashMap to a value that can
            | be used elsewhere.
            */
            new EventHandler<WorkerStateEvent>() {

                @Override
                public void handle(final WorkerStateEvent event) {

                    clips = load.getValue();

                }

            }

        );

        new Thread(load).start();

    }

    // Get a sound, returns the respective AudioClip.
    public static AudioClip getSound(final Audio name) {

        return clips.get(name);

    }

}
