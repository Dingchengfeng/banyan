package com.standard.banyan.kernel.resource.node;

import com.tiansu.nts.rcs.imap.manage.resource.TempResource;
import com.tiansu.nts.rcs.model.Node;

/**
 * 临时生成的节点
 *
 * @author dingchengfeng
 * @date 2023/07/04
 */
public class TempNode extends TempResource implements Node {
    private final Double x;

    private final Double y;

    public TempNode(String mapName, Double x, Double y) {
        super(Name.NODE, Type.TEMP, mapName, genLocalId(x, y));
        this.x = x;
        this.y = y;
    }

    public static String genLocalId(Double x, Double y) {
        return "(" + String.format("%.2f",x) + "," + String.format("%.2f",y) + ")";
    }

    @Override
    public Double getX() {
        return x;
    }

    @Override
    public Double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "TempNode{" + "x=" + x + ", y=" + y + '}';
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
