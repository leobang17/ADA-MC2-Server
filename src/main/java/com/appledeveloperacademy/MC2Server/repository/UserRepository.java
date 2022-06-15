package com.appledeveloperacademy.MC2Server.repository;

import com.appledeveloperacademy.MC2Server.domain.HealthTag;
import com.appledeveloperacademy.MC2Server.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

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

    public List<Member> findByUsercode(String usercode) throws NoResultException {
        return em.createQuery("select m from Member m where m.usercode = :usercode", Member.class)
                .setParameter("usercode", usercode)
                .getResultList();
    }

    public List<HealthTag> listHealthTagsById(Long userId) {
        return em.createQuery(
                        "SELECT h " +
                                "FROM HealthTag h " +
                                "JOIN FETCH h.member m " +
                                "WHERE m.id = :userId", HealthTag.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<HealthTag> listHealthTagsByTagContent(Long userId, String content) {
        return em.createQuery(
                        "SELECT t" +
                                " FROM HealthTag t" +
                                " JOIN t.member m" +
                                " WHERE m.id = :id" +
                                " AND t.content = :content", HealthTag.class)
                .setParameter("content", content)
                .setParameter("id", userId)
                .getResultList();
    }

    public void flush() {
        em.flush();
    }

}
