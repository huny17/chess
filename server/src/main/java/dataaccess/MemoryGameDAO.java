package dataaccess;

import model.UserData;

import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{

    private HashMap<String, UserData> games = new HashMap<>();

    @Override
    public void clear(){
        games.clear();
    }
}
