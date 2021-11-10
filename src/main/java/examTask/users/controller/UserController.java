package examTask.users.controller;

import examTask.users.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {
    private List<Map<String, String>> users = new ArrayList<>() {
        {
            add(new HashMap<>() {
                {
                    put("name", "Dimon");
                    put("email", "Dimon@gmail.com");
                    put("status", "phishing"); // can be "phishing" or "verified"
                }
            });

            add(new HashMap<>() {
                {
                    put("name", "Limon");
                    put("email", "LLimon@gmail.com");
                    put("status", "phishing"); // can be "phishing" or "verified"
                }
            });

        }
    };

    @GetMapping
    public List<Map<String, String>> list() {
        return users;
    }

    @GetMapping("{name}")
    public Map<String, String> getUser(@PathVariable String name) {
        return getUserData(name);
    }

    private Map<String, String> getUserData(String name) {
        return users.stream()
                .filter(user -> user.get("name").equals(name))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Map<String, String> createUser(@RequestBody Map<String, String> newUser) {
        newUser.put("status", "New");
        users.add(newUser);
        return newUser;
    }

    @PutMapping("{name}")
    public Map<String, String> updateUser(@PathVariable String name, @RequestBody Map<String, String> user) {
        Map<String, String> userFromDb = getUserData(name);

        userFromDb.putAll(user);
        userFromDb.put("name", name);
        return userFromDb;
    }

    @DeleteMapping("{name}")
    public void deleteUser(@PathVariable String name) {
        Map<String, String> user = getUser(name);

        users.remove(user);
    }

}
