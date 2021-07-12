package org.wso2.carbon.identity.api.server.choreo.management.common.factory;


import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.identity.configuration.mgt.core.ConfigurationManager;


/**
 * Service holder class for choreo configuration management.
 */
public class ConfigurationMgtOSGiServiceFactory extends AbstractFactoryBean<ConfigurationManager> {

    private ConfigurationManager configurationManager;

    @Override
    public Class<?> getObjectType() {

        return Object.class;
    }

    @Override
    protected ConfigurationManager createInstance() throws Exception {

        if (this.configurationManager == null) {
            ConfigurationManager taskOperationService = (ConfigurationManager) PrivilegedCarbonContext.
                    getThreadLocalCarbonContext().getOSGiService(ConfigurationManager.class, null);

            if (taskOperationService != null) {
                this.configurationManager = taskOperationService;
            } else {
                throw new Exception("Unable to retrieve ConfigurationManager service.");
            }
        }
        return this.configurationManager;
    }
}

