package game2048;

public class Helper {
    public static void main(String[] args) {
        Board board = new Board(4);
        Tile t = board.tile(3, 0);
        System.out.println(board.move(3, 3, t));
    }
}
