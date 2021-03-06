package ru.cyberbiology.test.util;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

@SuppressWarnings("WeakerAccess")
public class MathUtils {
    public static final int maxFnc = 4;
    public static final int addFnc = 8;
    public static final int cplxFnc = 4;
    public static final int multFnc = 4;
    public static final int diffRnd = 3; //4;
    public static final int maxRnd = diffRnd * (maxFnc + addFnc + cplxFnc + multFnc);

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
    public static Point rombXY(int i, int j, int max) {
        int subMax = (max / 2) * 2;
        if (i > subMax || j > subMax) {
            return new Point(i, j);
        }
        if (i + j < max / 2 || i + j >= max + max / 2 - 1) {
            return new Point(i, j);
        } else if (i - max / 2 > j - 1 || j - max / 2 > i - 1) {
            return new Point(i, j);
        }
        return fnc.apply(i, j, subMax);
    }
    public static Point rombXYi(int i, int j, int max) {
        int subMax = (max / 2) * 2;
        if (i > subMax || j > subMax) {
            return new Point(i, j);
        }
        if (i + j < max / 2 || i + j >= max + max / 2 - 1) {
            return fnc.apply(i, j, subMax);
        } else if (i - max / 2 > j - 1 || j - max / 2 > i - 1) {
            return fnc.apply(i, j, subMax);
        }
        return new Point(i, j);
    }

    public static Point squareXY(int i, int j, int max) {
        int subMax = max / 4;
        if (i < subMax || i >= subMax * 3 || j < subMax || j >= subMax * 3) {
            return new Point(i, j);
        }
        return addXY(fnc.apply(i - subMax, j - subMax, subMax * 2), subMax);
    }

    public static Point squareXYi(int i, int j, int max) {
        int subMax = max / 4;
        if ((i <= subMax || i > subMax * 3) || (j <= subMax || j > subMax * 3)) {
            return fnc.apply(i, j, max);
        }
        return new Point(i, j);
    }

    public static Point crossXY(int i, int j, int max) {
        int subMax = max / 4;
        if ((i > subMax) && (i <= (max - subMax - 1))
                || (j > subMax) && (j <= (max - subMax - 1))) {
            return new Point(i, j);
        }
        return fnc.apply(i, j, max + 1);
    }

    public static Point crossXYi(int i, int j, int max) {
        int subMax = max / 4;
        if ((i > subMax) && (i <= (max - subMax - 1))
                || (j > subMax) && (j <= (max - subMax - 1))) {
            return fnc.apply(i, j, max + 1);
        }
        return new Point(i, j);
    }

    public static Point roundXY(int i, int j, int max) {
        int minMax = (max / 4) * 4;
        if (i == 0 || j == 0 || i >= minMax || j >= minMax) {
            return new Point(i, j);
        }
        int subMax = minMax / 2;
        if ((i - subMax) * (i - subMax) + (j - subMax) * (j - subMax)
                > (subMax) * (subMax)) {
            return new Point(i, j);
        }
        return fnc.apply(i, j, minMax + 1);
    }

    public static Point roundXYi(int i, int j, int max) {
        int minMax = (max / 4) * 4;
        if (i == 0 || j == 0 || i >= minMax || j >= minMax) {
            return new Point(i, j);
        }
        int subMax = minMax / 2;
        if ((i - subMax) * (i - subMax) + (j - subMax) * (j - subMax)
                < (subMax) * (subMax)) {
            return new Point(i, j);
        }
        return fnc.apply(i, j, minMax + 1);
    }

    public static Point quartXY(int i, int j, int max) {
        //noinspection unchecked
        return quartXY(i, j, max, 2);
    }
    public static Point tripleXY(int i, int j, int max) {
        //noinspection unchecked
        return quartXY(i, j, max, 3);
    }
    public static Point dblQuartXY(int i, int j, int max) {
        //noinspection unchecked
        return quartXY(i, j, max, 4);
    }
    public static Point quintXY(int i, int j, int max) {
        //noinspection unchecked
        return quartXY(i, j, max, 5);
    }

    @SuppressWarnings("unchecked")
    public static Point quartXY(int i, int j, int max, int div, TriFunction<Point>... fncs) {
        int subMax = max / div;
        if (i >= subMax * div || j >= subMax * div) {
            return new Point(i, j);
        }
        TriFunction<Point> func;
        for (int ci = 0; ci < subMax; ci++) {
            for (int cj = 0; cj < subMax; cj++) {
                if (i >= subMax * ci && i < subMax * (ci + 1)) {
                    if (j >= subMax * cj && j < subMax * (cj + 1)) {
                        int fncInd = cj * div + ci;
                        if (fncs.length > fncInd) {
                            func = fncs[fncInd];
                        } else {
                            if (fncHolder.fncMap.get(fncInd) == null) {
                                fncHolder.fncMap.put(fncInd, getFnc(r.nextInt((maxFnc + addFnc) * diffRnd)));
                            }
                            func = fncHolder.fncMap.get(fncInd);
                        }
                        return addY(addX(func.apply(i - subMax * ci,
                                j - subMax * cj, subMax), subMax * ci), subMax * cj);
                    }
                }
            }
        }
        return new Point(i, j);
    }

