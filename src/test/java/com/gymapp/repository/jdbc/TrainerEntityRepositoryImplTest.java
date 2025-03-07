package com.gymapp.repository.jdbc;

import static com.gymapp.repository.util.LiquibaseRunner.runLiquibaseMigrations;

class TrainerEntityRepositoryImplTest {

//    static TrainerRepository testTrainerRepository;
//    static DatabaseTestManager databaseTestManager = new DatabaseTestManager();
//
//    @BeforeAll
//    static void setUp() {
//
//        databaseTestManager.startContainer();
//        databaseTestManager.runLiquibaseMigration();
//        databaseTestManager.setConnectionAutoCommit(false);
//
//        testTrainerRepository = new TrainerRepositoryImpl(databaseTestManager.getDataSource());
//    }
//
//    @AfterAll
//    static void tearDown() {
//
//        databaseTestManager.stopContainer();
//    }
//
//    @AfterEach
//    void rollBackTransaction() {
//
//        databaseTestManager.rollbackTransaction();
//    }
//
//    @Test
//    void testSaveAndFindById() {
//
//        TrainerEntity trainerEntity = new TrainerEntity(null, "John Doe", "john.doe@example.com");
//
//        // Act: Save the trainer
//        Optional<TrainerEntity> savedTrainer = testTrainerRepository.save(trainerEntity);
//
//        // Ensure that the trainer was saved and the ID is generated
//        assertTrue(savedTrainer.isPresent(), "Trainer should be saved and return a valid Optional.");
//
//        // Act: Find the trainer by ID
//        Optional<TrainerEntity> foundTrainer = testTrainerRepository.findById(savedTrainer.get().id());
//
//        // Assert: Check if the trainer is found by ID
//        assertTrue(foundTrainer.isPresent(), "Trainer should be found by ID.");
//
//        // Assert: Check if the found trainer has the correct attributes
//        assertEquals(savedTrainer.get().id(), foundTrainer.get().id(), "Trainer IDs should match.");
//        assertEquals("John Doe", foundTrainer.get().name(), "Trainer names should match.");
//        assertEquals("john.doe@example.com", foundTrainer.get().email(), "Trainer emails should match.");
//    }
//
//    @Test
//    void testFindAll() {
//
//        TrainerEntity trainerEntity1 = new TrainerEntity(null, "Jane Smith", "jane.smith@example.com");
//        TrainerEntity trainerEntity2 = new TrainerEntity(null, "Bob Brown", "bob.brown@example.com");
//
//        Optional<TrainerEntity> savedTrainer1 = testTrainerRepository.save(trainerEntity1);
//        Optional<TrainerEntity> savedTrainer2 = testTrainerRepository.save(trainerEntity2);
//
//        // Ensure trainers were saved
//        assertTrue(savedTrainer1.isPresent(), "Trainer1 should be saved and present.");
//        assertTrue(savedTrainer2.isPresent(), "Trainer2 should be saved and present.");
//
//        // Act: Retrieve all trainers
//        List<TrainerEntity> trainerEntities = testTrainerRepository.findAll();
//
//        // Assert: Check the size and contents of the trainers list
//        assertEquals(2, trainerEntities.size(), "Should find 2 trainers");
//
//        // Assert: Verify that the saved trainers are in the list
//        assertTrue(trainerEntities.contains(savedTrainer1.get()), "List should contain savedTrainer1");
//        assertTrue(trainerEntities.contains(savedTrainer2.get()), "List should contain savedTrainer2");
//    }
//
//    @Test
//    void testDelete() {
//
//        TrainerEntity trainerEntity = new TrainerEntity(null, "Alice Green", "alice.green@example.com");
//
//        Optional<TrainerEntity> savedTrainer = testTrainerRepository.save(trainerEntity);
//
//        // Ensure that the trainer was saved and the ID is generated
//        assertTrue(savedTrainer.isPresent(), "Trainer should be saved and return a valid Optional.");
//
//        // Act: Delete the trainer
//        testTrainerRepository.delete(savedTrainer.get().id());
//
//        // Assert: Check that the trainer was deleted
//        Optional<TrainerEntity> result = testTrainerRepository.findById(savedTrainer.get().id());
//        assertFalse(result.isPresent(), "Trainer should no longer be present after deletion");
//    }
}
