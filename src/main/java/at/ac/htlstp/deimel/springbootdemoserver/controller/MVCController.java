package at.ac.htlstp.deimel.springbootdemoserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="")
public class MVCController {

    @RequestMapping("")
    public String test(Model model){
        model.addAttribute("name", "Deimel");
        return "testview";
    }

}
