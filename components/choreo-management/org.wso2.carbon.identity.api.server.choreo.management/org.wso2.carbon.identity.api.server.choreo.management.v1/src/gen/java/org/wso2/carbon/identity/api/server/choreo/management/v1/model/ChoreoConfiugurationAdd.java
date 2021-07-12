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

package org.wso2.carbon.identity.api.server.choreo.management.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;

import java.util.Objects;
import javax.validation.Valid;

public class ChoreoConfiugurationAdd  {
  
    private String refereneceName;
    private String url;
    private String apiKey;

    /**
    **/
    public ChoreoConfiugurationAdd refereneceName(String refereneceName) {

        this.refereneceName = refereneceName;
        return this;
    }
    
    @ApiModelProperty(required = true, value = "")
    @JsonProperty("refereneceName")
    @Valid
    @NotNull(message = "Property refereneceName cannot be null.")

    public String getRefereneceName() {
        return refereneceName;
    }
    public void setRefereneceName(String refereneceName) {
        this.refereneceName = refereneceName;
    }

    /**
    **/
    public ChoreoConfiugurationAdd url(String url) {

        this.url = url;
        return this;
    }
    
    @ApiModelProperty(required = true, value = "")
    @JsonProperty("url")
    @Valid
    @NotNull(message = "Property url cannot be null.")

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    /**
    **/
    public ChoreoConfiugurationAdd apiKey(String apiKey) {

        this.apiKey = apiKey;
        return this;
    }
    
    @ApiModelProperty(required = true, value = "")
    @JsonProperty("api-key")
    @Valid
    @NotNull(message = "Property apiKey cannot be null.")

    public String getApiKey() {
        return apiKey;
    }
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }



    @Override
    public boolean equals(java.lang.Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChoreoConfiugurationAdd choreoConfiugurationAdd = (ChoreoConfiugurationAdd) o;
        return Objects.equals(this.refereneceName, choreoConfiugurationAdd.refereneceName) &&
            Objects.equals(this.url, choreoConfiugurationAdd.url) &&
            Objects.equals(this.apiKey, choreoConfiugurationAdd.apiKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(refereneceName, url, apiKey);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("class ChoreoConfiugurationAdd {\n");
        
        sb.append("    refereneceName: ").append(toIndentedString(refereneceName)).append("\n");
        sb.append("    url: ").append(toIndentedString(url)).append("\n");
        sb.append("    apiKey: ").append(toIndentedString(apiKey)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
    * Convert the given object to string with each line indented by 4 spaces
    * (except the first line).
    */
    private String toIndentedString(java.lang.Object o) {

        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n");
    }
}
