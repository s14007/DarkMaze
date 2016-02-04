package jp.ac.it_college.std.s14007.android.darkmaze;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;


public class Map implements Player.OnMoveListener {
    private int blockSize;
    private int horizontalBlockNum;
    private int verticalBlockNum;

    private Block[][] blocks;
    private Block[][] targetBlock = new Block[3][3];

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

    @Override
    public boolean canMove(int left, int top, int right, int bottom) {
        int verticalBlock = top / blockSize;
        int horizontalBlock = left / blockSize;

        /*for (int y = 0; y < verticalBlockNum; y++) {
            for (int x = 0; x < horizontalBlockNum; x++) {
                if (blocks[y][x] .type == Block.TYPE_WALL
                        && blocks[y][x].rect.intersects(left, top, right, bottom)) {
                    return false;
                }
            }
        }*/

        seTargetBlock(verticalBlock, horizontalBlock);

        int yLength = targetBlock.length;
        int xLength = targetBlock[0].length;

        for (int y = 0; y < yLength; y++) {
            for (int x = 0; x < xLength; x++) {
                if (targetBlock[y][x] == null) {
                    continue;
                }
                if (targetBlock[y][x].type == Block.TYPE_WALL
                        && targetBlock[y][x].rect.intersects(left, top, right, bottom)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void seTargetBlock(int verticalBlockNum, int horizontalBlockNum) {
        targetBlock[1][1] = getBlock(verticalBlockNum, horizontalBlockNum);

        targetBlock[0][0] = getBlock(verticalBlockNum - 1, horizontalBlockNum - 1);
        targetBlock[0][1] = getBlock(verticalBlockNum - 1, horizontalBlockNum);
        targetBlock[0][2] = getBlock(verticalBlockNum - 1, horizontalBlockNum + 1);

        targetBlock[1][0] = getBlock(verticalBlockNum, horizontalBlockNum - 1);
        targetBlock[1][2] = getBlock(verticalBlockNum, horizontalBlockNum + 1);

        targetBlock[2][0] = getBlock(verticalBlockNum + 1, horizontalBlockNum - 1);
        targetBlock[2][1] = getBlock(verticalBlockNum + 1, horizontalBlockNum);
        targetBlock[2][2] = getBlock(verticalBlockNum + 1, horizontalBlockNum + 1);
    }

    private Block getBlock(int y, int x) {
        if (y < 0 || x < 0 || y >= verticalBlockNum || x >= horizontalBlockNum) {
            return null;
        }
        return blocks[y][x];
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
