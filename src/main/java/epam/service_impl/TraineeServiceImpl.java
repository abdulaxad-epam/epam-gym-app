package epam.service_impl;

import epam.entity.Trainee;
import epam.exception.TraineeNotFoundException;
import epam.mapper.TraineeMapper;
import epam.repositories.TraineeRepository;
import epam.response_dto.TraineeResponseDTO;
import epam.service.TraineeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class TraineeServiceImpl implements TraineeService {

    private final TraineeRepository traineeRepository;

    private final TraineeMapper traineeMapper;

    @Override
    public TraineeResponseDTO createTrainee(UUID id, Trainee trainee) {
        Trainee inserted = traineeRepository.insert(id, trainee);
        return traineeMapper.toTraineeResponseDTO(inserted);
    }

    @Override
    public TraineeResponseDTO updateTrainee(UUID id, Trainee trainee) {

        if (traineeRepository.existsById(id)) {

            Trainee update = traineeRepository.update(id, trainee);

            return traineeMapper.toTraineeResponseDTO(update);

        } else
            throw new TraineeNotFoundException("Trainee not found");
    }

    @Override
    public void deleteTrainee(UUID id) {
        if (traineeRepository.existsById(id)) {
            traineeRepository.delete(id);
        } else
            throw new TraineeNotFoundException("Trainee not found");
    }

    @Override
    public TraineeResponseDTO getTraineeByUsername(String username) {
        Optional<Trainee> byId = traineeRepository.findByUsername(username);
        if (byId.isPresent())
            return traineeMapper.toTraineeResponseDTO(byId.get());
        else
            throw new TraineeNotFoundException("Trainee not found");
    }

    @Override
    public List<TraineeResponseDTO> getAllTrainees() {
        List<Trainee> trainees = traineeRepository.findAll();

        return trainees.stream().map(traineeMapper::toTraineeResponseDTO).toList();

    }
}
