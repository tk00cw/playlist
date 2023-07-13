package ss;

import java.util.*;

public class Tt {
    private int capacity;
    private Map<String, List<String>> store;
    private List<SongUserPair> recentlyPlayed;

    public Tt(int capacity) {
        this.capacity = capacity;
        this.store = new HashMap<>();
        this.recentlyPlayed = new LinkedList<>();
    }

    public void addSong(String user, String song) {
        if (!store.containsKey(user)) {
            store.put(user, new LinkedList<>());
        }

        List<String> userSongs = store.get(user);

        if (userSongs.size() == capacity) {
            SongUserPair oldestSong = recentlyPlayed.remove(0);
            store.get(oldestSong.getUser()).remove(oldestSong.getSong());
        }

        userSongs.add(song);
        recentlyPlayed.add(new SongUserPair(song, user));
    }

    public List<String> getRecentlyPlayed(String user) {
        List<String> userSongs = store.get(user);
        if (userSongs == null) {
            return Collections.emptyList();
        }

        List<String> recentlyPlayedSongs = new ArrayList<>();
        for (SongUserPair pair : recentlyPlayed) {
            if (pair.getUser().equals(user)) {
                recentlyPlayedSongs.add(pair.getSong());
            }
        }

        return recentlyPlayedSongs;
    }

    private static class SongUserPair {
        private String song;
        private String user;

        public SongUserPair(String song, String user) {
            this.song = song;
            this.user = user;
        }

        public String getSong() {
            return song;
        }

        public String getUser() {
            return user;
        }
    }

    // Example usage
    public static void main(String[] args) {
        Tt songStore = new Tt(3);

        songStore.addSong("user1", "S1");
        songStore.addSong("user1", "S2");
        songStore.addSong("user1", "S3");
        System.out.println(songStore.getRecentlyPlayed("user1"));  // Output: [S1, S2, S3]

        songStore.addSong("user1", "S4");
        System.out.println(songStore.getRecentlyPlayed("user1"));  // Output: [S2, S3, S4]

        songStore.addSong("user1", "S2");
        System.out.println(songStore.getRecentlyPlayed("user1"));  // Output: [S3, S4, S2]

        songStore.addSong("user1", "S1");
        System.out.println(songStore.getRecentlyPlayed("user1"));  // Output: [S4, S2, S1]
    }
}
