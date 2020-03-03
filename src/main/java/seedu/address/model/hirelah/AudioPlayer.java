package seedu.address.model.hirelah;


import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;

import javax.management.InvalidAttributeValueException;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Stores the audio file recorded during the interview session of an {@code Interviewee}.
 * Supports a minimal set of list operations.
 *
 */
public class AudioPlayer {
    private Long currentFrame;
    private final Clip clip;
    private String status;
    private AudioInputStream audioInputStream;
    private final File audioFile;

    // constructor to initialize streams and clip
    public AudioPlayer(File audioFile) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        // create AudioInputStream object
        this.audioFile = audioFile;
        audioInputStream = AudioSystem.getAudioInputStream(audioFile.getAbsoluteFile());

        // create clip reference
        clip = AudioSystem.getClip();

        // open audioInputStream to the clip
        clip.open(audioInputStream);

        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    // Method to play the audio
    public void play() {
        //start the clip
        clip.start();
        status = "play";
    }

    public void pause() {
        if (status.equals("paused")) {
            System.out.println("audio is already paused");
            return;
        }
        this.currentFrame = this.clip.getMicrosecondPosition();
        clip.stop();
        status = "paused";
    }

    // Method to resume the audio
    public void resumeAudio() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (status.equals("play")) {
            System.out.println("Audio is already "+ "being played");
            return;
        }
        clip.close();
        resetAudioStream();
        clip.setMicrosecondPosition(currentFrame);
        this.play();
    }

    // Method to jump over a specific part
    public AudioPlayer playAtTime(long c) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        //if (c > 0 && c < clip.getMicrosecondLength()) {
            resetAudioStream();
            currentFrame = c;
            AudioPlayer audioAtTime = new AudioPlayer(this.audioFile);
            audioAtTime.clip.setMicrosecondPosition(c);
            return audioAtTime;
        /*} else {

        }*/
    }

    // Method to reset audio stream
    public void resetAudioStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        audioInputStream = AudioSystem.getAudioInputStream(audioFile.getAbsoluteFile());
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
