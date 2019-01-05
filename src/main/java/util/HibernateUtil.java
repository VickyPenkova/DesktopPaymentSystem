package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

//This class will read the configuration in our persistence.xml file
public class HibernateUtil {

   private static EntityManagerFactory emFactoryObj;
   private static String PERSISTENCE_UNIT_NAME = "persistenceUnit";
   //Entity Manager is a model borrowed from the traditional
   //JDBC frameworks i.e. making it easier for the developers to perform the basic database operations with a very little code

   //Persistence Context is the set of entity instances where for any persistence entity identity,
   //there is a unique entity instance. The lifecycle of entity instances is managed
   //within the persistence context using the EntityManager. We can detach and merge the entity instances within a persistence context

   static {
      emFactoryObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
   }

   // This Method Is Used To Retrieve The 'EntityManager' Object
   public static EntityManager getEntityManager() {
      return emFactoryObj.createEntityManager();
   }
}
