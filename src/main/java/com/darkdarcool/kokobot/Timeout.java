package com.darkdarcool.kokobot;


import java.util.HashMap;

public class Timeout {
    private HashMap<String, HashMap<String, Long>> timeouts;
    private String cmdName = "";
    private String user = "";

    public Timeout() {
        timeouts = new HashMap<String, HashMap<String, Long>>();
    }
    public void setCmdName(String cmdName, String user) {
        this.cmdName = cmdName;
        this.user = user;
    }

    public void add(String user, String cmdName, long timeout) {
        if (!timeouts.containsKey(user)) {
            timeouts.put(user, new HashMap<String, Long>());
        }
        timeouts.get(user).put(cmdName, System.currentTimeMillis() + timeout);
    }

    public boolean hasTimeout() {

        if (timeouts.containsKey(user))  return timeouts.get(user).containsKey(cmdName);
        return false;
    }

    public boolean isTimeout() {
        return timeouts.get(user).get(cmdName) < System.currentTimeMillis();
    }

    public void remove() {
        timeouts.get(user).remove(cmdName);
    }

    public int timeLeft(int length) {
        length = length * 1000;
        int timeLeft = (int) (timeouts.get(user).get(cmdName) - System.currentTimeMillis()) * -1;
        if (timeLeft >= length) {
            return 0;
        }
        return length - timeLeft;
    }
    public long timeLeftLong(String user, String cmdName) {
        return timeouts.get(user).get(cmdName) - System.currentTimeMillis();
    }
    public void removeTimeoutIfActive() {
        if (hasTimeout()) {
            remove();
        }
    }
}
