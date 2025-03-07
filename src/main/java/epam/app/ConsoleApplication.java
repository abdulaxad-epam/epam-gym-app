package epam.app;

import epam.entity.Training;
import epam.entity.User;
import epam.facade.TrainingFacade;
import epam.request_dto.*;
import epam.response_dto.TraineeResponseDTO;
import epam.response_dto.TrainerResponseDTO;
import epam.response_dto.TrainingResponseDTO;
import epam.response_dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ConsoleApplication {

    private final TrainingFacade trainingFacade;
    private final Scanner scanner = new Scanner(System.in);

    // Session management
    private boolean isLoggedIn = false;
    private String currentUsername = null;
    private UserRole currentUserRole = null;

    private enum UserRole {
        TRAINEE,
        TRAINER
    }

    public void run() {
        displayWelcomeMessage();
        boolean exit = false;

        while (!exit) {
            if (isLoggedIn) {
                displayLoggedInMenu();
            } else {
                displayMainMenu();
            }

            int choice = getIntInput();

            if (isLoggedIn) {
                exit = handleLoggedInChoice(choice);
            } else {
                exit = handleLoggedOutChoice(choice);
            }
        }

        System.out.println("Exiting application. Goodbye!");
        scanner.close();
    }

    private void displayWelcomeMessage() {
        System.out.println("===========================================");
        System.out.println("      TRAINING MANAGEMENT SYSTEM");
        System.out.println("===========================================");
    }

    private void displayMainMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1. Login");
        System.out.println("2. Register as Trainee");
        System.out.println("3. Register as Trainer");
        System.out.println("0. Exit");
        System.out.print("Please select an option: ");
    }

    private void displayLoggedInMenu() {
        System.out.println("\nWelcome, " + currentUsername + " (" + currentUserRole + ")");
        System.out.println("1. Profile Management");

        if (currentUserRole == UserRole.TRAINEE) {
            System.out.println("2. Manage My Trainers");
            System.out.println("3. View My Trainings");
        } else {
            System.out.println("2. Manage My Trainees");
            System.out.println("3. Manage Trainings");
        }

        System.out.println("4. Logout");
        System.out.println("0. Exit");
        System.out.print("Please select an option: ");
    }

    private int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Invalid input
        }
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private boolean handleLoggedOutChoice(int choice) {
        switch (choice) {
            case 1:
                handleLogin();
                break;
            case 2:
                registerTrainee();
                break;
            case 3:
                registerTrainer();
                break;
            case 0:
                return true; // Exit
            default:
                System.out.println("Invalid option. Please try again.");
        }
        return false;
    }

    private boolean handleLoggedInChoice(int choice) {
        switch (choice) {
            case 1:
                handleProfileManagement();
                break;
            case 2:
                if (currentUserRole == UserRole.TRAINEE) {
                    handleManageTrainers();
                } else {
                    handleManageTrainees();
                }
                break;
            case 3:
                if (currentUserRole == UserRole.TRAINEE) {
                    handleViewTrainings();
                } else {
//                    handleManageTrainings();
                }
                break;
            case 4:
                handleLogout();
                break;
            case 0:
                return true; // Exit
            default:
                System.out.println("Invalid option. Please try again.");
        }
        return false;
    }

    private void handleLogin() {
        System.out.println("\n--- Login ---");
        String username = getStringInput("Enter username: ");
        String password = getStringInput("Enter password: ");

        AuthenticateRequestDTO authRequest = AuthenticateRequestDTO.builder()
                .username(username)
                .password(password)
                .build();

        Boolean authenticated = trainingFacade.authenticate(authRequest);

        if (Boolean.TRUE.equals(authenticated)) {
            System.out.println("Login successful!");
            isLoggedIn = true;
            currentUsername = username;

            // Determine user role (trainee or trainer)
            try {
                trainingFacade.getTraineeByUsername(username);
                currentUserRole = UserRole.TRAINEE;
            } catch (Exception e) {
                try {
                    trainingFacade.getTrainerByUsername(username);
                    currentUserRole = UserRole.TRAINER;
                } catch (Exception ex) {
                    System.out.println("Error determining user role. Please contact support.");
                    isLoggedIn = false;
                    currentUsername = null;
                }
            }
        } else {
            System.out.println("Login failed. Invalid username or password.");
        }
    }

    private void handleLogout() {
        isLoggedIn = false;
        currentUsername = null;
        currentUserRole = null;
        System.out.println("Logged out successfully.");
    }

    private void registerTrainee() {
        System.out.println("\n--- Trainee Registration ---");

        UserRequestDTO userDTO = createUserDTO();
        String address = getStringInput("Enter address: ");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime dob = null;

        boolean validDate = false;
        while (!validDate) {
            String dobString = getStringInput("Enter date of birth (yyyy-MM-dd): ");
            if (dobString.isEmpty()) {
                validDate = true;
            } else {
                try {
                    dob = LocalDate.parse(dobString, formatter).atStartOfDay();
                    validDate = true;
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format or values. Please use YYYY-MM-DD format with valid month/day values.");
                } catch (Exception e) {
                    System.out.println("Invalid date format.");
                }
            }
        }

        RegisterTraineeRequestDTO traineeRequest = RegisterTraineeRequestDTO.builder()
                .address(address)
                .dateOfBirth(dob)
                .user(userDTO)
                .build();

        Boolean registered = trainingFacade.register(traineeRequest);

        if (Boolean.TRUE.equals(registered)) {
            System.out.println("Trainee registration successful!");
            System.out.println("You can now login with username: " + userDTO.getFirstName().toLowerCase() + "." +
                    userDTO.getLastName().toLowerCase());
        } else {
            System.out.println("Registration failed.");
        }
    }

    private void registerTrainer() {
        System.out.println("\n--- Trainer Registration ---");

        UserRequestDTO userDTO = createUserDTO();
        String specialization = getStringInput("Enter specialization: ");

        RegisterTrainerRequestDTO trainerRequest = RegisterTrainerRequestDTO.builder()
                .specialization(specialization)
                .user(userDTO)
                .build();

        Boolean registered = trainingFacade.register(trainerRequest);

        if (Boolean.TRUE.equals(registered)) {
            System.out.println("Trainer registration successful!");
            System.out.println("You can now login with username: " + userDTO.getFirstName().toLowerCase() + "." +
                    userDTO.getLastName().toLowerCase());
        } else {
            System.out.println("Registration failed.");
        }
    }

    private UserRequestDTO createUserDTO() {
        String firstname = getStringInput("Enter first name: ");
        String lastname = getStringInput("Enter last name: ");
        String password = getStringInput("Enter password: ");

        return UserRequestDTO.builder()
                .firstName(firstname)
                .lastName(lastname)
                .isActive(true)
                .password(password)
                .build();
    }

    private void handleProfileManagement() {
        boolean back = false;

        while (!back) {
            System.out.println("\n--- Profile Management ---");
            System.out.println("1. View Profile");
            System.out.println("2. Update Profile");
            System.out.println("3. Change Password");
            System.out.println("4. Toggle Active Status (Currently: " +
                    (getUserActiveStatus() ? "Active" : "Inactive") + ")");
            System.out.println("5. Delete Account");
            System.out.println("0. Back");
            System.out.print("Please select an option: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    viewProfile();
                    break;
                case 2:
                    updateProfile();
                    break;
                case 3:
                    changePassword();
                    break;
                case 4:
                    toggleActiveStatus();
                    break;
                case 5:
                    if (deleteAccount()) {
                        back = true;
                    }
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private boolean getUserActiveStatus() {
        try {
            if (currentUserRole == UserRole.TRAINEE) {
                return trainingFacade.getTraineeByUsername(currentUsername).getUser().getIsActive();
            } else {
                return trainingFacade.getTrainerByUsername(currentUsername).getUser().getIsActive();
            }
        } catch (Exception e) {
            System.out.println("Error retrieving user status: " + e.getMessage());
            return false;
        }
    }

    private void viewProfile() {
        try {
            if (currentUserRole == UserRole.TRAINEE) {
                TraineeResponseDTO trainee = trainingFacade.getTraineeByUsername(currentUsername);
                System.out.println("\n--- Trainee Profile ---");
                System.out.println("Username: " + trainee.getUser().getUsername());
                System.out.println("Name: " + trainee.getUser().getFirstName() + " " + trainee.getUser().getLastName());
                System.out.println("Address: " + trainee.getAddress());
                System.out.println("Date of Birth: " + trainee.getTraineeDateOfBirth());
                System.out.println("Active: " + trainee.getUser().getIsActive());
            } else {
                TrainerResponseDTO trainer = trainingFacade.getTrainerByUsername(currentUsername);
                System.out.println("\n--- Trainer Profile ---");
                System.out.println("Username: " + trainer.getUser().getUsername());
                System.out.println("Name: " + trainer.getUser().getFirstName() + " " + trainer.getUser().getLastName());
                System.out.println("Specialization: " + trainer.getTrainerSpecialization());
                System.out.println("Active: " + trainer.getUser().getIsActive());
            }
        } catch (Exception e) {
            System.out.println("Error retrieving profile: " + e.getMessage());
        }
    }

    private void updateProfile() {
        try {
            if (currentUserRole == UserRole.TRAINEE) {
                // Get current trainee information
                TraineeResponseDTO currentTrainee = trainingFacade.getTraineeByUsername(currentUsername);

                // Prepare update DTO
                String newAddress = getStringInput("Enter new address (leave empty to keep current): ");
                if (newAddress.isEmpty()) {
                    newAddress = currentTrainee.getAddress();
                }

                UserRequestDTO userDTO = UserRequestDTO.builder()
                        .firstName(currentTrainee.getUser().getFirstName())
                        .lastName(currentTrainee.getUser().getLastName())
                        .isActive(currentTrainee.getUser().getIsActive())
                        .password(currentTrainee.getUser().getPassword())
                        .build();

                TraineeRequestDTO traineeDTO = TraineeRequestDTO.builder()
                        .address(newAddress)
                        .dateOfBirth(currentTrainee.getTraineeDateOfBirth())
                        .user(userDTO)
                        .build();

                TraineeResponseDTO response = trainingFacade.updateTrainee(currentUsername, traineeDTO);
                System.out.println("Profile updated successfully!");
            } else {
                // Get current trainer information
                TrainerResponseDTO currentTrainer = trainingFacade.getTrainerByUsername(currentUsername);

                // Prepare update DTO
                String newSpecialization = getStringInput("Enter new specialization (leave empty to keep current): ");
                if (newSpecialization.isEmpty()) {
                    newSpecialization = currentTrainer.getTrainerSpecialization();
                }

                UserRequestDTO userDTO = UserRequestDTO.builder()
                        .firstName(currentTrainer.getUser().getFirstName())
                        .lastName(currentTrainer.getUser().getLastName())
                        .isActive(currentTrainer.getUser().getIsActive())
                        .password(currentTrainer.getUser().getPassword())
                        .build();

                TrainerRequestDTO trainerDTO = TrainerRequestDTO.builder()
                        .specialization(newSpecialization)
                        .user(userDTO)
                        .build();

                TrainerResponseDTO response = trainingFacade.updateTrainer(currentUsername, trainerDTO);
                System.out.println("Profile updated successfully!");
            }
        } catch (Exception e) {
            System.out.println("Error updating profile: " + e.getMessage());
        }
    }

    private void changePassword() {
        String oldPassword = getStringInput("Enter current password: ");
        String newPassword = getStringInput("Enter new password: ");
        String confirmPassword = getStringInput("Confirm new password: ");

        if (!newPassword.equals(confirmPassword)) {
            System.out.println("Passwords do not match. Please try again.");
            return;
        }

        ChangePasswordRequestDTO changePasswordRequest = ChangePasswordRequestDTO.builder()
                .username(currentUsername)
                .oldPassword(oldPassword)
                .newPassword(newPassword)
                .build();

        try {
            // Note: This method call might need adjustment based on your actual implementation
            Boolean changed = trainingFacade.changePassword(changePasswordRequest);

            if (Boolean.TRUE.equals(changed)) {
                System.out.println("Password changed successfully!");
            } else {
                System.out.println("Failed to change password. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("Error changing password: " + e.getMessage());
        }
    }

    private void toggleActiveStatus() {
        try {
            boolean currentStatus = getUserActiveStatus();

            if (currentUserRole == UserRole.TRAINEE) {
                TraineeResponseDTO trainee = trainingFacade.getTraineeByUsername(currentUsername);

                UserRequestDTO userDTO = UserRequestDTO.builder()
                        .firstName(trainee.getUser().getFirstName())
                        .lastName(trainee.getUser().getLastName())
                        .password(trainee.getUser().getPassword())
                        .isActive(!currentStatus)
                        .build();

                TraineeRequestDTO traineeDTO = TraineeRequestDTO.builder()
                        .address(trainee.getAddress())
                        .dateOfBirth(trainee.getTraineeDateOfBirth())
                        .user(userDTO)
                        .build();

                trainingFacade.updateTrainee(currentUsername, traineeDTO);
            } else {
                TrainerResponseDTO trainer = trainingFacade.getTrainerByUsername(currentUsername);

                UserRequestDTO userDTO = UserRequestDTO.builder()
                        .firstName(trainer.getUser().getFirstName())
                        .lastName(trainer.getUser().getLastName())
                        .password(trainer.getUser().getPassword())
                        .isActive(!currentStatus)
                        .build();

                TrainerRequestDTO trainerDTO = TrainerRequestDTO.builder()
                        .specialization(trainer.getTrainerSpecialization())
                        .user(userDTO)
                        .build();

                trainingFacade.updateTrainer(currentUsername, trainerDTO);
            }

            System.out.println("Account " + (currentStatus ? "deactivated" : "activated") + " successfully!");

            if (currentStatus) {
                // If user deactivated their account, log them out
                handleLogout();
                return;
            }
        } catch (Exception e) {
            System.out.println("Error toggling active status: " + e.getMessage());
        }
    }

    private boolean deleteAccount() {
        System.out.println("\nWARNING: This will permanently delete your account.");
        String confirm = getStringInput("Type 'DELETE' to confirm: ");

        if (!"DELETE".equals(confirm)) {
            System.out.println("Account deletion cancelled.");
            return false;
        }

        try {
            if (currentUserRole == UserRole.TRAINEE) {
                trainingFacade.deleteTrainee(currentUsername);
            } else {
                trainingFacade.deleteTrainer(currentUsername);
            }

            System.out.println("Account deleted successfully.");
            handleLogout();
            return true;
        } catch (Exception e) {
            System.out.println("Error deleting account: " + e.getMessage());
            return false;
        }
    }

    private void handleManageTrainers() {
        boolean back = false;

        while (!back) {
            System.out.println("\n--- Manage My Trainers ---");
            System.out.println("1. View My Trainers");
            System.out.println("2. Add Trainer");
            System.out.println("3. Remove Trainer");
            System.out.println("0. Back");
            System.out.print("Please select an option: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    viewMyTrainers();
                    break;
                case 2:
                    addTrainer();
                    break;
                case 3:
                    removeTrainer();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void viewMyTrainers() {
        try {
            // This method would need implementation in your TrainingFacade
            List<TrainerResponseDTO> trainers = trainingFacade.getTrainersByTraineeUsername(currentUsername);

            if (trainers.isEmpty()) {
                System.out.println("You don't have any trainers assigned yet.");
                return;
            }

            System.out.println("\n--- My Trainers ---");
            for (int i = 0; i < trainers.size(); i++) {
                TrainerResponseDTO trainer = trainers.get(i);
                System.out.println((i + 1) + ". " + trainer.getUser().getFirstName() + " " +
                        trainer.getUser().getLastName() + " - " + trainer.getTrainerSpecialization() +
                        " (" + (trainer.getUser().getIsActive() ? "Active" : "Inactive") + ")");
            }
        } catch (Exception e) {
            System.out.println("Error retrieving trainers: " + e.getMessage());
        }
    }

    private void addTrainer() {
        try {
            // Get all available trainers
            List<TrainerResponseDTO> allTrainers = trainingFacade.getAllTrainers();

            if (allTrainers.isEmpty()) {
                System.out.println("No trainers available in the system.");
                return;
            }

            // Display available trainers
            System.out.println("\n--- Available Trainers ---");
            for (int i = 0; i < allTrainers.size(); i++) {
                TrainerResponseDTO trainer = allTrainers.get(i);
                System.out.println((i + 1) + ". " + trainer.getUser().getFirstName() + " " +
                        trainer.getUser().getLastName() + " - " + trainer.getTrainerSpecialization());
            }

            int trainerIndex = Integer.parseInt(getStringInput("\nSelect trainer number to add: ")) - 1;

            if (trainerIndex < 0 || trainerIndex >= allTrainers.size()) {
                System.out.println("Invalid selection.");
                return;
            }

            String trainerUsername = allTrainers.get(trainerIndex).getUser().getUsername();

            // This method would need implementation in your TrainingFacade
            Boolean added = trainingFacade.addTrainerToTrainee(currentUsername, trainerUsername);

            if (Boolean.TRUE.equals(added)) {
                System.out.println("Trainer added successfully!");
            } else {
                System.out.println("Failed to add trainer. They might already be assigned to you.");
            }
        } catch (Exception e) {
            System.out.println("Error adding trainer: " + e.getMessage());
        }
    }

    private void removeTrainer() {
        try {
            // Get current trainers
            List<TrainerResponseDTO> myTrainers = trainingFacade.getTrainersByTraineeUsername(currentUsername);

            if (myTrainers.isEmpty()) {
                System.out.println("You don't have any trainers to remove.");
                return;
            }

            // Display trainers
            System.out.println("\n--- My Trainers ---");
            for (int i = 0; i < myTrainers.size(); i++) {
                TrainerResponseDTO trainer = myTrainers.get(i);
                System.out.println((i + 1) + ". " + trainer.getUser().getFirstName() + " " +
                        trainer.getUser().getLastName() + " - " + trainer.getTrainerSpecialization());
            }

            int trainerIndex = Integer.parseInt(getStringInput("\nSelect trainer number to remove: ")) - 1;

            if (trainerIndex < 0 || trainerIndex >= myTrainers.size()) {
                System.out.println("Invalid selection.");
                return;
            }

            String trainerUsername = myTrainers.get(trainerIndex).getUser().getUsername();

            // This method would need implementation in your TrainingFacade
            Boolean removed = trainingFacade.removeTrainerFromTrainee(currentUsername, trainerUsername);

            if (Boolean.TRUE.equals(removed)) {
                System.out.println("Trainer removed successfully!");
            } else {
                System.out.println("Failed to remove trainer.");
            }
        } catch (Exception e) {
            System.out.println("Error removing trainer: " + e.getMessage());
        }
    }

    private void handleManageTrainees() {
        boolean back = false;

        while (!back) {
            System.out.println("\n--- Manage My Trainees ---");
            System.out.println("1. View My Trainees");
            System.out.println("2. View Trainee Details");
            System.out.println("0. Back");
            System.out.print("Please select an option: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    viewMyTrainees();
                    break;
                case 2:
                    viewTraineeDetails();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void viewMyTrainees() {
        try {
            // This method would need implementation in your TrainingFacade
            List<TraineeResponseDTO> trainees = trainingFacade.getTraineesByTrainerUsername(currentUsername);

            if (trainees.isEmpty()) {
                System.out.println("You don't have any trainees assigned yet.");
                return;
            }

            System.out.println("\n--- My Trainees ---");
            for (int i = 0; i < trainees.size(); i++) {
                TraineeResponseDTO trainee = trainees.get(i);
                System.out.println((i + 1) + ". " + trainee.getUser().getFirstName() + " " +
                        trainee.getUser().getLastName() +
                        " (" + (trainee.getUser().getIsActive() ? "Active" : "Inactive") + ")");
            }
        } catch (Exception e) {
            System.out.println("Error retrieving trainees: " + e.getMessage());
        }
    }

    private void viewTraineeDetails() {
        try {
            // Get current trainees
            List<TraineeResponseDTO> myTrainees = trainingFacade.getTraineesByTrainerUsername(currentUsername);

            if (myTrainees.isEmpty()) {
                System.out.println("You don't have any trainees assigned yet.");
                return;
            }

            // Display trainees
            System.out.println("\n--- My Trainees ---");
            for (int i = 0; i < myTrainees.size(); i++) {
                TraineeResponseDTO trainee = myTrainees.get(i);
                System.out.println((i + 1) + ". " + trainee.getUser().getFirstName() + " " +
                        trainee.getUser().getLastName());
            }

            int traineeIndex = Integer.parseInt(getStringInput("\nSelect trainee number to view details: ")) - 1;

            if (traineeIndex < 0 || traineeIndex >= myTrainees.size()) {
                System.out.println("Invalid selection.");
                return;
            }

            TraineeResponseDTO selectedTrainee = myTrainees.get(traineeIndex);

            System.out.println("\n--- Trainee Details ---");
            System.out.println("Name: " + selectedTrainee.getUser().getFirstName() + " " +
                    selectedTrainee.getUser().getLastName());
            System.out.println("Username: " + selectedTrainee.getUser().getUsername());
            System.out.println("Address: " + selectedTrainee.getAddress());
            System.out.println("Date of Birth: " + selectedTrainee.getTraineeDateOfBirth());
            System.out.println("Status: " + (selectedTrainee.getUser().getIsActive() ? "Active" : "Inactive"));

            // Display trainings for this trainee
            List<TrainingResponseDTO> traineeTrainings =
                    trainingFacade.getTrainingsByTraineeUsername(selectedTrainee.getUser().getUsername());

            if (!traineeTrainings.isEmpty()) {
                System.out.println("\nTrainings:");
                for (TrainingResponseDTO training : traineeTrainings) {
                    System.out.println("- " + training.getTrainingName() + " (" +
                            training.getTrainingDate() + ", Duration: " + training.getTrainingDuration() + ")");
                }
            } else {
                System.out.println("\nNo trainings yet for this trainee.");
            }
        } catch (Exception e) {
            System.out.println("Error retrieving trainee details: " + e.getMessage());
        }
    }

    private void handleViewTrainings() {
        try {
            // Get trainings for current trainee
            List<TrainingResponseDTO> trainings = trainingFacade.getTrainingsByTraineeUsername(currentUsername);

            if (trainings.isEmpty()) {
                System.out.println("You don't have any trainings yet.");
                return;
            }

            System.out.println("\n--- My Trainings ---");
            for (int i = 0; i < trainings.size(); i++) {
                TrainingResponseDTO training = trainings.get(i);
                System.out.println((i + 1) + ". " + training.getTrainingName() +
                        " with " + training.getTrainer().getUser().getFirstName() + " " +
                        training.getTrainer().getUser().getLastName() +
                        " (" + training.getTrainingDate() + ", Duration: " + training.getTrainingDuration() + ")");
            }

            // Ask if user wants to see more details
            String viewMore = getStringInput("\nEnter training number to view details (or press Enter to go back): ");

            if (!viewMore.isEmpty()) {
                try {
                    int trainingIndex = Integer.parseInt(viewMore) - 1;

                    if (trainingIndex >= 0 && trainingIndex < trainings.size()) {
                        TrainingResponseDTO selectedTraining = trainings.get(trainingIndex);

                        System.out.println("\n--- Training Details ---");
                        System.out.println("Name: " + selectedTraining.getTrainingName());
                        System.out.println("Type: " + selectedTraining.getTrainingType());
                        System.out.println("Date: " + selectedTraining.getTrainingDate());
                        System.out.println("Duration: " + selectedTraining.getTrainingDuration());
                        System.out.println("Trainer: " + selectedTraining.getTrainer().getUser().getFirstName() +
                                " " + selectedTraining.getTrainer().getUser().getLastName());
                        System.out.println("Trainer Specialization: " + selectedTraining.getTrainer().getTrainerSpecialization());
                    } else {
                        System.out.println("Invalid selection.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving trainings: " + e.getMessage());
        }
    }

                    private void handleTrainingManagement() {
        boolean back = false;

        while (!back) {
            System.out.println("\n--- Training Management ---");
            System.out.println("1. Create Training");
            System.out.println("2. Update Training");
            System.out.println("3. Delete Training");
            System.out.println("4. Get Training by Username");
            System.out.println("5. Get All Trainings");
            System.out.println("0. Back to Main Menu");
            System.out.print("Please select an option: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    createTraining();
                    break;
                case 2:
                    updateTraining();
                    break;
                case 3:
                    deleteTraining();
                    break;
                case 4:
                    getTrainingByUsername();
                    break;
                case 5:
                    getAllTrainings();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void createTraining() {
        System.out.println("\n--- Create Training ---");

        Training training = new Training();
        // Set properties based on user input

        try {
            TrainingResponseDTO response = trainingFacade.createTraining(training);
            System.out.println("Training created successfully with ID: " + response.getTrainingName());
        } catch (Exception e) {
            System.out.println("Error creating training: " + e.getMessage());
        }
    }

    private void updateTraining() {
        System.out.println("\n--- Update Training ---");
        String username = getStringInput("Enter username associated with training to update: ");

        Training training = new Training();
        // Set properties based on user input

        try {
            TrainingResponseDTO response = trainingFacade.updateTraining(username, training);
            System.out.println("Training updated successfully with ID: " + response.getTrainingName());
        } catch (Exception e) {
            System.out.println("Error updating training: " + e.getMessage());
        }
    }

    private void deleteTraining() {
        System.out.println("\n--- Delete Training ---");
        String username = getStringInput("Enter username associated with training to delete: ");

        try {
            trainingFacade.deleteTraining(username);
            System.out.println("Training deleted successfully.");
        } catch (Exception e) {
            System.out.println("Error deleting training: " + e.getMessage());
        }
    }

    private void getTrainingByUsername() {
        System.out.println("\n--- Get Training by Username ---");
        String username = getStringInput("Enter username: ");

        try {
            TrainingResponseDTO response = trainingFacade.getTrainingByUsername(username);
            displayTrainingInfo(response);
        } catch (Exception e) {
            System.out.println("Error retrieving training: " + e.getMessage());
        }
    }

    private void getAllTrainings() {
        System.out.println("\n--- All Trainings ---");

        try {
            List<TrainingResponseDTO> trainings = trainingFacade.getAllTrainings();

            if (trainings.isEmpty()) {
                System.out.println("No trainings found.");
                return;
            }

            for (TrainingResponseDTO training : trainings) {
                displayTrainingInfo(training);
                System.out.println("---------------------------");
            }
        } catch (Exception e) {
            System.out.println("Error retrieving trainings: " + e.getMessage());
        }
    }

    private void displayTrainingInfo(TrainingResponseDTO training) {
        System.out.println("Training ID: " + training.getClass());
        System.out.println("Training Name: " + training.getTrainingName());
        System.out.println("Trainee: " + training.getTrainee());
        System.out.println("Trainer: " + training.getTrainer());
        System.out.println("Type: " + training.getTrainingType());
        System.out.println("Date: " + training.getTrainingDate());
        System.out.println("Duration: " + training.getTrainingDuration());
    }
}
