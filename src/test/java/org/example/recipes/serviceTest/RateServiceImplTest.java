package org.example.recipes.serviceTest;

import org.example.recipes.login.IdGeneratorService;
import org.example.recipes.entity.Rate;
import org.example.recipes.rate.RateRepository;
import org.example.recipes.rate.RateServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RateServiceImplTest {

    @Mock
    private RateRepository repo;

    @Mock
    private IdGeneratorService idGenerator;

    @InjectMocks
    private RateServiceImpl service;

    @Test
    void getAverageRating_returnsZeroIfNoRecords() {
        when(repo.findAverageRating("r1")).thenReturn(null);
        assertThat(service.getAverageRating("r1")).isEqualTo(0f);
        verify(repo).findAverageRating("r1");
    }

    @Test
    void getAverageRating_returnsActualAvg() {
        when(repo.findAverageRating("r2")).thenReturn(7.5f);
        assertThat(service.getAverageRating("r2")).isEqualTo(7.5f);
    }

    @Test
    void getUserRating_returnsNullWhenNoRecord() {
        when(repo.findByUserIdAndRecipeId("u1", "r1")).thenReturn(null);
        assertThat(service.getUserRating("u1", "r1")).isNull();
    }

    @Test
    void getUserRating_returnsRatingWhenExists() {
        Rate r = new Rate();
        r.setRating(8);
        when(repo.findByUserIdAndRecipeId("u1", "r2")).thenReturn(r);
        assertThat(service.getUserRating("u1", "r2")).isEqualTo(8);
    }

    @Test
    void rate_createsNewRateWhenNotExists() {
        when(idGenerator.generateId()).thenReturn("ID00000004");
        when(repo.findByUserIdAndRecipeId("u1", "r1")).thenReturn(null);
        service.rate("u1", "r1", 9);

        verify(idGenerator).generateId();
        ArgumentCaptor<Rate> captor = ArgumentCaptor.forClass(Rate.class);
        verify(repo).save(captor.capture());
        Rate saved = captor.getValue();
        assertThat(saved.getRateId()).isEqualTo("ID00000004");
        assertThat(saved.getUserId()).isEqualTo("u1");
        assertThat(saved.getRecipeId()).isEqualTo("r1");
        assertThat(saved.getRating()).isEqualTo(9);
        assertThat(saved.getCreatedAt())
                .isCloseTo(LocalDateTime.now(), within(2, ChronoUnit.SECONDS));
    }

    @Test
    void rate_updatesExistingRate() {
        Rate existing = new Rate();
        existing.setRateId("ID00001");
        existing.setUserId("u1");
        existing.setRecipeId("r1");
        existing.setCreatedAt(LocalDateTime.now().minusDays(1));
        existing.setRating(3);
        when(repo.findByUserIdAndRecipeId("u1", "r1")).thenReturn(existing);

        service.rate("u1", "r1", 6);

        verify(repo).save(existing);
        assertThat(existing.getRating()).isEqualTo(6);
    }

    @Test
    void removeRating_deletesWhenExists() {
        Rate existing = new Rate();
        when(repo.findByUserIdAndRecipeId("u2", "r2")).thenReturn(existing);

        service.removeRating("u2", "r2");

        verify(repo).delete(existing);
    }

    @Test
    void removeRating_doesNothingWhenNotExists() {
        when(repo.findByUserIdAndRecipeId("u2", "r2")).thenReturn(null);

        service.removeRating("u2", "r2");

        verify(repo, never()).delete(any());
    }

    @Test
    void countRating_returnsCount() {
        when(repo.countByRecipeId("r1")).thenReturn(4);
        assertThat(service.countRating("r1")).isEqualTo(4);
        verify(repo).countByRecipeId("r1");
    }
}

