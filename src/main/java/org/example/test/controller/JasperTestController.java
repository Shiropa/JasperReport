package org.example.test.controller;


import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.example.test.services.JasperTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/jasper-test")
public class JasperTestController {

    @Autowired
    private JasperTestService service;

    @GetMapping(value = "/export")
    public void export(HttpServletResponse response) throws IOException, JRException {

        response.setContentType("application/x-download");
        response.setHeader("Content-Disposition", "attachment; filename=\"report.pdf\"");

        OutputStream out = response.getOutputStream();
        JasperPrint jasperPrint = service.exportPdfFile();
        JasperExportManager.exportReportToPdfStream(jasperPrint, out);
    }
}
