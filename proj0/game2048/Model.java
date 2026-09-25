package game2048;

import java.util.Formatter;
import java.util.Observable;


/** 2048 游戏的状态。
 *  @author TODO：在此填写你的姓名
 */
public class Model extends Observable {
    /** 棋盘当前的内容。 */
    private Board board;
    /** 当前分数。 */
    private int score;
    /** 到目前为止的最高分；游戏结束时更新。 */
    private int maxScore;
    /** 当且仅当游戏已结束时为 true。 */
    private boolean gameOver;

    /* 坐标系：棋盘的第 C 列、第 R 行（第 0 行、第 0 列位于棋盘左下角）
     * 对应 board.tile(c, r)。请注意！其用法类似于 (x, y) 坐标。
     */

    /** 方块的最大数值。 */
    public static final int MAX_PIECE = 2048;

    /** 创建一个棋盘边长为 SIZE、没有方块且分数为 0 的 2048 游戏。 */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** 创建一个新的 2048 游戏，其中 RAWVALUES 保存各方块的数值
     * （0 表示空位）。VALUES 按 (row, col) 索引，(0, 0) 对应左下角。
     * 仅用于测试。 */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** 返回 (COL, ROW) 处当前的 Tile，其中 0 <= ROW < size()、
     * 0 <= COL < size()。若该位置没有方块，则返回 null。
     * 此方法用于测试，应标记为废弃并移除。
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** 返回棋盘每边的格子数。
     * 此方法用于测试，应标记为废弃并移除。 */
    public int size() {
        return board.size();
    }

    /** 当且仅当游戏结束时返回 true（已无合法移动，或棋盘上出现数值为 2048 的方块）。 */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** 返回当前分数。 */
    public int score() {
        return score;
    }

    /** 返回当前游戏最高分（在游戏结束时更新）。 */
    public int maxScore() {
        return maxScore;
    }

    /** 清空棋盘并重置分数。 */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** 将 TILE 添加到棋盘；该位置当前必须没有 Tile。 */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** 向 SIDE 方向倾斜棋盘。当且仅当棋盘发生变化时返回 true。
     *
     * 1. 若沿移动方向相邻的两个 Tile 数值相同，则将它们合并为一个数值为原来
     *    两倍的 Tile，并将这个新数值加到 score 实例变量中。
     * 2. 一次倾斜中，由合并产生的方块不会再次合并。因此每次移动时，每个方块
     *    最多只会参与一次合并（也可能不参与）。
     * 3. 若沿移动方向连续三个方块数值相同，则移动方向前方的两个方块合并，
     *    后方的方块不合并。
     * */
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;

        // TODO：修改 this.board（可能还需要修改 this.score），以处理向 SIDE
        // 方向的倾斜。如果棋盘发生变化，将局部变量 changed 设为 true。

        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }

    /** 检查游戏是否结束，并相应地设置 gameOver 变量。
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** 判断游戏是否结束。 */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** 若 Board 上至少有一个空位，则返回 true。
     * 空位以 null 存储。
     * */
    public static boolean emptySpaceExists(Board b) {
        // TODO：完成此函数。
        for(int row = 0; row < b.size(); row++) {
            for (int col = 0; col < b.size(); col++) {
                if (b.tile(row, col) == null)
                    return true;
            }
        }
        return false;
    }

    /**
     * 若任一方块等于允许的最大数值，则返回 true。
     * 最大有效值由 MAX_PIECE 给出。注意：给定 Tile 对象 t，
     * 可通过 t.value() 获取其数值。
     */
    public static boolean maxTileExists(Board b) {
        // TODO：完成此函数。
        return false;
    }

    /**
     * 若棋盘上存在任何合法移动，则返回 true。
     * 存在合法移动有两种情况：
     * 1. 棋盘上至少有一个空位。
     * 2. 有两个数值相同的相邻方块。
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // TODO：完成此函数。
        return false;
    }


    @Override
     /** 以字符串形式返回模型，用于调试。 */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** 返回两个模型是否相等。 */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** 返回 Model 字符串形式的哈希码。 */
    public int hashCode() {
        return toString().hashCode();
    }
}
