package com.mergingtonhigh.schoolmanagement.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.mergingtonhigh.schoolmanagement.domain.valueobjects.ActivityType;
import com.mergingtonhigh.schoolmanagement.domain.valueobjects.Email;
import com.mergingtonhigh.schoolmanagement.domain.valueobjects.ScheduleDetails;

/**
 * Unit tests for Activity domain entity.
 */
class ActivityTest {

    @Test
    void shouldCreateActivityWithValidData() {
        // Arrange
        ScheduleDetails schedule = new ScheduleDetails(
                List.of("Monday", "Wednesday"),
                LocalTime.of(15, 30),
                LocalTime.of(17, 0));

        // Act
        Activity activity = new Activity(
                "Clube de Xadrez",
                "Aprenda estratégias de xadrez",
                "Seg/Qua 15:30-17:00",
                schedule,
                12,
                ActivityType.ACADEMIC);

        // Assert
        assertEquals("Clube de Xadrez", activity.getName());
        assertEquals("Aprenda estratégias de xadrez", activity.getDescription());
        assertEquals(12, activity.getMaxParticipants());
        assertEquals(0, activity.getCurrentParticipantCount());
        assertTrue(activity.canAddParticipant());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        // Arrange
        ScheduleDetails schedule = new ScheduleDetails(
                List.of("Monday"),
                LocalTime.of(15, 30),
                LocalTime.of(17, 0));

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Activity(null, "Descrição", "Horário", schedule, 12, ActivityType.ACADEMIC));
    }

    @Test
    void shouldAddParticipantSuccessfully() {
        // Arrange
        Activity activity = createTestActivity();
        Email studentEmail = new Email("student@mergington.edu");

        // Act
        activity.addParticipant(studentEmail);

        // Assert
        assertEquals(1, activity.getCurrentParticipantCount());
        assertTrue(activity.isParticipantRegistered(studentEmail));
    }

    @Test
    void shouldThrowExceptionWhenAddingDuplicateParticipant() {
        // Arrange
        Activity activity = createTestActivity();
        Email studentEmail = new Email("student@mergington.edu");
        activity.addParticipant(studentEmail);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> activity.addParticipant(studentEmail));
    }

    @Test
    void shouldRemoveParticipantSuccessfully() {
        // Arrange
        Activity activity = createTestActivity();
        Email studentEmail = new Email("student@mergington.edu");
        activity.addParticipant(studentEmail);

        // Act
        activity.removeParticipant(studentEmail);

        // Assert
        assertEquals(0, activity.getCurrentParticipantCount());
        assertFalse(activity.isParticipantRegistered(studentEmail));
    }

    @Test
    void shouldThrowExceptionWhenRemovingNonExistentParticipant() {
        // Arrange
        Activity activity = createTestActivity();
        Email studentEmail = new Email("student@mergington.edu");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> activity.removeParticipant(studentEmail));
    }

    @Test
    void shouldCreateMangaManiacsActivityWithCorrectProperties() {
        // Arrange
        ScheduleDetails schedule = new ScheduleDetails(
                List.of("Tuesday"),
                LocalTime.of(19, 0),
                LocalTime.of(20, 30));

        // Act
        Activity mangaActivity = new Activity(
                "Manga Maniacs",
                "Mergulhe no universo incrível dos mangás japoneses! Descubra heróis épicos, aventuras emocionantes e mundos fantásticos que vão despertar sua imaginação e criatividade.",
                "Terças-feiras às 19h",
                schedule,
                15,
                null); // Let ActivityType be auto-determined

        // Assert
        assertEquals("Manga Maniacs", mangaActivity.getName());
        assertEquals("Mergulhe no universo incrível dos mangás japoneses! Descubra heróis épicos, aventuras emocionantes e mundos fantásticos que vão despertar sua imaginação e criatividade.", mangaActivity.getDescription());
        assertEquals(15, mangaActivity.getMaxParticipants());
        assertEquals(0, mangaActivity.getCurrentParticipantCount());
        assertTrue(mangaActivity.canAddParticipant());
        // Should be detected as ARTS type due to content about graphic novels/manga
        assertEquals(ActivityType.ARTS, mangaActivity.getType());
    }

    private Activity createTestActivity() {
        ScheduleDetails schedule = new ScheduleDetails(
                List.of("Monday"),
                LocalTime.of(15, 30),
                LocalTime.of(17, 0));

        return new Activity(
                "Atividade de Teste",
                "Descrição de teste",
                "Seg 15:30-17:00",
                schedule,
                12,
                ActivityType.ACADEMIC);
    }
}