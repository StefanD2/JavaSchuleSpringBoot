package at.ac.htlstp.deimel.springbootdemoserver.controller;

import at.ac.htlstp.deimel.springbootdemoserver.config.Endpoints;
import at.ac.htlstp.deimel.springbootdemoserver.dto.database.UserDTO;
import at.ac.htlstp.deimel.springbootdemoserver.model.ChangePasswordModel;
import at.ac.htlstp.deimel.springbootdemoserver.model.ChangeUsernameModel;
import at.ac.htlstp.deimel.springbootdemoserver.service.database.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(Endpoints.manageUser)
public class ManageUserWebController {

    private final UserService userService;

    public ManageUserWebController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("")
    public String manageUser(Model model) {
        model.addAttribute("users", userService.findAll());
        return "manageUser";
    }

    @RequestMapping("/changePassword")
    public String changePassword(Model model, @ModelAttribute ChangePasswordModel changePasswordModel) {
        UserDTO userDTO = userService.findByID(Integer.parseInt(changePasswordModel.getIdUser()));
        userDTO.setPassword(changePasswordModel.getPassword());
        userService.save(userDTO);
        return "redirect:/manageUser";
    }

    @RequestMapping("/changeUsername")
    public String changeUsername(Model model, @ModelAttribute ChangeUsernameModel changeUsernameModel) {
        UserDTO userDTO = userService.findByID(Integer.parseInt(changeUsernameModel.getIdUser()));
        userDTO.setUsername(changeUsernameModel.getUsername());
        userService.save(userDTO);
        return "redirect:/manageUser";
    }


}
