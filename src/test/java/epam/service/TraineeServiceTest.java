package epam.service;


import epam.dao.TraineeDAO;
import epam.domain.Trainee;
import epam.exception.TraineeNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TraineeServiceTest {

    @Mock
    private TraineeDAO traineeDAO;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    private Trainee givenTrainee1;
    private Trainee givenTrainee2;

    @BeforeEach
    public void setUp() {
        givenTrainee1 = Trainee.builder()
                .firstname("John")
                .lastname("Doe")
                .username("johndoe")
                .isActive(true)
                .dateOfBirth("1995-06-15")
                .address("123 Main St, Springfield")
                .build();

        givenTrainee2 = Trainee.builder()
                .firstname("Jane")
                .lastname("Smith")
                .isActive(false)
                .dateOfBirth("1998-09-20")
                .address("456 Elm St, Metropolis")
                .build();
    }


    @Test
    public void testForTraineeCreate_TraineeIsCreated(){
        // Mocking
        when(traineeDAO.insert(givenTrainee1.getUserId(), givenTrainee1)).thenReturn(givenTrainee1);
        when(traineeDAO.insert(givenTrainee2.getUserId(), givenTrainee2)).thenReturn(givenTrainee2);

        // When
        Trainee actual1 = traineeService.createTrainee(givenTrainee1.getUserId(), givenTrainee1);
        Trainee actual2 = traineeService.createTrainee(givenTrainee2.getUserId(), givenTrainee2);

        // Then
        verify(traineeDAO, times(1)).insert(givenTrainee1.getUserId(), givenTrainee1);
        verify(traineeDAO, times(1)).insert(givenTrainee2.getUserId(), givenTrainee2);

        // Assert
        assertNotNull(actual1);
        assertNotNull(actual2);
        assertEquals(givenTrainee1, actual1);
        assertEquals(givenTrainee2, actual2);
    }


    @Test
    void testForTraineeUpdate_TraineeIsUpdated() {
        // Mocking
        when(traineeDAO.update(givenTrainee1.getUserId(), givenTrainee1)).thenReturn(givenTrainee1);

        //when
        Trainee result = traineeService.updateTrainee(givenTrainee1.getUserId(), givenTrainee1);


        //Assert
        assertNotNull(result);

        // Verify
        verify(traineeDAO).update(givenTrainee1.getUserId(), givenTrainee1);
    }


    @Test
    public void testForTraineeDelete_TraineeIsDeleted(){
        // Mocking
        doNothing().when(traineeDAO).delete(givenTrainee1.getUserId());

        // When
        traineeService.deleteTrainee(givenTrainee1.getUserId());

        // Verify
        verify(traineeDAO).delete(givenTrainee1.getUserId());

    }

    @Test
    public void testForTraineeFindById_TraineeIsFound(){
        // Mocking
        when(traineeDAO.findById(givenTrainee1.getUserId())).thenReturn(Optional.of(givenTrainee1));

        // When
        Trainee result = traineeService.getTraineeById(givenTrainee1.getUserId());

        //Assert
        assertNotNull(result);
        assertEquals(givenTrainee1, result);

        //Verify
        verify(traineeDAO).findById(givenTrainee1.getUserId());
    }

    @Test
    public void testForTraineeFindById_TraineeNotFound(){
        //Mocking
        when(traineeDAO.findById(givenTrainee1.getUserId())).thenReturn(Optional.empty());

        //Assert
        assertThrows(TraineeNotFoundException.class, () -> traineeService.getTraineeById(givenTrainee1.getUserId()));

        //Verify
        verify(traineeDAO).findById(givenTrainee1.getUserId());
    }
}
