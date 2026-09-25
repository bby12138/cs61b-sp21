package game2048;

import static game2048.Side.*;

/** 2048 游戏的输入/输出及 GUI 控制器。
 *  @author P. N. Hilfinger. */
public class Game {

    /** 为 MODEL 所表示的游戏创建控制器，使用 SOURCE 提供按键输入和随机 Tile。 */
    public Game(Model model, InputSource source) {
        _model = model;
        _source = source;
        _playing = true;
    }

    /** 当且仅当尚未收到 Quit 命令时返回 true。 */
    boolean playing() {
        return _playing;
    }

    /** 清空棋盘并开始一局游戏，直到收到退出或开始新游戏的请求。
     * 每当添加方块或倾斜导致棋盘变化时，更新视图。 */
    void playGame() {
        _model.clear();
        _model.addTile(getValidNewTile());
        while (_playing) {
            if (!_model.gameOver()) {
                _model.addTile(getValidNewTile());
                _model.notifyObservers();
            }

            boolean moved;
            moved = false;
            while (!moved) {
                String cmnd = _source.getKey();
                switch (cmnd) {
                    case "Quit":
                        _playing = false;
                        return;
                    case "New Game":
                        return;
                    case "Up": case "Down": case "Left": case "Right":
                    case "\u2190": case "\u2191": case "\u2192": case "\u2193":
                        if (!_model.gameOver() && _model.tilt(keyToSide(cmnd))) {
                            _model.notifyObservers(cmnd);
                            moved = true;
                        }
                        break;
                    default:
                        break;
                }

            }
        }
    }

    /** 返回 KEY（“Up”“Down”“Left”或“Right”）所表示的方向。 */
    private Side keyToSide(String key) {
        switch (key) {
            case "Up": case "\u2191":
                return NORTH;
            case "Down": case "\u2193":
                return SOUTH;
            case "Left": case "\u2190":
                return WEST;
            case "Right": case "\u2192":
                return EAST;
            default:
                throw new IllegalArgumentException("unknown key designation");
        }
    }

    /** 返回一个合法方块：不断读取输入源提供的方块，直到找到能放入当前棋盘的方块。
     * 假定棋盘上至少有一个空格。 */
    private Tile getValidNewTile() {
        while (true) {
            Tile tile = _source.getNewTile(_model.size());
            if (_model.tile(tile.col(), tile.row()) == null) {
                return tile;
            }
        }
    }

    /** 游戏棋盘。 */
    private Model _model;

    /** 来自标准输入的输入源。 */
    private InputSource _source;

    /** 用户仍愿意继续游戏时为 true。 */
    private boolean _playing;

}
