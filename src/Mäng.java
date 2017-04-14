import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Mäng extends Application {
    TextField textField;

    private Kaardipakk kaardipakk = new Kaardipakk();

    private KäesKaardid diiler, mängija;


    private Text text = new Text();


    private SimpleBooleanProperty jätkamine = new SimpleBooleanProperty(false);

    private HBox diileriKaardid = new HBox(20);
    private HBox mängijaKaardid = new HBox(20);


    private Parent põhiMeetod(Button tagasi) {
        int panus;
        diiler = new KäesKaardid(diileriKaardid.getChildren());
        mängija = new KäesKaardid(mängijaKaardid.getChildren());

        Pane pane = new Pane();
        pane.setPrefSize(800, 600);


        Region taust = new Region();
        taust.setPrefSize(800, 600);
        taust.setStyle("-fx-background-color: rgba(0, 0, 0, 1)");


        VBox layout = new VBox(5);

        Rectangle ülemine = new Rectangle(800, 450);

        ülemine.setFill(Color.GREEN);
        Rectangle alumine = new Rectangle(800, 150);

        alumine.setFill(Color.BLACK);

        // ülemine
        VBox ülemineV = new VBox(50);
        ülemineV.setAlignment(Pos.TOP_CENTER);

        Text diilriPunktid = new Text("Diiler: ");
        Text mängijaPunktid = new Text("Mängija: ");

        ülemineV.getChildren().addAll(diilriPunktid, diileriKaardid, text, mängijaKaardid, mängijaPunktid);

        // alumine
        Label raha = new Label("Raha: ");
        raha.setFont(Font.font(16));
        VBox alumineV = new VBox(raha);
        alumineV.setAlignment(Pos.CENTER);


        TextField textField = new TextField();
        textField.setPromptText("Siseta panus siia");
        Button mängi = new Button("MÄNGI!");
        Button võta = new Button("VÕTA");
        Button loobu = new Button("LOOBU");


        HBox NuppudeHBox = new HBox(15, mängi, võta, loobu, textField, tagasi);
        NuppudeHBox.setAlignment(Pos.CENTER);

        alumineV.getChildren().addAll(NuppudeHBox);

        // Lisan Stackid layoudile

        layout.getChildren().addAll(new StackPane(ülemine, ülemineV), new StackPane(alumine, alumineV));
        pane.getChildren().addAll(taust, layout);

        // bindin nupud
        // Need on võetud internetist
        mängi.disableProperty().bind(jätkamine);
        võta.disableProperty().bind(jätkamine.not());
        loobu.disableProperty().bind(jätkamine.not());
        mängijaPunktid.textProperty().bind(new SimpleStringProperty("Player: ").concat(mängija.väärtusPoperty().asString()));
        diilriPunktid.textProperty().bind(new SimpleStringProperty("Dealer: ").concat(diiler.väärtusPoperty().asString()));
        mängija.väärtusPoperty().addListener((obs, vana, uus) -> {
            if (uus.intValue() >= 21) {
                lopetaMäng();
            }
        });
        diiler.väärtusPoperty().addListener((obs, vana, uus) -> {
            if (uus.intValue() >= 21) {
                lopetaMäng();
            }
        });

        // intstaleerin nuppud

        mängi.setOnAction(event -> {
            alustaUutMängu();
            //panus = Integer.parseInt(textField.getText());
            textField.clear();
        });

        võta.setOnAction(event -> {
            mängija.võtaKaart(kaardipakk.jagaKaart());
        });

        loobu.setOnAction(event -> {
            while (diiler.väärtusPoperty().get() < 17) {
                diiler.võtaKaart(kaardipakk.jagaKaart());
            }

            lopetaMäng();
        });

        return pane;
    }


    private void alustaUutMängu() {
        jätkamine.set(true);
        text.setText("");

        kaardipakk.teeKaardiPakk();

        diiler.teeTühjaksKäed();
        mängija.teeTühjaksKäed();

        diiler.võtaKaart(kaardipakk.jagaKaart());
        diiler.võtaKaart(kaardipakk.jagaKaart());
        mängija.võtaKaart(kaardipakk.jagaKaart());
        mängija.võtaKaart(kaardipakk.jagaKaart());
    }


    private void lopetaMäng() {
        jätkamine.set(false);

        // see on võetud internetist
        int diilriVäärtus = diiler.väärtusPoperty().get();
        int mängijaväärtus = mängija.väärtusPoperty().get();
        String s = "Exceptional case: d: " + diilriVäärtus + " p: " + mängijaväärtus;


        if (diilriVäärtus == 21 || mängijaväärtus > 21 || diilriVäärtus == mängijaväärtus
                || (diilriVäärtus < 21 && diilriVäärtus > mängijaväärtus)) {

            s = "DILLER";
        } else if (mängijaväärtus == 21 || diilriVäärtus > 21 || mängijaväärtus > diilriVäärtus) {
            s = "MÄNGIJA";
        }

        text.setText(s + " VÕITIS");
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Button tagasi = new Button("TAGASI");


        Label label = new Label("Tere tulemast mängude kogumikku");
        label.setFont(Font.font(30));
        label.setTextFill(Color.BLACK);
        Button button1 = new Button("Välju");
        button1.setOnAction(event -> {
            System.exit(0);
        });

        Button button = new Button("BLACKJACK");
        tagasi.setOnAction(event -> {
            primaryStage.setScene(new Scene(esimeseleStseenile(label, button, button1), 800, 600, Color.GREEN));
        });
        button.setOnAction(event -> {
            primaryStage.setScene(new Scene(põhiMeetod(tagasi)));
        });


        primaryStage.setScene(new Scene(esimeseleStseenile(label, button, button1), 800, 600, Color.GREEN));


        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Mängude kogumik");
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }


    public GridPane esimeseleStseenile(Label label, Button button, Button button1) {
        GridPane root = new GridPane();
        root.setStyle("-fx-background-color: transparent;");


        root.setHgap(8);
        root.setVgap(8);

        ColumnConstraints cons1 = new ColumnConstraints();
        cons1.setHgrow(Priority.NEVER);
        root.getColumnConstraints().add(cons1);

        ColumnConstraints cons2 = new ColumnConstraints();
        cons2.setHgrow(Priority.ALWAYS);

        root.getColumnConstraints().addAll(cons1, cons2);

        RowConstraints rcons1 = new RowConstraints();
        rcons1.setVgrow(Priority.NEVER);

        RowConstraints rcons2 = new RowConstraints();
        rcons2.setVgrow(Priority.ALWAYS);

        root.getRowConstraints().addAll(rcons1, rcons2);
        //root.setGridLinesVisible(true);
        root.add(label, 2, 0);
        root.setHalignment(label, HPos.CENTER);
        root.add(button, 2, 1);
        root.setHalignment(button, HPos.CENTER);
        root.add(button1, 2, 8);
        root.setHalignment(button1, HPos.RIGHT);
        return root;
    }

}
