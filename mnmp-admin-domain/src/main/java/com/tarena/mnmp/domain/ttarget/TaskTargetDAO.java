package com.tarena.mnmp.domain.ttarget;

import com.tarena.mnmp.domain.TaskTargetDO;
import java.util.List;

public interface TaskTargetDAO {
    int deleteByPrimaryKey(Long id);

    int insert(TaskTargetDO record);

    int insertSelective(TaskTargetDO record);

    TaskTargetDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TaskTargetDO record);

    int updateByPrimaryKey(TaskTargetDO record);

    int insertBatch(List<TaskTargetDO> list);
}