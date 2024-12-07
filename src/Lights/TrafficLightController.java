package Lights;

import javafx.application.Platform;


public class TrafficLightController implements Runnable {
    private final TrafficLight northLight;
    private final TrafficLight southLight;
    private final TrafficLight eastLight;
    private final TrafficLight westLight;
    
    // Verrou pour synchroniser l'état de tous les feux
    private final Object lock = new Object();

    public TrafficLightController(TrafficLight northLight, TrafficLight southLight,
                                  TrafficLight eastLight, TrafficLight westLight) {
        this.northLight = northLight;
        this.southLight = southLight;
        this.eastLight = eastLight;
        this.westLight = westLight;
    }

    @Override
    public void run() {
        while (true) {
            try {
                synchronized (lock) {
                    // Étape 1 : Nord-Sud verts, Est-Ouest rouges
                    updateLights(northLight, southLight, TrafficLight.State.GREEN);
                    updateLights(eastLight, westLight, TrafficLight.State.RED);
                    lock.wait(5000); // Attendre 5 secondes avant de passer à la suite

                    // Étape 2 : Nord-Sud jaunes
                    updateLights(northLight, southLight, TrafficLight.State.YELLOW);
                    lock.wait(2000); // Attendre 2 secondes

                    // Étape 3 : Nord-Sud rouges, Est-Ouest verts
                    updateLights(northLight, southLight, TrafficLight.State.RED);
                    updateLights(eastLight, westLight, TrafficLight.State.GREEN);
                    lock.wait(5000); // Attendre 5 secondes

                    // Étape 4 : Est-Ouest jaunes
                    updateLights(eastLight, westLight, TrafficLight.State.YELLOW);
                    lock.wait(2000); // Attendre 2 secondes

                    // **Transition immédiate** vers tout rouge sans délai
                    updateLights(northLight, southLight, TrafficLight.State.RED);
                    updateLights(eastLight, westLight, TrafficLight.State.RED);
                    
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }


    // Méthode pour mettre à jour l'état des feux de circulation
    private void updateLights(TrafficLight light1, TrafficLight light2, TrafficLight.State state) {
        Platform.runLater(() -> {
            light1.setState(state);
            light2.setState(state);
        });
    }
}
