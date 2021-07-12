package org.wso2.carbon.identity.api.server.choreo.management.v1.core;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.identity.api.server.choreo.management.common.ChoreoConfigurationManagementConstants;
import org.wso2.carbon.identity.api.server.choreo.management.common.ChoreoConfigurationManagementDataHolder;
import org.wso2.carbon.identity.api.server.choreo.management.common.error.APIError;
import org.wso2.carbon.identity.api.server.choreo.management.common.error.ErrorResponse;
import org.wso2.carbon.identity.api.server.choreo.management.v1.model.ChoreoConfiugurationAdd;
import org.wso2.carbon.identity.api.server.choreo.management.v1.model.Choreoconfiguration;
import org.wso2.carbon.identity.api.server.choreo.management.v1.model.ChoreoconfigurationUpdateRequest;

import org.wso2.carbon.identity.configuration.mgt.core.exception.ConfigurationManagementClientException;
import org.wso2.carbon.identity.configuration.mgt.core.exception.ConfigurationManagementException;
import org.wso2.carbon.identity.configuration.mgt.core.exception.ConfigurationManagementServerException;
import org.wso2.carbon.identity.configuration.mgt.core.model.Attribute;
import org.wso2.carbon.identity.configuration.mgt.core.model.Resource;
import org.wso2.carbon.identity.configuration.mgt.core.model.Resources;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.core.Response;

import static org.wso2.carbon.identity.api.server.choreo.management.common.ChoreoConfigurationManagementConstants.API_KEY;
import static org.wso2.carbon.identity.api.server.choreo.management.common.ChoreoConfigurationManagementConstants.CHOREO_RESOURCE_TYPE;
import static org.wso2.carbon.identity.api.server.choreo.management.common.ChoreoConfigurationManagementConstants.CONFIG_MGT_ERROR_CODE_DELIMITER;
import static org.wso2.carbon.identity.api.server.choreo.management.common.ChoreoConfigurationManagementConstants.ENDPOINT_URL;
import static org.wso2.carbon.identity.api.server.choreo.management.common.ChoreoConfigurationManagementConstants.ErrorMessage.ERROR_CODE_CONFLICT_CONFIGURATION;
import static org.wso2.carbon.identity.api.server.choreo.management.common.ChoreoConfigurationManagementConstants.ErrorMessage.ERROR_CODE_END_POINT_URI_NOT_SPECIFIED;
import static org.wso2.carbon.identity.api.server.choreo.management.common.ChoreoConfigurationManagementConstants.ErrorMessage.ERROR_CODE_ERROR_ADDING_CHOREO_CONFIGURATION;
import static org.wso2.carbon.identity.api.server.choreo.management.common.ChoreoConfigurationManagementConstants.ErrorMessage.ERROR_CODE_ERROR_DELETING_CHOREO_CONFIGURATION;
import static org.wso2.carbon.identity.api.server.choreo.management.common.ChoreoConfigurationManagementConstants.ErrorMessage.ERROR_CODE_ERROR_GETTING_CHOREO_CONFIGURATION;
import static org.wso2.carbon.identity.api.server.choreo.management.common.ChoreoConfigurationManagementConstants.ErrorMessage.ERROR_CODE_ERROR_GETTING_CHOREO_CONFIGURATION_BY_TYPE;
import static org.wso2.carbon.identity.api.server.choreo.management.common.ChoreoConfigurationManagementConstants.ErrorMessage.ERROR_CODE_ERROR_UPDATING_CHORE_CONFIGURATION;
import static org.wso2.carbon.identity.api.server.choreo.management.common.ChoreoConfigurationManagementConstants.ErrorMessage.ERROR_CODE_REFERENCE_NAME_NOT_SPECIFIED;
import static org.wso2.carbon.identity.api.server.choreo.management.common.ChoreoConfigurationManagementConstants.CHOREO_ERROR_PREFIX;
import static org.wso2.carbon.identity.api.server.choreo.management.common.ChoreoConfigurationManagementConstants.RESOURCE_NOT_EXISTS_ERROR_CODE;

