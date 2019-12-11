package org.example.mychat;

import org.example.mychat.entity.Message;
import org.example.mychat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class GreetingController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name",
            required=false, defaultValue="World") String name, Map<String, Object> model) {
        model.put("name", name);
        return "greeting";
    }

    @GetMapping
    public String main(Map<String, Object> model){

        Iterable<Message> messages = messageRepository.findAll();

        model.put("messages", messages);
        return "main";
    }

    /**
     *
     * @param text value from URL
     * @param tag value from URL
     * @param model
     * @return
     */
    @PostMapping
    public String add(@RequestParam String text, @RequestParam String tag,
                      Map<String, Object> model){
        Message message = new Message(text, tag);
        messageRepository.save(message);

        Iterable<Message> messages = messageRepository.findAll();

        model.put("messages", messages);
        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model){
        List<Message> messages = messageRepository.findByTag(filter);
        model.put("messages", messages);
        return "main";
    }

}
