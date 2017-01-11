package dao;

import dao.base.AbstractGenericDAO;
import model.Spice;

import java.util.List;

/**
 * Created by lkropiew on 2017-01-11.
 */
public class SpiceDAO extends AbstractGenericDAO<Spice>{

    @Override
    public Spice findOne(Long id) {
        return null;
    }

    public List<Spice> findAll() {
        return findAll(Spice.class);
    }
}
