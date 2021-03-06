import java.util.Random;

public class RunAll {

  public static void main(String[] args) {
    int port = 8082;

    AmazonsServer server =  new AmazonsServer(port);
    AmazonsClient clientA = new AmazonsClient();
    AmazonsClient clientB = new AmazonsClient();

    clientA.registerListener(new MiniMaxAIClientListenerC("AI C"));
    clientA.registerListener(new GUIListener());

    clientB.registerListener(new MiniMaxAIClientListenerC("AI D"));

    clientA.connect(port);
    clientB.connect(port);

  }
}
