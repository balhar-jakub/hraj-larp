package cz.hrajlarp.model;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public class MyDAO {

//    private SessionFactory sessionFactory;
//
//    @Autowired
//    public void setSessionFactory(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }
//
//    @Transactional(readOnly=false)
//    public void addPerson(HrajUserEntity p) {
//        Session session = sessionFactory.openSession();
//        session.save(p);
//        session.close();
//    }
//
//    @Transactional(readOnly=true)
//    public void getAllObjects(){
//        final Session session = sessionFactory.openSession();
//        try {
//            System.out.println("querying all the managed entities...");
//            final Map metadataMap = sessionFactory.getAllClassMetadata();
//            for (Object key : metadataMap.keySet()) {
//                final ClassMetadata classMetadata = (ClassMetadata) metadataMap.get(key);
//                final String entityName = classMetadata.getEntityName();
//                final Query query = session.createQuery("from " + entityName);
//                System.out.println("executing: " + query.getQueryString());
//                for (Object o : query.list()) {
//                    System.out.println("  " + o);
//                }
//            }
//        }
//
//        finally {
//            session.close();
//        }
//    }
}
