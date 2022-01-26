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

//        response.setContentType("application/x-download");
        response.setContentType("application/pdf");
//        response.setHeader("Content-Disposition", "attachment; filename=\"report.pdf\""); //for download
        response.setHeader("Content-Disposition", "inline; filename=\"report.pdf\""); //for show

        OutputStream out = response.getOutputStream();
        JasperPrint jasperPrint = service.exportMadrashaPdfFile();
        JasperExportManager.exportReportToPdfStream(jasperPrint, out);
    }
}
