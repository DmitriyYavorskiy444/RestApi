package examTask.users.controllers;

import examTask.users.entities.User;
import examTask.users.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
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

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User newUser) {
//        newUser.setNewUserCreationTime(LocalDateTime.now());

        newUser.setStatus("New");
        userRepository.save(newUser);
        kafkaTemplate.send(TOPIC, new User(newUser.getId(), newUser.getName(),
                newUser.getEmail(), newUser.getStatus()));
        return userRepository.save(newUser);
    }

    @PutMapping("/update/{id}")
    public User updateUser(
            @PathVariable("id") User userFromDb,
            @RequestBody User user
    ) {
        BeanUtils.copyProperties(user, userFromDb, "id");
        return userRepository.save(userFromDb);
    }

    @PatchMapping("/updateValue/{id}")
    public User updateUserValue(
            @RequestBody User user,
            @PathVariable("id") User userFromDb
    ) {
        BeanUtils.copyProperties(user, userFromDb, "id");
        return userRepository.save(userFromDb);
    }

//    @PatchMapping("/heavyresource/{id}")
//    public ResponseEntity<?> partialUpdateName(
//            @RequestBody HeavyResourceAddressOnly partialUpdate, @PathVariable("id") String id) {
//
//        heavyResourceRepository.save(partialUpdate, id);
//        return ResponseEntity.ok("resource address updated");
//    }


    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable("id") User user) {
        userRepository.delete(user);
    }
}