package com.standard.banyan.common.math;

/**
 * 向量
 *
 * @author dingchengfeng
 * @description 封装向量相关方法
 * @date 2023/07/25
 */
public class Vector<T extends Number> {
    private final Double x;

    private final Double y;

    /**
     * x方向单位向量
     */
    private static final Vector<Integer> BASE_VECTOR_X = new Vector<>(1,0);

    public Vector(T x, T y) {
        this.x = x.doubleValue();
        this.y = y.doubleValue();
    }


    /**
     * 利用余弦定理计算两个向量的夹角弧度(单位：rad)
     * 从this旋转到other的最小角度，符号表示方向
     *
     * @param other 另一个向量
     * @return （-π, π]
     */
    public Double calculateRadian(Vector<? extends Number> other) {
        // 夹角余弦值（可根据余弦定理推导出）
        double value =
            (x * other.x + y * other.y) / (Math.sqrt(x * x + y * y) * Math.sqrt(other.x * other.x + other.y * other.y));

        return this.calculateDirection(other) * Math.acos(value);
    }

    /**
     * 计算当前this向量旋转到other向量的旋转方向，旋转角度小于180度的方向（向量平行的话，按逆时针旋转）
     * 向量叉乘:二维向量叉乘得到垂直于这两个向量的法向量且遵循右手定则
     * （若坐标系是满足右手定则的，当右手的四指从a以不超过180度的转角转向b时，竖起的大拇指指向是c的方向。c = a ∧ b）
     *
     * @return -1 顺时针，1 逆时针
     */
    public Integer calculateDirection(Vector<? extends Number> other) {
        if ((x * other.y - other.x * y) < 0) {
            return -1;
        } else {
            return 1;
        }
    }

    /**
     * 向量弧度：和x轴正方向夹角（单位1弧度）（-π,π]
     *
     * @return 弧度
     */
    public Double radian() {
        return BASE_VECTOR_X.calculateRadian(this);
    }

    /**
     * this投影到that的长度
     *
     * @param that 另一个向量
     * @return 投影到that的长度
     */
    public Double projectionTo(Vector<? extends Number> that) {
        return Math.abs(this.x * that.x + this.y * that.y) / Math.sqrt(that.x * that.x + that.y * that.y);
    }

    /**
     * 模长
     *
     * @return 模长
     */
    public Double modulo() {
        return Math.sqrt(x * x + y * y);
    }

}
