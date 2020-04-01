package seedu.address.model.graduation;

public enum AggregationType {
    ALL("All"),
    ANY("Any"),
    AT_LEAST_MC(">=");
    private String aggregationType;

    AggregationType(String aggregationType) {
        this.aggregationType = aggregationType;
    }

    public String getAggregationType(int minMCs) {
        if (aggregationType.equals(">=")) {
            return aggregationType + " " + minMCs + " MCs";
        }
        return aggregationType;
    }
}
