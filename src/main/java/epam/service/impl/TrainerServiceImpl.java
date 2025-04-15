package epam.service.impl;

import epam.dto.request_dto.TrainerRequestDTO;
import epam.dto.response_dto.RegisterTrainerResponseDTO;
import epam.dto.response_dto.TrainerResponseDTO;
import epam.dto.response_dto.TrainingResponseDTO;
import epam.entity.Trainer;
import epam.entity.Training;
import epam.entity.User;
import epam.exception.exception.TrainerNotFoundException;
import epam.mapper.TrainerMapper;
import epam.mapper.TrainingMapper;
import epam.repository.TrainerRepository;
import epam.service.TrainerService;
import epam.service.TrainingTypeService;
import epam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;

    private final TrainingTypeService trainingTypeService;

    private final TrainerMapper trainerMapper;
    private final TrainingMapper trainingMapper;

    private final UserService userService;

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
        return trainerRepository.findTraineeByUser_Username(user.getUsername()).map(trainer -> {
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
    public void deleteTrainer(Authentication connectedUser) {
        UserDetails user = (UserDetails) connectedUser.getPrincipal();
        String username = user.getUsername();
        if (userService.existsByUsername(username)) {
            trainerRepository.deleteTrainerByUser_Username(username);
        }
        throw new TrainerNotFoundException("Trainer not found");
    }

    @Override
    public TrainerResponseDTO getTrainerByUsername(Authentication connectedUser) {
        UserDetails user = (UserDetails) connectedUser.getPrincipal();
        return trainerRepository.findTraineeByUser_Username(user.getUsername())
                .map(trainerMapper::toTrainerResponseDTO)
                .orElseThrow(() -> new TrainerNotFoundException("Trainer not found"));
    }

    @Override
    public List<TrainingResponseDTO> getTrainerTrainings(Authentication connectedUser,
                                                         String periodFrom, String periodTo, String traineeName) {
        UserDetails user = (UserDetails) connectedUser.getPrincipal();
        List<Training> training = trainerRepository.getTrainerTrainings(
                user.getUsername(), periodFrom, periodTo, traineeName).orElseThrow(
                () -> new TrainerNotFoundException("Trainer not found")
        );
        return training.stream().map(trainingMapper::toTrainingResponseDTO).toList();
    }

    @Transactional
    @Override
    public void updateTrainerStatus(Authentication connectedUser, Boolean isActive) {
        UserDetails user = (UserDetails) connectedUser.getPrincipal();
        Optional<Trainer> trainer = trainerRepository.findTraineeByUser_Username(user.getUsername());
        trainer.ifPresentOrElse(t -> t.getUser().setIsActive(isActive), () -> {
            throw new TrainerNotFoundException("Trainer not found");
        });
    }
}
