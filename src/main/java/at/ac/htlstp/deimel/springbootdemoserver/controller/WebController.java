package at.ac.htlstp.deimel.springbootdemoserver.controller;

import at.ac.htlstp.deimel.springbootdemoserver.config.Endpoints;
import at.ac.htlstp.deimel.springbootdemoserver.model.FormModel;
import at.ac.htlstp.deimel.springbootdemoserver.model.SternDreickModel;
import at.ac.htlstp.deimel.springbootdemoserver.service.database.AdressService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * für Browser-Server Kommunikation
 */
@Controller
@RequestMapping(path = "")
public class WebController {

    private final AdressService adressService;

    public WebController(AdressService adressService) {
        this.adressService = adressService;
    }

    @RequestMapping("")
    public String test(Model model) {
        model.addAttribute("name", "Deimel");
        return "mainview";
    }

    @RequestMapping("/form")
    public String form(Model model) {
        model.addAttribute("v1", "");
        return "form";
    }

    @RequestMapping("/formeval")
    public String formEvaluate(@ModelAttribute FormModel formModel, Model model) {
        model.addAttribute("v1", formModel.getTextInput1());
        return "form";
    }

    @RequestMapping("/sterndreieck")
    public String sternDreieck(Model model) {
        model.addAttribute("tSR1", "");
        model.addAttribute("tSR2", "");
        model.addAttribute("tSR3", "");
        model.addAttribute("tDR1", "");
        model.addAttribute("tDR2", "");
        model.addAttribute("tDR3", "");
        return "sternDreieck";
    }

    @RequestMapping("/sternEval")
    public String evalStern(@ModelAttribute SternDreickModel sternDreickModel, Model model) {
        Double Ra = Double.parseDouble(sternDreickModel.getR1().replace(',', '.'));
        Double Rb = Double.parseDouble(sternDreickModel.getR2().replace(',', '.'));
        Double Rc = Double.parseDouble(sternDreickModel.getR3().replace(',', '.'));
        model.addAttribute("tSR1", String.format("%.3f", Ra));
        model.addAttribute("tSR2", String.format("%.3f", Rb));
        model.addAttribute("tSR3", String.format("%.3f", Rc));
        double sum = Ra * Rb + Rb * Rc + Rc * Ra;
        model.addAttribute("tDR1", String.format("%.3f", sum / Rb));
        model.addAttribute("tDR2", String.format("%.3f", sum / Rc));
        model.addAttribute("tDR3", String.format("%.3f", sum / Ra));
        return "sternDreieck";
    }

    @RequestMapping("/dreieckEval")
    public String evalDreieck(@ModelAttribute SternDreickModel sternDreickModel, Model model) {
        Double Rab = Double.parseDouble(sternDreickModel.getR1().replace(',', '.'));
        Double Rbc = Double.parseDouble(sternDreickModel.getR2().replace(',', '.'));
        Double Rca = Double.parseDouble(sternDreickModel.getR3().replace(',', '.'));
        model.addAttribute("tDR1", String.format("%.3f", Rab));
        model.addAttribute("tDR2", String.format("%.3f", Rbc));
        model.addAttribute("tDR3", String.format("%.3f", Rca));
        double sum = Rab + Rca + Rbc;
        model.addAttribute("tSR1", String.format("%.3f", Rca * Rab / sum));
        model.addAttribute("tSR2", String.format("%.3f", Rab * Rbc / sum));
        model.addAttribute("tSR3", String.format("%.3f", Rbc * Rca / sum));
        return "sternDreieck";
    }

    @RequestMapping("/datenbankRest")
    public String datenbank(Model model) {
        return "datenbankRest";
    }

    @RequestMapping(Endpoints.login)
    public String login(Model model) {
        return "login";
    }

    @RequestMapping(Endpoints.logout)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:" + Endpoints.login;
    }

    @RequestMapping(Endpoints.accesDenied)
    public String accesDenied(Model model){
        return "redirect:"  + Endpoints.login; // TODO add accessDenied page
    }
}
