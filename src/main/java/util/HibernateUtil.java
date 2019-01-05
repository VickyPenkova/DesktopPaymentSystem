package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Using Hibernate for object-relational mapping map objects
 * to an RDBMS and to implement the object-oriented programming
 * concepts in a relational database.
 * Using Entity Manager - a model borrowed from the traditional
 * JDBC frameworks i.e. making it easier for the developers to perform
 * the basic database operations with a very little code.
 * The Class HibernateUtil has the configuration for persistence provider.
 * The mapping between Java objects and database tables is defined
 * via persistence metadata. The JPA provider will use the persistence
 * metadata information to perform the correct database operations.
 * This class will read the configuration in our persistence.xml file
 */
public class HibernateUtil {

   private static EntityManagerFactory emFactoryObj;
   private static String PERSISTENCE_UNIT_NAME = "persistenceUnit";
   
   static {
      emFactoryObj = Persistence
            .createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
   }

   /**
    * This Method Is Used To Retrieve The 'EntityManager' Object
    */

   public static EntityManager getEntityManager() {
      return emFactoryObj.createEntityManager();
   }
}
