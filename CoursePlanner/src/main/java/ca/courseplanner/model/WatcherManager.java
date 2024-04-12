package ca.courseplanner.model;

import java.util.ArrayList;
import java.util.List;

public class WatcherManager {
    private List<Watcher> watchers = new ArrayList<>();

    public void addWatcher(Watcher watcher) {
        watchers.add(watcher);
    }

    public List<Watcher> getWatchers() {
        return watchers;
    }

    public Watcher getWatcherById(int id) {
        return watchers.get(id - 1);
    }

    public void removeWatcherById(int id) {
        watchers.remove(id - 1);
    }
}
