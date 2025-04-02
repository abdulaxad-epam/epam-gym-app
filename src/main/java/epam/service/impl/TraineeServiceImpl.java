package epam.service.impl;

import epam.exception.exception.DateConversionException;
import epam.exception.exception.TraineeNotFoundException;
import epam.dto.response_dto.RegisterTraineeResponseDTO;
import epam.dto.request_dto.TraineeRequestDTO;
import epam.dto.response_dto.TraineeResponseDTO;
import epam.entity.Trainee;
import epam.mapper.TraineeMapper;
import epam.repository.TraineeRepository;
import epam.service.TraineeService;
import epam.dto.response_dto.TrainingResponseDTO;
import epam.entity.Training;
import epam.mapper.TrainingMapper;
import epam.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TraineeServiceImpl implements TraineeService {

    private final TraineeRepository traineeRepository;

    private final TraineeMapper traineeMapper;

    private final TrainingService trainingService;

    private final TrainingMapper trainingMapper;

    @Override
    public RegisterTraineeResponseDTO createTrainee(TraineeRequestDTO traineeRequestDTO) {

        return traineeMapper.toRegisterTraineeResponseDTO(
                traineeRepository.insert(
                        traineeMapper.toTrainee(traineeRequestDTO)
                )
        );
    }

    @Override
    public TraineeResponseDTO updateTrainee(String username, String firstname, String lastname, String dateOfBirth, String address, Boolean isActive) {
        Optional<Trainee> trainee = traineeRepository.findByUsername(username);

        trainee.ifPresentOrElse(t -> {
            try {

                t.getUser().setFirstname(firstname);
                t.getUser().setLastname(lastname);
                if(dateOfBirth != null && !dateOfBirth.isEmpty()){
                    t.setDateOfBirth(LocalDate.parse(dateOfBirth));
                }
                if(address != null && !address.isEmpty()){
                t.setAddress(address);
                }
                if (isActive != null) {
                t.getUser().setIsActive(isActive);
                }

            } catch (Exception exception) {
                throw new DateConversionException("Cannot convert dateOfBirth to date");
            }

        }, () -> {
            throw new TraineeNotFoundException(String.format("Trainee not found with username: %s", username));
        });
        return traineeMapper.toTraineeResponseDTO(trainee.get());
    }

    @Override
    public void deleteTrainee(String username) {
        if (!traineeRepository.existsByUsername(username)) {
            throw new TraineeNotFoundException(String.format("Trainee not found with username: %s", username));
        }
        trainingService.deleteTraining(username);

        traineeRepository.deleteTraineeByUsername(username);
    }

    @Override
    public TraineeResponseDTO getTraineeByUsername(String username) {
        return traineeMapper.toTraineeResponseDTO(traineeRepository.findByUsername(username.toLowerCase())
                .orElseThrow(() -> new TraineeNotFoundException("Trainee not found")));
    }

    @Override
    public void updateTraineeStatus(String username, Boolean isActive) {
        Optional<Trainee> trainee = traineeRepository.findByUsername(username);
        trainee.ifPresentOrElse(t -> t.getUser().setIsActive(isActive), () -> {
            throw new TraineeNotFoundException(String.format("Trainee not found with username: %s", username));
        });
    }

    @Override
    public List<TrainingResponseDTO> getTraineeTrainings(String username, String periodFrom, String periodTo, String trainerName, String trainingType) {
        List<Training> trainings = traineeRepository.getTraineeTrainings(username, periodFrom, periodTo, trainerName, trainingType)
                .orElseThrow(() -> new TraineeNotFoundException(String.format("Trainee not found with username: %s", username)));
        return trainings.stream().map(trainingMapper::toTrainingResponseDTO).toList();
    }
}
