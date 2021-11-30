package examTask.users.controllers;

import examTask.users.entities.Users;
import examTask.users.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @PatchMapping("/updateValue/{id}")
    public ResponseEntity<?> updateValue(
            @RequestBody Users partialUpdate, @PathVariable("id") String id) {

        userRepository.save(partialUpdate);
        return ResponseEntity.ok("users values updated");
    }

    //    @PatchMapping("/updateValue/{id}")
//    public Users updateValue(
//            @PathVariable("id") Users userFromDb,
//            @RequestBody Users users
//
//    ) {
//        Optional<Users> row = userRepository.findById(userFromDb.getId());
//        if(row.isPresent()){
//            Users item = row.get();
//            if(!users.getName().isEmpty()){
//                item.setName(users.getName());
//            }
//            if(!users.getEmail().isEmpty()){
//                item.setEmail(users.getEmail());
//            }
//            if(!users.getStatus().isEmpty()){
//                item.setStatus(users.getStatus());
//            }
//            return userRepository.save(item);
//        }
//
////        BeanUtils.copyProperties(users, userFromDb, "id");
//        return userRepository.save(null);
//    }
//    -----------------------------------------
//
//    @PatchMapping("/updateValue/{id}")
//    ResponseEntity<Users> patchClaim(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
//        Optional<Users> users = userRepository.findById(id);
//        if (users.isPresent()) {
//            fields.forEach((key, value) -> {
//                Field field = ReflectionUtils.findField(Users.class, (String) key);
//                field.setAccessible(true);
//                ReflectionUtils.setField(field, users.get(), value);
//            });
//            Users updatedUser = userRepository.save(users.get());
//            updatedUser.add(linkTo(methodOn(UserRepository.class)))
//        }
////
////        // Sanitize and validate the data
////        if (id <= 0 || fields == null || fields.isEmpty() || !fields.get("id").equals(id)){
////            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400 Invalid claim object received or invalid id or id does not match object
////        }
//////        Claim claim = claimService.get(id);
////        Optional<Users> users = userRepository.findById(id);
//////        Users item = userRepository.getById(id);
////        // Does the object exist?
////        if( users == null){
////            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Claim object does not exist
////        }
////        // Remove id from request, we don't ever want to change the id.
////        // This is not necessary, you can just do it to save time on the reflection
////        // loop used below since we checked the id above
////        fields.remove("id");
////        fields.forEach((k, v) -> {
////            // use reflection to get field k on object and set it to value v
////            // Change Claim.class to whatver your object is: Object.class
////            Field field = ReflectionUtils.findField(Users.class, k); // find field in the object class
////            field.setAccessible(true);
////            ReflectionUtils.setField(field, item, v); // set given field for defined object to value V
////        });
////        userRepository.save(item);
////        return new ResponseEntity<>(item, HttpStatus.OK);
//    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable("id") Users users) {
        userRepository.delete(users);
    }
}