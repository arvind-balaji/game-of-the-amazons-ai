public class RunAll {

  public static void main(String[] args) {
    int port = 8082;

    new AmazonsServer(port);

    AmazonsClient clientA = new AmazonsClient();
    AmazonsClient clientB = new AmazonsClient();

    clientA.registerListener(new MyAIClientListener("AI A"));
    clientA.registerListener(new GUIListener());

    clientB.registerListener(new MyAIClientListenerB("AI B"));

    clientA.connect(port);
    clientB.connect(port);
  }
}