/**
 * Invoke internal OSGi service to perform Choreo configuration management operations.
 */
public class ChoreoConfigurationManagemenetService {

    private static final Log log = LogFactory.getLog(ChoreoConfigurationManagemenetService.class);

    /**
     * Create a Choreo configuration resource with a resource file.
     *
     * @param choreoConfigurationAdd Choreo configuration post request.
     * @return Choreo configuration.
     */
    public Choreoconfiguration addChoreoConfiguration(ChoreoConfiugurationAdd choreoConfigurationAdd) {

        int tenantId = PrivilegedCarbonContext.getThreadLocalCarbonContext().getTenantId();
        validateChoreoConfigurationAdd(choreoConfigurationAdd, tenantId);
        Resource choreoConfigurationResource = null;
        try {
            choreoConfigurationResource = buildResourceFromChoreoConfigurationAdd(choreoConfigurationAdd, null);
            ChoreoConfigurationManagementDataHolder.getChoreoConfigurationConfigManager()
                    .addResource(CHOREO_RESOURCE_TYPE, choreoConfigurationResource);
        } catch (ConfigurationManagementException e) {
            throw handleConfigurationMgtException(e, ERROR_CODE_ERROR_ADDING_CHOREO_CONFIGURATION,
                    choreoConfigurationAdd.getRefereneceName());
        }
        return buildChoreoConfigurationFromResource(choreoConfigurationResource);
    }

    /**
     * Build a Choreo configuration response from Choreo configuration's resource object.
     *
     * @param resource Choreo configuration resource object.
     * @return Choreo configuration response.
     */
    private Choreoconfiguration buildChoreoConfigurationFromResource(Resource resource) {

        Choreoconfiguration choreoconfiguration = new Choreoconfiguration();
        choreoconfiguration.setRefereneceName(resource.getResourceName());
        choreoconfiguration.setUrl(resource.getAttributes().get(0).getValue());
        choreoconfiguration.setApiKey(resource.getAttributes().get(1).getValue());
        return choreoconfiguration;
    }

    /**
     * Validate the Choreo configuration post request.
     *
     * @param choreoConfigurationAdd Choreo configuration post request.
     * @param tenantId       Tenant id.
     */
    private void validateChoreoConfigurationAdd(
            ChoreoConfiugurationAdd choreoConfigurationAdd, int tenantId) {

        String choreoConfigurationAddReferenceName = choreoConfigurationAdd.getRefereneceName();
        if (StringUtils.isBlank(choreoConfigurationAddReferenceName)) {
            throw handleException(Response.Status.BAD_REQUEST, ERROR_CODE_REFERENCE_NAME_NOT_SPECIFIED,
                    null);
        }
        if (StringUtils.isBlank(choreoConfigurationAdd.getUrl())) {
            throw handleException(Response.Status.BAD_REQUEST, ERROR_CODE_END_POINT_URI_NOT_SPECIFIED,
                    null);
        }
        try {
            // Check whether a publisher already exists with the same name in the particular tenant to be added.
            Resource resource =
                    ChoreoConfigurationManagementDataHolder.getChoreoConfigurationConfigManager()
                            .getResource(CHOREO_RESOURCE_TYPE, choreoConfigurationAddReferenceName);
            if (resource != null) {
                throw handleException(Response.Status.CONFLICT, ERROR_CODE_CONFLICT_CONFIGURATION,
                        choreoConfigurationAddReferenceName);
            }
        } catch (ConfigurationManagementException e) {
            if (!RESOURCE_NOT_EXISTS_ERROR_CODE.equals(e.getErrorCode())) {
                throw handleConfigurationMgtException(e, ERROR_CODE_ERROR_GETTING_CHOREO_CONFIGURATION,
                        choreoConfigurationAddReferenceName);
            }
        }
    }

