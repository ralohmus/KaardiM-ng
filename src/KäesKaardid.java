import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;



/**
 * Mänigja käes olevad kaardid
 */
public class KäesKaardid {
    // Selle võtsin internetist
    private ObservableList<Node> kaardids;
    // selle võtsin internetist
    private SimpleIntegerProperty väärtus = new SimpleIntegerProperty(0);

    private int ässad = 0;

    /**
     * Selle võtsin internetist
     * @param kaardids
     */
    public KäesKaardid(ObservableList<Node> kaardids) {
        this.kaardids = kaardids;

    }


    public void võtaKaart(Kaardid kaardid) {
        kaardids.add(kaardid);

        if (kaardid.numbrid == Kaardid.Numbrid.ÄSS) {
            ässad++;
        }
        if (väärtus.get() + kaardid.väärtus > 21 && ässad > 0) {
            väärtus.set(väärtus.get() + kaardid.väärtus - 10); // ässa väärtus on 1 mitte 11
            ässad--;
        } else {
            väärtus.set(väärtus.get() + kaardid.väärtus);
        }


    }


    public void teeTühjaksKäed() {
        kaardids.clear();
        väärtus.set(0);
        ässad = 0;


    }

    // Selle võtsininternetist
    public SimpleIntegerProperty väärtusPoperty() {
        return väärtus;

    }


}