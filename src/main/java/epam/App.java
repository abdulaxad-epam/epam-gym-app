package epam;


import epam.dao.TraineeDAO;
import epam.dao.TrainerDAO;
import epam.dao.TrainingDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("epam")
public class App {

    private final static Log log = LogFactory.getLog(App.class.getName());

    public static void main( String[] args ) {

        ApplicationContext context = new AnnotationConfigApplicationContext(App.class);

        Object traineeDAO = context.getBean("traineeDAO");

        Object trainingDAO = context.getBean("trainingDAO");

        Object trainerDAO = context.getBean("trainerDAO");

        if (traineeDAO instanceof TraineeDAO) {
            log.info("Trainee DAO loaded");
            log.info(((TraineeDAO) traineeDAO).findAll().toString());
        }

        if(trainingDAO instanceof TrainingDAO) {
            log.info("Training DAO loaded");
            log.info(((TrainingDAO) trainingDAO).findAll().toString());
        }

        if(trainerDAO instanceof TrainerDAO) {
            log.info("Trainer DAO loaded");
            log.info(((TrainerDAO) trainerDAO).findAll().toString());
        }

    }
}
