package seedu.address.logic;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import seedu.address.model.Pet;
import seedu.address.ui.MainWindow;

public class PetManager {

    public final String HANGRY_MOOD_STRING = "HANGRY";
    public final String HAPPY_MOOD_STRING = "HAPPY";

    private Pet pet;
    private MainWindow mainWindow;
    private Path petImage;
    private Path expBarImage;
    private String expBarText;
    private String levelText;

    private LocalDateTime lastDoneTaskTime;
    private LocalDateTime timeForHangry;
    private Timer timer;
    private TimerTask timerTask;
    private boolean hasStarted;

    public PetManager() {
        this.timer = new Timer();
        this.timerTask =
                new TimerTask() {
                    public void run() {
                        changeToHangry();
                        updateDisplayElements();
                        mainWindow.updatePetDisplay();
                    }
                };
        this.hasStarted = false;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
        this.lastDoneTaskTime = LocalDateTime.parse(pet.getLastDoneTaskTime());
        // use this for ACTUAL
        // this.timeForHangry = lastDoneTaskTime.plusHours(24);
        // use this for TESTING
        this.timeForHangry = lastDoneTaskTime.plusMinutes(1);
    }

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void updateMoodWhenLogIn() {
        LocalDateTime now = LocalDateTime.now();
        if (pet.getMood().equals(HAPPY_MOOD_STRING)) {
            Duration duration = Duration.between(now, timeForHangry);
            if (duration.isNegative()) {
                changeToHangry();
                updateDisplayElements();
                hasStarted = false;
            } else {
                hasStarted = true;
                Date timeForMoodChange =
                        Date.from(timeForHangry.atZone(ZoneId.systemDefault()).toInstant());
                timer.schedule(timerTask, timeForMoodChange);
            }
        }
    }

    public void incrementPomExp() {
        this.pet.incrementPomExp();
    }

    public void incrementExp() {
        this.pet.incrementExp();
        updateDisplayElements();
    }

    public void updateMoodWhenDone() {
        if (hasStarted) {
            // secondTimer.cancel();
            timer.cancel();
        }
        timer = new Timer();
        this.timerTask =
                new TimerTask() {
                    public void run() {
                        changeToHangry();
                        updateDisplayElements();
                        mainWindow.updatePetDisplay();
                    }
                };
        lastDoneTaskTime = LocalDateTime.now();
        pet.setLastDoneTaskTime(lastDoneTaskTime.toString());
        // For ACTUAL
        // timeForHangry = lastDoneTaskTime.plusHours(24);
        // For TESTING
        timeForHangry = lastDoneTaskTime.plusMinutes(1);
        Date timeForMoodChange =
                Date.from(timeForHangry.atZone(ZoneId.systemDefault()).toInstant());
        timer.schedule(timerTask, timeForMoodChange);
        changeToHappy();
        updateDisplayElements();
    }

    public void handleExit() {
        hasStarted = false;
        timer.cancel();
    }

    void changeToHangry() {
        pet.changeHangry();
    }

    void changeToHappy() {
        pet.changeHappy();
    }

    public void updateDisplayElements() {

        int exp = Integer.parseInt(pet.getExp());
        int expBarInt = exp % 100;
        expBarText = String.format("%d XP / 100 XP", expBarInt);

        levelText = this.pet.getLevel();

        String mood = pet.getMood();

        if (levelText.equals("1")) {
            Path path =
                    (mood.equals(HAPPY_MOOD_STRING)
                            ? Paths.get("images", "pet", "level1.png")
                            : Paths.get("images", "pet", "level1hangry.png"));
            petImage = path;
        } else if (levelText.equals("2")) {
            Path path =
                    (mood.equals(HAPPY_MOOD_STRING)
                            ? Paths.get("images", "pet", "level1.png")
                            : Paths.get("images", "pet", "level1hangry.png"));
            petImage = path;
        } else {
            petImage = Paths.get("images", "pet", "level3.png");
        }

        int expBarPerc = expBarInt / 10;

        switch (expBarPerc) {
            case 0:
                expBarImage = Paths.get("images", "pet", "ProgressBar0%.png");
                break;

            case 1:
                expBarImage = Paths.get("images", "pet", "ProgressBar10%.png");
                break;

            case 2:
                expBarImage = Paths.get("images", "pet", "ProgressBar20%.png");
                break;

            case 3:
                expBarImage = Paths.get("images", "pet", "ProgressBar30%.png");
                break;

            case 4:
                expBarImage = Paths.get("images", "pet", "ProgressBar40%.png");
                break;

            case 5:
                expBarImage = Paths.get("images", "pet", "ProgressBar50%.png");
                break;

            case 6:
                expBarImage = Paths.get("images", "pet", "ProgressBar60%.png");
                break;

            case 7:
                expBarImage = Paths.get("images", "pet", "ProgressBar70%.png");
                break;

            case 8:
                expBarImage = Paths.get("images", "pet", "ProgressBar80%.png");
                break;

            case 9:
                expBarImage = Paths.get("images", "pet", "ProgressBar90%.png");
                break;

            case 10:
                expBarImage = Paths.get("images", "pet", "ProgressBar100%.png");
                break;

            default:
                expBarImage = Paths.get("images", "pet", "ProgressBar0%.png");
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
}
