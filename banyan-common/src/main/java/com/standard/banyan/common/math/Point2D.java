package com.standard.banyan.common.math;


/**
 * @author dingchengfeng
 * @description 平面上的点
 * @date 2023/07/31
 */
public class Point2D<T extends Number> {
    private final T x;

    private final T y;

    public T getX() {
        return x;
    }

    public T getY() {
        return y;
    }

    public Point2D(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public Double distanceTo(Point2D<? extends Number> that) {
        return Math.hypot(this.x.doubleValue() - that.x.doubleValue(), this.y.doubleValue() - that.y.doubleValue());
    }

    public Double distanceToLineSegment(Point2D<? extends Number> b, Point2D<? extends Number> c) {
        Double bc = b.distanceTo(c);
        Double ac = this.distanceTo(c);
        Double ab = this.distanceTo(b);
        //三点组成的三角形,b点是钝角或直角
        if (ac * ac >= bc * bc + ab * ab) {
            return ab;
        }
        //三点组成的三角形,c点是钝角或直角
        if (ab * ab >= bc * bc + ac * ac) {
            return ac;
        }

        // 点到线段的垂足在线段上，求垂线段长度
        // 半周长
        Double p = (bc + ab + ac) / 2;
        // 海伦公式求面积
        double s = Math.sqrt(p * (p - bc) * (p - ab) * (p - ac));
        return 2 * s / bc;
    }
}
