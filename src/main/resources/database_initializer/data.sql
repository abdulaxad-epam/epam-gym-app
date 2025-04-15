INSERT INTO training_types (training_type_id, description) VALUES('75250412-9beb-417d-a087-4fb2692d39b5','STRENGTH_TRAINING');
INSERT INTO training_types (training_type_id, description) VALUES('2079212e-5168-4d4c-b029-4356067547ab','CARDIOVASCULAR_TRAINING');
INSERT INTO training_types (training_type_id, description) VALUES('1eb6f8c6-fdea-498c-a7c1-23772becb007','HYPERTROPHY_TRAINING');
INSERT INTO training_types (training_type_id, description) VALUES('294e7df1-ed6d-4dae-9ffd-5097c180ebdf','FUNCTIONAL_TRAINING');
INSERT INTO training_types (training_type_id, description) VALUES('2dadf7cd-e5aa-4ec2-9f21-f84fe1f62939','FLEXIBILITY');




-- Insert Training Types
INSERT INTO training_types (training_type_id, description) VALUES ('f582d589-c3a0-4864-9c97-ddae809a92e9', 'Strength Training');
INSERT INTO training_types (training_type_id, description) VALUES ('b7c9b5d0-1a9c-4ea0-82f7-559a9a3c4283', 'Cardio');
INSERT INTO training_types (training_type_id, description) VALUES ('3a7cf559-d2c7-4286-8ea3-13492a4dbc77', 'Flexibility');
INSERT INTO training_types (training_type_id, description) VALUES ('d1c3ed9a-e0a7-4fcc-8829-f32f5d701d10', 'HIIT');
INSERT INTO training_types (training_type_id, description) VALUES ('5e8d317b-aae7-4ba6-9729-57e3b8c617ef', 'Yoga');

-- Insert Users
INSERT INTO users (user_id, firstname, lastname, username, role, password, is_active) VALUES ('a1b2c3d4-e5f6-4a1b-8c1d-1a2b3c4d5e6f', 'John', 'Jose', 'john.jose', 'TRAINEE','$2a$10$cgCaoJdl4PL4Qh9tEQqmBuwtPL2sFnnHxpTzKVpnw8/fO9jrS2dbS', true);
INSERT INTO users (user_id, firstname, lastname, username, role, password, is_active) VALUES ('b2c3d4e5-f6a1-4b2c-9d1e-2a3b4c5d6e7f', 'Jane', 'Smith', 'jane.smith', 'TRAINEE','$2a$10$xiOLmP2DeJlOeC8wI.hb9.4rsfK2Z5cbZWW/ISaeEXjI73gyZyaNS', true);
INSERT INTO users (user_id, firstname, lastname, username, role, password, is_active) VALUES ('c3d4e5f6-a1b2-4c3d-0e1f-3a4b5c6d7e8f', 'Michael', 'Johnson', 'michael.johnson','TRAINEE', '$2a$10$AGSko5ZT0XFoIchWGdqR1.FnaZoLnxTv7iMGLKgYVF5YZRWD3vg1.', true);
INSERT INTO users (user_id, firstname, lastname, username, role, password, is_active) VALUES ('d4e5f6a1-b2c3-4d4e-1f1a-4a5b6c7d8e9f', 'Emma', 'Williams', 'emma.williams','TRAINEE', '$2a$10$/zuG6WDCBZz0RNFvtPXGveugduXe.zC8b5LWo4OXpHjpBbVWvYod.', true);

