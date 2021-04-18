package at.ac.htlstp.deimel.springbootdemoserver.controller;

import at.ac.htlstp.deimel.springbootdemoserver.config.Endpoints;
import at.ac.htlstp.deimel.springbootdemoserver.entity.AdresseEntity;
import at.ac.htlstp.deimel.springbootdemoserver.entity.OrtEntity;
import at.ac.htlstp.deimel.springbootdemoserver.entity.PersonEntity;
import at.ac.htlstp.deimel.springbootdemoserver.entity.PersonWohntinAdresseEntity;
import at.ac.htlstp.deimel.springbootdemoserver.repository.AdresseEntityRepository;
import at.ac.htlstp.deimel.springbootdemoserver.repository.OrtEntityRepository;
import at.ac.htlstp.deimel.springbootdemoserver.repository.PersonEntityRepository;
import at.ac.htlstp.deimel.springbootdemoserver.repository.PersonWohntinAdresseEntityRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = Endpoints.datenbankRest)
public class DatenbankRestController {

    private final AdresseEntityRepository adresseEntityRepository;
    private final OrtEntityRepository ortEntityRepository;
    private final PersonEntityRepository personEntityRepository;
    private final PersonWohntinAdresseEntityRepository personWohntinAdresseEntityRepository;

    public DatenbankRestController(AdresseEntityRepository adresseEntityRepository, OrtEntityRepository ortEntityRepository, PersonEntityRepository personEntityRepository, PersonWohntinAdresseEntityRepository personWohntinAdresseEntityRepository) {
        this.adresseEntityRepository = adresseEntityRepository;
        this.ortEntityRepository = ortEntityRepository;
        this.personEntityRepository = personEntityRepository;
        this.personWohntinAdresseEntityRepository = personWohntinAdresseEntityRepository;
    }

    @PostMapping(value = "/getTable", consumes = "text/plain")
    public ResponseEntity<String> getTable(@RequestBody String value) {
        String out;
        switch (value) {
            case "adresse":
                List<AdresseEntity> adresseEntityList = adresseEntityRepository.findAll();
                out = adressenListToHtmlTable(adresseEntityList, true);
                return ResponseEntity.ok(out);
            case "ort":
                List<OrtEntity> ortEntityList = ortEntityRepository.findAll();
                out = ortListToHtmlTable(ortEntityList);
                break;
            case "person":
                List<PersonEntity> personEntityList = personEntityRepository.findAll();
                out = personListToHtmlTable(personEntityList, true);
                break;
            default:
                return ResponseEntity.ok("Table not found");
        }
        return ResponseEntity.ok(out);
    }

    @PostMapping(value = "/getPersonInAdress", consumes = "text/plain")
    public ResponseEntity<String> getPersonInAdress(@RequestBody String value) {
        try {
            int id = Integer.parseInt(value);
            Optional<AdresseEntity> optionalAdresseEntity = adresseEntityRepository.findByIdAdresse(id);
            if (optionalAdresseEntity.isEmpty())
                throw new Exception("adress entity not found");
            AdresseEntity adresseEntity = optionalAdresseEntity.get();
            String out =
                    "<span>Personen in " + adresseEntity.getStrasse() + " " + adresseEntity.getHausNr() + ", " + adresseEntity.getOrt().getPLZ() + " " + adresseEntity.getOrt().getOrtsname() +
                            ":</span><br><br>";
            List<PersonWohntinAdresseEntity> personWohntinAdresseEntityList =
                    personWohntinAdresseEntityRepository.findAllByAdresse(adresseEntity);
            List<PersonEntity> personEntityList = new ArrayList<>();
            personWohntinAdresseEntityList.forEach(personWohntinAdresseEntity -> personEntityList.add(personWohntinAdresseEntity.getPerson()));
            out += "<table>" + personListToHtmlTable(personEntityList, false) + "</table>";
            return ResponseEntity.ok(out);
        } catch (Exception e) {
            return ResponseEntity.ok(e.toString());
        }
    }

