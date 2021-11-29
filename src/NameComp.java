import java.util.Comparator;

public class NameComp implements Comparator<ReceptData> {
    public int compare(ReceptData r1, ReceptData r2){
        return r1.getName().toLowerCase().compareTo(r2.getName().toLowerCase());
    }
}
