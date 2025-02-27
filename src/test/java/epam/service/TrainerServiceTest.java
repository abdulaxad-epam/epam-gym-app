package epam.service;

import epam.dao.TrainerDAO;
import epam.domain.Trainer;
import epam.exception.TrainerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TrainerServiceTest {

    @Mock
    private TrainerDAO trainerDAO;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    @Captor
    private ArgumentCaptor<Trainer> trainerCaptor;

    private Trainer givenTrainer;
    private Trainer givenTrainer2;

    @BeforeEach
    public void setUp() {
        givenTrainer = Trainer.builder()
                .firstname("John")
                .lastname("Doe")
                .username("johndoe")
                .isActive(true)
                .specialization("Specialization")
                .build();

        givenTrainer2 = Trainer.builder()
                .firstname("Jane")
                .lastname("Smith")
                .isActive(false)
                .specialization("Specialization")
                .build();
    }

    @Test
    public void testTrainerService_TrainerIsAdded() {
        //mocking
        when(trainerDAO.insert(givenTrainer.getUserId(), givenTrainer)).thenReturn(givenTrainer);

        //when
        trainerService.createTrainer(givenTrainer.getUserId(), givenTrainer);

        //then
        verify(trainerDAO, times(1)).insert(givenTrainer.getUserId(), givenTrainer);

    }

    @Test
    public void testTrainerService_TrainerIsUpdated() {
        //mocking
        when(trainerDAO.update(givenTrainer.getUserId(), givenTrainer2)).thenReturn(givenTrainer2);

        //when
        trainerService.updateTrainer(givenTrainer.getUserId(), givenTrainer2);

        //then
        verify(trainerDAO, times(1)).update(givenTrainer.getUserId(), givenTrainer2);

    }

    @Test
    public void testTrainerService_TrainerIsDeleted() {
        //when
        trainerService.deleteTrainer(givenTrainer.getUserId());

        //then
        verify(trainerDAO, times(1)).delete(givenTrainer.getUserId());
    }


    @Test
    public void testTrainerService_TrainerIsReturned() {
        //given
        Trainer expectedTrainer = Trainer.builder()
                .firstname("John")
                .lastname("Doe")
                .specialization("Fitness Coach")
                .isActive(true)
                .username("johndoe")
                .build();

        UUID userId = expectedTrainer.getUserId();

        //mocking
        when(trainerDAO.findById(userId)).thenReturn(Optional.of(expectedTrainer));

        //when
        Trainer actualTrainer = trainerService.getTrainerById(userId);

        //then
        verify(trainerDAO, times(1)).findById(userId);
        assertNotNull(actualTrainer);
        assertEquals(expectedTrainer, actualTrainer);
    }



    @Test
    public void testTrainerService_TrainerNotFound() {
        //given
        UUID trainerId = UUID.randomUUID();

        //mocking
        when(trainerDAO.findById(trainerId))
                .thenThrow(new TrainerNotFoundException("Trainer not found with ID: " + trainerId));

        //when
        TrainerNotFoundException exception = assertThrows(
                TrainerNotFoundException.class, () -> trainerService.getTrainerById(trainerId));

        //verify
        assertTrue(exception.getMessage().contains("Trainer not found with ID: " + trainerId));
        verify(trainerDAO, times(1)).findById(trainerId);
    }

    @Test
    public void testTrainerService_UsernameWithDot() {
        //given
        UUID trainerId1 = UUID.randomUUID();
        UUID trainerId2 = UUID.randomUUID();

        Trainer expectedTrainer1 = Trainer.builder().firstname("Mark").lastname("Jose")
                .username("Mark.Jose").build();

        Trainer expectedTrainer2 = Trainer.builder().firstname("John").lastname("Doe")
                .username("John.Doe").build();


        //mocking
        when(trainerDAO.findById(trainerId1)).thenReturn(Optional.of(expectedTrainer1));
        when(trainerDAO.findById(trainerId2)).thenReturn(Optional.of(expectedTrainer2));


        //when
        Trainer actualTrainer1 = trainerService.getTrainerById(trainerId1);
        Trainer actualTrainer2 = trainerService.getTrainerById(trainerId2);


        //then
        verify(trainerDAO, times(1)).findById(trainerId1);
        verify(trainerDAO, times(1)).findById(trainerId2);

        assertNotNull(actualTrainer1);
        assertNotNull(actualTrainer2);

        assertEquals(expectedTrainer1.getUsername(), actualTrainer1.getUsername());
        assertEquals(expectedTrainer2.getUsername(), actualTrainer2.getUsername());
    }

    @Test
    public void testTrainerService_UsernameWithSerialNumber() {

        UUID trainerId1 = UUID.randomUUID();
        UUID trainerId2 = UUID.randomUUID();
        UUID trainerId3 = UUID.randomUUID();
        UUID trainerId4 = UUID.randomUUID();
        // Given
        Trainer expectedTrainer1 = Trainer.builder().userId(trainerId1).firstname("John").lastname("Doe")
                .username("John.Doe").build();
        Trainer expectedTrainer2 = Trainer.builder().userId(trainerId2).firstname("John").lastname("Doe")
                .username("John.Doe1").build();
        Trainer expectedTrainer3 = Trainer.builder().userId(trainerId3).firstname("John").lastname("Doe")
                .username("John.Doe2").build();
        Trainer expectedTrainer4 = Trainer.builder().userId(trainerId4).firstname("John").lastname("Doe")
                .username("John.Doe3").build();

        // Mocking
        when(trainerDAO.insert(trainerId1, expectedTrainer1)).thenReturn(expectedTrainer1);
        when(trainerDAO.insert(trainerId2, expectedTrainer2)).thenReturn(expectedTrainer2);
        when(trainerDAO.insert(trainerId3, expectedTrainer3)).thenReturn(expectedTrainer3);
        when(trainerDAO.insert(trainerId4, expectedTrainer4)).thenReturn(expectedTrainer4);


        // When
        Trainer actualTrainer1 = trainerService.createTrainer(expectedTrainer1.getUserId(), expectedTrainer1);
        Trainer actualTrainer2 = trainerService.createTrainer(expectedTrainer2.getUserId(), expectedTrainer2);
        Trainer actualTrainer3 = trainerService.createTrainer(expectedTrainer3.getUserId(), expectedTrainer3);
        Trainer actualTrainer4 = trainerService.createTrainer(expectedTrainer4.getUserId(), expectedTrainer4);

        // Then
        verify(trainerDAO, times(4)).insert(any(UUID.class), trainerCaptor.capture());
        List<Trainer> capturedTrainers = trainerCaptor.getAllValues();

        // Assert
        assertEquals(4, capturedTrainers.size());
        assertEquals("John.Doe", capturedTrainers.get(0).getUsername());
        assertEquals("John.Doe1", capturedTrainers.get(1).getUsername());
        assertEquals("John.Doe2", capturedTrainers.get(2).getUsername());
        assertEquals("John.Doe3", capturedTrainers.get(3).getUsername());

        assertNotNull(actualTrainer1);
        assertNotNull(actualTrainer2);
        assertNotNull(actualTrainer3);
        assertNotNull(actualTrainer4);
    }

    @Test
    void testGetAllTrainers_ReturnsEmptyList_WhenNoTrainersExist() {
        when(trainerDAO.findAll()).thenReturn(Collections.emptyMap());

        List<Trainer> result = trainerService.getAllTrainers();

        assertTrue(result.isEmpty(), "Result should be an empty list");
        verify(trainerDAO, times(1)).findAll();
    }

    @Test
    void testGetAllTrainers_ReturnsListOfTrainees_WhenTrainersExist() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        Map<UUID, Trainer> trainerMap = new HashMap<>();
        trainerMap.put(id1, givenTrainer);
        trainerMap.put(id2, givenTrainer2);

        when(trainerDAO.findAll()).thenReturn(trainerMap);

        List<Trainer> result = trainerService.getAllTrainers();

        assertEquals(2, result.size());
        assertTrue(result.contains(givenTrainer), "Result should contain trainer1");
        assertTrue(result.contains(givenTrainer2), "Result should contain trainer2");
        verify(trainerDAO, times(1)).findAll();
    }
}