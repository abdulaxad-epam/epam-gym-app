package epam.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trainee_trainer")
public class TraineeTrainer {


    @EmbeddedId
    private TraineeTrainerId id;

    @ManyToOne
    @MapsId("traineeId")
    @JoinColumn(name = "trainee_id")
    private Trainee trainee;

    @ManyToOne

    @MapsId("trainerId")
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @Data
    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    static class TraineeTrainerId implements Serializable {

        @Column(name = "trainee_id")
        private UUID traineeId;

        @Column(name = "trainer_id")
        private UUID trainerId;

    }
}
