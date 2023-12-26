package com.crio.jukebox.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.Optional;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.repositories.ISongRepository;
import com.crio.jukebox.repositories.IUserPlayListRepository;
import com.crio.jukebox.repositories.IUserRepository;
import com.crio.jukebox.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserRepositoryTest {
    private IUserRepository userRepository;
    private ISongRepository songRepository;
    private IUserPlayListRepository userPlayListRepository;

    @BeforeEach
    public void setUp() {
        userRepository = new UserRepository();
    }

    @Test
    public void testSave() {
        User user = new User("John", "Doe");
        User savedUser = userRepository.saveTwo(user);
        assertNotNull(savedUser.getId());
        assertEquals(user.getName(), savedUser.getName());
    }

    @Test
    public void testFindAll() {
        User user1 = new User("John", "Doe");
        User user2 = new User("Jane", "Doe");
        userRepository.saveTwo(user1);
        userRepository.saveTwo(user2);
        List<User> users = userRepository.findAll();
        assertEquals(2, users.size());
    }

    @Test
    public void testFindById() {
        User user = new User("John", "Doe");
        User savedUser = userRepository.saveTwo(user);
        Optional<User> optionalUser = userRepository.findById(savedUser.getId());
        assertTrue(optionalUser.isPresent());
        assertEquals(savedUser.getName(), optionalUser.get().getName());
    }

    @Test
    public void testExistsById() {
        User user = new User("John", "Doe");
        User savedUser = userRepository.saveTwo(user);
        assertTrue(userRepository.existsById(savedUser.getId()));
        assertFalse(userRepository.existsById("invalid-id"));
    }

    @Test
    public void testDelete() {
        User user = new User("John", "Doe");
        User savedUser = userRepository.saveTwo(user);
        userRepository.delete(savedUser);
        assertFalse(userRepository.existsById(savedUser.getId()));
    }

    @Test
    public void testDeleteById() {
        User user = new User("John", "Doe");
        User savedUser = userRepository.saveTwo(user);
        String id = savedUser.getId();
        userRepository.deleteById(id);
        assertFalse(userRepository.existsById(savedUser.getId()));
    }

}