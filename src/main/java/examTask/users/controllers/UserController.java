package examTask.users.controllers;

import examTask.users.entities.User;
import examTask.users.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    private KafkaTemplate<String, User> kafkaTemplate;
    private static final String TOPIC = "RestTest";

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @GetMapping("{id}")
    public User getUser(@PathVariable("id") User user) {
        return user;
    }

    @PostMapping
    public User createUser(@RequestBody User newUser) {
//        newUser.setNewUserCreationTime(LocalDateTime.now());
        newUser.setStatus("New");
        userRepository.save(newUser);
        kafkaTemplate.send(TOPIC, new User(newUser.getId(), newUser.getName(),
                newUser.getEmail(), newUser.getStatus()));
        return userRepository.save(newUser);
    }

    @PutMapping("{id}")
    public User updateUser(
            @PathVariable("id") User userFromDb,
            @RequestBody User user
    ) {
        BeanUtils.copyProperties(user, userFromDb, "id");
        return userRepository.save(userFromDb);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable("id") User user) {
        userRepository.delete(user);
    }
}