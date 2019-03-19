package eu.stolin.serverless

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.serverless.ApiGatewayResponse

class HelloWorld: RequestHandler<Any, ApiGatewayResponse> {

    override fun handleRequest(input: Any, context: Context): ApiGatewayResponse {

        return ApiGatewayResponse.build {
            statusCode = 200
            rawBody = "Hello Kotlin Meetup Hradec Kralove "
        }
    }
}