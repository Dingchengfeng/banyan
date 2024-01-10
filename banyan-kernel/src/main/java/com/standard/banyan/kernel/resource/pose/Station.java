package com.standard.banyan.kernel.resource.pose;

import com.tiansu.nts.rcs.imap.entity.MapJson;
import com.tiansu.nts.rcs.imap.manage.resource.MapResource;
import com.tiansu.nts.rcs.model.Node;
import com.tiansu.nts.rcs.model.Resource;

/**
 * 站点
 *
 * @author dingchengfeng
 * @date 2023/07/04
 */
public class Station extends MapResource implements Pose {
    private final MapJson.Data.Station mapStation;

    private final Type type;

    private Node node;

    @Override
    public String toString() {
        return "Station{" + "theta=" + mapStation.getTheta() + ", node=" + node + '}';
    }

    public Station(String mapName, MapJson.Data.Station mapStation) {
        super(Resource.Name.STATION, Resource.Type.MAP, mapName, mapStation.getId().toString());
        this.mapStation = mapStation;
        this.type = Type.valueOf(mapStation.getType());
    }

    @Override
    public Double getTheta() {
        return mapStation.getTheta();
    }

    @Override
    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Type getType() {
        return type;
    }

    public Integer getNodeLocalId(){
        return mapStation.getNodeId();
    }

    public Integer getEdgeLocalId(){
        return mapStation.getEdgeId();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public enum Type {
        /**
         * 工作点
         */
        WORK,
        /**
         * 停靠点
         */
        PARK,
        /**
         * 充电点
         */
        CHARGE,
        /**
         * 电梯
         */
        LIFT
    }
}
