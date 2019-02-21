package com.dsmhack.igniter.controllers;

import com.dsmhack.igniter.BatchRunner;
import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.user.UserFormat;
import com.dsmhack.igniter.services.user.UserImportException;
import com.dsmhack.igniter.services.user.UserImportService;
import com.dsmhack.igniter.services.user.UserImportServiceRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserImportController {

  private final BatchRunner batchRunner;
  private final UserImportServiceRegistry userImportServiceRegistry;

  public UserImportController(BatchRunner batchRunner,
                              UserImportServiceRegistry userImportServiceRegistry) {
    this.batchRunner = batchRunner;
    this.userImportServiceRegistry = userImportServiceRegistry;
  }

  @GetMapping
  public String userImportForm(Model model) {
    model.addAttribute("teamPrefix", getTeamPrefix());
    model.addAttribute("userFormats", userImportServiceRegistry.getSupportedFormats());
    return "userImport";
  }

  @PostMapping
  public String uploadUsers(@RequestParam("file") MultipartFile file,
                            @RequestParam("teamPrefix") String teamPrefix,
                            @RequestParam("numberOfTeams") int numberOfTeams,
                            @RequestParam("userFormat") UserFormat userFormat,
                            RedirectAttributes redirectAttributes) throws IOException, UserImportException {

    UserImportService userImportService = userImportServiceRegistry.getService(userFormat)
        .orElseThrow(() -> new RuntimeException("User format not supported: " + userFormat));

    List<User> users = userImportService.getUsers(new InputStreamReader(file.getInputStream()));

    redirectAttributes.addFlashAttribute(
        "message",
        "You successfully uploaded " + users.size() + " users from " + file.getOriginalFilename()
    );

    batchRunner.onboardEveryone(teamPrefix, numberOfTeams, users);

    return "redirect:/";
  }

  private String getTeamPrefix() {
    return LocalDate.now().getYear() + "_team";
  }
}
