package cz.hrajlarp.model.dao;

import cz.hrajlarp.api.GenericBuilder;
import cz.hrajlarp.api.GenericHibernateDAO;
import cz.hrajlarp.api.IBuilder;
import cz.hrajlarp.model.entity.AccountantEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 */
@Repository
@Transactional
public class AccountantDAO extends GenericHibernateDAO<AccountantEntity, Integer> {
    @Override
    public IBuilder getBuilder() {
        return new GenericBuilder<AccountantEntity>(AccountantEntity.class);
    }
}
