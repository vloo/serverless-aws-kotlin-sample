package eu.stolin.serverless

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.cloudsearchdomain.AmazonCloudSearchDomainClientBuilder
import com.amazonaws.services.cloudsearchdomain.model.UploadDocumentsRequest
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.S3Event
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.apache.logging.log4j.LogManager


class AddToSearch : RequestHandler<S3Event, String> {

    private val logger = LogManager.getLogger(javaClass)

    override fun handleRequest(input: S3Event, context: Context): String {
        logger.info("start handler")

        val s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
                .build()

        val config = AwsClientBuilder.EndpointConfiguration("search-text-domain-ghynngvkjlpp2soyojyoaeva2q.us-east-1.cloudsearch.amazonaws.com", "us-east-1")

        val domainClient = AmazonCloudSearchDomainClientBuilder
                .standard()
                .withEndpointConfiguration(config)
                .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
                .build()

        input.records.forEach { record ->
            val bucket = record.s3.bucket.name
            val key = record.s3.getObject().key
            logger.info("src file name: ", key)

            logger.info("going to download: ", bucket + key)
            val s3Object = s3Client.getObject(bucket, key)
            val s3Stream = s3Object.objectContent.delegateStream


            val uploadRequest = UploadDocumentsRequest()
            uploadRequest.documents = s3Stream
            domainClient.uploadDocuments(uploadRequest)
        }
        logger.info("finish handler")
        return "OK"
    }

}