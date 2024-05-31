package server;

public interface ServerRepository {
    public void saveInRepos(String text);
    public String readFromRepos();
}
