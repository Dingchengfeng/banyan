package com.standard.banyan.kernel.resource;

import com.tiansu.nts.rcs.model.Resource;

/**
 * 世界资源
 *
 * @author dingchengfeng
 * @date 2023/07/04
 */
public abstract class WorldResource implements Resource {
    private final GlobalId globalId;


    protected WorldResource(Name name, Type type, String mapName, String localId) {
        this.globalId = new GlobalId(name, type, mapName, localId);
    }

    @Override
    public GlobalId getGlobalId() {
        return globalId;
    }

    @Override
    public String toString() {
        return globalId.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorldResource that = (WorldResource)o;
        return globalId.equals(that.globalId);
    }

    @Override
    public int hashCode() {
        return globalId.hashCode();
    }
}
