package org.example.recipes.follow;

import org.example.recipes.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, String> {
    boolean existsByFollowerIdAndFollowingId(String follower, String following);
    Follow findByFollowerIdAndFollowingId(String follower, String following);
    int countByFollowingId(String userId);
    int countByFollowerId(String userId);
    List<Follow> findByFollowingId(String userId);
    List<Follow> findByFollowerId(String userId);

}