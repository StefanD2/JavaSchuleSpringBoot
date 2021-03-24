package at.ac.htlstp.deimel.springbootdemoserver.controller;

import at.ac.htlstp.deimel.springbootdemoserver.dto.AdressDTO;
import at.ac.htlstp.deimel.springbootdemoserver.dto.OrtDTO;
import at.ac.htlstp.deimel.springbootdemoserver.dto.PersonDTO;
import at.ac.htlstp.deimel.springbootdemoserver.dto.PersonWohntInAdresseDTO;
import at.ac.htlstp.deimel.springbootdemoserver.service.AdressService;
import at.ac.htlstp.deimel.springbootdemoserver.service.OrtService;
import at.ac.htlstp.deimel.springbootdemoserver.service.PersonService;
import at.ac.htlstp.deimel.springbootdemoserver.service.PersonWohntInAdresseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(path = "/datenbank")
public class DatenbankWebController {

    private final AdressService adressService;
    private final PersonService personService;
    private final OrtService ortService;
    private final PersonWohntInAdresseService personWohntInAdresseService;

    public DatenbankWebController(AdressService adressService, PersonService personService, OrtService ortService, PersonWohntInAdresseService personWohntInAdresseService) {
        this.adressService = adressService;
        this.personService = personService;
        this.ortService = ortService;
        this.personWohntInAdresseService = personWohntInAdresseService;
    }

    @RequestMapping("")
    public String datenbank() {
        return "datenbank";
    }

    @RequestMapping("/getAdressen")
    public String getAdressen(Model model) {
        List<AdressDTO> adressDTOList = adressService.findAll();
        model.addAttribute("adressenList", adressDTOList);
        return "datenbank";
    }

    @RequestMapping("/getPersonen")
    public String getPersonen(Model model) {
        List<PersonDTO> personDTOList = personService.findAll();
        model.addAttribute("personenList", personDTOList);
        return "datenbank";
    }

    @RequestMapping("/getOrte")
    public String getOrte(Model model) {
        List<OrtDTO> ortDTOList = ortService.findAll();
        model.addAttribute("ortList", ortDTOList);
        return "datenbank";
    }

    @RequestMapping(value = "/getPersonInAdresse")
    public String getPersonInAdresse(@RequestParam(value = "id") String value, Model model) {
        List<AdressDTO> adressDTOList = adressService.findAll();
        model.addAttribute("adressenList", adressDTOList);
        try {
            int id = Integer.parseInt(value);
            AdressDTO adressDTO = adressService.findByIdAdresse(id);
            if (adressDTO != null) {
                List<PersonWohntInAdresseDTO> personWohntInAdresseDTOList =
                        personWohntInAdresseService.findAllByAdresse(adressDTO);
                model.addAttribute("personInAdresseList", personWohntInAdresseDTOList);
                model.addAttribute("aAdresse", adressDTO);
            } else {
                model.addAttribute("internalError", "Adress-Id " + id + " does not exist");
            }
        } catch (NumberFormatException e) {
            model.addAttribute("internalError", e.toString());
        }
        return "datenbank";
    }

    @RequestMapping(value = "/getAdressenForPerson")
    public String getAdressenForPerson(@RequestParam(value = "id") String value, Model model) {
        List<PersonDTO> personDTOList = personService.findAll();
        model.addAttribute("personenList", personDTOList);
        try {
            int id = Integer.parseInt(value);
            PersonDTO personDTO = personService.findByIdPerson(id);
            if (personDTO != null) {
                List<PersonWohntInAdresseDTO> personWohntInAdresseDTOList =
                        personWohntInAdresseService.findAllByPerson(personDTO);
                model.addAttribute("aPerson", personDTO);
                model.addAttribute("adressenForPersonList", personWohntInAdresseDTOList);
            } else {
                model.addAttribute("internalError", "Person-Id " + id + " does not exist");
            }
        } catch (NumberFormatException e) {
            model.addAttribute("internalError", e.toString());
        }
        return "datenbank";
    }

    @RequestMapping(value = "/getAdressenInOrt")
    public String getAdressenInOrt(@RequestParam(value = "id") String value, Model model) {
        List<OrtDTO> ortDTOList = ortService.findAll();
        model.addAttribute("ortList", ortDTOList);
        try {
            int id = Integer.parseInt(value);
            OrtDTO ortDTO = ortService.findByIdOrt(id);
            if (ortDTO != null) {
                model.addAttribute("aOrt", ortDTO);
                List<AdressDTO> adressDTOList = ortDTO.getAdressen();
                model.addAttribute("adressenInOrtList", ortDTO.getAdressen());
            } else {
                model.addAttribute("internalError", "Ort-ID " + id + " does not exist");
            }
        } catch (NumberFormatException e) {
            model.addAttribute("internalError", e.toString());
        }
        return "datenbank";
    }

}
