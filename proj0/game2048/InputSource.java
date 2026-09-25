package game2048;

/** 描述输入命令的来源。
 *  @author P. N. Hilfinger
 */
interface InputSource {

    /** 返回一个命令字符串。 */
    String getKey();

    /** 返回一个候选 Tile，其行号和列号均在 0 到 SIZE-1 的范围内。 */
    Tile getNewTile(int size);

}
