package sample;

import entities.ShopEntity;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.List;
import java.util.Properties;

public class Main extends Application {
    // Create the SessionFactory when you start the application.
    private static final SessionFactory SESSION_FACTORY;

    /**
     * Initialize the SessionFactory instance.
     */
    static {
        // Create a Configuration object.
        Configuration config = new Configuration();
        // Configure using the application resource named hibernate.cfg.xml.
        config.configure();
        // Extract the properties from the configuration file.
        Properties prop = config.getProperties();

        // Create StandardServiceRegistryBuilder using the properties.
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(prop);

        // Build a ServiceRegistry
        ServiceRegistry registry = builder.build();

        // Create the SessionFactory using the ServiceRegistry
        SESSION_FACTORY = config.buildSessionFactory(registry);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        readAll();
        launch(args);
    }

    public static List<ShopEntity> readAll() {
        List<ShopEntity> students = null;

        // Create a session
        Session session = SESSION_FACTORY.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            List shops = session.createSQLQuery("SELECT * FROM DesktopPaymentSystem.Shop where shopId=1").list();
            transaction.commit();
            System.out.println(shops);
        }
        catch (HibernateException ex) {

            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }

            // Print the Exception
            ex.printStackTrace();
        } finally {
            // Close the session
            session.close();
        }
        return students;
    }
}
