package game2048;

/** 表示 2048 棋盘上带数字方块的图像。
 *  @author P. N. Hilfinger.
 */
public class Tile {

    /** 在 (ROW, COL) 处创建数值为 VALUE 的新方块。此构造器为私有，
     * 因此所有方块均由工厂方法 create、move 和 merge 创建。 */
    private Tile(int value, int col, int row) {
        this.value = value;
        this.row = row;
        this.col = col;
        this.next = null;
    }

    /** 返回当前行号。 */
    public int row() {
        return row;
    }

    /** 返回当前列号。 */
    public int col() {
        return col;
    }

    /** 返回传给构造器的数值。 */
    public int value() {
        return value;
    }

    /** 返回下一状态。在被移动或合并之前，后继就是自身。 */
    public Tile next() {
        return next == null ? this : next;
    }

    /** 返回位于 (ROW, COL)、数值为 VALUE 的新方块。 */
    public static Tile create(int value, int col, int row) {
        return new Tile(value, col, row);
    }

    /** 返回移动到 (COL, ROW) 后的结果。 */
    public Tile move(int col, int row) {
        Tile result = new Tile(value, col, row);
        next = result;
        return result;
    }

    /** 返回移动到 (COL, ROW) 后将 OTHERTILE 与自身合并的结果。 */
    public Tile merge(int col, int row, Tile otherTile) {
        assert value == otherTile.value();
        next = otherTile.next = new Tile(2 * value, col, row);
        return next;
    }

    /** 返回自身与后继方块之间相差的行数或列数（没有后继时为 0）。 */
    public int distToNext() {
        if (next == null) {
            return 0;
        } else {
            return Math.max(Math.abs(row - next.row()),
                            Math.abs(col - next.col()));
        }
    }

    @Override
    public String toString() {
        return String.format("%d@(%d, %d)", value(), col(), row());
    }

    /** 本方块的数值。 */
    private final int value;

    /** 本方块在棋盘上的上一个位置。 */
    private final int row, col;

    /** 后继方块：本方块移动到或合并成的方块。 */
    private Tile next;
}
