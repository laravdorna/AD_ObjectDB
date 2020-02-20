/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg1.obj_db;

import javax.persistence.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        // Open a database connection
        // (create a new database if it doesn't exist yet):
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("$objectdb/db/p3.odb");
        EntityManager em = emf.createEntityManager();

        // Store 10 Point objects in the database:
        em.getTransaction().begin();
        for (int i = 0; i < 10; i++) {
            Point p = new Point(i, i);
            em.persist(p);
        }
        em.getTransaction().commit();

        // Find the number of Point objects in the database:
        Query q1 = em.createQuery("SELECT COUNT(p) FROM Point p");
        System.out.println("Total Points: " + q1.getSingleResult());

        // Find the average X value:
        Query q2 = em.createQuery("SELECT AVG(p.x) FROM Point p");
        System.out.println("Average X: " + q2.getSingleResult());

        // Retrieve all the Point objects from the database:
        TypedQuery<Point> query
                = em.createQuery("SELECT p FROM Point p", Point.class);
        List<Point> results = query.getResultList();
        for (Point p : results) {
            System.out.println(p);
        }

        //modificar un objeto
        System.out.println("MODIFICAR el objeto 1:");
        em.getTransaction().begin();
        Query q3 = em.createQuery("SELECT p FROM Point p WHERE id=1");
        System.out.println(" antes: " + q3.getSingleResult());
        Point p = (Point) q3.getSingleResult();

        p.setX(100);
        System.out.println(" despues: " + q3.getSingleResult());
        em.getTransaction().commit();

        //modificar todos los  objetos:
          System.out.println("MODIFICAR todos los objetos:");
        em.getTransaction().begin();
        Query Q4 = em.createQuery(
                "UPDATE Point SET x = x+2, y = y+1");
        int updateCount = Q4.executeUpdate();
        em.getTransaction().commit();
        
        // Retrieve all the Point objects from the database:
        TypedQuery<Point> query5
                = em.createQuery("SELECT p FROM Point p", Point.class);
        List<Point> results5 = query5.getResultList();
        for (Point a : results5) {
            System.out.println(a);
        }
        
        
        // Close the database connection:
        em.close();
        emf.close();
    }
}
