package game2048;

import ucb.gui2.TopLevel;
import ucb.gui2.LayoutSpec;

import java.util.Observable;
import java.util.Observer;

import java.util.concurrent.ArrayBlockingQueue;

import java.awt.event.KeyEvent;


/** 2048 棋盘和按钮的 GUI 控制器。
 *  @author P. N. Hilfinger
 */
class GUI extends TopLevel implements Observer {

    /** 棋盘的最小尺寸（像素）。 */
    private static final int MIN_SIZE = 500;

    /** 创建标题为 TITLE 的新窗口，用于显示 MODEL。 */
    GUI(String title, Model model) {
        super(title, true);
        addMenuButton("Game->New", this::newGame);
        addMenuButton("Game->Quit", this::quit);

        addLabel("", "Score", new LayoutSpec("y", 1));

        _model = model;
        _model.addObserver(this);

        _widget = new BoardWidget(model.size());
        add(_widget,
            new LayoutSpec("y", 0,
                           "height", "REMAINDER",
                           "width", "REMAINDER"));

        _widget.requestFocusInWindow();
        _widget.setKeyHandler("keypress", this::keyPressed);
        setPreferredFocus(_widget);
        setScore(0, 0);
    }

    /** 响应“Quit”按钮的点击。 */
    public void quit(String dummy) {
        _pendingKeys.offer("Quit");
        _widget.requestFocusInWindow();
    }

    /** 响应“New Game”按钮的点击。 */
    public void newGame(String dummy) {
        _pendingKeys.offer("New Game");
        _widget.requestFocusInWindow();
    }

    /** 响应用户按下按键 E，将该按键加入待处理按键队列。 */
    public void keyPressed(String unused, KeyEvent e) {
        _pendingKeys.offer(e.getKeyText(e.getKeyCode()));
    }

    /** 返回下一个待处理事件，必要时等待。普通按键以被按字符的键码报告；
     * 此外，点击菜单按钮会产生消息“Quit”或“New Game”。 */
    String readKey() {
        try {
            return _pendingKeys.take();
        } catch (InterruptedException excp) {
            throw new Error("unexpected interrupt");
        }
    }

    /** 将显示的当前分数设为 SCORE，将当前最高分设为 MAXSCORE。 */
    public void setScore(int score, int maxScore) {
        setLabel("Score", String.format("Score: %6d / Max score: %6d",
                                        score, maxScore));
    }

    /** 当模型调用 notifyObservers 方法时，它会通知本对象模型已经改变，
     * 因为构造器已将本对象注册为模型的 Observer。 */
    @Override
    public void update(Observable model, Object arg) {
        _widget.update(_model);
        setScore(_model.score(), _model.maxScore());
    }

    /** 棋盘组件。 */
    private BoardWidget _widget;
    /** 当前显示的游戏模型。 */
    private Model _model;

    /** 待处理的按键队列。 */
    private ArrayBlockingQueue<String> _pendingKeys =
        new ArrayBlockingQueue<>(5);

}
