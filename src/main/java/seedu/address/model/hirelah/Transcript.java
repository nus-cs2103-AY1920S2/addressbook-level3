package seedu.address.model.hirelah;

import seedu.address.commons.exceptions.IllegalValueException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.time.Instant;

/**
 * TODO: Javadoc
 */
public class Transcript {
    private final RemarkList remarkList;
    private File audioFile;
    /*make it mutable, to set and get the audio file
    file; when play,  refer to this file
   // private final AudioPlayer audioPlayer;
    private final Map<Attribute, Integer>the starting remark;
            Map from q number
            Have a FileObject, the path to the audio recording
            //just store the file, no need to care about the file path*/

    Transcript() {
        this.remarkList = new RemarkList();
        this.audioFile = null;
    }

    public void setAudioFile(File audioFile) {
        this.audioFile = audioFile;
    }

    private long getInterviewDuration() {
        return remarkList.getLastRemarkTime();
    }

    public Remark getTranscriptAtTime(long timeMs) throws IllegalValueException {
        if (timeMs > this.getInterviewDuration()) {
            throw new IllegalValueException("The duration of the interview is only " + this.getInterviewDuration());
        }
        Remark currentRemark = this.remarkList.getRemarkAtTime(timeMs);
        return currentRemark;
    }

    public Remark getTranscriptAtQuestion(Question question)
            throws UnsupportedAudioFileException, IOException, LineUnavailableException, IllegalValueException {
        Remark currentRemark = this.remarkList.getRemarkAtQuestion(question);
        long timeMs = this.remarkList.getTimeOfQuestion(question);
        return currentRemark;
    }
    /*
    TODO: need to create audioString method in Interviewee class, and the format of the audio file name has to be a certain format
     some way to
     After finish interview, we rename the file as the intended name e.g. "marioaudio.wav"
     1. store remark objects
     2. query by time
     3. query by question number
     4. contains the audio file object as well
     */
}
