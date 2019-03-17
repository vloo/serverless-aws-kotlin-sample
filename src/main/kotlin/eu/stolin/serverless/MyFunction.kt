package eu.stolin.serverless

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.serverless.ApiGatewayResponse

class MyFunction: RequestHandler<Map<String, Any>, MyResponse> {

    override fun handleRequest(input: Map<String, Any>, context: Context?): MyResponse {


        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}