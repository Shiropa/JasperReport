package org.example.test.controller;


import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.example.test.services.RegistrationReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/registration-report")
public class RegistrationReportController {

    @Autowired
    private RegistrationReportService service;

    @GetMapping(value = "/registered-applicant")
    public void registeredApplicant(HttpServletResponse response) {
        service.exportRegistrationPdfFile(response);
    }
}
