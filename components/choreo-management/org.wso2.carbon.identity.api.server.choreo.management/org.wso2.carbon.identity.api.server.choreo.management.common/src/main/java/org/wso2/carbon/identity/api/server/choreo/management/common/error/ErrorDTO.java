package org.wso2.carbon.identity.api.server.choreo.management.common.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import javax.validation.constraints.NotNull;

/**
 * Common DTO for all the API related error responses.
 */
@ApiModel(description = "")
public class ErrorDTO implements Serializable {

    private static final long serialVersionUID = 1984069451956875663L;

    @NotNull
    private String code = null;

    @NotNull
    private String message = null;

    private String description = null;

    private String ref = null;

    /**
     * Get code.
     **/
    @ApiModelProperty(required = true, value = "")
    @JsonProperty("code")
    public String getCode() {

        return code;
    }

    /**
     * Set code.
     **/
    public void setCode(String code) {

        this.code = code;
    }

    /**
     * Get Message.
     **/
    @ApiModelProperty(required = true, value = "")
    @JsonProperty("message")
    public String getMessage() {

        return message;
    }

    /**
     * Set Message.
     **/
    public void setMessage(String message) {

        this.message = message;
    }

    /**
     * Get description.
     **/
    @ApiModelProperty(value = "")
    @JsonProperty("description")
    public String getDescription() {

        return description;
    }

    /**
     * Set description.
     **/
    public void setDescription(String description) {

        this.description = description;
    }

    /**
     * Get traceId.
     **/
    @ApiModelProperty(value = "")
    @JsonProperty("traceId")
    public String getRef() {

        return ref;
    }

    /**
     * Set traceId.
     **/
    public void setRef(String ref) {

        this.ref = ref;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("class ErrorDTO {\n");

        sb.append("  code: ").append(code).append("\n");
        sb.append("  message: ").append(message).append("\n");
        sb.append("  description: ").append(description).append("\n");
        sb.append("  traceId: ").append(ref).append("\n");
        sb.append("}\n");
        return sb.toString();
    }
}
