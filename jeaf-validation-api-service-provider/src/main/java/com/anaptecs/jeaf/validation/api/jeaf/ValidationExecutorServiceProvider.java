/**
 * Copyright 2004 - 2022 anaptecs GmbH, Burgstr. 96, 72764 Reutlingen, Germany
 *
 * All rights reserved.
 */
package com.anaptecs.jeaf.validation.api.jeaf;

import com.anaptecs.jeaf.core.api.ServiceProvider;
import com.anaptecs.jeaf.validation.api.ValidationExecutor;

/**
 * Interface enables {@link ValidationExecutor} to be used as JEAF Service Provider.
 * 
 * @author JEAF Development Team
 */
public interface ValidationExecutorServiceProvider extends ValidationExecutor, ServiceProvider {
}
