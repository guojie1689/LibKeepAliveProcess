package com.joyveb.lkap.uppriority;

import android.app.Service;

/**
 * 提权策略
 */
public interface IUpPriorityStrategy {

    void upPriority(Service service, Class<? extends Service> classes);

}
