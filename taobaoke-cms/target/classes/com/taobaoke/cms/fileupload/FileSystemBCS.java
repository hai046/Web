package com.taobaoke.cms.fileupload;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.baidu.inf.iis.bcs.BaiduBCS;
import com.baidu.inf.iis.bcs.auth.BCSCredentials;
import com.baidu.inf.iis.bcs.model.ObjectMetadata;
import com.baidu.inf.iis.bcs.model.X_BS_ACL;
import com.baidu.inf.iis.bcs.request.PutObjectRequest;
import com.baidu.inf.iis.bcs.response.BaiduBCSResponse;
import com.taobaoke.cms.utils.PathUtils;

public class FileSystemBCS{
    private static final Log log = LogFactory.getLog(FileSystemBCS.class);

	static String HOST = "bcs.duapp.com";
    static String ACCESSKEY = "03f07e4fe48891054112f7e5ff098967";
    static String SECRETKEY = "CAbe185f353bb55595ab409cbf9b9ee9";
    static String BUCKET = "outin-root";
    static String ROOT = "/taose/";
    BaiduBCS baiduBCS = new BaiduBCS(new BCSCredentials(ACCESSKEY, SECRETKEY), HOST);
    
    private static FileSystemBCS instance = new FileSystemBCS();
    
    private FileSystemBCS(){
        
    }
    
    public static FileSystemBCS getInstance(){
        return instance;
    }
	
	

    public String storeFile(byte[] bytes, String fileName, String contentType) {
        System.out.println( "=======store ||" + fileName + "||" + bytes.length );
        System.out.println( "=======startstore ||" + fileName + "||" + bytes.length );
        
        fileName = String.format(PathUtils.generateBasePath(fileName), "o");
        InputStream inputStream = new ByteArrayInputStream(bytes);
        
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(getPhotoTypeFromPic(fileName));
        metadata.setContentLength( bytes.length );
        PutObjectRequest request = new PutObjectRequest(BUCKET, ROOT + fileName, inputStream, metadata);
        request.setAcl(X_BS_ACL.PublicRead);
        
        BaiduBCSResponse<ObjectMetadata> response = baiduBCS.putObject(request);
        ObjectMetadata objectMetadata = response.getResult();
        log.info("x-bs-request-id: " + response.getRequestId());
        log.info(objectMetadata);
                    
        System.out.println( "=======storethread photo ||" + fileName + "||" + bytes.length );
        return fileName;
    }

    
    private String getPhotoTypeFromPic(String path){
        String fileName = path.substring( path.lastIndexOf('/') + 1 );
        String type = "jpeg";
        int typeIndex = fileName.lastIndexOf('/');
        if( typeIndex != -1 ){
            type = fileName.substring(typeIndex+1);
        }
        type = "image/" + type;
        return type;
    }
    
}
