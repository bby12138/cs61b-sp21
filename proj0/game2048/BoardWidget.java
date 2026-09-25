package game2048;

import ucb.gui2.Pad;

import java.util.ArrayList;
import java.util.HashMap;

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.FontMetrics;

import static java.lang.Math.max;
import static java.lang.Math.abs;
import static java.lang.Math.round;

/** 显示 2048 棋盘的组件。
 *  @author P. N. Hilfinger
 */
class BoardWidget extends Pad {

    /* 控制尺寸、速度、颜色和字体的参数。 */

    /** 空格和网格线的颜色。 */
    static final Color
        EMPTY_SQUARE_COLOR = new Color(205, 192, 176),
        BAR_COLOR = new Color(184, 173, 158);

    /** 分隔方块的间隔宽度和方块边长（像素）。 */
    static final int
        TILE_SEP = 15,
        TILE_SIDE = 100,
        TILE_SIDE_SEP = TILE_SEP + TILE_SIDE;

    /** 数值不超过 2 位的方块所用字体。 */
    static final Font TILE_FONT2 = new Font("SansSerif", 1, 48);
    /** 数值为 3 位的方块所用字体。 */
    static final Font TILE_FONT3 = new Font("SansSerif", 1, 40);
    /** 数值为 4 位的方块所用字体。 */
    static final Font TILE_FONT4 = new Font("SansSerif", 1, 32);

    /** 棋盘叠加文字的颜色。 */
    static final Color OVERLAY_COLOR = new Color(200, 0, 0, 64);

    /** 棋盘叠加文字的字体。 */
    static final Font OVERLAY_FONT = new Font("SansSerif", 1, 64);

    /** 动画各步骤之间的等待时间（毫秒）。 */
    static final int TICK = 10;

    /** 每秒移动的距离（以行/列为单位）。 */
    static final float MOVE_DELTA = 10.0f;

    /** “绽放效果”所增加的尺寸比例。 */
    static final float BLOOM_FACTOR = 0.1f;

    /** 方块完成“绽放”所需时间（秒）。 */
    static final float BLOOM_TIME = 0.5f;

    /** 方块向外或向内“绽放”所经历的时钟周期数。 */
    static final int BLOOM_TICKS = (int) (20.0 * BLOOM_TIME / TICK);

    /** 方块数值到其文字颜色和背景颜色的映射。 */
    static final HashMap<Integer, Color[]> TILE_COLORS = new HashMap<>();

    /** 方块数值及其对应背景色和前景色的列表。 */
    private static final int[][] TILE_COLOR_MAP = {
        { 2, 0x776e65, 0xeee4da },
        { 4, 0x776e65, 0xede0c8 },
        { 8, 0xf9f6f2, 0xf2b179 },
        { 16, 0xf9f6f2, 0xf59563 },
        { 32, 0xf9f6f2, 0xf67c5f },
        { 64, 0xf9f6f2, 0xf65e3b },
        { 128, 0xf9f6f2, 0xedcf72 },
        { 256, 0xf9f6f2, 0xedcc61 },
        { 512, 0xf9f6f2, 0xedc850 },
        { 1024, 0xf9f6f2, 0xedc53f },
        { 2048, 0xf9f6f2, 0xedc22e },
    };

    static {
        /* { “标签”, “文字颜色（十六进制）”, “背景颜色（十六进制）” } */
        for (int[] tileData : TILE_COLOR_MAP) {
            TILE_COLORS.put(tileData[0],
                            new Color[] { new Color(tileData[1]),
                                          new Color(tileData[2]) });
        }
    };

    /** 创建一个具有 SIZE 行和 SIZE 列的 2048 棋盘图形表示。 */
    BoardWidget(int size) {
        _size = size;
        _boardSide = size * TILE_SIDE_SEP + TILE_SEP;
        _tiles = new ArrayList<>();
        setPreferredSize(_boardSide, _boardSide);
    }

    /** 清除棋盘上的所有方块。 */
    synchronized void clear() {
        _tiles.clear();
        repaint();
    }

    /** 指示是否应显示“GAME OVER”标签。 */
    synchronized void markEnd() {
        _end = true;
        repaint();
    }

    @Override
    /** 在 G 上绘制棋盘。 */
    public synchronized void paintComponent(Graphics2D g) {
        g.setColor(EMPTY_SQUARE_COLOR);
        g.fillRect(0, 0, _boardSide, _boardSide);
        g.setColor(BAR_COLOR);
        for (int k = 0; k <= _boardSide; k += TILE_SIDE_SEP) {
            g.fillRect(0, k, _boardSide, TILE_SEP);
            g.fillRect(k, 0, TILE_SEP, _boardSide);
        }
        for (Tile tile : _tiles) {
            render(g, tile);
        }
        if (_end) {
            g.setFont(OVERLAY_FONT);
            FontMetrics metrics = g.getFontMetrics();
            g.setColor(OVERLAY_COLOR);
            g.drawString("GAME OVER",
                         (_boardSide
                          - metrics.stringWidth("GAME OVER")) / 2,
                         (2 * _boardSide + metrics.getMaxAscent()) / 4);
        }
    }

