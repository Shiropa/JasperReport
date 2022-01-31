package org.example.test.services;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.example.test.database.entities.Madrasah;
import org.example.test.database.entities.Registration;
import org.example.test.database.repositories.MadrasahRepository;
import org.example.test.database.repositories.RegistrationRepository;
import org.example.test.database.specification.RegistrationSpecification;
import org.example.test.dto.JasperReport.AdmittedRegisteredCandidateDTO;
import org.example.test.dto.MadrasahDTO;
import org.example.test.dto.RegistrationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@Service
public class JasperTestService {


    private final ResourceLoader resourceLoader;
    private final EntityManagerFactory entityManagerFactory;
    private final HelperService helperService;
    private final RegistrationRepository registrationRepository;
    private final RegistrationSpecification registrationSpecification;
    private final MadrasahRepository madrasahRepository;

    public JasperTestService(ResourceLoader resourceLoader, EntityManagerFactory entityManagerFactory, HelperService helperService,
                             RegistrationRepository registrationRepository, RegistrationSpecification registrationSpecification, MadrasahRepository madrasahRepository) {
        this.registrationRepository = registrationRepository;
        this.registrationSpecification = registrationSpecification;
        this.resourceLoader = resourceLoader;
        this.entityManagerFactory = entityManagerFactory;
        this.helperService = helperService;
        this.madrasahRepository = madrasahRepository;
    }

    public JasperPrint exportRegistrationPdfFile() throws JRException, IOException {

        List<AdmittedRegisteredCandidateDTO> results = getData();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("board", results.get(0).getMadrasah().getBoard().getNameBn());
        parameters.put("madrasah", results.get(0).getMadrasah().getNameBn());
        parameters.put("items", results);

//        classpath:report/registered_applicant.jrxml
//        return JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(results));
        return JasperFillManager.fillReport(
                JasperCompileManager.compileReport(resourceLoader.getResource("classpath:report/registered_applicant.jrxml").getURI().getPath()),
                parameters, new JREmptyDataSource());
    }

    private List<AdmittedRegisteredCandidateDTO> getData() {

        List<Registration> results = registrationRepository.findAll(registrationSpecification.findRegistrationByMadrasahIdAndBoardId((short) 1383, (short) 1));

        if (results.isEmpty()) {
            return Collections.emptyList();
        }

        AtomicInteger serial = new AtomicInteger(1);
        return results.stream().map(m -> {
            AdmittedRegisteredCandidateDTO dto = new ModelMapper().map(m, AdmittedRegisteredCandidateDTO.class);
            dto.setSerialNoBn(helperService.convertToBn(String.valueOf(serial.get())));
            dto.setDateOfBirthBn(helperService.getSimpleDateFormatInBn(m.getDateOfBirth()));
            dto.setRegistrationIdBn(helperService.convertToBn(m.getRegistrationId()));
            serial.addAndGet(1);
            return dto;
        }).collect(Collectors.toList());
    }

    public JasperPrint exportMadrasahPdfFile() throws JRException, IOException {

        List<MadrasahDTO> results = getAllMadrasahWithApplicant();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("data", results);
//        classpath:report/madrasah_with_applicant.jrxml
        return JasperFillManager.fillReport(
                JasperCompileManager.compileReport(resourceLoader.getResource("classpath:report/madrasah_with_applicant.jrxml").getURI().getPath()),
                parameters, new JREmptyDataSource());
    }


    private List<MadrasahDTO> getAllMadrasahWithApplicant() {
        List<Madrasah> madrasahs = madrasahRepository.findAll();
        List<Registration> registrations = registrationRepository.findAll();

        return madrasahs.stream().map(m -> {
            MadrasahDTO madrasahDTO = new ModelMapper().map(m, MadrasahDTO.class);
            List<Registration> registrationList = registrations.stream().filter(f -> f.getMadrasah().getId().equals(m.getId())).collect(Collectors.toList());
            madrasahDTO.setRegistrations(registrationList.isEmpty() ? Collections.emptyList() :
                    registrationList.stream().map(r -> new ModelMapper().map(r, RegistrationDTO.class)).collect(Collectors.toList())
            );
            return madrasahDTO;
        }).filter(f -> !f.getRegistrations().isEmpty()).collect(Collectors.toList());
        
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