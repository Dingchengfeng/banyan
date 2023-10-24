package com.standard.banyan.file.persistent.repository.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.text.CharSequenceUtil;
import cn.xuyanwu.spring.file.storage.FileInfo;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.standard.banyan.file.persistent.entity.FileDetailDO;
import com.standard.banyan.file.persistent.mapper.FileDetailMapper;
import com.standard.banyan.file.persistent.repository.FileDetailRepository;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文件记录表 服务实现类
 * </p>
 *
 * @author duanjigang
 * @since 2023-03-06
 */
@Service
public class FileDetailRepositoryImpl extends ServiceImpl<FileDetailMapper, FileDetailDO> implements FileDetailRepository {

    @Override
    public boolean record(FileInfo info) {
        Long id = this.lambdaQuery()
                .eq(FileDetailDO::getUrl, info.getUrl())
                .select(FileDetailDO::getId)
                .oneOpt()
                .map(FileDetailDO::getId)
                .orElse(null);
        FileDetailDO detailDO = BeanUtil.copyProperties(info, FileDetailDO.class, "attr");
        if (info.getAttr() != null) {
            String attrString = JSON.toJSONString(info.getAttr());
            detailDO.setAttr(attrString);
        }
        detailDO.setId(id);
        boolean b = saveOrUpdate(detailDO);
        if (b) {
            info.setId(Long.toString(detailDO.getId()));
        }
        return b;
    }

    @Override
    public FileInfo getByUrl(String url) {
        FileDetailDO detailDO = lambdaQuery().eq(FileDetailDO::getUrl, url).one();
        if (detailDO == null) {
            return null;
        }
        FileInfo info = BeanUtil.copyProperties(detailDO, FileInfo.class, "attr");
        info.setId(Long.toString(detailDO.getId()));
        if (CharSequenceUtil.isNotBlank(detailDO.getAttr())) {
            info.setAttr(JSON.parseObject(detailDO.getAttr(), Dict.class));
        }
        return info;
    }

    @Override
    public boolean delete(String url) {
        return lambdaUpdate().eq(FileDetailDO::getUrl, url).remove();
    }
}
