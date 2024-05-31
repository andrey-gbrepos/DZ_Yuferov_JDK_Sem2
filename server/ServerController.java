package server;

import client.ClientController;
import java.util.ArrayList;
import java.util.List;

public class ServerController {
    private boolean work;
    private ServerView serverView;
    private ServerRepository serverRepository;
    private List<ClientController> clientControllers;

    public ServerController(ServerView serverView, ServerRepository serverRepository) {
        this.serverView = serverView;
        this.serverRepository = serverRepository;
        clientControllers = new ArrayList<>();
        serverView.setServerController(this);
    }

    private void showOnWindow(String text) {
        serverView.viewMessage(text + "\n");
    }

    public String getHistory(){
        return serverRepository.readFromRepos();
    }
    public void saveInRepos(String text){
        serverRepository.saveInRepos(text);
    }

    public boolean connectUser(ClientController clientController){
        if (!work){
            return false;
        }
        clientControllers.add(clientController);
        showOnWindow(clientController.getName() + " присоединился к беседе");
        return true;
    }

    public void disconnectUser(ClientController clientController){
        clientControllers.remove(clientController);
        if (clientControllers != null){
            clientController.disconnectFromServer();
            showOnWindow(clientController.getName() + " отключился от беседы.");
        }
    }

    public void startServer(){
        if (work){
            showOnWindow("Сервер уже был запущен.");
        } else {
            work = true;
            showOnWindow("Сервер запущен.");
        }
    }

    public void stopServer(){
        if (!work){
            showOnWindow("Сервер уже был остановлен.");
        } else {
            work = false;
            while (!clientControllers.isEmpty()){
                disconnectUser(clientControllers.get(clientControllers.size()-1));
            }
           showOnWindow("Сервер остановлен.");
        }
    }

    public void message(String text){
        if (!work){
            return;
        }
        showOnWindow(text);
        answerAll(text);
        saveInRepos(text);
    }

    private void answerAll(String text){
        for (ClientController clientController: clientControllers){
            clientController.answerFromServer(text);
        }
    }

}
