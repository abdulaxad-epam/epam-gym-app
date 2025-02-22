package epam.app;


import epam.domain.Trainee;
import epam.domain.Trainer;
import epam.domain.Training;
import epam.enums.TrainingType;
import epam.exception.TraineeNotFoundException;
import epam.exception.TrainerNotFoundException;
import epam.exception.TrainingNotFoundException;
import epam.facade.TrainingFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@Component
public class ConsoleApplication {
    private final Scanner scanner = new Scanner(System.in);
    private TrainingFacade trainingFacade;

    @Autowired
    public void setTrainingFacade(TrainingFacade trainingFacade) {
        this.trainingFacade = trainingFacade;
    }

//    @PostConstruct
    public void start() {
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getChoice();

            switch (choice) {
                case 1:
                    handleTraineeMenu();
                    break;
                case 2:
                    handleTrainerMenu();
                    break;
                case 3:
                    handleTrainingMenu();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private void displayMainMenu() {
        System.out.println("\n=== Training Management System ===");
        System.out.println("1. Trainee Management");
        System.out.println("2. Trainer Management");
        System.out.println("3. Training Management");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private void handleTraineeMenu() {
        boolean subMenu = true;
        while (subMenu) {
            displayTraineeMenu();
            int choice = getChoice();

            switch (choice) {
                case 1:
                    createTrainee();
                    break;
                case 2:
                    updateTrainee();
                    break;
                case 3:
                    deleteTrainee();
                    break;
                case 4:
                    viewTrainee();
                    break;
                case 5:
                    viewAllTrainees();
                    break;
                case 0:
                    subMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handleTrainerMenu() {
        boolean subMenu = true;
        while (subMenu) {
            displayTrainerMenu();
            int choice = getChoice();

            switch (choice) {
                case 1:
                    createTrainer();
                    break;
                case 2:
                    updateTrainer();
                    break;
                case 3:
                    deleteTrainer();
                    break;
                case 4:
                    viewTrainer();
                    break;
                case 5:
                    viewAllTrainers();
                    break;
                case 0:
                    subMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handleTrainingMenu() {
        boolean subMenu = true;
        while (subMenu) {
            displayTrainingMenu();
            int choice = getChoice();

            switch (choice) {
                case 1:
                    createTraining();
                    break;
                case 2:
                    viewTraining();
                    break;
                case 3:
                    viewAllTrainings();
                    break;
                case 0:
                    subMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayTraineeMenu() {
        System.out.println("\n=== Trainee Management ===");
        System.out.println("1. Create Trainee");
        System.out.println("2. Update Trainee");
        System.out.println("3. Delete Trainee");
        System.out.println("4. View Trainee");
        System.out.println("5. View All Trainees");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter your choice: ");
    }

    private void displayTrainerMenu() {
        System.out.println("\n=== Trainer Management ===");
        System.out.println("1. Create Trainer");
        System.out.println("2. Update Trainer");
        System.out.println("3. Delete Trainer");
        System.out.println("4. View Trainer");
        System.out.println("5. View All Trainers");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter your choice: ");
    }

    private void displayTrainingMenu() {
        System.out.println("\n=== Training Management ===");
        System.out.println("1. Create Training");
        System.out.println("2. View Training");
        System.out.println("3. View All Trainings");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter your choice: ");
    }

    private int getChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void createTrainee() {
        System.out.println("\n=== Create Trainee ===");

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter date of birth (YYYY-MM-DD): ");
        String dateOfBirth = scanner.nextLine();

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        Trainee trainee = Trainee.builder()
                .firstname(firstName)
                .lastname(lastName)
                .dateOfBirth(dateOfBirth)
                .address(address)
                .isActive(true)
                .build();

        UUID id = UUID.randomUUID();
        try {
            Trainee createdTrainee = trainingFacade.createTrainee(id, trainee);
            System.out.println("Trainee created successfully!");
            System.out.println("ID: " + createdTrainee.getUserId());
            System.out.println("Username: " + createdTrainee.getUsername());
            System.out.println("Password: " + createdTrainee.getPassword());
        } catch (Exception e) {
            System.out.println("Error creating trainee: " + e.getMessage());
        }
    }


    private void updateTrainee() {
        System.out.println("\n=== Update Trainee ===");
        System.out.print("Enter trainee ID: ");
        String idStr = scanner.nextLine();

        try {
            UUID id = UUID.fromString(idStr);
            Trainee existingTrainee = trainingFacade.getTraineeById(id);

            System.out.println("Current details:");
            System.out.println(existingTrainee);
            System.out.println("\nEnter new details (press Enter to keep current value):");

            System.out.print("First name [" + existingTrainee.getFirstname() + "]: ");
            String firstName = scanner.nextLine();
            if (!firstName.isEmpty()) {
                existingTrainee.setFirstname(firstName);
            }

            System.out.print("Last name [" + existingTrainee.getLastname() + "]: ");
            String lastName = scanner.nextLine();
            if (!lastName.isEmpty()) {
                existingTrainee.setLastname(lastName);
            }

            System.out.print("Date of birth [" + existingTrainee.getDateOfBirth() + "]: ");
            String dateOfBirth = scanner.nextLine();
            if (!dateOfBirth.isEmpty()) {
                existingTrainee.setDateOfBirth(dateOfBirth);
            }

            System.out.print("Address [" + existingTrainee.getAddress() + "]: ");
            String address = scanner.nextLine();
            if (!address.isEmpty()) {
                existingTrainee.setAddress(address);
            }

            System.out.print("Is active [" + existingTrainee.getIsActive() + "] (true/false): ");
            String isActiveStr = scanner.nextLine();
            if (!isActiveStr.isEmpty()) {
                existingTrainee.setIsActive(Boolean.parseBoolean(isActiveStr));
            }

            trainingFacade.updateTrainee(id, existingTrainee);
            System.out.println("Trainee updated successfully");
        } catch (Exception e) {
            System.out.println("Error updating trainee: " + e.getMessage());
        }
    }

    private void deleteTrainee() {
        System.out.println("\n=== Delete Trainee ===");
        System.out.print("Enter trainee ID: ");
        String idStr = scanner.nextLine();

        try {
            UUID id = UUID.fromString(idStr);
            trainingFacade.deleteTrainee(id);
            System.out.println("Trainee deleted successfully");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid UUID format");
        } catch (TraineeNotFoundException e) {
            System.out.println("Trainee not found");
        } catch (Exception e) {
            System.out.println("Error deleting trainee: " + e.getMessage());
        }
    }

    private void viewTrainee() {
        System.out.println("\n=== View Trainee ===");
        System.out.print("Enter trainee ID: ");
        String idStr = scanner.nextLine();

        try {
            UUID id = UUID.fromString(idStr);
            Trainee trainee = trainingFacade.getTraineeById(id);
            System.out.println(trainee);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid UUID format");
        } catch (TraineeNotFoundException e) {
            System.out.println("Trainee not found");
        } catch (Exception e) {
            System.out.println("Error viewing trainee: " + e.getMessage());
        }
    }

    private void viewAllTrainees() {
        System.out.println("\n=== All Trainees ===");
        try {
            List<Trainee> trainees = trainingFacade.getAllTrainees();
            trainees.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Error viewing trainees: " + e.getMessage());
        }
    }

    private void createTrainer() {
        System.out.println("\n=== Create Trainer ===");

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter specialization: ");
        String specialization = scanner.nextLine();

        Trainer trainer = Trainer.builder()
                .firstname(firstName)
                .lastname(lastName)
                .specialization(specialization)
                .isActive(true)
                .build();

        UUID id = UUID.randomUUID();
        try {
            Trainer createdTrainer = trainingFacade.createTrainer(id, trainer);
            System.out.println("Trainer created successfully!");
            System.out.println("ID: " + createdTrainer.getUserId());
            System.out.println("Username: " + createdTrainer.getUsername());
            System.out.println("Password: " + createdTrainer.getPassword());
        } catch (Exception e) {
            System.out.println("Error creating trainer: " + e.getMessage());
        }
    }

    private void updateTrainer() {
        System.out.println("\n=== Update Trainer ===");
        System.out.print("Enter trainer ID: ");
        String idStr = scanner.nextLine();

        try {
            UUID id = UUID.fromString(idStr);
            Trainer existingTrainer = trainingFacade.getTrainerById(id);

            System.out.println("Current details:");
            System.out.println(existingTrainer);
            System.out.println("\nEnter new details (press Enter to keep current value):");

            System.out.print("First name [" + existingTrainer.getFirstname() + "]: ");
            String firstName = scanner.nextLine();
            if (!firstName.isEmpty()) {
                existingTrainer.setFirstname(firstName);
            }

            System.out.print("Last name [" + existingTrainer.getLastname() + "]: ");
            String lastName = scanner.nextLine();
            if (!lastName.isEmpty()) {
                existingTrainer.setLastname(lastName);
            }

            System.out.print("Specialization [" + existingTrainer.getSpecialization() + "]: ");
            String specialization = scanner.nextLine();
            if (!specialization.isEmpty()) {
                existingTrainer.setSpecialization(specialization);
            }

            System.out.print("Is active [" + existingTrainer.getIsActive() + "] (true/false): ");
            String isActiveStr = scanner.nextLine();
            if (!isActiveStr.isEmpty()) {
                existingTrainer.setIsActive(Boolean.parseBoolean(isActiveStr));
            }

            trainingFacade.updateTrainer(id, existingTrainer);
            System.out.println("Trainer updated successfully");
        } catch (Exception e) {
            System.out.println("Error updating trainer: " + e.getMessage());
        }
    }

    private void deleteTrainer() {
        System.out.println("\n=== Delete Trainer ===");
        System.out.print("Enter trainer ID: ");
        String idStr = scanner.nextLine();

        try {
            UUID id = UUID.fromString(idStr);
            trainingFacade.deleteTrainer(id);
            System.out.println("Trainer deleted successfully");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid UUID format");
        } catch (TrainerNotFoundException e) {
            System.out.println("Trainer not found");
        } catch (Exception e) {
            System.out.println("Error deleting trainer: " + e.getMessage());
        }
    }

    private void viewTrainer() {
        System.out.println("\n=== View Trainer ===");
        System.out.print("Enter trainer ID: ");
        String idStr = scanner.nextLine();

        try {
            UUID id = UUID.fromString(idStr);
            Trainer trainer = trainingFacade.getTrainerById(id);
            System.out.println(trainer);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid UUID format");
        } catch (TrainerNotFoundException e) {
            System.out.println("Trainer not found");
        } catch (Exception e) {
            System.out.println("Error viewing trainer: " + e.getMessage());
        }
    }

    private void viewAllTrainers() {
        System.out.println("\n=== All Trainers ===");
        try {
            List<Trainer> trainers = trainingFacade.getAllTrainers();
            trainers.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Error viewing trainers: " + e.getMessage());
        }
    }

    private void createTraining() {
        System.out.println("\n=== Create Training ===");

        System.out.print("Enter training name: ");
        String name = scanner.nextLine();

        System.out.print("Enter trainee ID: ");
        String traineeId = scanner.nextLine();

        System.out.print("Enter trainer ID: ");
        String trainerId = scanner.nextLine();

        System.out.print("Enter training date (YYYY-MM-DD): ");
        String trainingDate = scanner.nextLine();

        System.out.println("Enter training type (Available types: " +
                String.join(", ", Arrays.toString(TrainingType.values())) + "): ");
        String trainingType = scanner.nextLine();

        System.out.print("Enter training duration (e.g., '2 hours'): ");
        String duration = scanner.nextLine();

        Training training = new Training.TrainingBuilder()
                .trainingId(UUID.randomUUID())
                .trainingName(name)
                .traineeId(traineeId)
                .trainerId(trainerId)
                .trainingDate(trainingDate)
                .trainingType(TrainingType.valueOf(trainingType.toUpperCase()))
                .trainingDuration(duration)
                .build();

        UUID id = UUID.randomUUID();
        try {
            trainingFacade.createTraining(id, training);
            System.out.println("Training created successfully with ID: " + id);
        } catch (Exception e) {
            System.out.println("Error creating training: " + e.getMessage());
        }
    }

    private void viewTraining() {
        System.out.println("\n=== View Training ===");
        System.out.print("Enter training ID: ");
        String idStr = scanner.nextLine();

        try {
            UUID id = UUID.fromString(idStr);
            Training training = trainingFacade.getTrainingById(id);
            System.out.println(training);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid UUID format");
        } catch (TrainingNotFoundException e) {
            System.out.println("Training not found");
        } catch (Exception e) {
            System.out.println("Error viewing training: " + e.getMessage());
        }
    }

    private void viewAllTrainings() {
        System.out.println("\n=== All Trainings ===");
        try {
            List<Training> trainings = trainingFacade.getAllTrainings();
            trainings.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Error viewing trainings: " + e.getMessage());
        }
    }
}
