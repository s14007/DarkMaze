package jp.ac.it_college.std.s14007.android.darkmaze;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by s14007 on 16/02/03.
 */
public class Map {
    private int blockSize;
    private int horizontalBlockNum;
    private int verticalBlockNum;

    private Block[][] blocks;

    public Map(int width, int height, int bs) {
        this.blockSize = bs;
        horizontalBlockNum = width / blockSize;
        verticalBlockNum = height / blockSize;

        if (horizontalBlockNum % 2 == 0) {
            horizontalBlockNum--;
        }

        if (verticalBlockNum % 2 == 0) {
            verticalBlockNum--;
        }

        createMap();
    }

    private void createMap() {
        int[][] map = MazeGenerator.getMap(255, horizontalBlockNum, verticalBlockNum);
        blocks = new Block[verticalBlockNum][horizontalBlockNum];
        for (int y = 0; y < verticalBlockNum; y++) {
            for (int x = 0; x < horizontalBlockNum; x++) {
                int type = map[y][x];
                int left = x * blockSize + 1;
                int top = y * blockSize + 1;
                int right = left + blockSize - 2;
                int bottom = top + blockSize - 2;
                blocks[y][x] = new Block(type, left, top, right, bottom);
            }
        }
    }

    void drawMap(Canvas canvas) {
        for (int y = 0; y < verticalBlockNum; y++) {
            for (int x = 0; x < horizontalBlockNum; x++) {
                blocks[y][x].draw(canvas);
            }
        }
    }

    static class Block {
        private static final int TYPE_FLOOR = 0;
        private static final int TYPE_WALL = 1;

        private static final Paint PAINT_FLOOR = new Paint();
        private static final Paint PAINT_WALL = new Paint();

        static {
            PAINT_FLOOR.setColor(Color.LTGRAY);
            PAINT_WALL.setColor(Color.BLACK);
        }

        private final int type;

        final Rect rect;

        private Block(int type, int left, int top, int right, int bottom) {
            this.type = type;
            rect = new Rect(left, top, right, bottom);
        }

        private Paint getPaint() {
            switch (type) {
                case TYPE_FLOOR:
                    return PAINT_FLOOR;
                case TYPE_WALL:
                    return PAINT_WALL;
            }
            return null;
        }

        private void draw(Canvas canvas) {
            canvas.drawRect(rect, getPaint());
        }
    }
 }
