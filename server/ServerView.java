package server;

public interface ServerView {
    public void initWindow();
    public void viewMessage(String str);
    void setServerController (ServerController serverController);
}
