package client;

import server.ServerController;

/**
 * класс содержащий логику работы клиента
 *
 * @clientView абстракция графического интерфейса
 * @server объект для связи с сервером
 */
public class ClientController {
    private boolean connected;
    private String name;
    private ClientView clientView;
    private ServerController server;

    //сеттеры
    public void setClientView(ClientView clientView) {
        this.clientView = clientView;
    }

    public void setServer(ServerController server) {
        this.server = server;
    }

    /**
     * Метод попытки подключения к серверу. Вызывается из GUI
     * @param name имя пользователя, которым будем подписывать исходящие сообщения
     * @return ответ от сервера. true, если прошли авторизацию
     */
    public boolean connectToServer(String name) {
        this.name = name;

        if (server.connectUser(this)){
            showOnWindow("Вы успешно подключились!\n");
            connected = true;
            String log = server.getHistory();
            if (log != null){
                showOnWindow(log);
            }
            return true;
        } else {
            showOnWindow("Подключение не удалось");
            return false;
        }
    }

    /**
     * Метод отключения от сервера инициализированное сервером
     */
    public void disconnectedFromServer() {
        if (connected) {
            connected = false;
            clientView.disconnectedFromServer();
            showOnWindow("Вы были отключены от сервера!");
        }
    }

    /**
     * Метод отключения от сервера инициализированное клиентом (например закрыто GUI)
     */
    public void disconnectFromServer() {
        server.disconnectUser(this);
    }

    /**
     * Метод, с помощью которого сервер передает клиенту сообщения
     * @param text текст переданный от сервера
     */
    public void answerFromServer(String text) {
        showOnWindow(text);
    }

    /**
     * Метод для передачи сообщения на сервер
     * @param text текст передаваемого сообщения
     */
    public void message(String text) {
        if (connected) {
            if (!text.isEmpty()) {
                server.message(name + ": " + text);
            }
        } else {
            showOnWindow("Нет подключения к серверу");
        }
    }

    /**
     * Метод вывода сообщения на GUI
     * @param text текст, который требуется вывести на экран
     */
    private void showOnWindow(String text) {
        clientView.showMessage(text);
    }

    public String getName() {
        return name;
    }
}