    /**
     * Build Resource by Choreo configuration body request.
     *
     * @param choreoConfigurationAdd choreo configuration post body.
     * @param inputStream    Choreo configuration publisher file stream.
     * @return Resource object.
     */
    private Resource buildResourceFromChoreoConfigurationAdd(ChoreoConfiugurationAdd choreoConfigurationAdd,
                                                             InputStream inputStream) {

        Resource resource = new Resource();
        resource.setResourceName(choreoConfigurationAdd.getRefereneceName());
        List<Attribute> attributeList = new ArrayList();
        attributeList.add(new Attribute(ENDPOINT_URL, choreoConfigurationAdd.getUrl()));
        attributeList.add(new Attribute(API_KEY, choreoConfigurationAdd.getApiKey()));
        resource.setAttributes(attributeList);

        // Set file.
       /* ResourceFile file = new ResourceFile();
        file.setName(choreoConfiugurationAdd.getRefereneceName());
        file.setInputStream(inputStream);
        List<ResourceFile> resourceFiles = new ArrayList<>();
        resourceFiles.add(file);
        resource.setFiles(resourceFiles);*/
        return resource;
    }

    /**
     * Delete a Choreo Configuration sender by name.
     *
     * @param configurationReferenceName Name of the Choreo configuration.
     */
    public void deleteChoreoConfiguration(String configurationReferenceName) {

        try {
            ChoreoConfigurationManagementDataHolder.getChoreoConfigurationConfigManager()
                    .deleteResource(CHOREO_RESOURCE_TYPE, configurationReferenceName);
        } catch (ConfigurationManagementException e) {
            throw handleConfigurationMgtException(e, ERROR_CODE_ERROR_DELETING_CHOREO_CONFIGURATION,
                    configurationReferenceName);
        }
    }

    /**
     * Retrieve the Choreo Configuration details by name.
     *
     * @param referenceName Choreo configuration's reference name.
     * @return Choreo configuration.
     */
    public Choreoconfiguration getChoreoConfiguration(String referenceName) {

        try {
            Resource resource = ChoreoConfigurationManagementDataHolder.getChoreoConfigurationConfigManager()
                    .getResource(CHOREO_RESOURCE_TYPE, referenceName);
            return buildChoreoConfigurationFromResource(resource);
        } catch (ConfigurationManagementException e) {
            throw handleConfigurationMgtException(e, ERROR_CODE_ERROR_GETTING_CHOREO_CONFIGURATION, referenceName);
        }
    }

    /**
     * Retrieve all Choreo Configurations of the tenant.
     *
     * @return Choreo Configurations of the tenant.
     */
    public List<Choreoconfiguration> getChoreoConfigurations() {

        try {
            Resources configurationResources =
                    ChoreoConfigurationManagementDataHolder.
                    getChoreoConfigurationConfigManager()
                    .getResourcesByType(CHOREO_RESOURCE_TYPE);
            List<Resource> choreoConfigurationResources = configurationResources.
                    getResources();
            return choreoConfigurationResources.stream().map(resource ->
                    buildChoreoConfigurationFromResource(resource)).collect(
                    Collectors.toList());
        } catch (ConfigurationManagementException e) {
            throw handleConfigurationMgtException(e, ERROR_CODE_ERROR_GETTING_CHOREO_CONFIGURATION_BY_TYPE,
                    CHOREO_RESOURCE_TYPE);
        }
    }

    /**
     * Update Choreo configuration details by name.
     *
     * @param referenceName               Choreo configuration's name.
     * @param choreoConfigurationUpdateRequest Choreo configuration's updated configurations.
     * @return Updated Choreo configuration.
     */
    public Choreoconfiguration updateChoreoConfiguration(String referenceName,
    ChoreoconfigurationUpdateRequest choreoConfigurationUpdateRequest) {

        Resource choreoConfigurationResource = null;
        ChoreoConfiugurationAdd choreoConfiugurationAdd =
                buildChoreoConfigurationAddFromChoreoConfigurationUpdateRequest
                        (referenceName, choreoConfigurationUpdateRequest);
        try {
            choreoConfigurationResource = buildResourceFromChoreoConfigurationAdd(choreoConfiugurationAdd, null);
            ChoreoConfigurationManagementDataHolder.getChoreoConfigurationConfigManager()
                    .replaceResource(CHOREO_RESOURCE_TYPE, choreoConfigurationResource);
        } catch (ConfigurationManagementException e) {
            throw handleConfigurationMgtException(e, ERROR_CODE_ERROR_UPDATING_CHORE_CONFIGURATION, referenceName);
        }
        return buildChoreoConfigurationFromResource(choreoConfigurationResource);
    }

