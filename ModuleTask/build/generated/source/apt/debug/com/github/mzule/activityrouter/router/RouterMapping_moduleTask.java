package com.github.mzule.activityrouter.router;

import cn.com.task.TaskActivity;

public final class RouterMapping_moduleTask {
  public static final void map() {
    java.util.Map<String,String> transfer = null;
    com.github.mzule.activityrouter.router.ExtraTypes extraTypes;

    transfer = null;
    extraTypes = new com.github.mzule.activityrouter.router.ExtraTypes();
    extraTypes.setTransfer(transfer);
    com.github.mzule.activityrouter.router.Routers.map("task_list", TaskActivity.class, null, extraTypes);

  }
}
