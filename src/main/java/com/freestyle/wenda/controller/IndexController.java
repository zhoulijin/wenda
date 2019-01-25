package com.freestyle.wenda.controller;

import com.freestyle.wenda.model.User;
import com.freestyle.wenda.service.UserService;
import com.freestyle.wenda.service.WendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    WendaService wendaService;

    @Autowired
    UserService userService;

    @RequestMapping(path = {"/", "/index", "test"}, method = {RequestMethod.GET})
    @ResponseBody
    public String index(HttpSession httpSession) {
        User user = new User();
        user.setName("fasdf");
        user.setPassword("fdas");
        userService.add(user);
        return wendaService.getMessage(1) + "hello nowcoder!" + " " + httpSession.getAttribute("message");
    }

    @RequestMapping(path = {"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("groupId") String groupId,
                          @PathVariable("userId") int userId,
                          @RequestParam("type") int type,
                          @RequestParam(value = "key", defaultValue = "zz", required = false) String key) {
        return String.format("this is %s / %d type = %d, key = %s", groupId, userId, type, key);
    }

    @RequestMapping(path = {"/home"}, method = {RequestMethod.GET})
    public String home(Model model) {
        model.addAttribute("key", "value");
        List<String> colors = Arrays.asList("Red", "Green", "Blue");
        model.addAttribute("colors", colors);

        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 4; i ++) {
            map.put(String.valueOf(i), String.valueOf(i * i));
        }
        model.addAttribute("map", map);

        return "home";
    }

    @RequestMapping(path = {"/request"}, method = {RequestMethod.GET})
    @ResponseBody
    public String request(Model model, HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(request.getMethod() + "<br>");
        stringBuilder.append(request.getRequestURI() + "<br>");
        return stringBuilder.toString();
    }


    @RequestMapping(path = {"/redirect/{code}"}, method = {RequestMethod.GET})
    public String redirect(@PathVariable("code") int code) {
        return "redirect:/";
    }


    @RequestMapping(path = {"/admin"}, method = {RequestMethod.GET})
    @ResponseBody
    public String admin(@RequestParam("key") String key) {
        if ("admin".equals(key)) {
            return "hello admin";
        }
        throw new IllegalArgumentException("can shu bu dui");
    }

    @ExceptionHandler
    @ResponseBody
    public String error(Exception e) {
        return "error " + e.getMessage();
    }
}
