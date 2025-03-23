package epam.trainer.service.impl;

import epam.trainer.dto.TrainerRequestDTO;
import epam.trainer.dto.TrainerResponseDTO;
import epam.trainer.entity.Trainer;
import epam.shared.training_type.entity.TrainingType;
import epam.shared.exception.exception.TrainerNotFoundException;
import epam.trainer.mapper.TrainerMapper;
import epam.trainer.repository.TrainerRepository;
import epam.trainer.service.TrainerService;
import epam.shared.training_type.service.TrainingTypeService;
import epam.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;

    private final TrainingTypeService trainingTypeService;

    private final TrainerMapper trainerMapper;

    private final UserService userService;

    @Override
    public TrainerResponseDTO createTrainer(TrainerRequestDTO trainerRequestDTO) {

        TrainingType trainingType = trainingTypeService.getTrainingByTrainingName(trainerRequestDTO.getSpecialization());

        Trainer trainer = trainerMapper.toTrainer(trainerRequestDTO, trainingType);

        Trainer inserted = trainerRepository.insert(trainer);

        return trainerMapper.toTrainerResponseDTO(inserted);
    }

    @Transactional
    @Override
    public TrainerResponseDTO updateTrainer(String username, TrainerRequestDTO trainerRequestDTO) {

        Optional<UUID> id = trainerRepository.getIdByUsername(username);
        TrainingType trainingType = trainingTypeService.getTrainingByTrainingName(trainerRequestDTO.getSpecialization());

        if (id.isPresent()) {
            Trainer trainer = trainerRepository.findById(id.get())
                    .orElseThrow(() -> new TrainerNotFoundException("Trainer not found"));

            trainer.setSpecialization(trainingType);

            return trainerMapper.toTrainerResponseDTO(trainer);
        }

        throw new TrainerNotFoundException("Trainer not found");
    }


    @Override
    public void deleteTrainer(String username) {
        if (userService.existsByUsername(username)) {
            trainerRepository.deleteTrainerByUsername(username);
        } else {
            throw new TrainerNotFoundException("Trainer not found");
        }
    }

    @Override
    public TrainerResponseDTO getTrainerByUsername(String username) {
        Optional<Trainer> byUsername = trainerRepository.findByUsername(username);
        if (byUsername.isPresent()) {
            return trainerMapper.toTrainerResponseDTO(byUsername.get());
        } else {
            throw new TrainerNotFoundException("Trainer not found");
        }
    }

    @Override
    public List<TrainerResponseDTO> getAllTrainers() {
        List<Trainer> trainers = trainerRepository.findAll();
        return trainers.stream().map(trainerMapper::toTrainerResponseDTO).toList();
    }

    @Override
    public List<TrainerResponseDTO> getTrainersByTrainee(String currentUsername) {
        List<Trainer> trainers = trainerRepository.findTrainersByTrainee(currentUsername);
        return trainers.stream().map(trainerMapper::toTrainerResponseDTO).toList();
    }
}
