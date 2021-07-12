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

package org.wso2.carbon.identity.api.server.choreo.management.v1;

import org.springframework.beans.factory.annotation.Autowired;

import org.wso2.carbon.identity.api.server.choreo.management.v1.model.ChoreoConfiugurationAdd;
import org.wso2.carbon.identity.api.server.choreo.management.v1.model.Choreoconfiguration;
import org.wso2.carbon.identity.api.server.choreo.management.v1.model.ChoreoconfigurationUpdateRequest;
import org.wso2.carbon.identity.api.server.choreo.management.v1.model.Error;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import io.swagger.annotations.*;

@Path("/ChoreoConfigurations")
@Api(description = "The ChoreoConfigurations API")

public class ChoreoConfigurationsApi  {

    @Autowired
    private ChoreoConfigurationsApiService delegate;

    @Valid
    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Create an choreo Configuration", notes = "This API provides the capability to create an Choreo configuration ", response = Choreoconfiguration.class, authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "Choreo", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Successful Response", response = Choreoconfiguration.class),
        @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Error.class),
        @ApiResponse(code = 405, message = "Method Not Allowed.", response = Error.class),
        @ApiResponse(code = 409, message = "Conflict", response = Error.class),
        @ApiResponse(code = 500, message = "Server Error", response = Error.class)
    })
    public Response createChoreoConfiguration(@ApiParam(value = "" ) @Valid ChoreoConfiugurationAdd choreoConfiugurationAdd) {

        return delegate.createChoreoConfiguration(choreoConfiugurationAdd );
    }

    @Valid
    @DELETE
    @Path("/{reference-name}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Delete an choreo confuguration by name", notes = "This API provides the capability to delete a Choreo configuration by name. ", response = Void.class, authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "Choreo", })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "No Content", response = Void.class),
        @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Error.class),
        @ApiResponse(code = 405, message = "Method Not Allowed.", response = Error.class),
        @ApiResponse(code = 500, message = "Server Error", response = Error.class)
    })
    public Response deleteChoreoConfiguration(@ApiParam(value = "reference-name of the choreo endpoint",required=true) @PathParam("reference-name") String referenceName) {

        return delegate.deleteChoreoConfiguration(referenceName );
    }

    @Valid
    @GET
    @Path("/{reference-name}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Retrieve Choreo configuration by reference name", notes = "This API provides the capability to retrieve an Choreo configuration by name. ", response = Choreoconfiguration.class, authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "Choreo", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful Response", response = Choreoconfiguration.class),
        @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Error.class),
        @ApiResponse(code = 405, message = "Method Not Allowed.", response = Error.class),
        @ApiResponse(code = 500, message = "Server Error", response = Error.class)
    })
    public Response getChoreoConfiguration(@ApiParam(value = "reference-name of the choreo endpoint",required=true) @PathParam("reference-name") String referenceName) {

        return delegate.getChoreoConfiguration(referenceName );
    }

    @Valid
    @GET
    
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Get a list of choreo configured endpoints", notes = "This API provides the capability to retrieve the list of choreo configured URLs. ", response = Choreoconfiguration.class, responseContainer = "List", authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "Choreo", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful Response", response = Choreoconfiguration.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Error.class),
        @ApiResponse(code = 405, message = "Method Not Allowed.", response = Error.class),
        @ApiResponse(code = 500, message = "Server Error", response = Error.class)
    })
    public Response getChoreoConfigurationList() {

        return delegate.getChoreoConfigurationList();
    }

    @Valid
    @PUT
    @Path("/{reference-name}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Update a Choreo configuration", notes = "This API provides the capability to update an Choreo configuration by reference-name. ", response = Choreoconfiguration.class, authorizations = {
        @Authorization(value = "BasicAuth"),
        @Authorization(value = "OAuth2", scopes = {
            
        })
    }, tags={ "Choreo" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful Response", response = Choreoconfiguration.class),
        @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Void.class),
        @ApiResponse(code = 403, message = "Forbidden", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found", response = Error.class),
        @ApiResponse(code = 405, message = "Method Not Allowed.", response = Error.class),
        @ApiResponse(code = 500, message = "Server Error", response = Error.class)
    })
    public Response updateChoreoConfiguration(@ApiParam(value = "reference-name of the choreo endpoint",required=true) @PathParam("reference-name") String referenceName, @ApiParam(value = "" ,required=true) @Valid ChoreoconfigurationUpdateRequest choreoconfigurationUpdateRequest) {

        return delegate.updateChoreoConfiguration(referenceName,  choreoconfigurationUpdateRequest );
    }

}
