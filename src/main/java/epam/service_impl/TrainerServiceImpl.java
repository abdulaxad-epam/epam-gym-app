package epam.service_impl;

import epam.entity.Trainee;
import epam.entity.Trainer;
import epam.exception.TrainerNotFoundException;
import epam.mapper.TrainerMapper;
import epam.repository.TrainerRepository;
import epam.request_dto.TrainerRequestDTO;
import epam.response_dto.TrainerResponseDTO;
import epam.service.TrainerService;
import epam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;

    private final TrainerMapper trainerMapper;

    private final UserService userService;

    @Override
    public TrainerResponseDTO createTrainer(TrainerRequestDTO trainerRequestDTO) {
        Trainer trainer = trainerMapper.toTrainer(trainerRequestDTO);
        Trainer inserted = trainerRepository.insert(trainer);

        return trainerMapper.toTrainerResponseDTO(inserted);
    }

    @Override
    public TrainerResponseDTO updateTrainer(String username, TrainerRequestDTO trainerRequestDTO) {

        Optional<UUID> id = trainerRepository.getIdByUsername(username);

        if (id.isPresent()) {
            Trainer trainer = trainerMapper.toTrainer(trainerRequestDTO);

            Trainer updated = trainerRepository.update(id.get(),trainer);
            return trainerMapper.toTrainerResponseDTO(updated);
        } else throw new TrainerNotFoundException("Trainer not found");
    }

    @Override
    public void deleteTrainer(String username) {
        if (userService.existsByUsername(username)) {
            trainerRepository.deleteTrainerByUsername(username);
        } else throw new TrainerNotFoundException("Trainer not found");
    }

    @Override
    public TrainerResponseDTO getTrainerByUsername(String username) {
        Optional<Trainer> byUsername = trainerRepository.findByUsername(username);
        if (byUsername.isPresent()) {
            return trainerMapper.toTrainerResponseDTO(byUsername.get());
        } else throw new TrainerNotFoundException("Trainer not found");
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
