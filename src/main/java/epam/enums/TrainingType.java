package epam.enums;

import lombok.Getter;

@Getter
public enum TrainingType {
    STRENGTH_TRAINING("Strength Training (Resistance Training)"),
    CARDIOVASCULAR_TRAINING("Cardiovascular Training (Endurance Training)"),
    HYPERTROPHY_TRAINING("Hypertrophy Training (Muscle Growth)"),
    FUNCTIONAL_TRAINING("Functional Training"),
    FLEXIBILITY("Flexibility & Mobility Training");

    private final String description;

    TrainingType(String description) {
        this.description = description;
    }

}

