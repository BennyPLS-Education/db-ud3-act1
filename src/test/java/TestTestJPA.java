import model.Cos;
import model.Localitat;
import org.junit.jupiter.api.Test;

public class TestTestJPA {
    
    @Test
    public void testConstructor() {
        final var testJPA = getTestJPA();
        assert testJPA.getPersistenceUnit().equals("interins-pu");
    }
    
    @Test
    public void testGetCentre() {
        final var testJPA = getTestJPA();
        var centre = testJPA.getCentre("07001964");
        assert centre.getIdCentre().equals("07001964");
    }
    
    @Test
    public void testModifyAddressAspirant() {
        final var testJPA = getTestJPA();
        testJPA.modifyAddressAspirant("12345678A", "Carrer de la Mar, 1, 07001 Palma");
    }
    
    @Test
    public void testUpdateCentre() {
        final var testJPA = getTestJPA();
        var centre = testJPA.getCentre("07001964");
        centre.setNomCentre("IES Joan Ramis i Ramis (Palma)");
        testJPA.updateCentre(centre);
    }
    
    @Test
    public void testCreateCentre() {
        final var testJPA = getTestJPA();
        testJPA.createCentre("I002", "IES Guillem Sagrera", "07031");
    }
    
    @Test
    public void testDeleteCentre() {
        final var testJPA = getTestJPA();
        var centre = testJPA.getCentre("I002");
        testJPA.delete(centre);
        centre = testJPA.getCentre("I002");
        assert centre == null;
    }
    
    @Test
    public void testGetCossos() {
        final var testJPA = getTestJPA();
        var cos = testJPA.getCossos();
        for (Cos co : cos) {
            System.out.println(co);
        }
    }
    
    @Test
    public void testgetNamedQueryCossos() {
        final var testJPA = getTestJPA();
        var aspirants = testJPA.getCossosNamedQuery();
    }
    
    @Test
    public void testGetCentreByLocality() {
        final var testJPA = getTestJPA();
        var localitat = new Localitat();
        localitat.setIdLocalitat("07001");
        var centres = testJPA.getCenterByLocality(localitat);
    }
    
    private static TestJPA getTestJPA() {
        return new TestJPA("interins-pu");
    }
}