    @PostMapping(value = "/getAdressenForPerson", consumes = "text/plain")
    public ResponseEntity<String> getAdressenForPerson(@RequestBody String value) {
        try {
            int id = Integer.parseInt(value);
            Optional<PersonEntity> optionalPersonEntity = personEntityRepository.findByIdPerson(id);
            if (optionalPersonEntity.isEmpty())
                throw new Exception("person entity not found");
            PersonEntity personEntity = optionalPersonEntity.get();
            String out =
                    "<span>Adressen für " + personEntity.getVorname() + " " + personEntity.getFamname() + ", geboren " +
                            "am " + personEntity.getGeburt() +
                            ":</span><br><br>";
            List<PersonWohntinAdresseEntity> personWohntinAdresseEntityList =
                    personWohntinAdresseEntityRepository.findAllByPerson(personEntity);
            List<AdresseEntity> adresseEntityList = new ArrayList<>();
            personWohntinAdresseEntityList.forEach(personWohntinAdresseEntity -> adresseEntityList.add(personWohntinAdresseEntity.getAdresse()));
            out += "<table>" + adressenListToHtmlTable(adresseEntityList, false) + "</table>";
            return ResponseEntity.ok(out);
        } catch (Exception e) {
            return ResponseEntity.ok(e.toString());
        }
    }

    @PostMapping(value = "/getAdressenInOrt", consumes = "text/plain")
    public ResponseEntity<String> getAdressenInOrt(@RequestBody String value) {
        try {
            int id = Integer.parseInt(value);
            Optional<OrtEntity> optionalOrtEntity = ortEntityRepository.findByIdOrt(id);
            if (optionalOrtEntity.isEmpty())
                throw new Exception("ort entity not found");
            OrtEntity ortEntity = optionalOrtEntity.get();
            String out =
                    "<span>Adressen für " + ortEntity.getPLZ() + " " + ortEntity.getOrtsname() +
                            ":</span><br><br>";
            List<AdresseEntity> adresseEntityList = ortEntity.getAdressen();
            out += "<table>" + adressenListToHtmlTable(adresseEntityList, false, false) + "</table>";
            return ResponseEntity.ok(out);
        } catch (Exception e) {
            return ResponseEntity.ok(e.toString());
        }
    }

    private String adressenListToHtmlTable(List<AdresseEntity> adresseEntityList, boolean get) {
        return adressenListToHtmlTable(adresseEntityList, get, true);
    }

    private String adressenListToHtmlTable(List<AdresseEntity> adresseEntityList, boolean get, boolean ort) {
        StringBuilder out = new StringBuilder("<tr><th>Straße</th><th>Hausnummer</th>" + (ort ?
                "<th>Ort</th>" : "") + (get ? "<th>Personen</th>" : "") +
                "</tr>");
        for (AdresseEntity adresseEntity : adresseEntityList) {
            out.append("<tr><td>").append(adresseEntity.getStrasse()).append("</td><td>").append(adresseEntity.getHausNr()).append("</td>").append(ort ? "<td>" +
                    adresseEntity.getOrt().getPLZ() + " " + adresseEntity.getOrt().getOrtsname() + "</td>" : "").append(get ? "<td><button onclick=\"getPersonInAdress('" + adresseEntity.getIdAdresse() + "')\">get" +
                    "</button>" : "").append("</td>").append("</tr>");
        }
        return out.toString();
    }

    private String personListToHtmlTable(List<PersonEntity> personEntityList, boolean get) {
        StringBuilder out = new StringBuilder("<tr><th>Vorname</th><th>Familienname</th><th>Geburtsdatum</th><th>Telefon</th><th>E-Mail</th>" +
                (get ? "<th>Adressen</th>" : "") + "</tr>");
        for (PersonEntity personEntity : personEntityList) {
            out.append("<tr><td>").append(personEntity.getVorname()).append("</td><td>").append(personEntity.getFamname()).append("</td><td>").append(personEntity.getGeburt()).append("</td><td>").append(personEntity.getTelefon()).append("</td><td>").append(personEntity.getEmail()).append("</td>").append(get ? "<th><button onclick=\"getAdressenForPerson('" + personEntity.getIdPerson() + "')\">get" +
                    "</button></th>" : "").append("</tr>");
        }
        return out.toString();
    }

    private String ortListToHtmlTable(List<OrtEntity> ortEntityList) {
        StringBuilder out = new StringBuilder("<tr><th>PLZ</th><th>Ortsname</th><th>Vorwahl</th> " + ("<th>Adressen</th>") + "</tr>");
        for (OrtEntity ortEntity : ortEntityList) {
            out.append("<tr><td>").append(ortEntity.getPLZ()).append("</td><td>").append(ortEntity.getOrtsname()).append("</td><td>").append(ortEntity.getTelefon()).append("</td>").append("<td><button onclick=\"getAdressenInOrt('").append(ortEntity.getIdOrt()).append("')\">get").append("</button></td>").append("</tr>");
        }

        return out.toString();
    }

}
