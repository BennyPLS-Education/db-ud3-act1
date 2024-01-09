import model.Centre;
import model.Localitat;

public class Main {
    public static void main(String[] args) {
        final var testJPA = new TestJPA("interins-pu");
        var locality = new Localitat();
        locality.setIdLocalitat("070010001");
        var cos = testJPA.getCenterByLocality(locality);
        for (Centre co : cos) {
            System.out.println(co);
        }
    }
}