    /**
     * Build Choreo configuration add object from Choreo configuration update request.
     *
     * @param referenceName               Choreo configuration's reference name.
     * @param choreoConfigurationUpdateRequest Choreo configuration's update request body.
     * @return Choreo configuration add object
     */
    private ChoreoConfiugurationAdd buildChoreoConfigurationAddFromChoreoConfigurationUpdateRequest(
            String referenceName, ChoreoconfigurationUpdateRequest choreoConfigurationUpdateRequest) {

        ChoreoConfiugurationAdd choreoConfiugurationAdd = new ChoreoConfiugurationAdd();
        choreoConfiugurationAdd.setRefereneceName(referenceName);
        choreoConfiugurationAdd.setApiKey(choreoConfigurationUpdateRequest.getApiKey());
        choreoConfiugurationAdd.setUrl(choreoConfigurationUpdateRequest.getUrl());
        return choreoConfiugurationAdd;
    }

    private APIError handleConfigurationMgtException(ConfigurationManagementException e,
                                                     ChoreoConfigurationManagementConstants.ErrorMessage errorEnum,
                                                     String data) {

        ErrorResponse errorResponse = getErrorBuilder(errorEnum, data).build(log, e, errorEnum.getDescription());
        Response.Status status;
        if (e instanceof ConfigurationManagementClientException) {
            if (e.getErrorCode() != null) {
                String errorCode = e.getErrorCode();
                errorCode = errorCode.contains(CONFIG_MGT_ERROR_CODE_DELIMITER) ? errorCode :
                        CHOREO_ERROR_PREFIX + errorCode;
                errorResponse.setCode(errorCode);
            }
            errorResponse.setDescription(e.getMessage());
            status = Response.Status.BAD_REQUEST;
        } else if (e instanceof ConfigurationManagementServerException) {
            if (e.getErrorCode() != null) {
                String errorCode = e.getErrorCode();
                errorCode = errorCode.contains(CONFIG_MGT_ERROR_CODE_DELIMITER) ? errorCode :
                        CHOREO_ERROR_PREFIX + errorCode;
                errorResponse.setCode(errorCode);
            }
            errorResponse.setDescription(e.getMessage());
            status = Response.Status.INTERNAL_SERVER_ERROR;
        } else {
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }
        return new APIError(status, errorResponse);
    }

    /**
     * Handle exceptions generated in API.
     *
     * @param status HTTP Status.
     * @param error  Error Message information.
     * @return APIError.
     */
    private APIError handleException(Response.Status status, ChoreoConfigurationManagementConstants.ErrorMessage error,
                                     String data) {

        return new APIError(status, getErrorBuilder(error, data).build());
    }

    /**
     * Return error builder.
     *
     * @param errorMsg Error Message information.
     * @return ErrorResponse.Builder.
     */
    private ErrorResponse.Builder getErrorBuilder(ChoreoConfigurationManagementConstants.ErrorMessage errorMsg,
                                                  String data) {

        return new ErrorResponse.Builder().withCode(errorMsg.getCode()).withMessage(errorMsg.getMessage())
                .withDescription(includeData(errorMsg, data));
    }

    /**
     * Include context data to error message.
     *
     * @param error Error message.
     * @param data  Context data.
     * @return Formatted error message.
     */
    private static String includeData(ChoreoConfigurationManagementConstants.ErrorMessage error, String data) {

        String message;
        if (StringUtils.isNotBlank(data)) {
            message = String.format(error.getDescription(), data);
        } else {
            message = error.getDescription();
        }
        return message;
    }
}

