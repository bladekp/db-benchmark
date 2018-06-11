package pl.bladekp.dbbenchmark.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class DaoJdbc implements Dao{

    private SessionFactory session;

    @Autowired
    public DaoJdbc(SessionFactory session){
        this.session = session;
    }

    @Override
    public void execute(String query) {
        session.getCurrentSession().createQuery(query).list();
    }
}
