package ru.itmo.wp.lesson8.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.itmo.wp.lesson8.domain.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "UPDATE user SET passwordSha=SHA1(CONCAT('cddb3fc705370d0193ce', ?1, ?2)) WHERE id=?1", nativeQuery = true)
    void updatePassword(long id, String password);

    long countByLogin(String login);

    List<User> findAllByOrderByCreationTimeDesc();
}
