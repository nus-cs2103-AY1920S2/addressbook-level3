package seedu.address.model;

public class Pet implements ReadOnlyPet {
    private static final String DEFAULT_NAME = "BB Productive";
    private static final String DEFAULT_EXP = "0";
    private static final String DEFAULT_LEVEL = "1";
    private static final String DEFAULT_MOOD = "HAPPY";
    public String exp;
    public String level;
    public String name;
    public String mood;

    public Pet(String name, String exp, String level, String mood) {
        this.exp = exp;
        this.level = level;
        this.name = name;
        this.mood = mood;
    }

    public Pet(ReadOnlyPet source) {
        this.exp = source.getExp();
        this.level = source.getLevel();
        this.name = source.getName();
        this.mood = source.getMood();
    }

    public Pet() {
        this(DEFAULT_NAME, DEFAULT_EXP, DEFAULT_LEVEL, DEFAULT_MOOD);
    }

    public void setName(String name) {
        this.name = name;
    }

    // When a task is done, exp level increases by 5
    public void incrementExp() {
        int newExp = Integer.parseInt(this.exp) + 5;
        this.exp = Integer.toString(newExp);

        // update level
        int expInteger = Integer.parseInt(this.exp);
        int levelInteger = (int) Math.ceil(expInteger / 99.0);
        this.level = Integer.toString(levelInteger);
    }

    public void incrementPomExp() {
        int newExp = Integer.parseInt(this.exp) + 25;
        this.exp = Integer.toString(newExp);

        // update level
        int expInteger = Integer.parseInt(this.exp);
        int levelInteger = (int) Math.ceil(expInteger / 99.0);
        this.level = Integer.toString(levelInteger);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getLevel() {
        return level;
    }

    @Override
    public String getExp() {
        return this.exp;
    }

    @Override
    public String getMood() {
        return this.mood;
    }

    @Override
    public String toString() {
        return String.format("Hi I'm pet %s! my Exp is %s and my level is %s", name, exp, level);
    }
}
