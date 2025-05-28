package epam.service.impl;

import epam.client.TrainingServiceClient;
import epam.dto.request_dto.TraineeRequestDTO;
import epam.dto.request_dto.UpdateTraineeRequestDTO;
import epam.dto.response_dto.RegisterTraineeResponseDTO;
import epam.dto.response_dto.TraineeResponseDTO;
import epam.client.dto.TrainingResponseDTO;
import epam.entity.Trainee;
import epam.exception.exception.TraineeNotFoundException;
import epam.mapper.TraineeMapper;
import epam.repository.TraineeRepository;
import epam.service.TraineeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TraineeServiceImpl implements TraineeService {

    private final TraineeRepository traineeRepository;

    private final TraineeMapper traineeMapper;

    private final TrainingServiceClient trainingServiceClient;

    @Override
    public RegisterTraineeResponseDTO createTrainee(TraineeRequestDTO traineeRequestDTO) {

        return traineeMapper.toRegisterTraineeResponseDTO(
                traineeRepository.save(
                        traineeMapper.toTrainee(traineeRequestDTO)
                )
        );
    }

    @Override
    @Transactional
    public TraineeResponseDTO updateTrainee(Authentication connectedUser, UpdateTraineeRequestDTO requestDTO) {

        UserDetails user = (UserDetails) connectedUser.getPrincipal();
        String username = user.getUsername();

        Optional<Trainee> trainee = traineeRepository.findTraineeByUser_Username(username);

        trainee.ifPresentOrElse(t -> {


            t.getUser().setFirstname(requestDTO.getFirstname());
            t.getUser().setLastname(requestDTO.getLastname());
            if (requestDTO.getDateOfBirth() != null) {
                t.setDateOfBirth(requestDTO.getDateOfBirth());
            }
            if (requestDTO.getAddress() != null) {
                t.setAddress(requestDTO.getAddress());
            }
            if (requestDTO.getIsActive() != null) {
                t.getUser().setIsActive(requestDTO.getIsActive());
            }
        }, () -> {
            throw new TraineeNotFoundException(String.format("Trainee not found with username: %s", username));
        });
        return traineeMapper.toTraineeResponseDTO(trainee.get());
    }

    @Override
    @Transactional
    public void deleteTrainee(Authentication connectedUser) {

        UserDetails user = (UserDetails) connectedUser.getPrincipal();
        String username = user.getUsername();

        if (!traineeRepository.existsTraineeByUser_Username(username)) {
            throw new TraineeNotFoundException(String.format("Trainee not found with username: %s", username));
        }

        traineeRepository.deleteTraineeByUser_Username(username);
    }

    @Override
    @Transactional(readOnly = true)
    public TraineeResponseDTO getTraineeProfile(Authentication connectedUser) {
        UserDetails user = (UserDetails) connectedUser.getPrincipal();
        return traineeMapper.toTraineeResponseDTO(traineeRepository.findTraineeByUser_Username(user.getUsername().toLowerCase())
                .orElseThrow(() -> new TraineeNotFoundException("Trainee not found")));
    }

    @Transactional
    @Override
    public void updateTraineeStatus(Authentication connectedUser, Boolean isActive) {
        UserDetails user = (UserDetails) connectedUser.getPrincipal();
        String username = user.getUsername();
        Optional<Trainee> trainee = traineeRepository.findTraineeByUser_Username(username);
        trainee.ifPresentOrElse(t -> t.getUser().setIsActive(isActive), () -> {
            throw new TraineeNotFoundException(String.format("Trainee not found with username: %s", username));
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainingResponseDTO> getTraineeTrainings(String periodFrom, String periodTo,
                                                         String trainerName, String trainingType, Authentication connectedUser) {
        UserDetails user = (UserDetails) connectedUser.getPrincipal();
        String traineeName = user.getUsername();

        return trainingServiceClient.getTraineeTrainings(traineeName, periodFrom, periodTo, trainerName, trainingType);
    }

    @Override
    @Transactional(readOnly = true)
    public Trainee getTraineeProfile(String traineeUsername) {
        return traineeRepository.findTraineeByUser_username((traineeUsername))
                .orElseThrow(() -> new TraineeNotFoundException("Trainee not found with username: " + traineeUsername));
    }


    @Override
    @Transactional(readOnly = true)
    public Trainee getTraineeProfile(UUID traineeId) {
        return traineeRepository.findTraineeByTraineeId((traineeId))
                .orElseThrow(() -> new TraineeNotFoundException("Trainee not found with traineeId: " + traineeId));
    }

}
