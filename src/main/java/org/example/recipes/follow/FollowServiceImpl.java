package org.example.recipes.follow;

import org.example.recipes.entity.Follow;
import org.example.recipes.login.IdGeneratorService;
import org.example.recipes.user.UserRepository;
import org.example.recipes.user.UserSimpleDTO;
import org.example.recipes.entity.Users;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowServiceImpl implements FollowService {
    private final FollowRepository repo;
    private final IdGeneratorService idGenerator;
    private final UserRepository userRepo;

    public FollowServiceImpl(
            FollowRepository repo,
            IdGeneratorService idGenerator,
            UserRepository userRepo
    ) {
        this.repo = repo;
        this.idGenerator = idGenerator;
        this.userRepo = userRepo;
    }

    @Override
    public boolean isFollowing(String followerId, String followingId) {
        return repo.existsByFollowerIdAndFollowingId(followerId, followingId);
    }

    @Override
    @Transactional
    public void follow(String followerId, String followingId) {
        if (followerId.equals(followingId)) {
            throw new IllegalArgumentException("Không thể follow chính mình");
        }
        if (repo.existsByFollowerIdAndFollowingId(followerId, followingId)) {
            return; // hoặc throw BusinessException nếu bạn muốn báo lỗi
        }
        Follow e = new Follow();
        e.setFollowId(idGenerator.generateId());
        e.setFollowerId(followerId);
        e.setFollowingId(followingId);
        e.setFollowTime(LocalDateTime.now());
        repo.save(e);
    }

    @Override
    @Transactional
    public void unfollow(String followerId, String followingId) {
        Follow e = repo.findByFollowerIdAndFollowingId(followerId, followingId);
        if (e != null) {
            repo.delete(e);
        }
    }

    @Override
    public int countFollowers(String userId) {
        return repo.countByFollowingId(userId);
    }

    @Override
    public int countFollowing(String userId) {
        return repo.countByFollowerId(userId);
    }

    @Override
    public List<UserSimpleDTO> getFollowers(String userId) {
        return repo.findByFollowingId(userId).stream()
                .map(f -> {
                    Users u = userRepo.findById(f.getFollowerId())
                            .orElseThrow(() -> new IllegalArgumentException("User không tồn tại: " + f.getFollowerId()));
                    UserSimpleDTO dto = new UserSimpleDTO();
                    dto.setUsername(u.getUsername());
                    dto.setAvatarUrl(u.getAvatarUrl());
                    dto.setBirthDate(u.getDateOfBirth());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<UserSimpleDTO> getFollowing(String userId) {
        return repo.findByFollowerId(userId).stream()
                .map(f -> {
                    Users u = userRepo.findById(f.getFollowingId())
                            .orElseThrow(() -> new IllegalArgumentException("User không tồn tại: " + f.getFollowingId()));
                    UserSimpleDTO dto = new UserSimpleDTO();
                    dto.setUsername(u.getUsername());
                    dto.setAvatarUrl(u.getAvatarUrl());
                    dto.setBirthDate(u.getDateOfBirth());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
