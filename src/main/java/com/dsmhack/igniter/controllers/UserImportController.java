package com.dsmhack.igniter.controllers;

import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.IntegrationServicesRegistry;
import com.dsmhack.igniter.services.TeamConfigurationService;
import com.dsmhack.igniter.services.TeamConfigurationServiceFactory;
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

  private final UserImportServiceRegistry userImportServiceRegistry;
  private final IntegrationServicesRegistry integrationServicesRegistry;
  private final TeamConfigurationServiceFactory teamConfigurationServiceFactory;

  public UserImportController(UserImportServiceRegistry userImportServiceRegistry,
                              IntegrationServicesRegistry integrationServicesRegistry,
                              TeamConfigurationServiceFactory teamConfigurationServiceFactory) {
    this.userImportServiceRegistry = userImportServiceRegistry;
    this.integrationServicesRegistry = integrationServicesRegistry;
    this.teamConfigurationServiceFactory = teamConfigurationServiceFactory;
  }

  @GetMapping
  public String userImportForm(Model model) {
    model.addAttribute("teamPrefix", getTeamPrefix());
    model.addAttribute("userFormats", userImportServiceRegistry.getSupportedFormats());
    model.addAttribute("integrations", integrationServicesRegistry.getSupportedIntegrations());
    return "userImport";
  }

  @PostMapping
  public String uploadUsers(@RequestParam("file") MultipartFile file,
                            @RequestParam("teamPrefix") String teamPrefix,
                            @RequestParam("numberOfTeams") int numberOfTeams,
                            @RequestParam("userFormat") UserFormat userFormat,
                            @RequestParam("integrations") List<String> integrations,
                            RedirectAttributes redirectAttributes) throws IOException, UserImportException {

    UserImportService userImportService = userImportServiceRegistry.getService(userFormat)
        .orElseThrow(() -> new RuntimeException("User format not supported: " + userFormat));

    List<User> users = userImportService.getUsers(new InputStreamReader(file.getInputStream()));

    redirectAttributes.addFlashAttribute(
        "message",
        "You successfully uploaded " + users.size() + " users from " + file.getOriginalFilename()
    );

    TeamConfigurationService teamConfigurationService = teamConfigurationServiceFactory.create(integrations);

    teamConfigurationService.createTeams(teamPrefix, numberOfTeams)
        .forEach(teamName -> teamConfigurationService.addUsersToTeam(teamName, users));

    return "redirect:/";
  }

  private String getTeamPrefix() {
    return LocalDate.now().getYear() + "_team";
  }
}
