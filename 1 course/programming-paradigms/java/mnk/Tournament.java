package mnk;

class Tournament {
    private int[][] stat;
    private final int currentplayer1;
    private final int currentplayer2;
    private final int result;

    Tournament(int currentplayer1, int currentplayer2, int[][] stat, int result) {
        this.stat = stat;
        this.result = result;
        this.currentplayer1 = currentplayer1;
        this.currentplayer2 = currentplayer2;
    }

    void addInStat() {
        switch (result) {
            case (0):
                stat[currentplayer1][1]++;
                stat[currentplayer1][3]++;
                stat[currentplayer2][1]++;
                stat[currentplayer2][3]++;
                break;
            case (1):
                stat[currentplayer1][0]++;
                stat[currentplayer1][3] += 3;
                stat[currentplayer2][2]++;
                break;
            case (2):
                stat[currentplayer2][0]++;
                stat[currentplayer2][3] += 3;
                stat[currentplayer1][2]++;
                break;
            case(-1):
                break;
        }
    }
}
