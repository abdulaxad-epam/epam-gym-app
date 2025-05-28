package epam.epamgymapptrainerworkloadservice.service.impl;

import epam.epamgymapptrainerworkloadservice.dto.TrainerWorkloadRequestDTO;
import epam.epamgymapptrainerworkloadservice.dto.TrainerWorkloadResponseDTO;
import epam.epamgymapptrainerworkloadservice.entity.TrainerWorkload;
import epam.epamgymapptrainerworkloadservice.enums.ActionType;
import epam.epamgymapptrainerworkloadservice.handler.exception.TrainerWorkloadNotFoundException;
import epam.epamgymapptrainerworkloadservice.mapper.TrainerWorkloadMapper;
import epam.epamgymapptrainerworkloadservice.repostiory.TrainerWorkloadRepository;
import epam.epamgymapptrainerworkloadservice.service.TrainerWorkloadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainerWorkloadServiceImpl implements TrainerWorkloadService {

    private final TrainerWorkloadRepository trainerWorkloadRepository;
    private final TrainerWorkloadMapper trainerWorkloadMapper;

    @Override
    public List<TrainerWorkload> getTrainerWorkload(String trainerUsername, Integer year) {
        if (year == null) {
            return trainerWorkloadRepository.findTrainerWorkloadsByTrainerUsername(trainerUsername);
        }
        return trainerWorkloadRepository.findTrainerWorkloadsByTrainerUsernameAndTrainingDateBetween(
                trainerUsername, LocalDate.of(year, 1, 1), LocalDate.of(year, 12, 31)
        );
    }

    @Override
    @Transactional
    public TrainerWorkloadResponseDTO actionOn(TrainerWorkloadRequestDTO trainerWorkloadRequestDTO) {
        return trainerWorkloadRequestDTO.getActionType().equals(ActionType.ADD.name()) ?
                actionOnADD(trainerWorkloadRequestDTO) : actionOnDELETE(trainerWorkloadRequestDTO);
    }

    @Transactional(rollbackFor = Exception.class)
    public TrainerWorkloadResponseDTO actionOnADD(TrainerWorkloadRequestDTO trainerWorkloadRequestDTO) {

        LocalDate trainingDate = trainerWorkloadRequestDTO.getTrainingDate();
        int year = trainingDate.getYear();
        int month = trainingDate.getMonthValue();

        Optional<TrainerWorkload> trainerWorkload = trainerWorkloadRepository.findTrainerWorkloadByTrainerUsernameAndTrainingDate(
                trainerWorkloadRequestDTO.getTrainerUsername(), LocalDate.of(year, month, 1));


        if (trainerWorkload.isPresent()) {
            TrainerWorkload workload = trainerWorkload.get();
            int updatedDuration = workload.getTrainingDuration() + trainerWorkloadRequestDTO.getTrainingDuration();
            workload.setTrainingDuration(updatedDuration);
            return trainerWorkloadMapper.toTrainerWorkloadResponseDTO(workload);
        }

        TrainerWorkload workload = trainerWorkloadMapper.toTrainerWorkload(trainerWorkloadRequestDTO);

        return trainerWorkloadMapper.toTrainerWorkloadResponseDTO(trainerWorkloadRepository.save(workload));
    }

    @Transactional(rollbackFor = Exception.class)
    public TrainerWorkloadResponseDTO actionOnDELETE(TrainerWorkloadRequestDTO trainerWorkloadRequestDTO) {

        LocalDate trainingDate = trainerWorkloadRequestDTO.getTrainingDate();
        int year =  trainingDate.getYear();
        int month = trainingDate.getMonthValue();

        Optional<TrainerWorkload> trainerWorkload = trainerWorkloadRepository.findTrainerWorkloadByTrainerUsernameAndTrainingDate(
                trainerWorkloadRequestDTO.getTrainerUsername(), LocalDate.of(year, month, 1)
        );

        return trainerWorkload.map(workload -> {
            int updatedDuration = workload.getTrainingDuration() - trainerWorkloadRequestDTO.getTrainingDuration();
            workload.setTrainingDuration(updatedDuration);
            return trainerWorkloadMapper.toTrainerWorkloadResponseDTO(workload);
        }).orElseThrow(() ->
                new TrainerWorkloadNotFoundException("Trainer workload on year [" + year + "] and month [" + month + "] not found")
        );
    }
}
