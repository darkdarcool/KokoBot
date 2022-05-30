package me.darkdarcool.kokobot;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private String id;
    private DB db;

    /**
     * Creates a class based on a user's id.
     * Will not throw an exception if the user does not exist,
     * instead you should call the userExists method first to check
     * @param _id
     * @param _db
     */
    public User(String _id, DB _db) {
        id = _id;
        db = _db;
    }

    /**
     * Checks if the user exists in the database.
     * @param shouldCreateOnFail If the user does not exist, should it be created
     * @return boolean
     * @throws Exception
     */
    public boolean userExists(boolean shouldCreateOnFail) throws Exception {
        boolean exists = db.doesExistChild("users", id);
        if (!exists && shouldCreateOnFail) {
            createUser();
            // return true; (Removed because we still need to know if the user existed before)
        }
        return exists;
    }

    /**
     * Creates a user in the database.
     */
    private void createUser() {
        db.addData("users", id, defaultUserData());
    }

    /**
     * Returns the default data for a user.
     * @return
     */
    private HashMap defaultUserData() {
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("id", id);
        data.put("items", new ArrayList<>());
        data.put("coins", 0);
        data.put("isPremium", false); // <- idfk why I added this
        return data;
    }

    /**
     * Gets the users coin count
     * @return int
     */
    public int getCoins() {
        int coins = 0;
        try {
            coins = Integer.parseInt(db.getData("users", id).get("coins").toString());
        } catch (Exception err) {
            err.printStackTrace();
        }
        return coins;
    }

    public void setCoins(int coins) {
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("coins", coins);
        try {
            db.updateData("users", id, data);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public String getID() {
        return id;
    }

}