INSERT INTO users (user_id, firstname, lastname, username, role, password, is_active) VALUES ('e5f6a1b2-c3d4-4e5f-2a1b-5a6b7c8d9e0f', 'Robert', 'Brown', 'robert.brown', 'TRAINER', '$2a$10$6QFYro8X89S1Tw78fGE.6OsJcdhapUXWGIPNbGSmk8YoLLotlr0mG', true);
INSERT INTO users (user_id, firstname, lastname, username, role, password, is_active) VALUES ('f6a1b2c3-d4e5-4f6a-3b1c-6a7b8c9d0e1f', 'Sarah', 'Davis', 'sarah.davis','TRAINER',  '$2a$10$lNXrtyD8Bdd73VWrId1j9eIaVyVDLmBdiSMcxBMYNZ335hHA1N5Li', true);
INSERT INTO users (user_id, firstname, lastname, username, role, password, is_active) VALUES ('1a2b3c4d-5e6f-4a1b-4c1d-7a8b9c0d1e2f', 'David', 'Miller', 'david.miller', 'TRAINER', '$2a$10$gDfVc58kg4jRzbYh4fkaeOZ6ahPfOsko9aRNmo9WDcQY0oQDHZsau', true);
INSERT INTO users (user_id, firstname, lastname, username, role, password, is_active) VALUES ('2b3c4d5e-6f1a-4b2c-5d1e-8a9b0c1d2e3f', 'Lisa', 'Wilson', 'lisa.wilson', 'TRAINER', '$2a$10$FXq5KwEp3M2/VQwkzn.Gee0VnwYX58uHAo2uh9bSkZrd7WFa.6N3q', true);

INSERT INTO users (user_id, firstname, lastname, username, role, password, is_active) VALUES ('3c4d5e6f-7a8b-4c3d-9e0f-9a0b1c2d3e4f', 'James', 'Taylor', 'james.taylor', 'TRAINER', '$2a$10$6LaxPjZX3Q.987m41Kt9Z.ULcRevwuDeb4lPOcNqskGcziGJoMzSy', true);
INSERT INTO users (user_id, firstname, lastname, username, role, password, is_active) VALUES ('4d5e6f7a-8b9c-4d5e-0f1a-0b1c2d3e4f5a', 'Emily', 'Anderson', 'emily.anderson', 'TRAINER', '$2a$10$Iviqgf6GUVDgqG41/yEfIeBSOs.ffHj1uhT6dgv6/udJWLlmx1ejS', true);


INSERT INTO trainees (trainee_id, date_of_birth, address, user_id) VALUES ('1a2b3c4d-5e6f-4a1b-8c1d-1a2b3c4d5e6f', '1990-05-15', '123 Main St, Anytown', 'a1b2c3d4-e5f6-4a1b-8c1d-1a2b3c4d5e6f');
INSERT INTO trainees (trainee_id, date_of_birth, address, user_id) VALUES ('2b3c4d5e-6f1a-4b2c-9d1e-2a3b4c5d6e7f', '1988-08-22', '456 Oak Ave, Somecity', 'b2c3d4e5-f6a1-4b2c-9d1e-2a3b4c5d6e7f');
INSERT INTO trainees (trainee_id, date_of_birth, address, user_id) VALUES ('3c4d5e6f-1a2b-4c3d-0e1f-3a4b5c6d7e8f', '1992-03-10', '789 Pine Rd, Othertown', 'c3d4e5f6-a1b2-4c3d-0e1f-3a4b5c6d7e8f');
INSERT INTO trainees (trainee_id, date_of_birth, address, user_id) VALUES ('4d5e6f1a-2b3c-4d4e-1f1a-4a5b6c7d8e9f', '1995-11-28', '101 Maple Dr, Newville', 'd4e5f6a1-b2c3-4d4e-1f1a-4a5b6c7d8e9f');


