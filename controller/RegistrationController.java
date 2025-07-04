package com.gmail.src.ecommerce.controller;

import com.gmail.src.ecommerce.domain.User;
import com.gmail.src.ecommerce.domain.dto.CaptchaResponseDto;
import com.gmail.src.ecommerce.service.UserService;
import com.gmail.src.ecommerce.utils.ControllerUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    public static final String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";
    private final UserService userService;
    private final RestTemplate restTemplate;

    @Value("${recaptcha.secret}")
    private String secret;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@RequestParam("password2") String passwordConfirm,
                               @RequestParam("g-recaptcha-response") String captchaResponse,
                               @Valid User user,
                               BindingResult bindingResult,
                               Model model) {
        String url = String.format(CAPTCHA_URL, secret, captchaResponse);

        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);

        if (!response.isSuccess()) {
            model.addAttribute("captchaError", "Fill captcha");
        }

        boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirm);
        boolean isPasswordDifferent = user.getPassword() != null && !user.getPassword().equals(passwordConfirm);

        if (isConfirmEmpty) {
            model.addAttribute("password2Error", "Подтверждение пароля не может быть пустым");
        }

        if (isPasswordDifferent) {
            model.addAttribute("passwordError", "Пароли не совпадают");
        }

        if (isConfirmEmpty || isPasswordDifferent || bindingResult.hasErrors() || !response.isSuccess()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errors);

            return "registration";
        }

        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "Пользователь существует!");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activateEmailCode(@PathVariable String code, Model model) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("messageType", "alert-success");
            model.addAttribute("message", "Пользователь успешно активирован");
        } else {
            model.addAttribute("messageType", "alert-danger");
            model.addAttribute("message", "Код активации не найден");
        }
        return "login";
    }
}