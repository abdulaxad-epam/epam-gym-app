package epam.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

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

    @Column(nullable = false)
    private String traineeId;

    @Column(nullable = false)
    private String trainerId;

    @Column(nullable = false)
    private String trainingName;

    @Column(nullable = false)
    @CreationTimestamp
    private String trainingDate;

    @JoinColumn(name = "trainingType_id")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private TrainingType trainingType;

    @Column(nullable = false)
    private String trainingDuration;

}