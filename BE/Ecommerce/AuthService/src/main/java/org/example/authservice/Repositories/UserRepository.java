package org.example.authservice.Repositories;

import org.example.authservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User save(User user);
    public Optional<User> findUserByEmail(String email);

}
