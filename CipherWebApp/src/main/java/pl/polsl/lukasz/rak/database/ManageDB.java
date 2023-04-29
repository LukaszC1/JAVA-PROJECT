/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lukasz.rak.database;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import pl.polsl.lukasz.rak.entities.HistoryEntity;
/**
 * Class used to add objects to the database and retrieve them.
 *
 * @author Łukasz Rak
 * @version FINAL-5
 */
public class ManageDB {
    
    /**The entity manager which establishes the connection to the database.*/
   private EntityManager em;
    
   /**The constructor of ManageDB class.*/
   public ManageDB(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pl.polsl.lukasz.rak_JPA_jar_1.0-SNAPSHOTPU");
        EntityManager entityManager = emf.createEntityManager();
        this.em = entityManager;
        //connecting to the database
    }
    
     /**
     * Add an entity object to the database.
     *
     * @param entity the HistoryEntity object
     */
    public void add(HistoryEntity entity)
    {
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        }
        catch(PersistenceException e) {
            em.getTransaction().rollback();
        }
    }

    /**
     * Method which reads the data from the database and returns it as a list.
     *
     */
    public List<HistoryEntity> read()
    {
        try {
            List<HistoryEntity> history = em.createQuery("SELECT h FROM HistoryEntity h").getResultList();
            return history;
        }
        catch(PersistenceException e) {
            em.getTransaction().rollback();
        }
        return null;
    }

     /**
     * Function which closes the entity manager.
     *
     */
    public void closeConnection() {
        em.close();
    }
}
