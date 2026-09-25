package game2048;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/** 一种从 GUI 接收命令的 InputSource。
 *  @author P. N. Hilfinger
 */
class GUISource implements InputSource {

    /** 提供来自 SOURCE 的输入，并且当且仅当 LOG 为 true 时记录输入。
     * 使用 RANDOMSOURCE 随机选择方块，其数值为 2 的概率是 PROBOF2。 */
    GUISource(GUI source, Random randomSource, double probOf2,
              String logFileName) {
        _source = source;
        _randomSource = randomSource;
        _probOf2 = probOf2;

        if (logFileName != null) {

            File logFile = new File(logFileName);
            try {
                _logFileWriter = new FileWriter(logFile);
            } catch (IOException e) {
                System.err.println("Error: no such file " + logFileName);
                System.exit(1);
            }
        }
    }

    @Override
    /** 返回并记录按下了哪个方向键。 */
    public String getKey() {
        String command = _source.readKey();
        switch (command) {
            case "↑" :
                command = "Up";
                break;
            case "→" :
                command = "Right";
                break;
            case "↓" :
                command = "Down";
                break;
            case "←" :
                command = "Left";
                break;
            default :
                break;
        }

        String logLine = String.format("K %s%n", command);

        if (_logFileWriter != null) {
            System.out.print(logLine);
            try {
                _logFileWriter.write(logLine);
            } catch (IOException e) {
                System.err.print("Error: cannot write to log file");
                System.exit(1);
            }
        }
        return command;
    }

    @Override
     /** 在大小为 SIZE 的棋盘中返回一个位置随机的方块：其数值以 _probOf2 的概率
      * 为 2，以 1 - _probOf2 的概率为 4。 */
    public Tile getNewTile(int size) {
        int c = _randomSource.nextInt(size), r = _randomSource.nextInt(size);
        int v = _randomSource.nextDouble() <= _probOf2 ? 2 : 4;
        if (_logFileWriter != null) {
            System.out.printf("T %d %d %d%n", v, c, r);
        }
        return Tile.create(v, c, r);
    }

    /** 输入源。 */
    private GUI _source;
    /** 用于生成 Tile 的随机数源。 */
    private Random _randomSource;
    /** 新 Tile 的数值为 2 而非 4 的概率。 */
    private double _probOf2;
    /** 用于记录输入的 FileWriter（无需记录时为 null）。 */
    private FileWriter _logFileWriter;

}
