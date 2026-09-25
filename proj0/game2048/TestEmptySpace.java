package game2048;

import org.junit.Test;

import static org.junit.Assert.*;

/** 测试 Model 的静态方法 emptySpaceExists()。
 *
 * @author Omar Khan
 */
public class TestEmptySpace {

    /** 用于测试的 Board。 */
    static Board b;

    @Test
    /** 注意，这并不是一种可能出现的棋盘状态。 */
    public void testCompletelyEmpty() {
        int[][] rawVals = new int[][] {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        b = new Board(rawVals, 0);
        assertTrue("Board is full of empty space\n" + b,
                Model.emptySpaceExists(b));
    }

    @Test
    /** 测试除最上方一行外均已填满的棋盘。 */
    public void testEmptyTopRow() {
        int[][] rawVals = new int[][] {
                {0, 0, 0, 0},
                {2, 4, 2, 4},
                {4, 2, 4, 2},
                {2, 4, 2, 4},
        };

        b = new Board(rawVals, 0);

        assertTrue("Top row is empty\n" + b, Model.emptySpaceExists(b));
    }

    @Test
    /** 测试除最下方一行外均已填满的棋盘。 */
    public void testEmptyBottomRow() {
        int[][] rawVals = new int[][] {
                {2, 4, 2, 4},
                {4, 2, 4, 2},
                {2, 4, 2, 4},
                {0, 0, 0, 0},
        };

        b = new Board(rawVals, 0);
        assertTrue("Bottom row is empty\n" + b,
                Model.emptySpaceExists(b));
    }


    @Test
    /** 测试除最左侧一列外均已填满的棋盘。 */
    public void testEmptyLeftCol() {
        int[][] rawVals = new int[][] {
                {0, 4, 2, 4},
                {0, 2, 4, 2},
                {0, 4, 2, 4},
                {0, 2, 4, 2},
        };

        b = new Board(rawVals, 0);

        assertTrue("Left col is empty\n" + b,
                Model.emptySpaceExists(b));
    }

    @Test
    /** 测试除最右侧一列外均已填满的棋盘。 */
    public void testEmptyRightCol() {
        int[][] rawVals = new int[][] {
                {2, 4, 2, 0},
                {4, 2, 4, 0},
                {2, 4, 2, 0},
                {4, 2, 4, 0},
        };

        b = new Board(rawVals, 0);

        assertTrue("Right col is empty\n" + b,
                Model.emptySpaceExists(b));
    }

    @Test
    /** 测试仅有一个空位、其余位置均已填满的棋盘。 */
    public void testAlmostFullBoard() {
        int[][] rawVals = new int[][] {
                {2, 4, 2, 4},
                {4, 2, 4, 2},
                {2, 0, 2, 4},
                {4, 2, 4, 2},
        };

        b = new Board(rawVals, 0);

        assertTrue("Board is not full\n" + b,
                Model.emptySpaceExists(b));
    }

    @Test
    /** 测试完全填满的棋盘。由于仍可合并，游戏尚未结束；但 emptySpaceExists
     * 方法应当只查找空位，而不应检查相邻方块的数值。 */
    public void testFullBoard() {
        int[][] rawVals = new int[][] {
                {2, 2, 2, 2},
                {2, 2, 2, 2},
                {2, 2, 2, 2},
                {2, 2, 2, 2},
        };

        b = new Board(rawVals, 0);

        assertFalse("Board is full\n" + b, Model.emptySpaceExists(b));
    }

    @Test
    /** 测试完全填满的棋盘。 */
    public void testFullBoardNoMerge() {
        int[][] rawVals = new int[][] {
                {2, 4, 2, 4},
                {4, 2, 4, 2},
                {2, 4, 2, 4},
                {4, 2, 4, 2},
        };

        b = new Board(rawVals, 0);

        assertFalse("Board is full\n" + b, Model.emptySpaceExists(b));
    }
}
