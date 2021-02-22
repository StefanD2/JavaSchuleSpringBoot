package at.ac.htlstp.deimel.springbootdemoserver.controller;

import at.ac.htlstp.deimel.springbootdemoserver.model.FormModel;
import at.ac.htlstp.deimel.springbootdemoserver.model.SternDreickModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * für Browser-Server Kommunikation
 */
@Controller
@RequestMapping(path = "")
public class WebController {

    @RequestMapping("")
    public String test(Model model) {
        model.addAttribute("name", "Deimel");
        return "testview";
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
        model.addAttribute("tSR1", Ra);
        model.addAttribute("tSR2", Rb);
        model.addAttribute("tSR3", Rc);
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
        model.addAttribute("tDR1", Rab);
        model.addAttribute("tDR2", Rbc);
        model.addAttribute("tDR3", Rca);
        double sum = Rab + Rca + Rbc;
        model.addAttribute("tSR1", String.format("%.3f", Rca * Rab / sum));
        model.addAttribute("tSR2", String.format("%.3f", Rab * Rbc / sum));
        model.addAttribute("tSR3", String.format("%.3f", Rbc * Rca / sum));
        return "sternDreieck";
    }

}
