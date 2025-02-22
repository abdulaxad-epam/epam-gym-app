package epam.service;

import epam.dao.TrainerDAO;
import epam.domain.Trainer;
import epam.exception.TrainerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static junit.framework.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TrainerServiceTest {

    @Mock
    private TrainerDAO trainerDAO;

    @InjectMocks
    private TrainerServiceImpl trainerService;

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
    public void testTrainerService_UsernameWithSerialNumber(){
        //given
        Trainer expectedTrainer1 = Trainer.builder().firstname("John").lastname("Doe")
                .username("John.Doe").build();
        Trainer expectedTrainer2 = Trainer.builder().firstname("John").lastname("Doe")

                .username("John.Doe").build();
        Trainer expectedTrainer3 = Trainer.builder().firstname("John").lastname("Doe")
                .username("John.Doe").build();
        Trainer expectedTrainer4 = Trainer.builder().firstname("John").lastname("Doe")
                .username("John.Doe").build();


        //mocking
        AtomicInteger counter = new AtomicInteger(0);
        when(trainerDAO.insert(any(UUID.class), any(Trainer.class))).thenAnswer(invocation -> {
            Trainer trainer = invocation.getArgument(1);

            String baseUsername = trainer.getUsername();
            String newUsername = baseUsername;
            int count = counter.getAndIncrement();

            if (count > 0) {
                newUsername = baseUsername + count;
            }

            trainer.setUsername(newUsername);
            return trainer;
        });


        //when
        Trainer actualTrainer1 = trainerService.createTrainer(expectedTrainer1.getUserId(), expectedTrainer1);
        Trainer actualTrainer2 = trainerService.createTrainer(expectedTrainer2.getUserId(), expectedTrainer2);
        Trainer actualTrainer3 = trainerService.createTrainer(expectedTrainer3.getUserId(), expectedTrainer3);
        Trainer actualTrainer4 = trainerService.createTrainer(expectedTrainer4.getUserId(), expectedTrainer4);


        //then
        verify(trainerDAO, times(1)).insert(expectedTrainer1.getUserId(), expectedTrainer1);
        verify(trainerDAO, times(1)).insert(expectedTrainer2.getUserId(), expectedTrainer2);
        verify(trainerDAO, times(1)).insert(expectedTrainer3.getUserId(), expectedTrainer3);
        verify(trainerDAO, times(1)).insert(expectedTrainer4.getUserId(), expectedTrainer4);


        assertNotNull(actualTrainer1);
        assertNotNull(actualTrainer2);
        assertNotNull(actualTrainer3);
        assertNotNull(actualTrainer4);

        assertEquals("John.Doe", actualTrainer1.getUsername());
        assertEquals("John.Doe1", actualTrainer2.getUsername());
        assertEquals("John.Doe2", actualTrainer3.getUsername());
        assertEquals("John.Doe3", actualTrainer4.getUsername());
    }

}
