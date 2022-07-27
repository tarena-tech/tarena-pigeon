package com.tarena.mnmp.domain.ttarget;

import com.tarena.mnmp.domain.TaskTargetDO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskTargetService {


    @Autowired
    private TaskTargetDAO taskTargetDAO;

    public void saveBatch(List<TaskTargetDO> list) {
        taskTargetDAO.insertBatch(list);
    }
}
