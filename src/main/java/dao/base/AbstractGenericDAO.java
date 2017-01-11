package dao.base;

import model.base.AbstractJPAEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lkropiew on 2017-01-11.
 */
public abstract class AbstractGenericDAO<T extends AbstractJPAEntity>{

    @PersistenceContext(unitName = "spice")
    protected EntityManager em;

    /**
     *
     * @param entity Obiekt zapisywany do bazy.
     * @param <S>
     * @return Obiekt, zapisywany przez metodę, bądź też jego zmergowana wersja.
     */
    public <S extends T> S save(S entity) {
        if(entity.getId() == null) {
            em.persist(entity);
            return entity;
        } else {
            return em.merge(entity);
        }
    }

    /**
     *
     * @param entity Obiekt, który zostanie usunięty.
     */
    public void delete(T entity) {
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    /**
     *
     * @param entities - Lista obiektów do usunięcia.
     */
    public void delete(Iterable<? extends T> entities) {
        for (T entity : entities) {
            delete(entity);
        }
    }

    /**
     *
     * @param entities Lista obiektów do zapisania.
     * @param <S>
     * @return Lista obiektów zapisywanych do bazy - zwracana w przypadku mergowania ich.
     */
    public <S extends T> List<S> save(Iterable<S> entities) {

        List<S> result = new ArrayList<S>();

        if (entities == null) {
            return result;
        }

        for (S entity : entities) {
            result.add(save(entity));
        }

        return result;
    }

    /**
     *
     * @param id Identyfikator wyszukiwanego obiektu.
     * @return Wyszukiwany obiekt.
     */
    public abstract T findOne(Long id);


    /**
     *
     * @param clazz Typ wyszukiwanych obiektów
     * @return Lista wszystkich elementów danego typu.
     */
    protected List<T> findAll(Class<T> clazz) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(clazz);
        Root<T> rootEntry = cq.from(clazz);
        TypedQuery<T> allQuery = em.createQuery(cq.select(rootEntry));
        return allQuery.getResultList();
    }

    /**
     *
     * @param clazz Typ wyszukiwanych obiektów
     * @param order kolejność wyszukiwanych elementów, asc/desc
     * @return Lista wszystkich elementów danego typu.
     */
    protected List<T> findAll(Class<T> clazz, String order) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(clazz);
        Root<T> rootEntry = cq.from(clazz);
        Path<T> valuePath = rootEntry.get(order);
        Order[] orders = {cb.desc(valuePath)};
        TypedQuery<T> allQuery = em.createQuery(cq.select(rootEntry).orderBy(orders));
        return allQuery.getResultList();
    }

    /**
     *
     * @param clazz Typ wyszukiwanych obiektów
     * @param id Identyfiaktor wyszukiwanego obiektu.
     * @return 'true' jesli obiekt o zadanym identyfikatorze istnieje. 'false' otherwise.
     */
    protected boolean exists(Class<T> clazz, Long id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<T> root = cq.from(clazz);
        return em.createQuery(cq.select(cb.count(root)).where(cb.equal(root.get("id"), id))).getSingleResult() == 1;
    }

    /**
     *
     * @param query Parametryzowane zapytanie.
     * @return Zwraca obiekt typu zgodny z parametryzowanym zapytaniem,
     * bądź 'null', jeśli brak wyniku.
     */
    protected T getSingleResultOrNull(TypedQuery<T> query) {
        try{
            return query.getSingleResult();
        } catch(NoResultException ex) {
            return null;
        }
    }
}
