package ca.courseplanner.model.watcher;

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
        Watcher watcher = null;
        int actualIndex = id - 1;
        if (actualIndex >= 0 && actualIndex < watchers.size()){
            watcher = watchers.get(actualIndex);
        }
        return watcher;
    }

    public void removeWatcherById(int id) {
        int actualIndex = id - 1;
        if (actualIndex < 0 || actualIndex > watchers.size() - 1) {
            throw new IllegalArgumentException();
        }
        watchers.remove(actualIndex);
    }
}
