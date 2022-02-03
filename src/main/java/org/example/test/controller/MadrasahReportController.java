package org.example.test.controller;

import org.example.test.services.MadrasahReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/madrasah-report")
public class MadrasahReportController {

    @Autowired
    private MadrasahReportService service;

    @GetMapping(value = "/all-madrasah")
    public void allMadrasah(HttpServletResponse response) {
        service.exportMadrasahPdfFile(response);
    }
}
