package epam.domain;

import epam.enums.TrainingType;
import lombok.*;

import java.util.UUID;


@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Training {

    private UUID trainingId;
    private String traineeId;
    private String trainerId;
    private String trainingName;
    private String trainingDate;
    private TrainingType trainingType;
    private String trainingDuration;

    private Training(TrainingBuilder builder) {
        this.trainingId = builder.trainingId;
        this.traineeId = builder.traineeId;
        this.trainerId = builder.trainerId;
        this.trainingName = builder.trainingName;
        this.trainingDate = builder.trainingDate;
        this.trainingType = builder.trainingType;
        this.trainingDuration = builder.trainingDuration;
    }

    public static class TrainingBuilder {

        private UUID trainingId;
        private String traineeId;
        private String trainerId;
        private String trainingName;
        private String trainingDate;
        private TrainingType trainingType;
        private String trainingDuration;

        public TrainingBuilder trainingId(UUID trainingId) {
            this.trainingId = trainingId;
            return this;
        }

        public TrainingBuilder traineeId(String traineeId) {
            this.traineeId = traineeId;
            return this;
        }

        public TrainingBuilder trainerId(String trainerId) {
            this.trainerId = trainerId;
            return this;
        }

        public TrainingBuilder trainingName(String trainingName) {
            this.trainingName = trainingName;
            return this;
        }

        public TrainingBuilder trainingDate(String trainingDate) {
            this.trainingDate = trainingDate;
            return this;
        }

        public TrainingBuilder trainingType(TrainingType trainingType) {
            this.trainingType = trainingType;
            return this;
        }

        public TrainingBuilder trainingDuration(String trainingDuration) {
            this.trainingDuration = trainingDuration;
            return this;
        }

        public Training build() {
            return new Training(this);
        }
    }
}

