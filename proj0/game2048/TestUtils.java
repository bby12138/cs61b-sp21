package game2048;

import static org.junit.Assert.*;

public class TestUtils {

    /** 要测试的 Model。 */
    static Model model;
    /** 这些测试所用 Board 的大小。 */
    public static final int SIZE = 4;

    /** 用于生成错误消息的工具方法。 */
    public static String boardShouldChange(Side side) {
        return "When tilted to the " + side + ", the model should change, but"
                + " the call to tilt returned false.\nModel after call:" + model;
    }

    /** 用于生成错误消息的工具方法。 */
    public static String boardShouldNotChange(Side side) {
        return "When tilted to the " + side + ", the model should NOT change,"
                + " but the call to tilt returned true.\nModel after call:"
                + model;
    }

    /**
     * 更新静态变量 model，使其成为棋盘属性由 VALUES 描述的 Model。
     */
    public static void updateModel(int[][] values, int score, int maxScore,
                                   boolean gameOver) {
        assert values.length == SIZE : "board must have 4x4 dimensions";
        assert values[0].length == SIZE : "board must have 4x4 dimensions";
        model = new Model(values, score, maxScore, gameOver);
    }

    /**
     * 检查静态变量 model 是否按 VALUES 所描述的方式配置，且其分数属性为 SCORE。
     * @param values 描述预期棋盘的二维整数数组，其中元素“0”表示 null Tile。
     * @param score 模型应具有的分数。
     * @param maxScore 模型应具有的最高分。
     * @param prevBoard 此次移动前棋盘的状态。
     * @param currMove 棋盘倾斜所朝的 Side。
     */
    public static void checkModel(int[][] values, int score, int maxScore,
                                  String prevBoard, Side currMove) {

        Model expected = new Model(values, score, maxScore, false);
        String errMsg = String.format("Board incorrect. Before tilting towards"
                        + " %s, your board looked like:%s%nAfter the call to"
                        + " tilt, we expected:%s%nBut your board looks like:%s.",
                currMove, prevBoard, expected.toString(), model.toString());
        assertEquals(errMsg, expected, model);
    }

    /**
     * 检查调用 tilt 方法返回的布尔值是否正确。
     *
     * @param s 倾斜的方向（传给 tilt 的参数）。
     * @param expected 预期的返回值。
     * @param actual 实际的返回值。
     */
    public static void checkChanged(Side s, boolean expected, boolean actual) {
        String changedErrMsg;
        if (expected) {
            changedErrMsg = boardShouldChange(s);
            assertTrue(changedErrMsg, actual);
        } else {
            changedErrMsg = boardShouldNotChange(s);
            assertFalse(changedErrMsg, actual);
        }
    }
}
