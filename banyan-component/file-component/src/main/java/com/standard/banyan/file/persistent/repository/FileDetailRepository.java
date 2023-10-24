package com.standard.banyan.file.persistent.repository;

import cn.xuyanwu.spring.file.storage.recorder.FileRecorder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.standard.banyan.file.persistent.entity.FileDetailDO;

/**
 * <p>
 * 文件记录表 服务类
 * </p>
 *
 * @author duanjigang
 * @since 2023-03-06
 */
public interface FileDetailRepository extends IService<FileDetailDO>, FileRecorder {

}
