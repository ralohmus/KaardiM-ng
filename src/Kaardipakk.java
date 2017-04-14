/**
 * Kaardipaki moodustamine ja kaarti tõmbamiseks vajalik meetod
 */
public class Kaardipakk {
    private Kaardid[] kaardids = new Kaardid[52];


    public Kaardipakk() {
        teeKaardiPakk();
    }

    /**
     * See meetod teeb kaardi pakki
     */
    public final void teeKaardiPakk() {
        int c = 0;
        for (Kaardid.Numbrid numbrid : Kaardid.Numbrid.values()) {
            for (Kaardid.Mast mast : Kaardid.Mast.values()) {
                kaardids[c] = new Kaardid(numbrid, mast);
                c++;
            }
        }
    }

    /**
     * See meethod jagab järgmise kaardi
     *
     * @return Tagastab ühe kaardi kaardipakist
     */
    public Kaardid jagaKaart() {
        Kaardid kaart = null;
        while (kaart == null) {
            int kaardiJärjekorraNumber = suvalineArv(0, 52);
            kaart = kaardids[kaardiJärjekorraNumber];
            kaardids[kaardiJärjekorraNumber] = null;
        }
        return kaart;

    }

    public static int suvalineArv(int min, int max) {
        int vahemik = max - min + 1;
        return min + (int) (Math.random() * vahemik);
    }
}
