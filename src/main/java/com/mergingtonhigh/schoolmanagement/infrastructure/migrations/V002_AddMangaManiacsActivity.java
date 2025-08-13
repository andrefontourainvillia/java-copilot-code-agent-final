package com.mergingtonhigh.schoolmanagement.infrastructure.migrations;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mergingtonhigh.schoolmanagement.domain.entities.Activity;
import com.mergingtonhigh.schoolmanagement.domain.valueobjects.ActivityType;
import com.mergingtonhigh.schoolmanagement.domain.valueobjects.ScheduleDetails;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;

/**
 * Migration to add the Manga Maniacs activity to the system.
 * This migration adds the missing manga club that was recently announced.
 */
@ChangeUnit(id = "add-manga-maniacs-activity", order = "002", author = "Andre Fontoura")
public class V002_AddMangaManiacsActivity {

    private final MongoTemplate mongoTemplate;

    public V002_AddMangaManiacsActivity(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Execution
    public void migrate() {
        // Check if the Manga Maniacs activity already exists
        Query query = new Query(Criteria.where("name").is("Manga Maniacs"));
        if (mongoTemplate.count(query, Activity.class) == 0) {
            addMangaManiacsActivity();
        }
    }

    private void addMangaManiacsActivity() {
        // Create the Manga Maniacs activity
        Activity mangaActivity = new Activity(
                "Manga Maniacs",
                "Mergulhe no universo incrível dos mangás japoneses! Descubra heróis épicos, aventuras emocionantes e mundos fantásticos que vão despertar sua imaginação e criatividade.",
                "Terças-feiras às 19h",
                new ScheduleDetails(List.of("Tuesday"), LocalTime.of(19, 0), LocalTime.of(20, 30)),
                15,
                ActivityType.ARTS);
        
        mongoTemplate.save(mangaActivity);
    }

    @RollbackExecution
    public void rollback() {
        // Remove the Manga Maniacs activity if rollback is needed
        Query query = new Query(Criteria.where("name").is("Manga Maniacs"));
        mongoTemplate.remove(query, Activity.class);
    }
}