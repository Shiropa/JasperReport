package org.example.test.database.specification;

import org.example.test.database.entities.Registration;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Component
public class RegistrationSpecification {

    public Specification<Registration> findRegistrationByMadrasahIdAndBoardId(Short madrasahId, Short boardId) {

        return (root, query, cb) -> {
            query.orderBy(Arrays.asList(cb.asc(root.get("madrasah").get("code")), cb.asc(root.get("registrationId"))));

            return Specification.where(boardWithId(boardId))
                    .and(madrasahWithId(madrasahId))
                    .toPredicate(root, query, cb);
        };
    }

    private Specification<Registration> boardWithId(Short boardId) {
        return (root, query, cb) -> cb.equal(root.get("madrasah").get("board").get("id"), boardId);
    }

    private Specification<Registration> madrasahWithId(Short madrasahId) {
        return (root, query, cb) -> cb.equal(root.get("madrasah").get("id"), madrasahId);
    }

    /*public Specification<Registration> findRegistrationByMadrasahIdAndBoardId(Short madrasahId, Short boardId) {

        return (root, query, cb) -> {
            Predicate[] predicates = new Predicate[2];
            predicates[0] = cb.equal(root.get("madrasah").get("board").get("id"), boardId);
            predicates[1] = cb.equal(root.get("madrasah").get("id"), madrasahId);
            return query.where(predicates)
                    .orderBy(Arrays.asList(cb.asc(root.get("madrasah").get("code")), cb.asc(root.get("registrationId")))).getRestriction();
        };
    }*/

}