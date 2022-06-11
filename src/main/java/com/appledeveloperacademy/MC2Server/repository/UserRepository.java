package com.appledeveloperacademy.MC2Server.repository;

import com.appledeveloperacademy.MC2Server.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findById(Long userId) {
        return em.find(Member.class, userId);
    }

    public Member findByUsercode(String usercode) throws NoResultException {
        return em.createQuery("select m from Member m where m.usercode = :usercode", Member.class)
                .setParameter("usercode", usercode)
                .getSingleResult();
    }
}
