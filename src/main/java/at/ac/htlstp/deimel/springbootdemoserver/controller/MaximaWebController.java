package at.ac.htlstp.deimel.springbootdemoserver.controller;

import at.ac.htlstp.deimel.springbootdemoserver.maxima.MaximaProcess;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@RequestMapping("/maxima")
public class MaximaWebController {

    //FIXME create custom HttpSession holder and remove this shit
    volatile private static HashMap<String, MaximaProcess> sessionProzess = new HashMap<>();
    volatile private static HashMap<String, Long> sessionProzessTime = new HashMap<>();
    private static final long sessionTimeout = 15 * 60 * 1000; // in ms

    @RequestMapping("")
    public String process(Model model, HttpSession httpSession) {
        MaximaProcess process;
        if (httpSession.isNew() || (process = sessionProzess.get(httpSession.getId())) == null) {
            process = new MaximaProcess();
            validateMaximaProcesses();
            sessionProzess.put(httpSession.getId(), process);
            sessionProzessTime.put(httpSession.getId(), System.currentTimeMillis());
        }
        model.addAttribute("commands", process.getCommands());
        return "maxima";
    }

    @RequestMapping("/execute")
    public String getMesg(@RequestParam("command") String command, Model model, HttpSession httpSession) {
        MaximaProcess process;
        if (httpSession.isNew() || (process = sessionProzess.get(httpSession.getId())) == null) {
            process = new MaximaProcess();
        }
        process.executeCommand(command);
        sessionProzess.put(httpSession.getId(), process);
        sessionProzessTime.put(httpSession.getId(), System.currentTimeMillis());
        validateMaximaProcesses();
        return "redirect:/maxima";
    }

    @RequestMapping("/destroy")
    public String destroy(HttpSession httpSession) {
        sessionProzess.get(httpSession.getId()).destroy();
        sessionProzess.remove(httpSession.getId());
        sessionProzessTime.remove(httpSession.getId());
        validateMaximaProcesses();
        httpSession.invalidate();
        return "maxima";
    }

    @RequestMapping("/restart")
    public String restart(HttpSession httpSession) {
        MaximaProcess process;
        if (httpSession.isNew() || (process = sessionProzess.get(httpSession.getId())) == null) {
            return "redirect:/maxima";
        }
        process.restartProcess();
        sessionProzess.put(httpSession.getId(), process);
        sessionProzessTime.put(httpSession.getId(), System.currentTimeMillis());
        validateMaximaProcesses();
        return "redirect:/maxima";
    }

    @RequestMapping("/rerun")
    public String rerun(HttpSession httpSession) {
        MaximaProcess process;
        if (httpSession.isNew() || (process = sessionProzess.get(httpSession.getId())) == null) {
            return "redirect:/maxima";
        }
        process.rerunCommands();
        sessionProzess.put(httpSession.getId(), process);
        sessionProzessTime.put(httpSession.getId(), System.currentTimeMillis());
        validateMaximaProcesses();
        return "redirect:/maxima";
    }

    @RequestMapping("/removeCommand")
    public String removeCommand(@RequestParam(value = "id") String value, HttpSession httpSession) {
        MaximaProcess process;
        if (httpSession.isNew() || (process = sessionProzess.get(httpSession.getId())) == null) {
            return "redirect:/maxima";
        }
        int id = Integer.parseInt(value);
        process.deleteCommand(id);
        sessionProzess.put(httpSession.getId(), process);
        sessionProzessTime.put(httpSession.getId(), System.currentTimeMillis());
        validateMaximaProcesses();
        return "redirect:/maxima";
    }

    private void validateMaximaProcesses() {
        new Thread(() -> {
            Iterator<Map.Entry<String, Long>> iterable = sessionProzessTime.entrySet().iterator();
            for (; iterable.hasNext(); ) {
                Map.Entry<String, Long> mapEntry = iterable.next();
                if (mapEntry.getValue() + sessionTimeout < System.currentTimeMillis()) {
                    sessionProzessTime.remove(mapEntry.getKey());
                    sessionProzess.remove(mapEntry.getKey());
                }
            }
        }).start();
    }

}
