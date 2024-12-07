package Lights;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class TrafficLightSimulator extends Application {
    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();

        // Créer les feux de circulation
        TrafficLight northLight = createTrafficLight();
        TrafficLight southLight = createTrafficLight();
        TrafficLight eastLight = createTrafficLight();
        TrafficLight westLight = createTrafficLight();

        // Ajouter les feux à l'interface (Nord, Sud, Est, Ouest)
        grid.add(createVBoxForLight(northLight), 1, 0); // Nord
        grid.add(createVBoxForLight(eastLight), 2, 1);  // Est
        grid.add(createVBoxForLight(southLight), 1, 2); // Sud
        grid.add(createVBoxForLight(westLight), 0, 1);  // Ouest

        // Créer le contrôleur et démarrer le thread
        TrafficLightController controller = new TrafficLightController(northLight, southLight, eastLight, westLight);
        Thread thread = new Thread(controller);
        thread.setDaemon(true); // Arrête le thread lorsque l'application se ferme
        thread.start();

        // Afficher la scène
        Scene scene = new Scene(grid, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Traffic Light Simulator");
        primaryStage.show();
    }

    private TrafficLight createTrafficLight() {
        Circle red = new Circle(20, Color.GRAY);
        Circle yellow = new Circle(20, Color.GRAY);
        Circle green = new Circle(20, Color.GRAY);
        return new TrafficLight(red, yellow, green);
    }

    private VBox createVBoxForLight(TrafficLight light) {
        VBox box = new VBox();
        box.getChildren().addAll(light.redLight, light.yellowLight, light.greenLight);
        box.setSpacing(10);
        return box;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
