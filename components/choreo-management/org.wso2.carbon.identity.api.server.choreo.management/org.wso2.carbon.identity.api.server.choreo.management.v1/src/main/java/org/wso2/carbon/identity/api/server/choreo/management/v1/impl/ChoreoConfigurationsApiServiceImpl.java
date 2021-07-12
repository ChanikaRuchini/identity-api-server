/*
* Copyright (c) 2021, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package org.wso2.carbon.identity.api.server.choreo.management.v1.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.wso2.carbon.identity.api.server.choreo.management.common.ContextLoader;
import org.wso2.carbon.identity.api.server.choreo.management.common.error.APIError;
import org.wso2.carbon.identity.api.server.choreo.management.common.error.ErrorResponse;
import org.wso2.carbon.identity.api.server.choreo.management.v1.ChoreoConfigurationsApiService;
import org.wso2.carbon.identity.api.server.choreo.management.v1.core.ChoreoConfigurationManagemenetService;

import org.wso2.carbon.identity.api.server.choreo.management.v1.model.ChoreoConfiugurationAdd;
import org.wso2.carbon.identity.api.server.choreo.management.v1.model.Choreoconfiguration;
import org.wso2.carbon.identity.api.server.choreo.management.v1.model.ChoreoconfigurationUpdateRequest;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.ws.rs.core.Response;

import static org.wso2.carbon.identity.api.server.choreo.management.common.ChoreoConfigurationManagementConstants.CHOREO_CONFIGURATION_CONTEXT_PATH;
import static org.wso2.carbon.identity.api.server.choreo.management.common.ChoreoConfigurationManagementConstants.PLUS;
import static org.wso2.carbon.identity.api.server.choreo.management.common.ChoreoConfigurationManagementConstants.URL_ENCODED_SPACE;
import static org.wso2.carbon.identity.api.server.choreo.management.common.ChoreoConfigurationManagementConstants.V1_API_PATH_COMPONENT;

/**
 * Implementation of Choreo Configuration Management REST API.
 */
public class ChoreoConfigurationsApiServiceImpl implements ChoreoConfigurationsApiService {

    @Autowired
    private ChoreoConfigurationManagemenetService choreoConfigurationManagemenetService;


    @Override
    public Response createChoreoConfiguration(ChoreoConfiugurationAdd choreoConfiugurationAdd) {

        Choreoconfiguration choreoconfiguration = choreoConfigurationManagemenetService.
                addChoreoConfiguration(choreoConfiugurationAdd);
        URI location = null;
        try {
            location = ContextLoader.buildURIForHeader(
                    V1_API_PATH_COMPONENT + CHOREO_CONFIGURATION_CONTEXT_PATH + "/" +
                            URLEncoder.encode(choreoconfiguration.getRefereneceName(), StandardCharsets.UTF_8.name())
                                    .replace(PLUS, URL_ENCODED_SPACE));
        } catch (UnsupportedEncodingException e) {
            ErrorResponse errorResponse =
                    new ErrorResponse.Builder().withMessage("Error due to unsupported encoding.").build();
            throw new APIError(Response.Status.METHOD_NOT_ALLOWED, errorResponse);
        }
        return Response.created(location).entity(choreoconfiguration).build();
    }

    @Override
    public Response deleteChoreoConfiguration(String referenceName) {

        choreoConfigurationManagemenetService.deleteChoreoConfiguration(referenceName);
        return Response.noContent().build();
    }

    @Override
    public Response getChoreoConfiguration(String referenceName) {

        return Response.ok().entity(choreoConfigurationManagemenetService.getChoreoConfiguration(referenceName))
                .build();

    }

    @Override
    public Response getChoreoConfigurationList() {

        return Response.ok().entity(choreoConfigurationManagemenetService.getChoreoConfigurations()).build();
    }

    @Override
    public Response updateChoreoConfiguration(String referenceName, ChoreoconfigurationUpdateRequest
            choreoconfigurationUpdateRequest) {

       return Response.ok()
                .entity(choreoConfigurationManagemenetService.updateChoreoConfiguration
                (referenceName, choreoconfigurationUpdateRequest))
                .build();
    }
}
