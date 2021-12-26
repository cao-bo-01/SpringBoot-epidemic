package com.epidemic.controller;

import com.epidemic.service.DataService;
import com.epidemic.util.MailComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DataController {

    @Autowired
    private DataService dataService;

    @GetMapping("/")
    public String list(Model model)  {
    model.addAttribute("dataList",dataService.list());

        return "data";
    }






}
