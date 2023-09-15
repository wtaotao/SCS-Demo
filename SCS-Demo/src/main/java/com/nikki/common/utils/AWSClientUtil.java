/**
 * AWSClientUtil.java
 * Created at 2021-08-26
 * Created by xieyingbin
 * Copyright (C) 2020 ANJI LOGISTICS, All rights reserved.
 */
package com.nikki.common.utils;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.TransferManager;

/**
 * AWSClient 文件上传工具类
 * 
 * @author Guosw
 */
public class AWSClientUtil {
    static AmazonS3 s3Client;
    static TransferManager xfer_mgr;
    private static Logger logger = LogManager.getLogger(AWSClientUtil.class);
    private static String endpoint = "http://10.130.138.203"; // 测试用
    private static String accessKey ="UWZ6PY88E0MEI137VZBR";  // 测试用
    private static String secretKey = "upCFwEn7KpTjHBWDQ75tI4hA5menYMTjEoBABe6g"; // 测试用
    private static String region = "us-east-1"; // 测试用

    /**
     * 获取AmazonS3 client实例
     *
     * @return
     */
    private static AmazonS3 getS3Client(String endpoint1, String accessKey1, String secretKey1) {
        if (null != s3Client) {
            return s3Client;
        }
        if(endpoint1 != null && accessKey1 != null && secretKey1 != null) {
        	endpoint = endpoint1;
        	accessKey = accessKey1;
        	secretKey = secretKey1;
        }
        try {
            AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
            ClientConfiguration clientConfig = new ClientConfiguration();
            clientConfig.setProtocol(Protocol.HTTP);
            clientConfig.setSignerOverride("S3SignerType");
            s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withClientConfiguration(clientConfig)
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region)).build();
        } catch (Exception e) {
            logger.error("getS3Client error", e);
        }
        return s3Client;
    }


    /**
     * 获取Bucket下文件列表
     *
     * @param bucketName
     * @return
     */
    public static List<S3ObjectSummary> listObjects(String endpoint, String accessKey, String secretKey, String bucketName) {
        s3Client = getS3Client(endpoint, accessKey, secretKey);
        try {
            logger.debug("Listing objects of " + bucketName);
            ObjectListing objectListing = s3Client.listObjects(bucketName);
            if (null == objectListing) {
                return null;
            }
            return objectListing.getObjectSummaries();
        } catch (Exception e) {
            logger.error("listObjects error,bucketName = " + bucketName, e);
        }
        return null;
    }
    
    public static List<String> listObjects1(String endpoint, String accessKey, String secretKey, String bucketName) {
    	s3Client = getS3Client(endpoint, accessKey, secretKey);
    	List<String> list = new ArrayList<String>();
        try {
        	logger.debug("Listing objects of " + bucketName);
        	ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucketName).withMaxKeys(2000);
            ListObjectsV2Result result;
            int x = 0;
            do {
                result = s3Client.listObjectsV2(req);
                
                for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
                	x++;
                	list.add(endpoint + "/" + bucketName + "/" + objectSummary.getKey());
                }
                // If there are more than maxKeys keys in the bucket, get a continuation token
                // and list the next objects.
                String token = result.getNextContinuationToken();
                req.setContinuationToken(token);
            } while (result.isTruncated());
        } catch (Exception e) {
        	logger.error("listObjects error,bucketName = " + bucketName, e);
        }
        return list;
    }

    
    /**
     * 判断存储通中对象是否存在
     *
     * @param bucketName
     * @param key
     * @return
     */
    public static boolean isObjectExit(String endpoint, String accessKey, String secretKey, String bucketName, String key) {
        s3Client = getS3Client(endpoint, accessKey, secretKey);
        int len = key.length();
        ObjectListing objectListing = s3Client.listObjects(bucketName);
        String s = new String();
        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            s = objectSummary.getKey();
            int slen = s.length();
            if (len == slen) {
                int i;
                for (i = 0; i < len; i++)
                    if (s.charAt(i) != key.charAt(i)) {
                        break;
                    }
                if (i == len) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 上传本地文件
     *
     * @param localFilePath 本地文件地址
     * @param remoteKey     S3文件Key
     */
    public static String uploadFile(String endpoint, String accessKey, String secretKey, String bucketName, String localFilePath) {
        File file = new File(localFilePath);
        return uploadFile(endpoint, accessKey, secretKey, bucketName,file);
    }

    /**
     * @param file      文件对象
     * @param remoteKey S3文件Key
     */
    public static String uploadFile(String endpoint, String accessKey, String secretKey, String bucketName, File file) {
        s3Client = getS3Client(endpoint, accessKey, secretKey);
        String resultStr = null;
        String remoteKey = file.getName();
        try {
        	s3Client.putObject(new PutObjectRequest(bucketName, remoteKey, file));
        	resultStr = endpoint + "/" + bucketName + "/" + remoteKey;
        	logger.info("uploadFile success: " + resultStr);
        } catch (Exception e) {
            logger.error("uploadFile error , remoteKey = " + remoteKey, e);
        }
        return resultStr;
    }

    /**
     * 根据文件流上传文件
     *
     * @param inputStream 文件流
     * @param remoteKey   S3文件Key
     */
    public static boolean uploadFile(String endpoint, String accessKey, String secretKey, String bucketName, InputStream inputStream, String remoteKey) {
        s3Client = getS3Client(endpoint, accessKey, secretKey);
        boolean isSuccess = true;
        try {
            s3Client.putObject(bucketName, remoteKey, inputStream, new ObjectMetadata());
        } catch (Exception e) {
            logger.error("uploadFile error , remoteKey = " + remoteKey, e);
            isSuccess = false;
        }
        return isSuccess;
    }

    /**
     * @param remoteKey     S3文件Key
     * @param localFilePath 下载到本地文件地址
     */
    public static void downloadFile(String endpoint, String accessKey, String secretKey, String bucketName, String remoteKey, String localFilePath) {
        s3Client = getS3Client(endpoint, accessKey, secretKey);
        try {
            s3Client.getObject(new GetObjectRequest(bucketName, remoteKey), new File(localFilePath));
        } catch (Exception e) {
            logger.error("uploadFile error , remoteKey = " + remoteKey, e);
        }
    }

    public static S3ObjectInputStream downloadFile(String endpoint, String accessKey, String secretKey, String bucketName, String remoteKey) {
        s3Client = getS3Client(endpoint, accessKey, secretKey);
        try {
            GetObjectRequest request = new GetObjectRequest(bucketName, remoteKey);
            S3Object object = s3Client.getObject(request);
            S3ObjectInputStream content = object.getObjectContent();
            return content;
        } catch (Exception e) {
            logger.error("downloadFile error , remoteKey = " + remoteKey, e);
        }
        return null;
    }

    public static URL getUrl(String endpoint, String accessKey, String secretKey, String bucketName, String remoteKey) {
        s3Client = getS3Client(endpoint, accessKey, secretKey);
        try {
            Date expiration = new Date(new Date().getTime() + 60 * 10 * 1000);
            URL url = s3Client.generatePresignedUrl(bucketName, remoteKey, expiration);
            logger.info(url.toString());
            return url;
        } catch (Exception e) {
            logger.error("getUrl error , remoteKey = " + remoteKey, e);
        }
        return null;
    }

    /**
     * @param remoteKey S3文件Key
     */
    public static void removeFile(String endpoint, String accessKey, String secretKey, String bucketName, String remoteKey) {
        s3Client = getS3Client(endpoint, accessKey, secretKey);
        try {
            s3Client.deleteObject(bucketName, remoteKey);
        } catch (Exception e) {
            logger.error("uploadFile error , remoteKey = " + remoteKey, e);
        }
    }
}

