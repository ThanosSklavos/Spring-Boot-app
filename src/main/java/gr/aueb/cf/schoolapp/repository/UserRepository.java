package gr.aueb.cf.schoolapp.repository;

import gr.aueb.cf.schoolapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(Long id);

    List<User> findByUsernameStartingWith(String username);
    @Query("SELECT count(*) > 0 FROM User U WHERE U.username = ?1 and U.password = ?2") //count() to return boolean
    boolean isUserValid(String username, String password);

    @Query("SELECT count(*) > 0 FROM User U WHERE U.username = ?1")
    boolean usernameExists(String username);
}
