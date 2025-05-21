package org.example.recipes.serviceTest;

import org.example.recipes.entity.Follow;
import org.example.recipes.follow.FollowRepository;
import org.example.recipes.follow.FollowServiceImpl;
import org.example.recipes.login.IdGeneratorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FollowServiceImplTest {

    @Mock
    private FollowRepository repo;

    @Mock
    private IdGeneratorService idGenerator;

    @InjectMocks
    private FollowServiceImpl service;

    @Captor
    private ArgumentCaptor<Follow> followCaptor;

    private final String follower = "userA";
    private final String following = "userB";

    @Test
    void isFollowing_returnsTrueWhenExists() {
        when(repo.existsByFollowerIdAndFollowingId(follower, following)).thenReturn(true);
        boolean result = service.isFollowing(follower, following);
        assertThat(result).isTrue();
        verify(repo).existsByFollowerIdAndFollowingId(follower, following);
    }

    @Test
    void isFollowing_returnsFalseWhenNotExists() {
        when(repo.existsByFollowerIdAndFollowingId(follower, following)).thenReturn(false);
        boolean result = service.isFollowing(follower, following);
        assertThat(result).isFalse();
        verify(repo).existsByFollowerIdAndFollowingId(follower, following);
    }

    @Test
    void follow_savesNewFollowWhenNotExist() {
        when(idGenerator.generateId()).thenReturn("ID00000002");
        service.follow(follower, following);
        verify(idGenerator).generateId();
        verify(repo).save(followCaptor.capture());
        Follow saved = followCaptor.getValue();
        assertThat(saved.getFollowId()).isEqualTo("ID00000002");
        assertThat(saved.getFollowerId()).isEqualTo(follower);
        assertThat(saved.getFollowingId()).isEqualTo(following);
    }

    @Test
    void follow_doesNotSaveWhenAlreadyFollowing() {
        when(repo.existsByFollowerIdAndFollowingId(follower, following)).thenReturn(true);
        service.follow(follower, following);
        verify(repo, never()).save(any());
        verify(idGenerator, never()).generateId();
    }

    @Test
    void unfollow_deletesWhenExists() {
        Follow existing = new Follow();
        existing.setFollowId("ID00000003");
        existing.setFollowerId(follower);
        existing.setFollowingId(following);
        when(repo.findByFollowerIdAndFollowingId(follower, following)).thenReturn(existing);

        service.unfollow(follower, following);
        verify(repo).delete(existing);
    }

    @Test
    void unfollow_doesNothingWhenNotExists() {
        when(repo.findByFollowerIdAndFollowingId(follower, following)).thenReturn(null);
        service.unfollow(follower, following);
        verify(repo, never()).delete(any());
    }
}
