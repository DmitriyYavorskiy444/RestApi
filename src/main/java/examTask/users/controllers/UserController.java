package examTask.users.controllers;

import examTask.users.entities.Users;
import examTask.users.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserRepository userRepository;

//    @Autowired
//    private KafkaTemplate<String, User> kafkaTemplate;
//    private static final String TOPIC = "RestTest";

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/list")
    public List<Users> allUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public Users getUser(@PathVariable("id") Users users) {
        return users;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Users createUser(
            @RequestBody Users newUsers) {
//        newUser.setNewUserCreationTime(LocalDateTime.now());
        newUsers.setStatus("New");
        userRepository.save(newUsers);
//        kafkaTemplate.send(TOPIC, new User(newUser.getId(), newUser.getName(),
//                newUser.getEmail(), newUser.getStatus()));
        return newUsers;
    }

    @PutMapping("/update/{id}")
    public Users updateUser(
            @PathVariable("id") Users usersFromDb,
            @RequestBody Users users
    ) {
        BeanUtils.copyProperties(users, usersFromDb, "id");
        return userRepository.save(usersFromDb);
    }

    //
//    @PatchMapping("/updateValue/{id}")
//    public User updateUserValue(
//            @PathVariable("id") User userFromDb,
//            @RequestBody User user
//
//    ) {
//        BeanUtils.copyProperties(user, userFromDb, "id");
//        return userRepository.saveAndFlush(userFromDb);
//    }
//    -----------------------------------------
//
//    @PatchMapping("/updateValue/{id}")
//    ResponseEntity<Claim> patchClaim(@PathVariable Long claimId, @RequestBody Map<String, Object> fields) {
//        // Sanitize and validate the data
//        if (claimId <= 0 || fields == null || fields.isEmpty() || !fields.get("claimId").equals(claimId)){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400 Invalid claim object received or invalid id or id does not match object
//        }
//        Claim claim = claimService.get(claimId);
//        // Does the object exist?
//        if( claim == null){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Claim object does not exist
//        }
//        // Remove id from request, we don't ever want to change the id.
//        // This is not necessary, you can just do it to save time on the reflection
//        // loop used below since we checked the id above
//        fields.remove("claimId");
//        fields.forEach((k, v) -> {
//            // use reflection to get field k on object and set it to value v
//            // Change Claim.class to whatver your object is: Object.class
//            Field field = ReflectionUtils.findField(Claim.class, k); // find field in the object class
//            field.setAccessible(true);
//            ReflectionUtils.setField(field, claim, v); // set given field for defined object to value V
//        });
//        claimService.saveOrUpdate(claim);
//        return new ResponseEntity<>(claim, HttpStatus.OK);
//    }
//    @PatchMapping("/heavyresource/{id}")
//    public ResponseEntity<?> partialUpdateName(
//            @RequestBody HeavyResourceAddressOnly partialUpdate, @PathVariable("id") String id) {
//
//        heavyResourceRepository.save(partialUpdate, id);
//        return ResponseEntity.ok("resource address updated");
//    }

//    @PatchMapping(value = "/users/{id}",
//            produces = {APPLICATION_JSON_VALUE},
//            consumes = {APPLICATION_JSON_VALUE})
//    public ResponseEntity<StuffResponse> patchStuff(@PathVariable("id") long id, @RequestBody User user) {
//        StuffDto dto = doStuff(user);
//        saveStuffToDatabase(dto);
//
//    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable("id") Users users) {
        userRepository.delete(users);
    }
}