package game2048;

import org.junit.Test;

import static org.junit.Assert.*;

/** 仅测试 tilt() 方法向上（Side.NORTH）倾斜的情况。
 *
 * @author Omar Khan
 */
public class TestUpOnly extends TestUtils {


    @Test
    /** 向上移动方块（不合并）。 */
    public void testUpNoMerge() {
        int[][] before = new int[][] {
                {0, 0, 4, 0},
                {0, 0, 0, 2},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][] {
                {0, 0, 4, 2},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        model = new Model(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.NORTH);
        checkChanged(Side.NORTH, true, changed);
        checkModel(after, 0, 0, prevBoard, Side.NORTH);
    }

    @Test
    /** 基本合并。 */
    public void testUpBasicMerge() {
        int[][] before = new int[][] {
                {0, 0, 0, 0},
                {0, 0, 2, 0},
                {0, 0, 2, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][] {
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
    /** 三个相同方块的合并；只有前面的两个方块应当合并。 */
    public void testUpTripleMerge() {
        int[][] before = new int[][] {
                {0, 0, 2, 0},
                {0, 0, 2, 0},
                {0, 0, 2, 0},
                {0, 0, 2, 0},
        };
        int[][] after = new int[][] {
                {0, 0, 4, 0},
                {0, 0, 4, 0},
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
    /** 一个容易出错的合并场景。
     *
     * 难点在于，最下行的 4 方块不应与最上行新生成的 4 方块合并。
     * 如果此测试失败，请思考如何确保底部的 4 方块不与顶部新生成的
     * 4 方块合并。*/
    public void testUpTrickyMerge() {
        int[][] before = new int[][] {
                {0, 0, 2, 0},
                {0, 0, 2, 0},
                {0, 0, 0, 0},
                {0, 0, 4, 0},
        };
        int[][] after = new int[][] {
                {0, 0, 4, 0},
                {0, 0, 4, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        updateModel(before, 0, 0, false);
        String prevBoard = model.toString();
        boolean changed = model.tilt(Side.NORTH);
        checkChanged(Side.NORTH, true, changed);
        checkModel(after, 4, 0, prevBoard, Side.NORTH);
    }
}
