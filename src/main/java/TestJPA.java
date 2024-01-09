import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Centre;
import model.Cos;
import model.Localitat;

import java.util.List;

public class TestJPA {
    EntityManagerFactory emf;
    private String persistenceUnit = null;
    
    public TestJPA(String persistenceUnit) {
        if (persistenceUnit == null || persistenceUnit.isEmpty() || persistenceUnit.isBlank()) {
            throw new IllegalArgumentException("Persistence unit must be a non-empty string");
        }
        
        this.persistenceUnit = persistenceUnit;
        emf = Persistence.createEntityManagerFactory(persistenceUnit);
    }
    
    public String getPersistenceUnit() {
        return persistenceUnit;
    }
    
    Centre getCentre(String idCentre) {
        var em = emf.createEntityManager();
        try {
            return em.find(model.Centre.class, idCentre);
        } finally {
            em.close();
        }
    }
    
    public void modifyAddressAspirant(String nif, String address) {
        var em = emf.createEntityManager();
        
        em.getTransaction().begin();
        try {
            var aspirant = em.find(model.Aspirant.class, nif);
            aspirant.setAdreca(address);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    public void updateCentre(Centre centre) {
        var em = emf.createEntityManager();
        
        em.getTransaction().begin();
        try {
            em.merge(centre);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    public void createCentre(String idCentre, String nomCentre, String idLocalitat) {
        var em = emf.createEntityManager();
        
        em.getTransaction().begin();
        try {
            var centre = new Centre();
            centre.setIdCentre(idCentre);
            centre.setNomCentre(nomCentre);
            var locality = new Localitat();
            locality.setIdLocalitat(idLocalitat);
            centre.setLocalitat(locality);
            em.persist(centre);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    public void delete(Object object) {
        var em = emf.createEntityManager();
        
        em.getTransaction().begin();
        try {
            em.remove(em.merge(object));
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    public List<Cos> getCossos() {
        var em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM Cos c", Cos.class)
                .getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Cos> getCossosNamedQuery() {
        var em = emf.createEntityManager();
        
        try {
            return em.createNamedQuery("Cos.findAll", Cos.class).getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Centre> getCenterByLocality(Localitat localitat) {
        var em = emf.createEntityManager();
        
        try {
            return em.createQuery("SELECT c FROM Centre c WHERE c.localitat = :localitat", Centre.class)
                .setParameter("localitat", localitat)
                .getResultList();
        } finally {
            em.close();
        }
    }
    
    public void close() {
        emf.close();
    }
}
