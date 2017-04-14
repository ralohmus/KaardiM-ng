import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public class Kaardid extends Parent {
    /**
     * Siit teen endale kaartide mastid, mida kasutama hakkan
     */
    enum Mast {
        RUUTU,
        ÄRTU,
        RISTI,
        POTI
    }

    /**
     * Siit teen endale kaartide numbrid, mida kasutama hakkan. Selle osa võtsin ka internetist.
     */
    enum Numbrid {
        KAKS(2), KOLM(3), NELI(4), VIIS(5), KUUS(6), SEITSE(7), KAHEKSA(8), ÜHEKSA(9), KÜMME(10), POISS(10), EMAND(10), KUNINGAS(10), ÄSS(11);
        final int value;

        private Numbrid(int value) {
            this.value = value;
        }
    }


    public final Numbrid numbrid;

    public final Mast mast;

    public final int väärtus;


    public Kaardid(Numbrid numbrid, Mast mast) {
        this.mast = mast;
        this.numbrid = numbrid;
        this.väärtus = numbrid.value;



        Rectangle ristkülik = new Rectangle(80, 100);
        ristkülik.setArcHeight(5);
        ristkülik.setArcWidth(5);
        ristkülik.setFill(Color.WHITE);

        Text text = new Text(toString());
        text.setWrappingWidth(80);

        getChildren().add(new StackPane(ristkülik, text));
    }


    public String toString() {
        return mast.toString() + " " + numbrid.toString();
    }
}
