package org.example.test.services;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

@Slf4j
@Service
public class JasperReportService {

    @Autowired
    private ResourceLoader resourceLoader;
    
    void generateReport(HttpServletResponse response, Map<String, Object> parameters, String fileName, Boolean show) {
        String type = show ? "inline" : "attachment";
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", type + "; filename=\"report.pdf\""); //for show
        getReport(fileName, parameters, response);
    }

    private void getReport(String fileName, Map<String, Object> parameters, HttpServletResponse response) {
        try {
            JasperPrint report = JasperFillManager.fillReport(
                    JasperCompileManager.compileReport(resourceLoader.getResource("classpath:report/" + fileName).getURI().getPath()),
                    parameters, new JREmptyDataSource());
            OutputStream out = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(report, out);
        } catch (JRException | IOException exp) {
            exp.printStackTrace();
        }
    }

}