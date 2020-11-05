package ru.cyberbiology.test.util;

import java.awt.*;
import java.util.Random;

public class MathUtils {
    public static final int diffRnd = 3;
    public static final int maxRnd = diffRnd * 6;

    public static Point addX(Point to, int addX) {
        return new Point(to.x + addX, to.y);
    }
    public static Point addY(Point to, int addY) {
        return new Point(to.x, to.y + addY);
    }
    public static Point addXY(Point to, int addXY) {
        return new Point(to.x + addXY, to.y + addXY);
    }

    public static Point diagXY(int i, int j, int max) {
        return new Point(j, i);
    }
    public static Point diagXYi(int i, int j, int max) {
        return new Point(max - j - 1, max - i - 1);
    }
    public static Point vertXY(int i, int j, int max) {
        return new Point(max - i - 1, j);
    }
    public static Point horizXY(int i, int j, int max) {
        return new Point(i, max - j - 1);
    }
    public static Point quartXY(int i, int j, int max) {
        return quartXY(i, j, max, 2);
    }
    public static Point dblQuartXY(int i, int j, int max) {
        return quartXY(i, j, max, 4);
    }
    private final static Random r = new Random();
    public static TriFunction<Point> fnc = getFnc(r.nextInt(4));
    public static Point quartXY(int i, int j, int max, int div, TriFunction<Point>... fncs) {
        int subMax = max / div;
        TriFunction<Point> func = fnc;
        for (int ci = 0; ci < subMax; ci++) {
            for (int cj = 0; cj < subMax; cj++) {
                if (i >= subMax * ci && i < subMax * (ci + 1)) {
                    if (j >= subMax * cj && j < subMax * (cj + 1)) {
                        if (fncs.length > cj * div + ci) {
                            func = fncs[cj * div + ci];
                        }
                        return addY(addX(func.apply(i - subMax * ci,
                                j - subMax * cj, subMax), subMax * ci), subMax * cj);
                    }
                }
            }
        }
        return new Point(i, j);
    }

    public static TriFunction<Point> getFnc(int rnd) {
        switch (rnd / diffRnd) {
            case 0: return MathUtils::diagXY;
            case 1: return MathUtils::diagXYi;
            case 2: return MathUtils::vertXY;
            case 3: return MathUtils::horizXY;
            case 4: return MathUtils::quartXY;
            case 5: return MathUtils::dblQuartXY;
        }
        return null;
    }

    public static Point addFnc(int rnd, TriFunction<Point> fnc, Point ij, int max) {
        switch (rnd % diffRnd) {
            case 0: return fnc.apply(ij.x, ij.y, max);
            case 1: return addX(fnc.apply(ij.x, ij.y, max), max);
            case 2: return addX(fnc.apply(ij.x, ij.y, max), max / 2);
        }
        return new Point(-1, -1);
    }

    public static Point getIJ(int rnd, int i, int j, int max) {
        switch (rnd % diffRnd) {
            case 0: return new Point(i, j);
            case 1: return new Point(i - max, j);
            case 2: return new Point(i - max / 2, j);
        }
        return new Point(-1, -1);
    }

    public static boolean intersect(int rnd, int i, int j, int wd, int ht, int max) {
        switch (rnd % diffRnd) {
            case 0:
                return  i < max && j < max;
            case 1:
                return  i >= wd - max;
            case 2:
                return  i >= max / 2 && i < wd - max / 2;
        }
        return false;
    }

    public static Point convertXY(int rnd, int i, int j, int max) {
        return addFnc(rnd, getFnc(rnd), getIJ(rnd, i, j, max), max);
    }
}
