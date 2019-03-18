package eu.stolin.serverless

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.serverless.ApiGatewayResponse
import org.apache.logging.log4j.LogManager

class UploadToS3 : RequestHandler<Map<String, Any>, ApiGatewayResponse> {
    private val logger = LogManager.getLogger(javaClass)

    override fun handleRequest(input:Map<String, Any>, context: Context): ApiGatewayResponse {
        logger.info("input text", input)




        return ApiGatewayResponse.build {
            statusCode = 201
            rawBody = "some link"
        }
    }
}