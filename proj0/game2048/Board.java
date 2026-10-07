package game2048;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * @author hug
 */
public class Board implements Iterable<Tile> {
    /** 棋盘当前的内容。 */
    private Tile[][] values;
    /** 棋盘当前视作北方的边。 */
    private Side viewPerspective;

    public Board(int size) {
        values = new Tile[size][size];
        viewPerspective = Side.NORTH;
    }

    /** 转换棋盘视角，使棋盘表现得如同 S 边朝北。 */
    public void setViewingPerspective(Side s) {
        viewPerspective = s;
    }

    /** 创建一个棋盘：RAWVALUES 保存棋盘上各方块的数值（0 表示 null），
     * 当前分数为 SCORE，观察方向设为北。 */
    public Board(int[][] rawValues, int score) {
        int size = rawValues.length;
        values = new Tile[size][size];
        viewPerspective = Side.NORTH;
        for (int col = 0; col < size; col += 1) {
            for (int row = 0; row < size; row += 1) {
                int value = rawValues[size - 1 - row][col];
                Tile tile;
                if (value == 0) {
                    tile = null;
                } else {
                    tile = Tile.create(value, col, row);
                }
                values[col][row] = tile;
            }
        }
    }

    /** 返回棋盘的大小。 */
    public int size() {
        return values.length;
    }

    /** 转换 Board 的观察视角。 */
    public void startViewingFrom(Side s) {
        viewPerspective = s;
    }

    /** 当观察棋盘时以 SIDE 为顶部（离观察者最远），返回 (COL, ROW) 处当前的 Tile。 */
    private Tile vtile(int col, int row, Side side) {
        return values[side.col(col, row, size())][side.row(col, row, size())];
    }

    /** 返回 (COL, ROW) 处当前的 Tile，其中 0 <= ROW < size()、
     * 0 <= COL < size()。若该位置没有方块，则返回 null。 */
    public Tile tile(int col, int row) {
        return vtile(col, row, viewPerspective);
    }

    /** 清空棋盘并重置分数。 */
    public void clear() {
        for (Tile[] column : values) {
            Arrays.fill(column, null);
        }
    }

    /** 将方块 T 添加到棋盘。 */
    public void addTile(Tile t) {
        values[t.col()][t.row()] = t;
    }

    /** 将 Tile TILE 放到第 COL 列、第 ROW 行；COL 和 ROW 是相对于当前
     * viewPerspective 的坐标。
     *
     * 返回此次移动是否发生了合并。
     * */
    public boolean move(int col, int row, Tile tile) {
        int pcol = viewPerspective.col(col, row, size()),
                prow = viewPerspective.row(col, row, size());
        if (tile.col() == pcol && tile.row() == prow) {
            return false;
        }
        Tile tile1 = vtile(col, row, viewPerspective);
        values[tile.col()][tile.row()] = null;

        if (tile1 == null) {
            values[pcol][prow] = tile.move(pcol, prow);
            return false;
        } else {
            values[pcol][prow] = tile.merge(pcol, prow, tile1);
            return true;
        }
    }

    @Override
    /** 以字符串形式返回棋盘，用于调试。 */
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
        return out.toString();
    }

    /** 遍历棋盘中的每个方块。 */
    private class AllTileIterator implements Iterator<Tile>, Iterable<Tile> {
        int r, c;

        AllTileIterator() {
            r = 0;
            c = 0;
        }

        public boolean hasNext() {
            return r < size();
        }

        public Tile next() {
            Tile t = tile(c, r);
            c = c + 1;
            if (c == size()) {
                c = 0;
                r = r + 1;
            }
            return t;
        }

        public Iterator<Tile> iterator() {
            return this;
        }
    }

    public Iterator<Tile> iterator() {
        return new AllTileIterator();
    }

}
