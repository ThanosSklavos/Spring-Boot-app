package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dto.UserDTO;
import gr.aueb.cf.schoolapp.model.User;
import gr.aueb.cf.schoolapp.repository.UserRepository;
import gr.aueb.cf.schoolapp.service.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public User registerUser(UserDTO userToRegister) {
        return userRepository.save(convertToUser(userToRegister));
    }

    @Transactional
    @Override
    public User updateUser(UserDTO userDTO) throws EntityNotFoundException {
        User user = userRepository.findUserById(userDTO.getId());
        if (user == null) throw new EntityNotFoundException(User.class, userDTO.getId());
        return userRepository.save(convertToUser(userDTO));
    }

    @Transactional
    @Override
    public void deleteUser(Long id) throws EntityNotFoundException {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getUsersByUsername(String username) throws EntityNotFoundException {
        List<User> users;
        users = userRepository.findByUsernameStartingWith(username);
        if (users.size() == 0) {
            throw new EntityNotFoundException(User.class, 0L);
        }
        return users;
    }

    @Override
    public User getUserById(Long id) throws EntityNotFoundException {
        Optional<User> user;
        user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new EntityNotFoundException(User.class, 0L);
        }
        return user.get();

    }

    @Override
    public boolean usernameAlreadyExists(String username) {
        return userRepository.usernameExists(username);
    }

    private static User convertToUser(UserDTO dto) {
        return new User(dto.getId(), dto.getUsername(), dto.getPassword());
    }
}
