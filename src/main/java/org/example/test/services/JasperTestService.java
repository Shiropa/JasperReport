package org.example.test.services;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.example.test.database.entities.Registration;
import org.example.test.database.repositories.BoardRepository;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        parameters.put("service", new HelperService());

        return JasperFillManager.fillReport(jasperReport, parameters, dataSource);
    }

    private List<Registration> getData() {
        EntityManager entitymanager = entityManagerFactory.createEntityManager( );
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
}
