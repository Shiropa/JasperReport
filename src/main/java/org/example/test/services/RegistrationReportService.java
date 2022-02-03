package org.example.test.services;

import lombok.extern.slf4j.Slf4j;
import org.example.test.database.entities.Madrasah;
import org.example.test.database.entities.Registration;
import org.example.test.database.repositories.RegistrationRepository;
import org.example.test.database.specification.RegistrationSpecification;
import org.example.test.dto.JasperReport.AdmittedRegisteredCandidateDTO;
import org.example.test.dto.MadrasahDTO;
import org.example.test.dto.RegistrationDTO;
import org.example.test.utils.Converter;
import org.example.test.utils.DateUtil;
import org.example.test.utils.StringUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RegistrationReportService extends Converter<Registration, RegistrationDTO> {

    private final StringUtil stringUtil;
    private final DateUtil dateUtil;
    private final RegistrationRepository repository;
    private final RegistrationSpecification specification;
    private final JasperReportService jasperReportService;

    public RegistrationReportService(StringUtil stringUtil, DateUtil dateUtil, RegistrationRepository repository,
                                     RegistrationSpecification specification, JasperReportService jasperReportService) {
        this.dateUtil = dateUtil;
        this.repository = repository;
        this.specification = specification;
        this.stringUtil = stringUtil;
        this.jasperReportService = jasperReportService;
    }

    public void exportRegistrationPdfFile(HttpServletResponse response) {

        List<AdmittedRegisteredCandidateDTO> results = getData();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("board", results.get(0).getMadrasah().getBoard().getNameBn());
        parameters.put("madrasah", results.get(0).getMadrasah().getNameBn());
        parameters.put("items", results);

        jasperReportService.showReport(response, parameters, "registered_applicant.jrxml");

    }

    private List<AdmittedRegisteredCandidateDTO> getData() {

        List<Registration> results = repository.findAll(specification.findRegistrationByMadrasahIdAndBoardId((short) 1383, (short) 1));

        if (results.isEmpty()) {
            return Collections.emptyList();
        }

        AtomicInteger serial = new AtomicInteger(1);
        return results.stream().map(m -> {
            AdmittedRegisteredCandidateDTO dto = new ModelMapper().map(m, AdmittedRegisteredCandidateDTO.class);
            dto.setSerialNoBn(stringUtil.convertToBn(String.valueOf(serial.get())));
            dto.setDateOfBirthBn(dateUtil.getSimpleDateFormatInBn(m.getDateOfBirth()));
            dto.setRegistrationIdBn(stringUtil.convertToBn(m.getRegistrationId()));
            serial.addAndGet(1);
            return dto;
        }).collect(Collectors.toList());
    }

    public List<RegistrationDTO> getRegistrationsByMadrasahId(Short id) {
        return convert(repository.findAllByMadrasahId(id));
    }

}

/*EntityManager entitymanager = entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = entitymanager.getCriteriaBuilder();
        CriteriaQuery<Registration> cr = cb.createQuery(Registration.class);
        Root<Registration> root = cr.from(Registration.class);

        Predicate[] predicates = new Predicate[2];
        predicates[0] = cb.equal(root.get("madrasah").get("board").get("id"), 1);
        predicates[1] = cb.equal(root.get("madrasah").get("id"), 1383);
        cr.select(root).where(predicates);
        cr.orderBy(cb.asc(root.get("madrasah").get("code")), cb.asc(root.get("registrationId")));

        TypedQuery<Registration> query = entitymanager.createQuery(cr);
        List<Registration> results = query.getResultList();*/