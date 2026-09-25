package game2048;

import java.util.Random;

import ucb.util.CommandArgs;

/** 2048 游戏的主类。
 *  @author P. N. Hilfinger
 */
public class Main {

    /** 棋盘每边的格子数。 */
    static final int BOARD_SIZE = 4;
    /** 随机方块选择数值 2（而非 4）的概率。 */
    static final double TILE2_PROBABILITY = 0.9;

    /** 主程序。ARGS 可包含选项 --seed=NUM（随机种子）和 --log
     * （记录移动及选中的随机方块）。 */
    public static void main(String... args) {
        CommandArgs options =
            new CommandArgs("--seed=(\\d+) --log=(.+)",
                            args);
        if (!options.ok()) {
            System.err.println("Usage: java game2048.Main [ --seed=NUM ] "
                               + "[ --log=LOG_FILE ]");
            System.exit(1);
        }

        Random gen = new Random();
        if (options.contains("--seed")) {
            gen.setSeed(options.getLong("--seed"));
        }

        Model model = new Model(BOARD_SIZE);

        GUI gui;

        gui = new GUI("2048 61B", model);
        gui.display(true);

        InputSource inp;

        inp = new GUISource(gui, gen, TILE2_PROBABILITY,
                            options.getFirst("--log"));

        Game game = new Game(model, inp);

        try {
            while (game.playing()) {
                game.playGame();
            }
        } catch (IllegalStateException excp) {
            System.err.printf("Internal error: %s%n", excp.getMessage());
            System.exit(1);
        }

        System.exit(0);
    }

}