    /** 在 G 上绘制 TILE。 */
    private void render(Graphics2D g, Tile tile) {
        int col0 = tile.col(),
            row0 = tile.row(),
            col1 = tile.next().col(),
            row1 = tile.next().row();
        int dcol = col0 < col1 ? 1 : col0 == col1 ? 0 : -1,
            drow = row0 < row1 ? 1 : row0 == row1 ? 0 : -1;

        float vcol, vrow;
        if (_distMoved >= max(abs(col0 - col1), abs(row0 - row1))) {
            vcol = col1; vrow = row1;
        } else {
            vcol = col0 + _distMoved * dcol;
            vrow = row0 + _distMoved * drow;
        }

        int ulx = Math.round(vcol * TILE_SIDE_SEP + TILE_SEP),
            uly = Math.round((_size - vrow - 1) * TILE_SIDE_SEP + TILE_SEP);

        if (tile.value() < 100) {
            g.setFont(TILE_FONT2);
        } else if (tile.value() < 1000) {
            g.setFont(TILE_FONT3);
        } else {
            g.setFont(TILE_FONT4);
        }
        FontMetrics metrics = g.getFontMetrics();
        int bloom;
        if (_bloomingTiles != null && _bloomingTiles.contains(tile)) {
            bloom = _bloom;
        } else {
            bloom = 0;
        }
        g.setColor(TILE_COLORS.get(tile.value())[1]);
        g.fillRect(ulx - bloom, uly - bloom, 2 * bloom + TILE_SIDE,
                   2 * bloom + TILE_SIDE);
        g.setColor(TILE_COLORS.get(tile.value())[0]);

        String label = Integer.toString(tile.value());
        g.drawString(label,
                     ulx + (TILE_SIDE - metrics.stringWidth(label)) / 2,
                     uly + (2 * TILE_SIDE + metrics.getMaxAscent()) / 4);

    }

    /** 返回 MODEL 中所有 Tile 的列表。 */
    private ArrayList<Tile> modelTiles(Model model) {
        ArrayList<Tile> result = new ArrayList<>();
        for (int col = 0; col < model.size(); col += 1) {
            for (int row = 0; row < model.size(); row += 1) {
                Tile tile = model.tile(col, row);
                if (tile != null) {
                    result.add(tile);
                }
            }
        }
        return result;
    }

    /** 返回 NEXTTILES 中所有新创建或由当前方块合并产生的方块列表。 */
    private ArrayList<Tile> newTiles(ArrayList<Tile> nextTiles) {
        ArrayList<Tile> bloomers = new ArrayList<>();
        bloomers.addAll(nextTiles);
        for (Tile tile : _tiles) {
            if (tile.next().value() == tile.value()) {
                bloomers.remove(tile.next());
            }
        }
        return bloomers;
    }

    /** 等待一个时钟周期（TICK 毫秒）。 */
    private void tick() {
        try {
            wait(TICK);
        } catch (InterruptedException excp) {
            assert false : "Internal error: unexpected interrupt";
        }
    }

    /** 为 BLOOMINGTILES 中的方块创建绽放效果。 */
    private void doBlooming(ArrayList<Tile> bloomingTiles) {
        _bloomingTiles = bloomingTiles;
        if (bloomingTiles.isEmpty()) {
            return;
        }
        for (int k = 1; k <= BLOOM_TICKS; k += 1) {
            _bloom = round(TILE_SIDE * BLOOM_FACTOR * k / BLOOM_TICKS);
            repaint();
            tick();
        }
        for (int k = BLOOM_TICKS - 1; k >= 0; k -= 1) {
            _bloom = round(TILE_SIDE * BLOOM_FACTOR * k / BLOOM_TICKS);
            repaint();
            tick();
        }
        _bloomingTiles = null;
    }


    /** 将方块移动到新位置，并从 MODEL 保存一组新方块；假定 MODEL 反映了
     * 所有移动完成后方块的下一状态。 */
    synchronized void update(Model model) {
        float dist;
        ArrayList<Tile> nextTiles = modelTiles(model);

        dist = 0.0f;
        for (Tile tile : _tiles) {
            dist = Math.max(dist, tile.distToNext());
        }
        _distMoved = 0.0f;
        while (_distMoved < dist) {
            repaint();
            tick();
            _distMoved = Math.min(dist,
                                  _distMoved + TICK * MOVE_DELTA / 1000.0f);
        }


        ArrayList<Tile> bloomers = newTiles(nextTiles);
        _tiles = nextTiles;
        doBlooming(bloomers);
        _end = model.gameOver();
        _distMoved = 0.0f;
        repaint();
    }

    /** 当前显示的 Tile 列表。 */
    private ArrayList<Tile> _tiles;
    /** 当前以绽放效果显示的 Tile 列表。 */
    private ArrayList<Tile> _bloomingTiles;

    /** 方块向下一位置已经移动的距离，以行和列为单位。 */
    private float _distMoved;
    /** _bloomingTiles 中方块各边需要增加的尺寸。 */
    private int _bloom;

    /** 行数和列数。 */
    private final int _size;

    /** 棋盘边长（像素）。 */
    private int _boardSide;
    /** 当且仅当正在显示“GAME OVER”消息时为 true。 */
    private boolean _end;
}
