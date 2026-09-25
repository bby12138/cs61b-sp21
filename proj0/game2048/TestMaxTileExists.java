package game2048;

import org.junit.Test;

import static org.junit.Assert.*;

/** 测试 Model 的静态方法 maxTileExists()。
 *
 * @author Omar Khan
 */
public class TestMaxTileExists {
    /** 用于测试的棋盘。 */
    static Board b;

    @Test
    /** 注意，这并不是一种可能出现的棋盘状态。 */
    public void testEmptyBoard() {
        int[][] rawVals = new int[][] {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        b = new Board(rawVals, 0);

        assertFalse("Board is empty\n" + b, Model.maxTileExists(b));
    }

    @Test
    /** 测试没有最大数值方块的满棋盘。 */
    public void testFullBoardNoMax() {
        int[][] rawVals = new int[][] {
                {2, 2, 2, 2},
                {2, 2, 2, 2},
                {2, 2, 2, 2},
                {2, 2, 2, 2},
        };

        b = new Board(rawVals, 0);

        assertFalse("No 2048 tile on board\n" + b, Model.maxTileExists(b));
    }

    @Test
    /** 测试含有最大数值方块的满棋盘。 */
    public void testFullBoardMax() {
        int[][] rawVals = new int[][] {
                {2, 2, 2, 2},
                {2, 2, 2, 2},
                {2, 2, 2, 2},
                {2, 2, 2, 2048},
        };

        b = new Board(rawVals, 0);

        assertTrue("One 2048 tile on board\n" + b,
                Model.maxTileExists(b));
    }

    @Test
    /** 测试存在多个最大数值方块的情况。 */
    public void testMultipleMax() {
        int[][] rawVals = new int[][] {
                {2, 2, 2, 2},
                {2, 2048, 0, 0},
                {0, 0, 0, 2},
                {0, 0, 2, 2048},
        };

        b = new Board(rawVals, 0);

        assertTrue("Two 2048 tile on board\n" + b,
                Model.maxTileExists(b));
    }

    @Test
    /** 测试最大数值方块位于右上角的情况。 */
    public void testTopRightCorner() {
        int[][] rawVals = new int[][] {
                {0, 0, 0, 2048},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };

        b = new Board(rawVals, 0);

        assertTrue("One 2048 tile on board\n" + b,
                Model.maxTileExists(b));
    }

    @Test
    /** 测试最大数值方块位于左上角的情况。 */
    public void testTopLeftCorner() {
        int[][] rawVals = new int[][] {
                {2048, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };

        b = new Board(rawVals, 0);

        assertTrue("One 2048 tile on board\n" + b,
                Model.maxTileExists(b));
    }

    @Test
    /** 测试最大数值方块位于左下角的情况。 */
    public void testBottomLeftCorner() {
        int[][] rawVals = new int[][] {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2048, 0, 0, 0}
        };

        b = new Board(rawVals, 0);

        assertTrue("One 2048 tile on board\n" + b,
                Model.maxTileExists(b));
    }

    @Test
    /** 测试最大数值方块位于右下角的情况。 */
    public void testBottomRightCorner() {
        int[][] rawVals = new int[][] {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 2048}
        };

        b = new Board(rawVals, 0);

        assertTrue("One 2048 tile on board\n" + b,
                Model.maxTileExists(b));
    }

}
