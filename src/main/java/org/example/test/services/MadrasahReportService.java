package org.example.test.services;

import lombok.extern.slf4j.Slf4j;
import org.example.test.database.entities.Madrasah;
import org.example.test.database.repositories.MadrasahRepository;
import org.example.test.dto.MadrasahDTO;
import org.example.test.utils.Converter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MadrasahReportService extends Converter<Madrasah, MadrasahDTO> {

    private final RegistrationReportService registrationReportService;
    private final MadrasahRepository madrasahRepository;
    private final JasperReportService jasperReportService;

    public MadrasahReportService(RegistrationReportService registrationReportService, MadrasahRepository madrasahRepository, JasperReportService jasperReportService) {
        this.registrationReportService = registrationReportService;
        this.madrasahRepository = madrasahRepository;
        this.jasperReportService = jasperReportService;
    }

    public void exportMadrasahPdfFile(HttpServletResponse response) {

        List<MadrasahDTO> results = getAllMadrasahWithApplicant();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("data", results);

        jasperReportService.showReport(response, parameters, "madrasah_with_applicant.jrxml");

    }


    private List<MadrasahDTO> getAllMadrasahWithApplicant() {
        List<Madrasah> madrasahs = madrasahRepository.findAll();

        List<MadrasahDTO> madrasahDTOList = new ArrayList<>();
        madrasahs.forEach(e -> {
            MadrasahDTO madrasahDTO = convert(e);
            madrasahDTO.setRegistrations(registrationReportService.getRegistrationsByMadrasahId(e.getId()));
            if (!madrasahDTO.getRegistrations().isEmpty()) {
                madrasahDTOList.add(madrasahDTO);
            }
        });
        return madrasahDTOList;
    }

}