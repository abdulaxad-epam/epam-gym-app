package epam.app;

import epam.facade.TrainingFacadeImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsoleApplication {

    private final TrainingFacadeImpl trainingFacade;
}
