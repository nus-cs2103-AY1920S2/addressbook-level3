package seedu.address.logic;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import seedu.address.model.Pet;

public class PetManager {

    public final String HANGRY_MOOD_STRING = "HANGRY";
    public final String HAPPY_MOOD_STRING = "HAPPY";

    private Pet pet;
    private Path petImage;
    private Path expBarImage;
    private String expBarText;
    private String levelText;

    private LocalDateTime lastDoneTaskTime;
    private LocalDateTime timeForHangry;

    public PetManager() {
    }

    public void setPet(Pet pet) {
        this.pet = pet;
        this.lastDoneTaskTime = LocalDateTime.parse(pet.getLastDoneTaskTime());
        // use this for ACTUAL
        // this.timeForHangry = lastDoneTaskTime.plusHours(24);
        // use this for TESTING
        this.timeForHangry = lastDoneTaskTime.plusMinutes(1);
    } 

    public void incrementPomExp() {
        this.pet.incrementPomExp();
    }

    public void incrementExp() {
        this.pet.incrementExp();
        updateDisplayElements();
    }

    public void updateLastDoneTaskWhenDone() {
        lastDoneTaskTime = LocalDateTime.now();
        pet.setLastDoneTaskTime(lastDoneTaskTime.toString());
        // For ACTUAL
        // timeForHangry = lastDoneTaskTime.plusHours(24);
        // For TESTING
        timeForHangry = lastDoneTaskTime.plusMinutes(1);
    }

    public void changeToHangry() {
        pet.changeHangry();
    }

    public void changeToHappy() {
        pet.changeHappy();
    }

    public void updateDisplayElements() {

        int exp = Integer.parseInt(pet.getExp());
        int expBarInt = exp % 100;
        expBarText = String.format("%d XP / 100 XP", expBarInt);

        levelText = this.pet.getLevel();

        String mood = pet.getMood();
      
        String petFilepath;

        if (levelText.equals("1")) {
            String path =
                    (mood.equals(HAPPY_MOOD_STRING)
                            ? "/images/pet/level1.png"
                           : "/images/pet/level1hangry.png");
           petFilepath = path;
        } else if (levelText.equals("2")) {
            String path =
                    (mood.equals(HAPPY_MOOD_STRING)
                            ? "/images/pet/level1.png"
                           : "/images/pet/level1hangry.png");
           petFilepath = path;
        } else {
            petFilepath = "/images/pet/level3.png";
       }

        int expBarPerc = expBarInt / 10;

        String expBarFilepath;

        switch (expBarPerc) {
            case 0:
                expBarFilepath = "/images/pet/ProgressBar0%.png";
                break;

            case 1:
                expBarFilepath = "/images/pet/ProgressBar10%.png";
                break;

            case 2:
                expBarFilepath = "/images/pet/ProgressBar20%.png";
                break;

            case 3:
                expBarFilepath = "/images/pet/ProgressBar30%.png";
                break;

            case 4:
                expBarFilepath = "/images/pet/ProgressBar40%.png";
                break;

            case 5:
                expBarFilepath = "/images/pet/ProgressBar50%.png";
                break;

            case 6:
                expBarFilepath = "/images/pet/ProgressBar60%.png";
                break;

            case 7:
                expBarFilepath = "/images/pet/ProgressBar70%.png";
                break;

            case 8:
                expBarFilepath = "/images/pet/ProgressBar80%.png";
                break;

            case 9:
                expBarFilepath = "/images/pet/ProgressBar90%.png";
                break;

            case 10:
                expBarFilepath = "/images/pet/ProgressBar100%.png";
                break;

            default:
                expBarFilepath = "/images/pet/ProgressBar0%.png";
                break;
        }
    }

    public String getPetName() {
        return pet.getName();
    }

    public String getLevelText() {
        return levelText;
    }

    public String getExpBarInt() {
        return expBarText;
    }

    public Path getExpBarImage() {
        return expBarImage;
    }

    public Path getPetImage() {
        return petImage;
    }

    public String getMood() {
        return pet.getMood();
    }

    public LocalDateTime getTimeForHangry() {
        return timeForHangry;
    }
}
