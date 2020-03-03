package seedu.address.model.hirelah;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Stores the audio file recorded during the interview session of an {@code Interviewee}.
 * Provides functionality to play, pause, resume and jump to a particular time for the audio file.
 */
public class AudioPlayer {
    //Solution below adapted from https://www.geeksforgeeks.org/play-audio-file-using-java/

    private Long frame;
    private final Clip clip;
    private AudioInputStream audioInputStream;
    private final File audioFile;

    /**
     * Constructs an {@code AudioPlayer} object with the specified audio file.
     *
     * @param audioFile The audio file to be encapsulated by the {@code AudioPlayer}.
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public AudioPlayer(File audioFile) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.audioFile = audioFile;
        audioInputStream = AudioSystem.getAudioInputStream(audioFile.getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Plays the audio file at the current frame that it is in.
     */
    public void play() {
        clip.start();
    }

    /**
     * Pauses the audio from playing.
     */
    public void pause() {
        this.frame = this.clip.getMicrosecondPosition();
        clip.stop();
    }

    /**
     * Resumes the audio from pausing at the frame when it last was paused.
     */
    public void resume() {
        clip.close();
        clip.setMicrosecondPosition(frame);
        this.play();
    }

    /**
     * Generates a new {@code AudioPlayer} that are set to start a particular time.
     *
     * @param timeInMs The time to be set as the starting time of the audio file.
     */
    public AudioPlayer playAtMs(long timeInMs)
            throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        frame = timeInMs;
        AudioPlayer audioAtTime = new AudioPlayer(this.audioFile);
        audioAtTime.clip.setMicrosecondPosition(timeInMs);
        return audioAtTime;
    }
}
