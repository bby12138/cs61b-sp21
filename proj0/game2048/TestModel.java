package game2048;
import org.junit.Test;

import static org.junit.Assert.*;

/** Model 类的测试。
 *
 * 这些测试会综合检验你编写的所有内容。在其他 Test 文件全部通过之前，
 * 不应尝试通过这些测试。
 *
 * @author Omar Khan
 */
public class TestModel extends TestUtils {

    /**
     * ******************
     * *    测试倾斜    *
     * ******************
     * <p>
     * 以下测试用于判断 `tilt` 方法是否正确。
     */

    @Test
    /** 检查三个相邻方块数值相同时，右侧两个方块是否合并。 */
    public void testTripleMerge1() {
        int[][] before = new int[][]{
                {2, 0, 0, 0},
                {2, 0, 0, 0},
                {2, 0, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {4, 0, 0, 0},
                {2, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.NORTH);
        checkChanged(Side.NORTH, true, changed);
        checkModel(after, 4, 0, prevBoard, Side.NORTH);
    }

    @Test
    /** 检查三个相邻方块数值相同时，右侧两个方块是否合并。 */
    public void testTripleMerge2() {
        int[][] before = new int[][]{
                {2, 0, 0, 0},
                {2, 0, 0, 0},
                {2, 0, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 0, 0, 0},
                {4, 0, 0, 0},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.SOUTH);
        checkChanged(Side.SOUTH, true, changed);
        checkModel(after, 4, 0, prevBoard, Side.SOUTH);
    }

    @Test
    /** 检查两组相邻方块是否都能正确合并。 */
    public void testQuadrupleMerge() {
        int[][] before = new int[][]{
                {0, 0, 0, 2},
                {0, 0, 0, 2},
                {0, 0, 0, 2},
                {0, 0, 0, 2},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 4},
                {0, 0, 0, 4},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.NORTH);
        checkChanged(Side.NORTH, true, changed);
        checkModel(after, 8, 0, prevBoard, Side.NORTH);
    }

    @Test
    /** 检查一个方块在每次倾斜中是否只合并一次。 */
    public void testSingleMergeUp() {
        int[][] before = new int[][]{
                {2, 0, 0, 0},
                {2, 0, 0, 0},
                {0, 0, 0, 0},
                {4, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {4, 0, 0, 0},
                {4, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.NORTH);
        checkChanged(Side.NORTH, true, changed);
        checkModel(after, 4, 0, prevBoard, Side.NORTH);
    }

    @Test
    /** 检查一个方块在每次倾斜中是否只合并一次。 */
    public void testSingleMergeSouth() {
        int[][] before = new int[][]{
                {4, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 0, 0, 0},
                {2, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {4, 0, 0, 0},
                {4, 0, 0, 0},
        };
        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.SOUTH);
        checkChanged(Side.SOUTH, true, changed);
        checkModel(after, 4, 0, prevBoard, Side.SOUTH);
    }

    @Test
    /** 检查一个方块在每次倾斜中是否只合并一次。 */
    public void testSingleMergeEast() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {4, 0, 2, 2},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 4, 4},
        };
        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.EAST);
        checkChanged(Side.EAST, true, changed);
        checkModel(after, 4, 0, prevBoard, Side.EAST);
    }

    @Test
    /** 检查一个方块在每次倾斜中是否只合并一次。 */
    public void testSingleMergeWest() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 2, 0, 4},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {4, 4, 0, 0},
        };
        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.WEST);
        checkChanged(Side.WEST, true, changed);
        checkModel(after, 4, 0, prevBoard, Side.WEST);
    }

    @Test
    /** 检查未产生任何变化的倾斜是否返回 false。 */
    public void testNoMove() {
        int[][] before = new int[][]{
                {2, 0, 2, 2},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] after = before;

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.NORTH);
        checkChanged(Side.NORTH, false, changed);
        checkModel(after, 0, 0, prevBoard, Side.NORTH);
    }

    @Test
    /** 向上移动方块（不合并）。 */
    public void testUpNoMerge() {
        int[][] before = new int[][]{
                {0, 0, 4, 0},
                {0, 0, 0, 2},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 4, 2},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.NORTH);
        checkChanged(Side.NORTH, true, changed);
        checkModel(after, 0, 0, prevBoard, Side.NORTH);
    }

    @Test
    /** 向上移动相邻方块（不合并）。 */
    public void testUpAdjacentNoMerge() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 4, 0},
                {0, 0, 2, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 4, 0},
                {0, 0, 2, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.NORTH);
        checkChanged(Side.NORTH, true, changed);
        checkModel(after, 0, 0, prevBoard, Side.NORTH);
    }

    @Test
    /** 向上移动不相邻的方块（不合并）。 */
    public void testUpNonAdjacentNoMerge1() {
        int[][] before = new int[][]{
                {0, 0, 4, 0},
                {0, 0, 0, 0},
                {0, 0, 2, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 4, 0},
                {0, 0, 2, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.NORTH);
        checkChanged(Side.NORTH, true, changed);
        checkModel(after, 0, 0, prevBoard, Side.NORTH);
    }

    @Test
    /** 向上移动不相邻的方块（不合并）；情况 2：两个方块都移动。 */
    public void testMoveUpNonAdjacentNoMerge2() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 4, 0},
                {0, 0, 0, 0},
                {0, 0, 2, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 4, 0},
                {0, 0, 2, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.NORTH);
        checkChanged(Side.NORTH, true, changed);
        checkModel(after, 0, 0, prevBoard, Side.NORTH);
    }

    @Test
    /** 向上合并相邻方块。 */
    public void testUpAdjacentMerge() {
        int[][] before = new int[][]{
                {0, 0, 2, 0},
                {0, 0, 2, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 4, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.NORTH);
        checkChanged(Side.NORTH, true, changed);
        checkModel(after, 4, 0, prevBoard, Side.NORTH);
    }

    @Test
    /** 向上合并不相邻的方块。 */
    public void testUpNonAdjacentMerge() {
        int[][] before = new int[][]{
                {0, 0, 2, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 2, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 4, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.NORTH);
        checkChanged(Side.NORTH, true, changed);
        checkModel(after, 4, 0, prevBoard, Side.NORTH);
    }

    @Test
    /** 向上移动并合并相邻方块。 */
    public void testUpAdjacentMergeMove() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 2, 0},
                {0, 0, 2, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 4, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.NORTH);
        checkChanged(Side.NORTH, true, changed);
        checkModel(after, 4, 0, prevBoard, Side.NORTH);
    }

    @Test
    /** 向右移动方块（不合并）。 */
    public void testRightNoMerge() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 4},
                {0, 0, 2, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 4},
                {0, 0, 0, 2},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.EAST);
        checkChanged(Side.EAST, true, changed);
        checkModel(after, 0, 0, prevBoard, Side.EAST);
    }

    @Test
    /** 向右移动相邻方块（不合并）。 */
    public void testRightAdjacentNoMerge() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 2, 4, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 2, 4},
                {0, 0, 0, 0},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.EAST);
        checkChanged(Side.EAST, true, changed);
        checkModel(after, 0, 0, prevBoard, Side.EAST);
    }

    @Test
    /** 向右移动相邻方块（不合并）。 */
    public void testRightNonAdjacentNoMerge1() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 2, 0, 4},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 2, 4},
                {0, 0, 0, 0},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.EAST);
        checkChanged(Side.EAST, true, changed);
        checkModel(after, 0, 0, prevBoard, Side.EAST);
    }

    @Test
    /** 向右移动相邻方块（不合并）；情况 2：两个方块都移动。 */
    public void testRightNonAdjacentNoMerge2() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 0, 4, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 2, 4},
                {0, 0, 0, 0},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.EAST);
        checkChanged(Side.EAST, true, changed);
        checkModel(after, 0, 0, prevBoard, Side.EAST);
    }

    @Test
    /** 向右合并相邻方块。 */
    public void testRightAdjacentMerge() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 2, 2},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 4},
                {0, 0, 0, 0},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.EAST);
        checkChanged(Side.EAST, true, changed);
        checkModel(after, 4, 0, prevBoard, Side.EAST);
    }

    @Test
    /** 向右合并不相邻的方块。 */
    public void testRightNonAdjacentMerge() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 0, 0, 2},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 4},
                {0, 0, 0, 0},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.EAST);
        checkChanged(Side.EAST, true, changed);
        checkModel(after, 4, 0, prevBoard, Side.EAST);
    }

    @Test
    /** 向右移动并合并相邻方块。 */
    public void testRightAdjacentMergeMove() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 2, 2, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 4},
                {0, 0, 0, 0},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.EAST);
        checkChanged(Side.EAST, true, changed);
        checkModel(after, 4, 0, prevBoard, Side.EAST);
    }

    @Test
    /** 向右移动并合并不相邻的方块。 */
    public void testRightNonAdjacentMergeMove() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 0, 2, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 4},
                {0, 0, 0, 0},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.EAST);
        checkChanged(Side.EAST, true, changed);
        checkModel(after, 4, 0, prevBoard, Side.EAST);
    }

    @Test
    /** 向下移动方块（不合并）。 */
    public void testDownNoMerge() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 0, 0, 0},
                {0, 4, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 4, 0, 0},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.SOUTH);
        checkChanged(Side.SOUTH, true, changed);
        checkModel(after, 0, 0, prevBoard, Side.SOUTH);
    }

    @Test
    /** 向下移动相邻方块（不合并）。 */
    public void testDownAdjacentNoMerge() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 4, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 4, 0, 0},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.SOUTH);
        checkChanged(Side.SOUTH, true, changed);
        checkModel(after, 0, 0, prevBoard, Side.SOUTH);
    }

    @Test
    /** 向下移动不相邻的方块（不合并）。 */
    public void testDownNonAdjacentNoMerge1() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 0, 0, 0},
                {0, 4, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 4, 0, 0},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.SOUTH);
        checkChanged(Side.SOUTH, true, changed);
        checkModel(after, 0, 0, prevBoard, Side.SOUTH);
    }

    @Test
    /** 向下合并相邻方块。 */
    public void testDownAdjacentMerge() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 2, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 4, 0, 0},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.SOUTH);
        checkChanged(Side.SOUTH, true, changed);
        checkModel(after, 4, 0, prevBoard, Side.SOUTH);
    }

    @Test
    /** 向下合并不相邻的方块。 */
    public void testDownNonAdjacentMerge() {
        int[][] before = new int[][]{
                {0, 2, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 2, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 4, 0, 0},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.SOUTH);
        checkChanged(Side.SOUTH, true, changed);
        checkModel(after, 4, 0, prevBoard, Side.SOUTH);
    }

    @Test
    /** 向下移动并合并相邻方块。 */
    public void testDownAdjacentMergeMove() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 2, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 4, 0, 0},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.SOUTH);
        checkChanged(Side.SOUTH, true, changed);
        checkModel(after, 4, 0, prevBoard, Side.SOUTH);
    }

    @Test
    /** 向下移动并合并不相邻的方块。 */
    public void testDownNonAdjacentMergeMove() {
        int[][] before = new int[][]{
                {0, 2, 0, 0},
                {0, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 4, 0, 0},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.SOUTH);
        checkChanged(Side.SOUTH, true, changed);
        checkModel(after, 4, 0, prevBoard, Side.SOUTH);
    }

    @Test
    /** 向左移动方块（不合并）。 */
    public void testLeftNoMerge() {
        int[][] before = new int[][]{
                {4, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {4, 0, 0, 0},
                {2, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.WEST);
        checkChanged(Side.WEST, true, changed);
        checkModel(after, 0, 0, prevBoard, Side.WEST);
    }

    @Test
    /** 向左移动相邻方块（不合并）。 */
    public void testLeftAdjacentNoMerge() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 4, 2, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {4, 2, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.WEST);
        checkChanged(Side.WEST, true, changed);
        checkModel(after, 0, 0, prevBoard, Side.WEST);
    }

    @Test
    /** 向左移动不相邻的方块（不合并）。 */
    public void testLeftNonAdjacentNoMerge1() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {4, 0, 2, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {4, 2, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.WEST);
        checkChanged(Side.WEST, true, changed);
        checkModel(after, 0, 0, prevBoard, Side.WEST);
    }

    @Test
    /** 向左合并相邻方块。 */
    public void testLeftAdjacentMerge() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {2, 2, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {4, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.WEST);
        checkChanged(Side.WEST, true, changed);
        checkModel(after, 4, 0, prevBoard, Side.WEST);
    }

    @Test
    /** 向左合并不相邻的方块。 */
    public void testLeftNonAdjacentMerge() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {2, 0, 0, 2},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {4, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.WEST);
        checkChanged(Side.WEST, true, changed);
        checkModel(after, 4, 0, prevBoard, Side.WEST);
    }

    @Test
    /** 向左移动并合并相邻方块。 */
    public void testLeftAdjacentMergeMove() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 2, 2, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {4, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.WEST);
        checkChanged(Side.WEST, true, changed);
        checkModel(after, 4, 0, prevBoard, Side.WEST);
    }

    @Test
    /** 向左移动并合并不相邻的方块。 */
    public void testLeftNonAdjacentMergeMove() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 2, 0, 2},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {4, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.WEST);
        checkChanged(Side.WEST, true, changed);
        checkModel(after, 4, 0, prevBoard, Side.WEST);
    }

    /**
     * ***********************
     * *    测试游戏结束     *
     * ***********************
     * <p>
     * 以下测试用于判断 `checkGameOver` 方法是否正确。
     */

    @Test
    /** 向任何方向倾斜都不会产生变化。 */
    public void testGameOverNoChange1() {
        int[][] board = {
                {2, 4, 2, 4},
                {4, 2, 4, 2},
                {2, 4, 2, 4},
                {4, 2, 4, 2}
        };

        updateModel(board, 0, 0, false);
        assertTrue("Game is over. No tilt would result in a change"
                + model, model.gameOver());
    }

    @Test
    /** 棋盘上存在 MAX_PIECE（2048）方块。 */
    public void testGameOverMaxPiece() {
        int[][] board = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 2048}
        };

        updateModel(board, 0, 0, false);
        assertTrue("Game is over. Tile with 2048 is on board:"
                + model, model.gameOver());
    }

    @Test
    /** 向任何方向倾斜都不会产生变化。 */
    public void testGameOverNoChange2() {
        int[][] board = {
                {128, 4, 2, 4},
                {4, 32, 4, 2},
                {8, 16, 2, 8},
                {4, 32, 4, 1024}
        };

        updateModel(board, 0, 0, false);
        assertTrue("Game is over. Tile with 2048 is on board:"
                + model, model.gameOver());
    }

    @Test
    /** 向任何方向倾斜都会改变棋盘。 */
    public void testGameNotOver1() {
        int[][] board = {
                {2, 4, 2, 2},
                {4, 2, 4, 2},
                {2, 4, 2, 4},
                {4, 2, 4, 2}
        };
        updateModel(board, 0, 0, false);
        assertFalse("Game isn't over. Any tilt will result in a change:"
                + model, model.gameOver());
    }

    @Test
    /** 向右或向下倾斜会改变棋盘。 */
    public void testGameNotOver2() {
        int[][] board = {
                {2, 4, 2, 4},
                {4, 2, 4, 2},
                {2, 4, 2, 4},
                {4, 2, 4, 0}
        };
        updateModel(board, 0, 0, false);
        assertFalse("Game isn't over. A tilt right or down will result"
                + " in a change:" + model, model.gameOver());
    }

    /**
     * *************************
     * *     多次移动测试      *
     * *************************
     * <p>
     * 以下测试会多次调用 `tilt` 方法，并检查每次移动后棋盘是否正确。
     * 在上述所有测试通过之前，不应期望这些测试能够通过。
     */

    @Test
    /** 测试 Model 上的多次移动。 */
    public void testMultipleMoves1() {
        int[][] board = new int[][]{
                {0, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 2}
        };

        String prevBoard;
        String currBoard;
        Side currMove;
        Tile toAdd;
        int totalScore = 0;

        updateModel(board, 0, 0, false);

        prevBoard = board.toString();
        currMove = Side.EAST;
        model.tilt(currMove);
        checkModel(new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 2},
                {0, 0, 0, 0},
                {0, 0, 0, 2}
        }, totalScore, 0, prevBoard, currMove);
        toAdd = Tile.create(2, 3, 1);
        model.addTile(toAdd);

        prevBoard = model.toString();
        currMove = Side.NORTH;
        totalScore += 4;
        model.tilt(currMove);
        checkModel(new int[][]{
                {0, 0, 0, 4},
                {0, 0, 0, 2},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        }, totalScore, 0, prevBoard, currMove);
        toAdd = Tile.create(2, 0, 1);
        model.addTile(toAdd);

        prevBoard = model.toString();
        currMove = Side.EAST;
        model.tilt(currMove);
        checkModel(new int[][]{
                {0, 0, 0, 4},
                {0, 0, 0, 2},
                {0, 0, 0, 2},
                {0, 0, 0, 0}
        }, totalScore, 0, prevBoard, currMove);
        toAdd = Tile.create(4, 2, 0);
        model.addTile(toAdd);

        prevBoard = model.toString();
        currMove = Side.NORTH;
        totalScore += 4;
        model.tilt(currMove);
        checkModel(new int[][]{
                {0, 0, 4, 4},
                {0, 0, 0, 4},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        }, totalScore, 0, prevBoard, currMove);
        toAdd = Tile.create(4, 0, 3);
        model.addTile(toAdd);

        prevBoard = model.toString();
        currMove = Side.SOUTH;
        totalScore += 8;
        model.tilt(currMove);
        checkModel(new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {4, 0, 4, 8}
        }, totalScore, 0, prevBoard, currMove);
    }

    @Test
    /** 测试 Model 上最终导致游戏结束的多次移动。 */
    public void testMultipleMoves2() {
        int[][] board = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 256, 256, 0},
                {1024, 0, 0, 512}
        };

        String prevBoard;
        String currBoard;
        Side currMove;
        Tile toAdd;
        int totalScore = 0;

        updateModel(board, 0, 0, false);

        prevBoard = model.toString();
        currMove = Side.EAST;
        totalScore += 512;
        model.tilt(currMove);
        checkModel(new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 512},
                {0, 0, 1024, 512}
        }, totalScore, 0, prevBoard, currMove);
        toAdd = Tile.create(2, 0, 0);
        model.addTile(toAdd);

        prevBoard = model.toString();
        currMove = Side.SOUTH;
        model.tilt(currMove);
        totalScore += 1024;
        checkModel(new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 0, 1024, 1024}
        }, totalScore, 0, prevBoard, currMove);
        toAdd = Tile.create(2, 0, 1);
        model.addTile(toAdd);

        prevBoard = model.toString();
        currMove = Side.WEST;
        model.tilt(currMove);
        totalScore += 2048;
        assertTrue("Game is over. Tile with 2048 is on board:"
                + model, model.gameOver());
        checkModel(new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 0, 0, 0},
                {2, 2048, 0, 0}
        }, totalScore, totalScore, prevBoard, currMove);
    }

    @Test
    /** 测试 Model 上的多次移动。 */
    public void testMultipleMoves3() {
        int[][] board = new int[][]{
                {0, 2, 2, 0},
                {4, 0, 4, 0},
                {4, 0, 8, 0},
                {8, 0, 0, 0}
        };

        String prevBoard;
        String currBoard;
        Side currMove;
        Tile toAdd;
        int totalScore = 0;

        updateModel(board, 0, 0, false);

        prevBoard = model.toString();
        currMove = Side.EAST;
        totalScore += 4 + 8;
        model.tilt(currMove);
        checkModel(new int[][]{
                {0, 0, 0, 4},
                {0, 0, 0, 8},
                {0, 0, 4, 8},
                {0, 0, 0, 8}
        }, totalScore, 0, prevBoard, currMove);
        toAdd = Tile.create(2, 1, 2);
        model.addTile(toAdd);

        prevBoard = model.toString();
        currMove = Side.SOUTH;
        totalScore += 16;
        model.tilt(currMove);
        checkModel(new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 4},
                {0, 0, 0, 8},
                {0, 2, 4, 16}
        }, totalScore, 0, prevBoard, currMove);
        toAdd = Tile.create(2, 1, 1);
        model.addTile(toAdd);

        prevBoard = model.toString();
        currMove = Side.NORTH;
        totalScore += 4;
        model.tilt(currMove);
        checkModel(new int[][]{
                {0, 4, 4, 4},
                {0, 0, 0, 8},
                {0, 0, 0, 16},
                {0, 0, 0, 0}
        }, totalScore, 0, prevBoard, currMove);
        toAdd = Tile.create(4, 0, 0);
        model.addTile(toAdd);

        prevBoard = model.toString();
        currMove = Side.NORTH;
        model.tilt(currMove);
        checkModel(new int[][]{
                {4, 4, 4, 4},
                {0, 0, 0, 8},
                {0, 0, 0, 16},
                {0, 0, 0, 0}
        }, totalScore, 0, prevBoard, currMove);
        toAdd = Tile.create(2, 3, 0);
        model.addTile(toAdd);

        prevBoard = model.toString();
        currMove = Side.EAST;
        totalScore += 8 + 8;
        model.tilt(currMove);
        checkModel(new int[][]{
                {0, 0, 8, 8},
                {0, 0, 0, 8},
                {0, 0, 0, 16},
                {0, 0, 0, 2}
        }, totalScore, 0, prevBoard, currMove);
    }
}
