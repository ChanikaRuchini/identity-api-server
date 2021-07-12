package org.wso2.carbon.identity.api.server.choreo.management.common;

import org.wso2.carbon.identity.configuration.mgt.core.ConfigurationManager;

/**
 * Service holder class for choreo configuration management.
 */
public class ChoreoConfigurationManagementDataHolder {

    private static ConfigurationManager choreoConfigurationConfigManager;

    /**
     * Get ConfigurationManager OSGi service.
     *
     * @return NotificationSenderConfig Manager.
     */
    public static ConfigurationManager getChoreoConfigurationConfigManager() {

        return choreoConfigurationConfigManager;
    }

    /**
     * Set ConfigurationManager OSGi service.
     *
     * @param choreoConfigurationConfigManager Configuration Manager.
     */
    public static void setChoreoConfigurationConfigManager(ConfigurationManager choreoConfigurationConfigManager) {

        ChoreoConfigurationManagementDataHolder.choreoConfigurationConfigManager = choreoConfigurationConfigManager;
    }

}
