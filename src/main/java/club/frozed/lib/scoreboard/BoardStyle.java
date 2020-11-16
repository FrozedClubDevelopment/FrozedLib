package club.frozed.lib.scoreboard;

/**
 * Created by Ryzeon
 * Project: FrozedHubDeluxe
 * Date: 10/11/2020 @ 13:30
 */

public enum BoardStyle {
    MODERN(false, 1),
    KOHI(true, 15),
    VIPER(true, -1),
    TEAMSHQ(true, 0);

    private int start;

    private boolean descending;

    BoardStyle(boolean descending, int start) {
        this.descending = descending;
        this.start = start;
    }

    public boolean isDescending() {
        return this.descending;
    }

    public int getStart() {
        return this.start;
    }
}
