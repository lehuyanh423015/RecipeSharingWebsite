package org.example.recipes.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "follows",
        uniqueConstraints = @UniqueConstraint(columnNames = {"follower_id","following_id"}))
public class Follow {
    @Id
    @Column(name="follow_id", length=10)
    private String followId;

    @Column(name="follower_id", length=10, nullable=false)
    private String followerId;

    @Column(name="following_id", length=10, nullable=false)
    private String followingId;

    @Column(name="follow_time")
    private LocalDateTime followTime;

    public Follow() {}

    public String getFollowId() {
        return followId;
    }

    public void setFollowId(String followId) {
        this.followId = followId;
    }

    public String getFollowerId() {
        return followerId;
    }

    public void setFollowerId(String followerId) {
        this.followerId = followerId;
    }

    public String getFollowingId() {
        return followingId;
    }

    public void setFollowingId(String followingId) {
        this.followingId = followingId;
    }

    public LocalDateTime getFollowTime() {
        return followTime;
    }

    public void setFollowTime(LocalDateTime followTime) {
        this.followTime = followTime;
    }
}