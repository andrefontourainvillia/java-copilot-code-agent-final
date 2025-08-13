package com.mergingtonhigh.schoolmanagement.infrastructure.migrations;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.mergingtonhigh.schoolmanagement.domain.entities.Activity;
import com.mergingtonhigh.schoolmanagement.domain.valueobjects.ActivityType;
import com.mergingtonhigh.schoolmanagement.domain.valueobjects.DifficultyLevel;
import com.mergingtonhigh.schoolmanagement.domain.valueobjects.ScheduleDetails;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;

/**
 * Migration to add sample activities with difficulty levels for testing.
 */
@ChangeUnit(id = "add-difficulty-level-test-activities", order = "003", author = "Andre Fontoura")
public class V003_AddDifficultyLevelTestActivities {

    @Execution
    public void migrate(MongoTemplate mongoTemplate) {
        // Add a beginner level chess activity
        Activity beginnerChess = new Activity(
                "Xadrez para Iniciantes",
                "Aprenda os movimentos básicos e regras fundamentais do xadrez",
                "Saturday, 10:00 AM - 11:30 AM",
                new ScheduleDetails(
                        List.of("Saturday"),
                        LocalTime.of(10, 0),
                        LocalTime.of(11, 30)),
                12,
                ActivityType.ACADEMIC,
                DifficultyLevel.INICIANTE);

        // Add an intermediate programming class
        Activity intermediateProgramming = new Activity(
                "Programação Intermediária",
                "Desenvolvimento de algoritmos mais complexos e estruturas de dados",
                "Thursday, 4:00 PM - 6:00 PM",
                new ScheduleDetails(
                        List.of("Thursday"),
                        LocalTime.of(16, 0),
                        LocalTime.of(18, 0)),
                15,
                ActivityType.TECHNOLOGY,
                DifficultyLevel.INTERMEDIARIO);

        // Add an advanced math competition prep
        Activity advancedMath = new Activity(
                "Preparação para Olimpíadas Avançadas",
                "Preparação intensiva para competições nacionais de matemática",
                "Sunday, 1:00 PM - 4:00 PM",
                new ScheduleDetails(
                        List.of("Sunday"),
                        LocalTime.of(13, 0),
                        LocalTime.of(16, 0)),
                8,
                ActivityType.ACADEMIC,
                DifficultyLevel.AVANCADO);

        // Save the activities
        mongoTemplate.save(beginnerChess);
        mongoTemplate.save(intermediateProgramming);
        mongoTemplate.save(advancedMath);
    }

    @RollbackExecution
    public void rollback(MongoTemplate mongoTemplate) {
        mongoTemplate.remove(mongoTemplate.findById("Xadrez para Iniciantes", Activity.class));
        mongoTemplate.remove(mongoTemplate.findById("Programação Intermediária", Activity.class));
        mongoTemplate.remove(mongoTemplate.findById("Preparação para Olimpíadas Avançadas", Activity.class));
    }
}