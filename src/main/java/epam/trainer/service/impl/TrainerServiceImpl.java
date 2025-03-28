package epam.trainer.service.impl;

import epam.shared.exception.exception.TrainerNotFoundException;
import epam.trainer.entity.Trainer;
import epam.training_type.service.TrainingTypeService;
import epam.trainer.dto.TrainerRequestDTO;
import epam.trainer.dto.TrainerResponseDTO;
import epam.trainer.mapper.TrainerMapper;
import epam.trainer.repository.TrainerRepository;
import epam.trainer.service.TrainerService;
import epam.training.dto.TrainingResponseDTO;
import epam.training.entity.Training;
import epam.training.mapper.TrainingMapper;
import epam.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Log
@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;

    private final TrainingTypeService trainingTypeService;

    private final TrainerMapper trainerMapper;
    private final TrainingMapper trainingMapper;

    private final UserService userService;

    @Override
    public TrainerResponseDTO createTrainer(TrainerRequestDTO trainerRequestDTO) {

        return trainerMapper.toTrainerResponseDTO(
                trainerRepository.insert(
                        trainerMapper.toTrainer(
                                trainerRequestDTO, trainingTypeService.getTrainingByTrainingName(trainerRequestDTO.getSpecialization())
                        )
                )
        );
    }

    @Transactional
    @Override
    public TrainerResponseDTO updateTrainer(String username, TrainerRequestDTO trainerRequestDTO) {
        return trainerRepository.findByUsername(username).map(trainer -> {
            trainer.setSpecialization(
                    trainingTypeService.getTrainingByTrainingName(trainerRequestDTO.getSpecialization())
            );
            return trainerMapper.toTrainerResponseDTO(trainer);
        }).orElseThrow(() -> new TrainerNotFoundException("Trainer not found"));
    }

    @Override
    public void deleteTrainer(String username) {
        if (userService.existsByUsername(username)) {
            trainerRepository.deleteTrainerByUsername(username);
        }
        throw new TrainerNotFoundException("Trainer not found");
    }

    @Override
    public TrainerResponseDTO getTrainerByUsername(String username) {
        return trainerRepository.findByUsername(username)
                .map(trainerMapper::toTrainerResponseDTO)
                .orElseThrow(() -> new TrainerNotFoundException("Trainer not found"));
    }

    @Override
    public List<TrainerResponseDTO> getAllTrainers() {
        return trainerRepository.findAll()
                .stream().map(trainerMapper::toTrainerResponseDTO).toList();
    }

    @Override
    public List<TrainerResponseDTO> getTrainersByTrainee(String currentUsername) {
        return trainerRepository.findTrainersByTrainee(currentUsername)
                .stream().map(trainerMapper::toTrainerResponseDTO).toList();
    }

    @Override
    public List<TrainingResponseDTO> getTrainerTrainings(String username, String periodFrom, String periodTo, String traineeName) {
        List<Training> training = trainerRepository.getTrainerTrainings(username, periodFrom, periodTo, traineeName).orElseThrow(() -> {
            log.warning("Exception occurred while getting trainers trainings with username: " + username);
            return new TrainerNotFoundException("Trainer not found");
        });
        return training.stream().map(trainingMapper::toTrainingResponseDTO).toList();
    }

    @Override
    public void updateTrainerStatus(String username, Boolean isActive) {
        Optional<Trainer> trainer = trainerRepository.findByUsername(username);
        trainer.ifPresentOrElse(t->t.getUser().setIsActive(isActive), () -> {
            log.warning("Exception occurred while updating trainer status with username: " + username);
            throw new TrainerNotFoundException("Trainer not found");
        });
    }
}