    @SuppressWarnings("unchecked")
    public static TriFunction<Point> multiple(TriFunction<Point>... funcs) {
        return (i, j, k) -> {
          var point =  new Point(i, j);
          for (int ii = 0; ii < funcs.length; ii++) {
              MathUtils.fnc = fncHolder.fncMap.get(ii);
              point = funcs[ii].apply(point.x, point.y, k);
          }
          return point;
        };
    }

    @SuppressWarnings("unchecked")
    public static TriFunction<Point> getFnc(int rnd) {
        switch (rnd / diffRnd) {
            // simple functions
            case 0: return MathUtils::diagXY;
            case 1: return MathUtils::diagXYi;
            case 2: return MathUtils::vertXY;
            case 3: return MathUtils::horizXY;
            // romb functions
            case 4: return MathUtils::rombXY;
            case 5: return MathUtils::rombXYi;
            case 6: return MathUtils::squareXY;
            case 7: return MathUtils::squareXYi;
            case 8: return MathUtils::roundXY;
            case 9: return MathUtils::roundXYi;
            case 10: return MathUtils::crossXY;
            case 11: return MathUtils::crossXYi;
            // multiple functions
            case 12: return multiple(MathUtils::rombXY, MathUtils::rombXYi);
            case 13: return multiple(MathUtils::squareXY, MathUtils::squareXYi);
            case 14: return multiple(MathUtils::roundXY, MathUtils::roundXYi);
            case 15: return multiple(MathUtils::crossXY, MathUtils::crossXYi);
            // complex functions
            case 16: return MathUtils::quartXY;
            case 17: return MathUtils::tripleXY;
            case 18: return MathUtils::dblQuartXY;
            case 19: return MathUtils::quintXY;
        }
        return MathUtils::diagXY;
    }

    @SuppressWarnings("unused")
    public static Point addFnc(int rnd, int i, TriFunction<Point> fnc, Point ij, int max) {
        switch (rnd % diffRnd) {
            case 0: return fnc.apply(ij.x, ij.y, max);
            case 1: return addX(fnc.apply(ij.x, ij.y, max), max);
            case 2: return addX(fnc.apply(ij.x, ij.y, max), max / 2);
//            case 3: return addFnc(i < max ? 0 : 1, i, fnc, ij, max);
        }
        return new Point(-1, -1);
    }

    public static Point getIJ(int rnd, int i, int j, int max) {
        switch (rnd % diffRnd) {
            case 0: return new Point(i, j);
            case 1: return new Point(i - max, j);
            case 2: return new Point(i - max / 2, j);
//            case 3: return new Point(i < max ? i : i - max, j);
        }
        return new Point(-1, -1);
    }

    @SuppressWarnings("unused")
    public static boolean intersect(int rnd, int i, int j, int wd, int ht, int max) {
        switch (rnd % diffRnd) {
            case 0:
                return  i < max && j < max;
            case 1:
                return  i >= wd - max;
            case 2:
                return  i >= max / 2 && i < wd - max / 2;
//            case 3:
//                return  j < max && (i < max || i >= wd - max);
        }
        return false;
    }

    public static Point convertXY(int rnd, int i, int j, int max) {
        return addFnc(rnd, i, getFnc(rnd), getIJ(rnd, i, j, max), max);
    }

    private final static Random r = new Random();
    private static TriFunction<Point> fnc = getFnc(r.nextInt(maxFnc * diffRnd));
    //todo need to be split by extrenal initialization
    private static TriFunction<Point> fnc1 = getFnc(r.nextInt(maxFnc * diffRnd));
    private static TriFunction<Point> fnc2 = getFnc(r.nextInt(maxFnc * diffRnd));

    public static class FncHolder {
        public final Map<Integer, TriFunction<Point>> fncMap = new HashMap<>();
    }
    private static FncHolder fncHolder = new FncHolder();

    public static void setFnc(TriFunction<Point> fnc) {
        MathUtils.fnc2 = MathUtils.fnc1;
        MathUtils.fnc1 = MathUtils.fnc;
        MathUtils.fnc = fnc;
    }

    public static void setFncHolder(FncHolder fncHolder) {
        MathUtils.fncHolder = fncHolder;
    }

    public static void setupMathFnc(TriFunction<Point> fnc, Supplier<TriFunction<Point>> mapFnc) {
        MathUtils.setFnc(fnc);
        FncHolder fncHolder = new FncHolder();
        fncHolder.fncMap.put(0, MathUtils.fnc);
        fncHolder.fncMap.put(1, MathUtils.fnc1);
        fncHolder.fncMap.put(2, MathUtils.fnc2);
        for (int ii = 3; ii < 99; ii++) {
            fncHolder.fncMap.put(ii, mapFnc.get());
        }
        MathUtils.setFncHolder(fncHolder);
    }
    @SuppressWarnings({"unchecked", "unused"})
    public static void setupMathFnc(TriFunction<Point> fnc, TriFunction<Point>... mapFncs) {
        MathUtils.setFnc(fnc);
        FncHolder fncHolder = new FncHolder();
        for (int ii = 0; ii < mapFncs.length; ii++) {
            fncHolder.fncMap.put(ii, mapFncs[ii]);
        }
        MathUtils.setFncHolder(fncHolder);
    }
}
