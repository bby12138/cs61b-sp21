package game2048;

/** 棋盘四条边的符号名称。
 *  @author P. N. Hilfinger */
public enum Side {
    /** 下方各符号方向 D 的参数（COL0、ROW0、DCOL 和 DROW）应按如下方式理解：
     * 棋盘的标准方向以顶部为 NORTH，行号和列号（参见 Model）从左下角开始。
     * 假设调整棋盘方向，使 D 边离你最远。那么：
     *   * (COL0*s, ROW0*s) 是调整方向后棋盘左下角的标准坐标
     *     （其中 s 为棋盘大小）；
     *   * 若 (c, r) 是调整方向后棋盘中某格的标准坐标，则
     *     (c+DCOL, r+DROW) 是其正上方一格的标准坐标。
     * 这样设计的目的，是使用下方的 col() 和 row() 方法将调整后的坐标
     * 转换为标准坐标，从而能用完全相同的代码计算棋盘向任意方向倾斜的结果。 */

    NORTH(0, 0, 0, 1), EAST(0, 1, 1, 0), SOUTH(1, 1, 0, -1),
    WEST(1, 0, -1, 0);

    /** 从棋盘任意格子沿 (DCOL, DROW) 方向可到达的边。这里的“方向
     * (DCOL, DROW)”表示：沿此 Side 方向移动一格，行号增加 DROW，
     * 列号增加 DCOL。当面向此 Side 观察棋盘时，(COL0, ROW0) 表示
     * 左下角格子的列号和行号。 */
    Side(int col0, int row0, int dcol, int drow) {
        this.row0 = row0;
        this.col0 = col0;
        this.drow = drow;
        this.dcol = dcol;
    }

    /** 返回与 S 相对的边。 */
    static Side opposite(Side s) {
        if (s == NORTH) {
            return SOUTH;
        } else if (s == SOUTH) {
            return NORTH;
        } else if (s == EAST) {
            return WEST;
        } else {
            return EAST;
        }
    }

    /** 对于大小为 SIZE、以此 Side 为顶部的棋盘，返回格子 (C, R) 的标准列号。 */
    public int col(int c, int r, int size) {
        return col0 * (size - 1) + c * drow + r * dcol;
    }

    /** 对于大小为 SIZE、以此 Side 为顶部的棋盘，返回格子 (C, R) 的标准行号。 */
    public int row(int c, int r, int size) {
        return row0 * (size - 1) - c * dcol + r * drow;
    }

    /** 描述此 Side 的参数，含义见本类开头的注释。 */
    private int row0, col0, drow, dcol;

};
