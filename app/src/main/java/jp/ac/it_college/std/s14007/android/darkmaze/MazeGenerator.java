package jp.ac.it_college.std.s14007.android.darkmaze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class MazeGenerator {
    public static final int FLOOR = 0;
    public static final int WALL = 1;
    public static final int START = 2;
    public static final int GOAL = 3;
    public static final int POLL = -1;

    public enum Direction {
        TOP,
        LEFT,
        RIGHT,
        BOTTOM,
    }

    public static int[][] getMap(int seed, int horizontalBlockNum, int verticalBlockNum) {
        int[][] result = new int[verticalBlockNum][horizontalBlockNum];

        for (int y = 0; y < verticalBlockNum; y++) {
            for (int x = 0; x < horizontalBlockNum; x++) {
                if (y == 0 || y == verticalBlockNum - 1) {
                    result[y][x] = WALL;
                } else if (x == 0 || x == horizontalBlockNum - 1) {
                    result[y][x] = WALL;
                } else if (x > 1 && x % 2 == 0 && y > 1 && y % 2 == 0) {
                    result[y][x] = POLL;
                } else {
                    result[y][x] = FLOOR;
                }
            }
        }

        result = generateMaze(seed, result);
        return result;
    }

    private static int[][] generateMaze(int seed, int[][] map) {
        Random rand = new Random(seed);

        int horizontal = map[0].length;
        int vertical = map.length;

        for (int y = 0; y < vertical; y++) {
            for (int x = 0; x < horizontal; x++) {
                if (map[y][x] == POLL) {
                    List<Direction> directionList = null;

                    if (y == 1) {
                        directionList = new ArrayList<>(
                                Arrays.asList(Direction.TOP, Direction.LEFT,
                                        Direction.RIGHT, Direction.BOTTOM));
                    } else {
                        directionList = new ArrayList<>(
                                Arrays.asList(Direction.LEFT, Direction.RIGHT,
                                        Direction.BOTTOM));
                    }

                    do {
                        Direction direction = directionList.get(
                                rand.nextInt(directionList.size()));
                        if (setDirection(y, x, direction, map)) {
                            break;
                        } else {
                            directionList.remove(direction);
                        }
                    } while (directionList.size() > 0);
                }
            }
        }
        return map;
    }

    private static boolean setDirection(int y, int x, Direction direction, int[][] map) {
        map[y][x] = WALL;

        switch (direction) {
            case LEFT:
                x -= 1;
                break;
            case RIGHT:
                x += 1;
                break;
            case BOTTOM:
                y -= 1;
                break;
            case TOP:
                y += 1;
                break;
        }

        if (x < 0 || y < 0 || x >= map[0].length || y >= map.length) {
            return false;
        }

        if (map[y][x] == WALL) {
            return false;
        }

        map[y][x] = WALL;

        return true;
    }


}
