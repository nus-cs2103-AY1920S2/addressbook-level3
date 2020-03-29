package seedu.address.logic;

import seedu.address.model.Pet;
import seedu.address.ui.PetDisplay;
import java.time.Duration;
import java.time.LocalDateTime;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class PetManager {
    private Pet pet;
    private LocalDateTime lastDoneTaskTime;
    private LocalDateTime timeForHangry;
    private PetDisplay petDisplay;
    private Timer timer;
    private TimerTask timerTask;
    // private Timer secondTimer;
    // private Timer thirdTimer;
    private boolean hasStarted;

    public PetManager() {
        this.timer = new Timer();
        this.timerTask = new TimerTask() {
            public void run() {
                changeToHangry();
                updatePetDisplayToHangry();
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
        System.out.println("When log in,  TIME FOR HANGRY: " + timeForHangry);
    }

    public void setPetDisplay(PetDisplay petDisplay) {
        this.petDisplay = petDisplay;
    }

    public void updateMoodWhenLogIn() {
        LocalDateTime now = LocalDateTime.now();
        if (pet.getMood().equals("HAPPY")) {
            Duration duration = Duration.between(now, timeForHangry);
            if (duration.isNegative()) {
                changeToHangry();
            } else {
                hasStarted = true;
                System.out.println("HASSTARTED IS TRUE WHEN LOG IN");
                Date timeForMoodChange = Date.from(timeForHangry.atZone(ZoneId.systemDefault()).toInstant());
                System.out.println("task is scheduled when logged in");
                timer.schedule(timerTask, timeForMoodChange);

                // System.out.print("THIS IS FIRST COUNTDOWN??!!!");
                // secondTimer = new Timer();
                // TimerTask task = new TimerTask() {
                // public void run() {
                // Duration duration = Duration.between(LocalDateTime.now(), timeForHangry);
                // System.out.println("DURATION LOG IN: " + duration);
                // if (duration.isNegative()) {
                // cancel();
                // }
                // }
                // };
                // Date date =
                // Date.from(lastDoneTaskTime.atZone(ZoneId.systemDefault()).toInstant());
                // secondTimer.scheduleAtFixedRate(task, date, 10000);

            }
        }
    }

    public void updateMoodWhenTaskDone() {
        if (hasStarted) {
            // System.out.println("timer has been cancelled starting new one");
            // secondTimer.cancel();
            timer.cancel();
        }
        timer = new Timer();
        lastDoneTaskTime = LocalDateTime.now();
        pet.setLastDoneTaskTime(lastDoneTaskTime.toString());
        // For ACTUAL
        // timeForHangry = lastDoneTaskTime.plusHours(24);
        // For TESTING
        timeForHangry = lastDoneTaskTime.plusMinutes(1);
        timerTask = new TimerTask() {
            @Override
            public void run() {
                changeToHangry();
                updatePetDisplayToHangry();
            }
        };
        Date timeForMoodChange = Date.from(timeForHangry.atZone(ZoneId.systemDefault()).toInstant());
        timer.schedule(timerTask, timeForMoodChange);
        changeToHappy();

        // System.out.print("SCEHDULE TIMERTASK....... THIS IS FINAL COUNTDOWN??!!!");
        // thirdTimer = new Timer();
        // TimerTask task = new TimerTask() {
        // public void run() {
        // Duration duration = Duration.between(LocalDateTime.now(), timeForHangry);
        // System.out.println("DURATION: " + duration);
        // if (duration.isNegative()) {
        // cancel();
        // }
        // }
        // };
        // Date date =
        // Date.from(lastDoneTaskTime.atZone(ZoneId.systemDefault()).toInstant());
        // thirdTimer.scheduleAtFixedRate(task, date, 10000);
    }

    public void updatePetDisplayWhenDone() {
        petDisplay.changeToHappy();
    }

    public void updatePetDisplayToHangry() {
        petDisplay.changeToHangry();
    }

    public void handleExit() {
        hasStarted = false;
        timer.cancel();
        // secondTimer.cancel();
        // thirdTimer.cancel();
    }

    void changeToHangry() {
        pet.changeHangry();
    }

    void changeToHappy() {
        pet.changeHappy();
    }
}