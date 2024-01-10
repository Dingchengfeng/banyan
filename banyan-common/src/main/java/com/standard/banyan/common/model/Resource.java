package com.standard.banyan.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

/**
 * 资源
 *
 * @author dingchengfeng
 * @date 2023/07/04
 */
public interface Resource {

    /**
     * 获取全局id
     *
     * @return GlobalId
     * @since 3.1.0
     */
    GlobalId getGlobalId();

    /**
     * 资源名
     *
     */
    enum Name {
        /**
         * 节点
         */
        NODE,

        /**
         * 边
         */
        EDGE,

        /**
         * 站点
         */
        STATION
    }

    /**
     * 资源类型
     *
     * @author dingchengfeng
     * @date 2023/07/04
     */
    enum Type {
        /**
         * 地图上的资源
         */
        MAP,
        /**
         * 临时生成的资源
         */
        TEMP
    }

    /**
     * 资源全局id
     *
     * @author dingchengfeng
     * @date 2023/07/04
     */
    @Getter
    @AllArgsConstructor
    class GlobalId implements Serializable {
        /**
         * 资源名称
         */
        private Name name;

        /**
         * 资源类型
         */
        private Type type;

        /**
         * 地图名
         */
        private String mapName;

        /**
         * 资源在地图上的id
         */
        private String localId;

        @Override
        public String toString() {
            return name + "-" + type + "-" + mapName + "." + localId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            GlobalId id = (GlobalId)o;
            return name == id.name && type == id.type && Objects.equals(mapName, id.mapName) && Objects.equals(localId,
                id.localId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, type, mapName, localId);
        }

    }
}
