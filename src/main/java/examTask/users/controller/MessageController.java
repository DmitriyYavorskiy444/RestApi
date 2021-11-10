package examTask.users.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
public class MessageController {
    public List<Map<String, String>> messages = new ArrayList<Map<String, String>>() {
        {
            add(new HashMap<String, String>() {
                {
                    put("name", "Dimon");
                    put("email", "Dimon@gmail.com");
                    put("status","phishing");
                }
            })
        }
    };

    @GetMapping
    public String list() {
        return "testing...Done";
    }
}
