package com.ut.inputcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class inputController {

  @GetMapping("/userInput")
  public String inputForm(Model model) {
    model.addAttribute("userInput", new inputObj());
    return "userInput";
  }

  @PostMapping("/userInput")
  public String resultSubmit(@ModelAttribute inputObj input, Model model) {
    model.addAttribute("userInput", input);
    return "result";
  }

}