package com.dsmhack.igniter.controllers;

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
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/slackInvite")
public class SlackInviteController {

  private final UserImportServiceRegistry userImportServiceRegistry;

  public SlackInviteController(UserImportServiceRegistry userImportServiceRegistry) {
    this.userImportServiceRegistry = userImportServiceRegistry;
  }

  @GetMapping
  public String slackInviteForm(Model model) {
    model.addAttribute("userFormats", userImportServiceRegistry.getSupportedFormats());
    return "slackInvite";
  }

  @PostMapping
  public String slackInviteList(@RequestParam("file") MultipartFile file,
                                @RequestParam("userFormat") UserFormat userFormat,
                                RedirectAttributes redirectAttributes) throws IOException, UserImportException {

    UserImportService userImportService = userImportServiceRegistry.getService(userFormat)
        .orElseThrow(() -> new RuntimeException("User format not supported: " + userFormat));

    List<User> users = userImportService.getUsers(new InputStreamReader(file.getInputStream()));

    String slackInviteList = users.stream()
        .map(user -> user.getFirstName() + " " + user.getLastName() + " <" + user.getSlackEmail() + ">")
        .collect(Collectors.joining(","));

    redirectAttributes.addFlashAttribute("slackInviteList", slackInviteList);

    return "redirect:/slackInvite";
  }
}
