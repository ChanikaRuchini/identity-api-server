package org.wso2.carbon.identity.api.server.choreo.management.common;


import org.apache.log4j.MDC;

import java.util.UUID;

import static org.wso2.carbon.identity.api.server.choreo.management.common.ChoreoConfigurationManagementConstants.CORRELATION_ID_MDC;

/**
 * Util class.
 */
public class Utils {

    /**
     * Get correlation id of current thread.
     *
     * @return Correlation-id.
     */
    public static String getCorrelation() {

        if (isCorrelationIDPresent()) {

            return MDC.get(CORRELATION_ID_MDC).toString();
        }
        return UUID.randomUUID().toString();
    }

    /**
     * Check whether correlation id present in the log MDC.
     *
     * @return Whether the correlation id is present.
     */
    public static boolean isCorrelationIDPresent() {

        return MDC.get(CORRELATION_ID_MDC) != null;
    }
}
