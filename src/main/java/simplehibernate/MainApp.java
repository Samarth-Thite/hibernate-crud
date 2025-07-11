package simplehibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class MainApp {

    public static void main(String[] args) {

     
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

      
            session.createNativeQuery("INSERT INTO regions (region_name) VALUES ('Asia')").executeUpdate();
            System.out.println("✅ Region inserted.");

        
            session.createNativeQuery("INSERT INTO countries (country_id, country_name, region_id) VALUES ('IN', 'India', 1)").executeUpdate();
            System.out.println("✅ Country inserted.");

       
            @SuppressWarnings("unchecked")
			List<Object[]> countries = session.createNativeQuery("SELECT * FROM countries").list();
            System.out.println("🔷 Countries Table:");
            for (Object[] c : countries) {
                System.out.println("ID: " + c[0] + ", Name: " + c[1] + ", Region ID: " + c[2]);
            }

         
            session.createNativeQuery("UPDATE countries SET country_name = 'Bharat' WHERE country_id = 'IN'").executeUpdate();
            System.out.println("✅ Country updated to Bharat.");

            // 👉 DELETE country
            session.createNativeQuery("DELETE FROM countries WHERE country_id = 'IN'").executeUpdate();
            System.out.println("✅ Country deleted.");

            tx.commit();
            System.out.println("🎯 All CRUD operations performed successfully.");

        } catch (Exception e) {
            if (tx != null) {
				tx.rollback();
			}
            e.printStackTrace();
        } finally {
            session.close();
            factory.close();
        }
    }
}
