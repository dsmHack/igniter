package com.dsmhack.igniter.controllers;

import com.dsmhack.igniter.BatchRunner;
import com.dsmhack.igniter.services.user.UserFormat;
import com.dsmhack.igniter.services.user.UserImportException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStreamReader;

@Controller
public class UserImportController {

  private final BatchRunner batchRunner;

  public UserImportController(BatchRunner batchRunner) {
    this.batchRunner = batchRunner;
  }

  @GetMapping("/")
  public String userImportForm(Model model) {
    return "userImport";
  }

  @PostMapping("/")
  public String uploadUsers(@RequestParam("file") MultipartFile file,
                            @RequestParam("fileFormat") UserFormat format,
                            RedirectAttributes redirectAttributes) throws IOException, UserImportException {

    redirectAttributes.addFlashAttribute("message",
                                         "You successfully uploaded " + file.getOriginalFilename() + "!");

    batchRunner.onboardEveryone(new InputStreamReader(file.getInputStream()));

    return "redirect:/";
  }
}
