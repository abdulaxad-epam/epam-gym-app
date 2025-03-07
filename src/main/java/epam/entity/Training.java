package epam.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trainings")
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID trainingId;

    @JoinColumn(name = "trainee_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Trainee.class)
    private Trainee trainee;

    @JoinColumn(name = "trainer_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Trainer.class)
    private Trainer trainer;

    @Column(nullable = false)
    private String trainingName;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime trainingDate;

    @JoinColumn(name = "trainingType_id")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private TrainingType trainingType;

    @Column(nullable = false)
    private Integer trainingDuration;

}