public class TypesOfPlayer {
    MinMaxPlayer minmaxplayer;
    HeuristicPlayer heuristicplayer;
    Player player;

    TypesOfPlayer(int playerId, String name, Board board, int score, int x, int y)
    {
        this.minmaxplayer = new MinMaxPlayer(playerId, name, board, score, x, y);
        this.heuristicplayer = new HeuristicPlayer(playerId, name, board, score, x, y);
        this.player = new Player(playerId, name, board, score, x, y);
    }

    MinMaxPlayer getMinMaxPlayer()
    {
        return minmaxplayer;
    }

    HeuristicPlayer getHeuristicPlayer()
    {
        return heuristicplayer;
    }

    Player getPlayer()
    {
        return player;
    }
}
