# minio-demo
A small demo shows how Minio works

## What is MinIO
MinIO is high performance Amazon S3 Compatible Cloud Storage.

## Prerequisites
* Docker
* Docker Compose
* Java 11

## How to run MinIO docker locally
```
docker run -p 9000:9000 -e "MINIO_ACCESS_KEY=minio" -e "MINIO_SECRET_KEY=minio123" minio/minio server /data
```
or run to start minio, nginx proxy as well as pre-created testing bucket
```
docker-compose up -d
```

## How to test MinIO
```
gradle bootRun
```

After running the command above, the application will create a testing bucket if not existing 
and upload testing_file.txt to that bucket. Go to to check the result:
```
http://127.0.0.1:9000/
```