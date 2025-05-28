package epam.client;


import epam.client.dto.TrainingRequestDTO;
import epam.client.dto.TrainingResponseDTO;
import epam.client.dto.TrainingWorkloadRequestDTO;
import epam.client.dto.TrainingWorkloadResponseDTO;
import epam.client.service.TrainingRestClient;
import epam.client.service.TrainingTypeService;
import epam.client.service.TrainingUrlBuilder;
import epam.dto.response_dto.TraineeResponseDTO;
import epam.dto.response_dto.TrainerResponseDTO;
import epam.entity.Trainee;
import epam.entity.Trainer;
import epam.entity.TrainingType;
import epam.mapper.TraineeMapper;
import epam.mapper.TrainerMapper;
import epam.service.TraineeService;
import epam.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class TrainingServiceClient {
    private TrainerService trainerService;
    private TraineeService traineeService;

    private final TrainingTypeService trainingTypeService;

    private final TraineeMapper traineeMapper;
    private final TrainerMapper trainerMapper;

    private final TrainingUrlBuilder urlBuilder;
    private final TrainingRestClient trainingRestClient;

    @Autowired
    public void setTraineeService(@Lazy TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @Autowired
    public void setTrainerService(@Lazy TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    public void deleteTrainingsByTrainer(UUID trainerId) {
        String url = urlBuilder.buildEndpointUrl("/remove/trainer/" + trainerId);
        trainingRestClient.deleteTrainerTrainings(url);
    }

    @PreAuthorize("hasRole('TRAINER')")
    public TrainingResponseDTO createTraining(TrainingRequestDTO trainingRequestDTO, Authentication auth) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        TrainingType trainingType = trainingTypeService.getTrainingByTrainingName(trainingRequestDTO.getTrainingType());
        Trainee trainee = traineeService.getTraineeProfile(trainingRequestDTO.getTraineeUsername());
        Trainer trainer = trainerService.getTrainerProfile(userDetails.getUsername());

        TrainingWorkloadRequestDTO requestDTO = TrainingWorkloadRequestDTO.builder()
                .trainingTypeId(trainingType.getTrainingTypeId())
                .traineeId(trainee.getTraineeId())
                .trainerId(trainer.getTrainerId())
                .trainingDate(trainingRequestDTO.getTrainingDate())
                .trainingDuration(trainingRequestDTO.getTrainingDuration())
                .build();

        String url = urlBuilder.buildEndpointUrl("");
        TrainingWorkloadResponseDTO response = trainingRestClient.postTraining(url, requestDTO).getBody();
        return convertToResponse(response);
    }

    @PreAuthorize("hasRole('TRAINEE')")
    public List<TrainingResponseDTO> getTraineeTrainings(String traineeName, String periodFrom, String periodTo, String trainerName, String trainingTypeName) {
        Trainee trainee = traineeService.getTraineeProfile(traineeName);
        String url = urlBuilder.buildTraineeTrainingsQueryUrl(trainee.getTraineeId(), trainerName, trainingTypeName, periodFrom, periodTo);
        List<TrainingWorkloadResponseDTO> workloads = trainingRestClient.getTrainings(url).getBody();
        return workloads == null ? List.of() : workloads.stream().map(this::convertToResponse).toList();
    }

    @PreAuthorize("hasRole('TRAINER')")
    public List<TrainingResponseDTO> getTrainerTrainings(String traineeName, String periodFrom, String periodTo, String username) {
        Trainer trainer = trainerService.getTrainerProfile(username);
        String url = urlBuilder.buildTrainerTrainingsQueryUrl(trainer.getTrainerId(), traineeName, periodFrom, periodTo);
        List<TrainingWorkloadResponseDTO> workloads = trainingRestClient.getTrainings(url).getBody();
        return workloads == null ? List.of() : workloads.stream().map(this::convertToResponse).toList();
    }

    @PreAuthorize("hasRole('TRAINER')")
    public String deleteTraining(UUID trainingId, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Trainer trainer = trainerService.getTrainerProfile(userDetails.getUsername());
        String url = urlBuilder.buildEndpointUrl("/remove/" + trainingId + "/trainer/" + trainer.getTrainerId());

        ResponseEntity<String> response = trainingRestClient.deleteTrainerTrainings(url);

        return response.getBody();
    }

    private TrainingResponseDTO convertToResponse(TrainingWorkloadResponseDTO dto) {
        TraineeResponseDTO trainee = traineeMapper.toTraineeResponseDTO(traineeService.getTraineeProfile(dto.getTraineeId()));
        TrainerResponseDTO trainer = trainerMapper.toTrainerResponseDTO(trainerService.getTrainerProfile(dto.getTrainerId()));
        String trainingType = trainingTypeService.getTrainingNameById(dto.getTrainingTypeId());

        return TrainingResponseDTO.builder()
                .trainingName(trainingType)
                .trainee(trainee)
                .trainer(trainer)
                .trainingDate(dto.getTrainingDate())
                .trainingType(trainingType)
                .trainingDuration(dto.getTrainingDuration())
                .build();
    }
}
