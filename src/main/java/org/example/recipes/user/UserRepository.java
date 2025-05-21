package org.example.recipes.user;

import org.example.recipes.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, String> {
    Optional<Users> findByUsername(String username);
    Optional<Users> findByEmail(String email);

    Optional<Users> findByUsernameOrEmail(String username, String email);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    List<Users> findByUsernameContainingIgnoreCase(String keyword);
    Optional<Users> findTopByOrderByIdDesc();

}
