package se.mikka.miniodemo;

import io.minio.MinioClient;
import io.minio.errors.ErrorResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@SpringBootApplication
public class MinioDemoApplication {

	private static final Logger logger = LoggerFactory.getLogger(MinioDemoApplication.class);

	private static String accessKey = "minio";
	private static String secretKey = "minio123";
	private static String bucket = "testing";
	private static String testingFile = "testing_file.txt";

	public static void main(String[] args) {
		SpringApplication.run(MinioDemoApplication.class, args);

		try {
			MinioClient minioClient = new MinioClient("http://localhost", accessKey, secretKey);

			boolean isExistBucket = minioClient.bucketExists(bucket);
			if (isExistBucket) {
				logger.info("Bucket {} is existing", bucket);
			} else {
				logger.info("Bucket {} is not existing. Creating it!", bucket);
				minioClient.makeBucket(bucket);
			}

			// Creating file if not existing
			try {
				minioClient.statObject(bucket, testingFile);
				logger.info("File {} is existing", testingFile);
			} catch (ErrorResponseException e) {
				logger.info("File {} is not existing. Creating it!", testingFile);

				Resource resourceTestingFile = new ClassPathResource(testingFile);
				minioClient.putObject(bucket, testingFile, resourceTestingFile.getInputStream(), resourceTestingFile.contentLength(),
						null, null, "application/octet-stream");
			}
		} catch (Exception e) {
			logger.error("Exception occurred with MinIO", e);
			throw new RuntimeException("Exception occurred with MinIO");
		}
	}

}
