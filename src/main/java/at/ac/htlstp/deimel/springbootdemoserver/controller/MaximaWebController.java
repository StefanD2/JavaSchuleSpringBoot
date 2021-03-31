package at.ac.htlstp.deimel.springbootdemoserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/maxima")
public class MaximaWebController {

    @RequestMapping("")
    public String process(Model model, HttpSession httpSession) {
        return "maxima";
    }

    @RequestMapping("/getMsg")
    public String getMesg(@RequestParam("pmsg") String pmsg, Model model, HttpSession httpSession) {
        List<String> msgs = (List<String>) httpSession.getAttribute("SessionMsg");
        if (msgs == null) {
            msgs = new ArrayList<>();
        }
        msgs.add(pmsg);
        httpSession.setAttribute("SessionMsg", msgs);
        model.addAttribute("msgs", msgs);
        return "maxima";
    }

    @RequestMapping("/destroy")
    public String destroy(HttpSession httpSession) {
        httpSession.invalidate();
        return "maxima";
    }
}
