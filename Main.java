import client.ClientController;
import client.ClientGUI;
import server.ServerController;
import server.ServerGUI;
import server.logFile;

public class Main {
    public static void main (String[] args) {
        ServerController serverController = new ServerController(new ServerGUI(), new logFile());

        ClientGUI clientGUI1 = new ClientGUI();
        ClientGUI clientGUI2 = new ClientGUI();
        ClientController clientController1 = new ClientController();
        clientController1.setClientView(clientGUI1);
        clientController1.setServer(serverController);
        clientGUI1.setClient(clientController1);
        ClientController clientController2 = new ClientController();
        clientController2.setClientView(clientGUI2);
        clientController2.setServer(serverController);
        clientGUI2.setClient(clientController2);
    }
}
