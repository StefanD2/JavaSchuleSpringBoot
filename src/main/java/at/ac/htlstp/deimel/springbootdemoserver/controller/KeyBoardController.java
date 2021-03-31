package at.ac.htlstp.deimel.springbootdemoserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/keyboard")
public class KeyBoardController {

    // nur ein test, nicht weitergemacht
    @RequestMapping("")
    public String keyboard(Model model) {
        char keys[][] = {{'a', 'b', 'c', 'd'}, {'e', 'f'}};
        model.addAttribute("keys", keys);
        return "virtualKeyboard";
    }

}
