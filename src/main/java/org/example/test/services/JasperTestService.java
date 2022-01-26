package org.example.test.services;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.example.test.database.entities.Madrasah;
import org.example.test.database.entities.Registration;
import org.example.test.database.repositories.BoardRepository;
import org.example.test.dto.MadrasahDTO;
import org.example.test.dto.RegistrationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class JasperTestService {

    private final BoardRepository boardRepository;

    private final ResourceLoader resourceLoader;

    private final EntityManagerFactory entityManagerFactory;

    public JasperTestService(BoardRepository boardRepository, ResourceLoader resourceLoader, EntityManagerFactory entityManagerFactory) {
        this.boardRepository = boardRepository;
        this.resourceLoader = resourceLoader;
        this.entityManagerFactory = entityManagerFactory;
    }

    public JasperPrint exportPdfFile() throws JRException, IOException {

        List<Registration> results = getData();

//        if (!results.isEmpty())
//            results.add(0, results.get(0));

        String path = resourceLoader.getResource("classpath:registered_applicant.jrxml").getURI().getPath();

        JasperReport jasperReport = JasperCompileManager.compileReport(path);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(results);

        // Parameters for report
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("board", results.get(0).getMadrasah().getBoard().getNameBn());
        parameters.put("madrasah", results.get(0).getMadrasah().getNameBn());
        parameters.put("items", results);

        return JasperFillManager.fillReport(jasperReport, parameters, dataSource);
    }

    private List<Registration> getData() {
        EntityManager entitymanager = entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = entitymanager.getCriteriaBuilder();
        CriteriaQuery<Registration> cr = cb.createQuery(Registration.class);
        Root<Registration> root = cr.from(Registration.class);

        Predicate[] predicates = new Predicate[2];
        predicates[0] = cb.equal(root.get("madrasah").get("board").get("id"), 1);
        predicates[1] = cb.equal(root.get("madrasah").get("id"), 1383);
        cr.select(root).where(predicates);
        cr.orderBy(cb.asc(root.get("madrasah").get("code")), cb.asc(root.get("registrationId")));

        TypedQuery<Registration> query = entitymanager.createQuery(cr);
        List<Registration> results = query.getResultList();
        return results;
    }

    public JasperPrint exportMadrashaPdfFile() throws JRException, IOException {

        List<MadrasahDTO> results = getAllMadrasahWithApplicant();

//        if (!results.isEmpty())
//            results.add(0, results.get(0));

        String path = resourceLoader.getResource("classpath:madrasha_with_applicant.jrxml").getURI().getPath();

        JasperReport jasperReport = JasperCompileManager.compileReport(path);
//        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(results);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("data", results);

        return JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
    }


    private List<MadrasahDTO> getAllMadrasahWithApplicant() {
        EntityManager entitymanager = entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = entitymanager.getCriteriaBuilder();
        CriteriaQuery<Madrasah> madrasahCriteriaQuery = cb.createQuery(Madrasah.class);
        Root<Madrasah> madrasahRoot = madrasahCriteriaQuery.from(Madrasah.class);
        madrasahCriteriaQuery.orderBy(cb.asc(madrasahRoot.get("id")));
        TypedQuery<Madrasah> madrasahTypedQuery = entitymanager.createQuery(madrasahCriteriaQuery);
        List<Madrasah> madrasahs = madrasahTypedQuery.getResultList();

        CriteriaQuery<Registration> registrationCriteriaQuery = cb.createQuery(Registration.class);
        registrationCriteriaQuery.from(Registration.class);
        TypedQuery<Registration> query = entitymanager.createQuery(registrationCriteriaQuery);
        List<Registration> registrations = query.getResultList();

        return madrasahs.stream().map(m -> {
            MadrasahDTO madrasahDTO = new ModelMapper().map(m, MadrasahDTO.class);
            List<Registration> registrationList = registrations.stream().filter(f -> f.getMadrasah().getId().equals(m.getId())).collect(Collectors.toList());
            madrasahDTO.setRegistrations(registrationList.isEmpty() ? Collections.emptyList() :
                    registrationList.stream().map(r -> new ModelMapper().map(r, RegistrationDTO.class)).collect(Collectors.toList())
            );
            return madrasahDTO;
        }).collect(Collectors.toList());



    }


}
