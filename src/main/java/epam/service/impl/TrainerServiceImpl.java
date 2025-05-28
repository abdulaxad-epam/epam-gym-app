package epam.service.impl;

import epam.client.TrainingServiceClient;
import epam.client.dto.TrainingResponseDTO;
import epam.dto.request_dto.TrainerRequestDTO;
import epam.dto.response_dto.RegisterTrainerResponseDTO;
import epam.dto.response_dto.TrainerResponseDTO;
import epam.entity.Trainer;
import epam.exception.exception.TrainerNotFoundException;
import epam.mapper.TrainerMapper;
import epam.repository.TrainerRepository;
import epam.service.TraineeTrainerService;
import epam.service.TrainerService;
import epam.client.service.TrainingTypeService;
import epam.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;

    private final TrainingTypeService trainingTypeService;

    private final TrainerMapper trainerMapper;

    private final TrainingServiceClient trainingServiceClient;

    @Override
    public RegisterTrainerResponseDTO createTrainer(TrainerRequestDTO trainerRequestDTO) {

        return trainerMapper.toRegisterTrainerResponseDTO(
                trainerRepository.save(
                        trainerMapper.toTrainer(
                                trainerRequestDTO, trainingTypeService.getTrainingByTrainingName(trainerRequestDTO.getSpecialization())
                        )
                )
        );
    }

    @Transactional
    @Override
    public TrainerResponseDTO updateTrainer(Authentication connectedUser, TrainerRequestDTO trainerRequestDTO) {
        UserDetails user = (UserDetails) connectedUser.getPrincipal();
        return trainerRepository.findTrainerByUser_Username(user.getUsername()).map(trainer -> {
            trainer.setSpecialization(
                    trainingTypeService.getTrainingByTrainingName(trainerRequestDTO.getSpecialization())
            );
            if (trainerRequestDTO.getUser() != null) {
                if (trainerRequestDTO.getUser().getFirstName() != null) {
                    trainer.getUser().setFirstname(trainerRequestDTO.getUser().getFirstName());
                }
                if (trainerRequestDTO.getUser().getLastName() != null) {
                    trainer.getUser().setLastname(trainerRequestDTO.getUser().getLastName());
                }
                if (trainerRequestDTO.getUser().getIsActive() != null) {
                    trainer.getUser().setIsActive(trainerRequestDTO.getUser().getIsActive());
                }
            }
            return trainerMapper.toTrainerResponseDTO(trainer);
        }).orElseThrow(() -> new TrainerNotFoundException("Trainer not found"));
    }

    @Override
    @Transactional
    public void deleteTrainer(Authentication connectedUser) {
        UserDetails user = (UserDetails) connectedUser.getPrincipal();
        String username = user.getUsername();


        trainerRepository.findTrainerByUser_Username(username).ifPresentOrElse(
                trainer -> {
                    trainingServiceClient.deleteTrainingsByTrainer(trainer.getTrainerId());
                    trainerRepository.deleteFromTrainerTrainee(trainer.getTrainerId());
                    trainerRepository.deleteTrainerByTrainerId(trainer.getTrainerId());
                    trainerRepository.flush();
                }
                , () -> {
                    throw new TrainerNotFoundException("Trainer with username: " + username + " does not exist");
                });

        log.info("Deleted trainings from user {}", username);
    }

    @Override
    public TrainerResponseDTO getTrainerProfile(Authentication connectedUser) {
        UserDetails user = (UserDetails) connectedUser.getPrincipal();
        return trainerRepository.findTrainerByUser_Username(user.getUsername())
                .map(trainerMapper::toTrainerResponseDTO)
                .orElseThrow(() -> new TrainerNotFoundException("Trainer not found"));
    }

    @Override
    public List<TrainingResponseDTO> getTrainerTrainings(Authentication connectedUser,
                                                         String periodFrom, String periodTo, String traineeName) {
        UserDetails user = (UserDetails) connectedUser.getPrincipal();

        return trainingServiceClient.getTrainerTrainings(traineeName, periodFrom, periodTo, user.getUsername());
    }

    @Transactional
    @Override
    public void updateTrainerStatus(Authentication connectedUser, Boolean isActive) {
        UserDetails user = (UserDetails) connectedUser.getPrincipal();
        Optional<Trainer> trainer = trainerRepository.findTrainerByUser_Username(user.getUsername());
        trainer.ifPresentOrElse(t -> t.getUser().setIsActive(isActive), () -> {
            throw new TrainerNotFoundException("Trainer not found");
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Trainer getTrainerProfile(String username) {
        return trainerRepository.findTrainerByUser_Username(username).orElseThrow(() -> new TrainerNotFoundException("Trainer not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Trainer getTrainerProfile(UUID trainerId) {
        return trainerRepository.findTrainerByTrainerId(trainerId).orElseThrow(() -> new TrainerNotFoundException("Trainer not found"));
    }
}
