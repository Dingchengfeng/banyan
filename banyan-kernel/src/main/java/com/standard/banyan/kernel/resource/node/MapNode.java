package com.standard.banyan.kernel.resource.node;

import com.tiansu.nts.rcs.imap.entity.MapJson;
import com.tiansu.nts.rcs.imap.manage.resource.MapResource;
import com.tiansu.nts.rcs.imap.manage.resource.pose.Station;
import com.tiansu.nts.rcs.model.Node;

/**
 * 地图上绘制的节点
 *
 * @author dingchengfeng
 * @date 2023/07/04
 */
public class MapNode extends MapResource implements Node {
    private final MapJson.Data.Node node;
    private Station station;
    public MapNode(String mapName, MapJson.Data.Node node) {
        super(Name.NODE, Type.MAP, mapName, node.getId().toString());
        this.node = node;
    }

    public void setStation(Station station){
        this.station = station;
    }

    public Station getStation(){
        return station;
    }

    @Override
    public Double getX() {
        return node.getX();
    }

    @Override
    public Double getY() {
        return node.getY();
    }

    @Override
    public String toString() {
        return "MapNode{" + "node=" + node + '}';
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
