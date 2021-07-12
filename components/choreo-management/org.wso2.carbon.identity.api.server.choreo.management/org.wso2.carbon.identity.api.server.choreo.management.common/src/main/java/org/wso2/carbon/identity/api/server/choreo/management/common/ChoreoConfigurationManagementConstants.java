package org.wso2.carbon.identity.api.server.choreo.management.common;


/**
 * Choreo configuration management related constant class.
 */
public class ChoreoConfigurationManagementConstants {

    public static final String CHOREO_ERROR_PREFIX = "NSM-";

    public static final String CHOREO_CONFIGURATION_CONTEXT_PATH = "/ChoreoConfigurations";
    public static final String CHOREO_RESOURCE_TYPE = "Choreo configuration";
    public static final String PLUS = "+";
    public static final String URL_ENCODED_SPACE = "%20";
    public static final String RESOURCE_NOT_EXISTS_ERROR_CODE = "CONFIGM_00017";
    public static final String CONFIG_MGT_ERROR_CODE_DELIMITER = "_";
    public static final String CORRELATION_ID_MDC = "Correlation-ID";
    public static final String SERVER_API_PATH_COMPONENT = "/api/server";
    public static final String V1_API_PATH_COMPONENT = "/v1";

    public static final String TENANT_NAME_FROM_CONTEXT = "TenantNameFromContext";
    public static final String TENANT_CONTEXT_PATH_COMPONENT = "/t/%s";

    // choreo configuration main properties.
    public static final String ENDPOINT_URL = "endPointURL";
    public static final String API_KEY = "apiKey";

    /**
     * Enums for error messages.
     */
    public enum ErrorMessage {

        ERROR_CODE_END_POINT_URI_NOT_SPECIFIED("CMT-60001", "Empty end point URI", "End point URI is " +
                "not specified in the request"),
        ERROR_CODE_REFERENCE_NAME_NOT_SPECIFIED("CMT-60002", "Empty reference name",
                "End point reference name is not specified in the request"),
        // Client errors 600xx.
        ERROR_CODE_CONFLICT_CONFIGURATION("60002", "Configuration already exists.",
                "There exists a Choreo Configuration: %s in the tenant."),

        // Server errors 650xx.
        ERROR_CODE_ERROR_GETTING_CHOREO_CONFIGURATION("65003", "Error while getting Choreo configurations.",
                "Error while retrieving Choreo configurations resource: %s."),
        ERROR_CODE_ERROR_ADDING_CHOREO_CONFIGURATION("65004", "Unable to add Choreo configuration.",
                "Server encountered an error while adding the Choreo configuration: %s"),
        ERROR_CODE_ERROR_DELETING_CHOREO_CONFIGURATION("65005", "Unable to delete Choreo configuration.",
                "Server encountered an error while deleting the Choreo configuration: %s"),
        ERROR_CODE_ERROR_GETTING_CHOREO_CONFIGURATION_BY_TYPE("65006", "Error while getting Choreo configuration.",
                "Error while retrieving %s Choreo configuration."),
        ERROR_CODE_ERROR_UPDATING_CHORE_CONFIGURATION("65007", "Unable to update Choreo configuration.",
                "Error while updating Choreo configuration: %s.");

        private final String code;
        private final String message;
        private final String description;

        ErrorMessage(String code, String message, String description) {

            this.code = code;
            this.message = message;
            this.description = description;
        }

        public String getCode() {

            return CHOREO_ERROR_PREFIX + code;
        }

        public String getMessage() {

            return message;
        }

        public String getDescription() {

            return description;
        }

        @Override
        public String toString() {

            return code + " | " + message;
        }
    }

}
