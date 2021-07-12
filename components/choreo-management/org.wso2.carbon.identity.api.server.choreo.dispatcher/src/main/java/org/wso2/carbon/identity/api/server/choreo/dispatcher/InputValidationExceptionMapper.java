package org.wso2.carbon.identity.api.server.choreo.dispatcher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.api.server.choreo.management.common.error.ErrorDTO;
import org.wso2.carbon.identity.api.server.choreo.management.common.error.ErrorResponse;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Map input validation exceptions.
 */
public class InputValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    private static final Log log = LogFactory.getLog(InputValidationExceptionMapper.class);

    private static final String ERROR_CODE = "FUA-15000";
    private static final String ERROR_MESSAGE = "Invalid Request";
    private static final String ERROR_DESCRIPTION = "Provided request body content is not in the expected format";

    @Override
    public Response toResponse(ConstraintViolationException e) {

        StringBuilder description = new StringBuilder();
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        for (ConstraintViolation constraintViolation : constraintViolations) {
            if (StringUtils.isNotBlank(description)) {
                description.append(" ");
            }
            description.append(constraintViolation.getMessage());
        }
        if (StringUtils.isBlank(description)) {
            description = new StringBuilder(ERROR_DESCRIPTION);
        }

        ErrorDTO errorDTO = new ErrorResponse.Builder()
                .withCode(ERROR_CODE)
                .withMessage(ERROR_MESSAGE)
                .withDescription(description.toString())
                .build(log, e.getMessage(), true);
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorDTO)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).build();
    }
}
