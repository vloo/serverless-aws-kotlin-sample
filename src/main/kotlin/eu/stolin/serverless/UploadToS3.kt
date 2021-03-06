package eu.stolin.serverless

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.serverless.ApiGatewayResponse
import org.apache.logging.log4j.LogManager
import java.util.*

const val BUCKET_NAME = "stolin-serverless-text"


class UploadToS3 : RequestHandler<Map<String, Any>, ApiGatewayResponse> {
    private val logger = LogManager.getLogger(javaClass)

    override fun handleRequest(input:Map<String, Any>, context: Context): ApiGatewayResponse {

        val s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
                .build()

        val body = input["body"] as String
        logger.info("input text", body)

        s3Client.putObject(BUCKET_NAME, UUID.randomUUID().toString(), body)

        return ApiGatewayResponse.build {
            statusCode = 201
            rawBody = "success "
        }
    }
}