INSERT INTO trainers (trainer_id, specialization, user_id) VALUES ('5e6f1a2b-3c4d-4e5f-2a1b-5a6b7c8d9e0f', 'f582d589-c3a0-4864-9c97-ddae809a92e9', 'e5f6a1b2-c3d4-4e5f-2a1b-5a6b7c8d9e0f');
INSERT INTO trainers (trainer_id, specialization, user_id) VALUES ('6f1a2b3c-4d5e-4f6a-3b1c-6a7b8c9d0e1f', 'b7c9b5d0-1a9c-4ea0-82f7-559a9a3c4283', 'f6a1b2c3-d4e5-4f6a-3b1c-6a7b8c9d0e1f');
INSERT INTO trainers (trainer_id, specialization, user_id) VALUES ('7a1b2c3d-4e5f-4a1b-4c1d-7a8b9c0d1e2f', 'd1c3ed9a-e0a7-4fcc-8829-f32f5d701d10', '1a2b3c4d-5e6f-4a1b-4c1d-7a8b9c0d1e2f');
INSERT INTO trainers (trainer_id, specialization, user_id) VALUES ('8b2c3d4e-5f6a-4b2c-5d1e-8a9b0c1d2e3f', '5e8d317b-aae7-4ba6-9729-57e3b8c617ef', '2b3c4d5e-6f1a-4b2c-5d1e-8a9b0c1d2e3f');
INSERT INTO trainers (trainer_id, specialization, user_id) VALUES ('9c3d4e5f-6a7b-4c3d-9e0f-9a0b1c2d3e4f', '3a7cf559-d2c7-4286-8ea3-13492a4dbc77', '3c4d5e6f-7a8b-4c3d-9e0f-9a0b1c2d3e4f');
INSERT INTO trainers (trainer_id, specialization, user_id) VALUES ('0d4e5f6a-7b8c-4d5e-0f1a-0b1c2d3e4f5a', 'b7c9b5d0-1a9c-4ea0-82f7-559a9a3c4283', '4d5e6f7a-8b9c-4d5e-0f1a-0b1c2d3e4f5a');


INSERT INTO trainer_trainee (trainee_id, trainer_id) VALUES ('1a2b3c4d-5e6f-4a1b-8c1d-1a2b3c4d5e6f', '5e6f1a2b-3c4d-4e5f-2a1b-5a6b7c8d9e0f');
INSERT INTO trainer_trainee (trainee_id, trainer_id) VALUES ('1a2b3c4d-5e6f-4a1b-8c1d-1a2b3c4d5e6f', '6f1a2b3c-4d5e-4f6a-3b1c-6a7b8c9d0e1f');
INSERT INTO trainer_trainee (trainee_id, trainer_id) VALUES ('2b3c4d5e-6f1a-4b2c-9d1e-2a3b4c5d6e7f', '6f1a2b3c-4d5e-4f6a-3b1c-6a7b8c9d0e1f');
INSERT INTO trainer_trainee (trainee_id, trainer_id) VALUES ('2b3c4d5e-6f1a-4b2c-9d1e-2a3b4c5d6e7f', '7a1b2c3d-4e5f-4a1b-4c1d-7a8b9c0d1e2f');
INSERT INTO trainer_trainee (trainee_id, trainer_id) VALUES ('3c4d5e6f-1a2b-4c3d-0e1f-3a4b5c6d7e8f', '7a1b2c3d-4e5f-4a1b-4c1d-7a8b9c0d1e2f');
INSERT INTO trainer_trainee (trainee_id, trainer_id) VALUES ('3c4d5e6f-1a2b-4c3d-0e1f-3a4b5c6d7e8f', '8b2c3d4e-5f6a-4b2c-5d1e-8a9b0c1d2e3f');
INSERT INTO trainer_trainee (trainee_id, trainer_id) VALUES ('4d5e6f1a-2b3c-4d4e-1f1a-4a5b6c7d8e9f', '8b2c3d4e-5f6a-4b2c-5d1e-8a9b0c1d2e3f');
INSERT INTO trainer_trainee (trainee_id, trainer_id) VALUES ('4d5e6f1a-2b3c-4d4e-1f1a-4a5b6c7d8e9f', '5e6f1a2b-3c4d-4e5f-2a1b-5a6b7c8d9e0f');


