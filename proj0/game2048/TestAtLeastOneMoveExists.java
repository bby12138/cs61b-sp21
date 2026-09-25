package game2048;
import org.junit.Test;

import static org.junit.Assert.*;

/** 测试 Model 的静态方法 atLeastOneMoveExists()。
 *
 * 在通过 TestEmptySpace 中的所有测试之前，不应期望这些测试能够通过。
 *
 * @author Omar Khan
 */
public class TestAtLeastOneMoveExists {

    /** 用于测试的 Board。 */
    static Board b;

    @Test
    /** 测试存在一些空位的棋盘。
     *
     * 注意，这并不是对空位的全面测试；相关测试请参见 TestEmptySpace 类。 */
    public void testEmptySpace() {
        int[][] rawVals = new int[][] {
                {0, 0, 4, 0},
                {0, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 0, 0, 0},
        };

        b = new Board(rawVals, 0);
        assertTrue("A tilt in any direction will change the board "
                        + "(there is empty space on the board)\n" + b,
                Model.atLeastOneMoveExists(b));
    }

    @Test
    /** 测试向任意方向倾斜都会发生变化的棋盘。 */
    public void testAnyDir() {
        int[][] rawVals = new int[][] {
                {2, 4, 2, 2},
                {4, 2, 4, 2},
                {2, 4, 2, 4},
                {4, 2, 4, 2},
        };

        b = new Board(rawVals, 0);
        assertTrue("A tilt in any direction will change the board\n"
                        + b,
                Model.atLeastOneMoveExists(b));
    }

    @Test
    /** 测试向左或向右倾斜会发生变化的棋盘。 */
    public void testLeftOrRight() {
        int[][] rawVals = new int[][] {
                {2, 4, 2, 4},
                {4, 8, 4, 2},
                {2, 2, 2, 4},
                {4, 8, 4, 2},
        };

        b = new Board(rawVals, 0);
        assertTrue("A tilt left or right will change the board\n" + b,
                Model.atLeastOneMoveExists(b));
    }

    @Test
    /** 测试向上或向下倾斜会发生变化的棋盘。 */
    public void testUpOrDown() {
        int[][] rawVals = new int[][] {
                {2, 4, 2, 4},
                {4, 8, 4, 2},
                {2, 16, 4, 8},
                {4, 8, 4, 2},
        };

        b = new Board(rawVals, 0);
        assertTrue("A tilt up or down will change the board\n" + b,
                Model.atLeastOneMoveExists(b));
    }

    @Test
    /** 测试仍存在合法移动的棋盘（棋盘上已有最大数值方块）。
     *
     * 虽然棋盘上出现最大数值方块意味着游戏结束，但不应在此方法中处理。 */
    public void testMoveExistsMaxPiece() {
        int[][] rawVals = new int[][] {
                {2, 4, 2, 4},
                {4, 2, 4, 2},
                {2, 2, 2, 4},
                {4, 2, 4, 2048},
        };

        b = new Board(rawVals, 0);
        assertTrue("A tilt in any direction will change the board\n"
                        + b,
                Model.atLeastOneMoveExists(b));
    }

    @Test
    /** 测试不存在合法移动的棋盘。 */
    public void testNoMoveExists1() {
        int[][] rawVals = new int[][] {
                {2, 4, 2, 4},
                {4, 2, 4, 2},
                {2, 4, 2, 4},
                {4, 2, 4, 2},
        };

        b = new Board(rawVals, 0);
        assertFalse("No move exists\n" + b,
                Model.atLeastOneMoveExists(b));
    }

    @Test
    /** 测试不存在合法移动的棋盘。 */
    public void testNoMoveExists2() {
        int[][] rawVals = new int[][] {
                {2, 1024, 2, 4},
                {4, 2, 4, 2},
                {2, 8, 16, 4},
                {512, 2, 4, 2},
        };

        b = new Board(rawVals, 0);
        assertFalse("No move exists\n" + b,
                Model.atLeastOneMoveExists(b));
    }

    @Test
    /** 测试不存在合法移动的棋盘。 */
    public void testNoMoveExists3() {
        int[][] rawVals = new int[][] {
                {8, 4, 2, 32},
                {32, 2, 4, 2},
                {2, 8, 2, 4},
                {4, 64, 4, 64},
        };

        b = new Board(rawVals, 0);
        assertFalse("No move exists\n" + b,
                Model.atLeastOneMoveExists(b));
    }

    @Test
    /** 测试不存在合法移动的棋盘。 */
    public void testNoMoveExists4() {
        int[][] rawVals = new int[][] {
                {2, 4, 2, 32},
                {32, 2, 4, 2},
                {2, 128, 2, 4},
                {4, 2, 4, 2},
        };

        b = new Board(rawVals, 0);
        assertFalse("No move exists\n" + b,
                Model.atLeastOneMoveExists(b));
    }

    @Test
    /** 测试不存在合法移动的棋盘。 */
    public void testNoMoveExists5() {
        int[][] rawVals = new int[][] {
                {8, 16, 2, 32},
                {32, 2, 64, 2},
                {2, 256, 128, 256},
                {1024, 8, 4, 2},
        };

        b = new Board(rawVals, 0);
        assertFalse("No move exists\n" + b,
                Model.atLeastOneMoveExists(b));
    }
}
