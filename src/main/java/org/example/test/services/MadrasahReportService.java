package org.example.test.services;

import lombok.extern.slf4j.Slf4j;
import org.example.test.converter.MadrasahToMadrasahDTO;
import org.example.test.database.repositories.MadrasahRepository;
import org.example.test.dto.MadrasahDTO;
import org.example.test.dto.RegistrationDTO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MadrasahReportService {

    private final RegistrationReportService registrationReportService;
    private final MadrasahRepository madrasahRepository;
    private final JasperReportService jasperReportService;
    private final MadrasahToMadrasahDTO madrasahToMadrasahDTO;

    public MadrasahReportService(RegistrationReportService registrationReportService, MadrasahRepository madrasahRepository, JasperReportService jasperReportService, MadrasahToMadrasahDTO madrasahToMadrasahDTO) {
        this.registrationReportService = registrationReportService;
        this.madrasahRepository = madrasahRepository;
        this.jasperReportService = jasperReportService;
        this.madrasahToMadrasahDTO = madrasahToMadrasahDTO;
    }

    public void exportMadrasahPdfFile(HttpServletResponse response) {

        List<MadrasahDTO> results = getAllMadrasahWithApplicant();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("data", results);

        jasperReportService.generateReport(response, parameters, "madrasah_with_applicant.jrxml", true);

    }


    private List<MadrasahDTO> getAllMadrasahWithApplicant() {
        List<MadrasahDTO> madrasahDTOList = new ArrayList<>();

        madrasahRepository.findAll().forEach(e -> {
            List<RegistrationDTO> registrations = registrationReportService.getRegistrationsByMadrasahId(e.getId());

            if (!registrations.isEmpty()) {
                MadrasahDTO madrasahDTO = madrasahToMadrasahDTO.convert(e);
                madrasahDTO.setRegistrations(registrations);
                madrasahDTOList.add(madrasahDTO);
            }

        });
        return madrasahDTOList;
    }

}