INSERT INTO trainings (training_id, trainee_id, trainer_id, training_name, training_date, training_type_id, training_duration) VALUES ('a9b8c7d6-e5f4-4a9b-8c7d-1a9b8c7d6e5f', '1a2b3c4d-5e6f-4a1b-8c1d-1a2b3c4d5e6f', '5e6f1a2b-3c4d-4e5f-2a1b-5a6b7c8d9e0f', 'Upper Body Focus', '2025-02-15 10:00:00', 'f582d589-c3a0-4864-9c97-ddae809a92e9', 60);
INSERT INTO trainings (training_id, trainee_id, trainer_id, training_name, training_date, training_type_id, training_duration) VALUES ('b8c7d6e5-f4a3-4b8c-7d6e-2b8c7d6e5f4a', '1a2b3c4d-5e6f-4a1b-8c1d-1a2b3c4d5e6f', '6f1a2b3c-4d5e-4f6a-3b1c-6a7b8c9d0e1f', 'Endurance Run', '2025-02-17 14:30:00', 'b7c9b5d0-1a9c-4ea0-82f7-559a9a3c4283', 45);
INSERT INTO trainings (training_id, trainee_id, trainer_id, training_name, training_date, training_type_id, training_duration) VALUES ('c7d6e5f4-a3b2-4c7d-6e5f-3c7d6e5f4a3b', '2b3c4d5e-6f1a-4b2c-9d1e-2a3b4c5d6e7f', '6f1a2b3c-4d5e-4f6a-3b1c-6a7b8c9d0e1f', 'Interval Training', '2025-02-19 09:00:00', 'b7c9b5d0-1a9c-4ea0-82f7-559a9a3c4283', 30);
INSERT INTO trainings (training_id, trainee_id, trainer_id, training_name, training_date, training_type_id, training_duration) VALUES ('d6e5f4a3-b2c1-4d6e-5f4a-4d6e5f4a3b2c', '2b3c4d5e-6f1a-4b2c-9d1e-2a3b4c5d6e7f', '7a1b2c3d-4e5f-4a1b-4c1d-7a8b9c0d1e2f', 'Advanced HIIT', '2025-02-21 16:00:00', 'd1c3ed9a-e0a7-4fcc-8829-f32f5d701d10', 40);
INSERT INTO trainings (training_id, trainee_id, trainer_id, training_name, training_date, training_type_id, training_duration) VALUES ('e5f4a3b2-c1d0-4e5f-4a3b-5e5f4a3b2c1d', '3c4d5e6f-1a2b-4c3d-0e1f-3a4b5c6d7e8f', '7a1b2c3d-4e5f-4a1b-4c1d-7a8b9c0d1e2f', 'Core Blast', '2025-02-22 11:30:00', 'd1c3ed9a-e0a7-4fcc-8829-f32f5d701d10', 50);
INSERT INTO trainings (training_id, trainee_id, trainer_id, training_name, training_date, training_type_id, training_duration) VALUES ('f4a3b2c1-d0e9-4f4a-3b2c-6f4a3b2c1d0e', '3c4d5e6f-1a2b-4c3d-0e1f-3a4b5c6d7e8f', '8b2c3d4e-5f6a-4b2c-5d1e-8a9b0c1d2e3f', 'Power Yoga', '2025-02-23 08:00:00', '5e8d317b-aae7-4ba6-9729-57e3b8c617ef', 75);
INSERT INTO trainings (training_id, trainee_id, trainer_id, training_name, training_date, training_type_id, training_duration) VALUES ('1d2e3f4a-5b6c-41d2-e3f4-71d2e3f4a5b6', '4d5e6f1a-2b3c-4d4e-1f1a-4a5b6c7d8e9f', '8b2c3d4e-5f6a-4b2c-5d1e-8a9b0c1d2e3f', 'Relaxation Yoga', '2025-02-24 17:00:00', '5e8d317b-aae7-4ba6-9729-57e3b8c617ef', 60);
INSERT INTO trainings (training_id, trainee_id, trainer_id, training_name, training_date, training_type_id, training_duration) VALUES ('2e3f4a5b-6c7d-42e3-f4a5-82e3f4a5b6c7', '4d5e6f1a-2b3c-4d4e-1f1a-4a5b6c7d8e9f', '5e6f1a2b-3c4d-4e5f-2a1b-5a6b7c8d9e0f', 'Lower Body Strength', '2025-02-25 12:45:00', 'f582d589-c3a0-4864-9c97-ddae809a92e9', 55);
