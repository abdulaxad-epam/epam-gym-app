package epam.client.service;

import epam.service.TraineeService;
import epam.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TrainingUrlBuilder {

    @Value("${training.service.name}")
    private String trainingServiceName;

    private TrainerService trainerService;
    private TraineeService traineeService;

    private final TrainingTypeService trainingTypeService;

    @Autowired
    public void setTrainerService(@Lazy TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @Autowired
    public void setTraineeService(@Lazy TraineeService traineeService) {
        this.traineeService = traineeService;
    }
    public String buildTraineeTrainingsQueryUrl(UUID traineeId, String trainer, String trainingTypeName, String periodFrom, String periodTo) {
        StringBuilder url = new StringBuilder(buildEndpointUrl("/trainee"));

        if (traineeId != null) {
            url.append("?traineeId=").append(traineeId);
        }
        if (trainer != null) {
            UUID trainerId = trainerService.getTrainerProfile(trainer).getTrainerId();
            url.append("&trainerId=").append(trainerId);
        }

        if (trainingTypeName != null) {
            UUID trainingTypeId = trainingTypeService.getTrainingByTrainingName(trainingTypeName).getTrainingTypeId();
            url.append("&trainingTypeId=").append(trainingTypeId);
        }

        if (periodFrom != null) {
            url.append("&periodFrom=").append(periodFrom);
        }
        if (periodTo != null) {
            url.append("&periodTo=").append(periodTo);
        }

        return url.toString();
    }

    public String buildEndpointUrl(String endpoint) {
        return "http://" + trainingServiceName + "/api/v1/trainings" + endpoint;
    }

    public String buildTrainerTrainingsQueryUrl(UUID trainerId, String trainee, String periodFrom, String periodTo) {
        StringBuilder url = new StringBuilder(buildEndpointUrl("/trainer"));

        if (trainerId != null) {
            url.append("?trainerId=").append(trainerId);
        }
        if (trainee != null) {
            UUID traineeId = traineeService.getTraineeProfile(trainee).getTraineeId();
            url.append("&traineeId=").append(traineeId);
        }
        if (periodFrom != null) {
            url.append("&periodFrom=").append(periodFrom);
        }
        if (periodTo != null) {
            url.append("&periodTo=").append(periodTo);
        }
        return url.toString();
    }


}
