package examTask.users.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {
    public List<Map<String, String>> users = new ArrayList<>() {
        {
            add(new HashMap<>() {
                {
                    put("name", "Dimon");
                    put("email", "Dimon@gmail.com");
                    put("status","phishing"); // can be "phishing" or "verified"
                }
            });
        }
    };

    @GetMapping
    public List<Map<String, String>> list() {
        return users;
    }
}
