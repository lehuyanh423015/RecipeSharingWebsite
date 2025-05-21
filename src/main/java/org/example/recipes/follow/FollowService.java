package org.example.recipes.follow;

import org.example.recipes.user.UserSimpleDTO;

import java.util.List;

public interface FollowService {
    boolean isFollowing(String followerId, String followingId);
    void follow(String followerId, String followingId);
    void unfollow(String followerId, String followingId);
    int countFollowers(String userId);
    int countFollowing(String userId);
    List<UserSimpleDTO> getFollowers(String userId);
    List<UserSimpleDTO> getFollowing(String userId);
}