#config
IMAGE=iotportal/sdf-developer-demo
VERSION=latest
PORT=8899
REDIS_HOST=127.0.0.1
REDIS_PASSWORD=XN07Vh0PPxU1mPDy
REDIS_PORT=6379

base_dir=$(cd "$(dirname "$0")";pwd)

app_path=$base_dir/sdf-developer-demo/logs

#pull image
docker pull $IMAGE:$VERSION

mkdir -p $app_path
touch $app_path/api_stdout.log

#run
docker run -d \
-p $PORT:$PORT \
--env PORT=$PORT \
-v $app_path/api_stdout.log:/data/app/logs/api_stdout.log \
--env REDIS_HOST=$REDIS_HOST \
--env REDIS_PASSWORD=$REDIS_PASSWORD \
--env REDIS_PORT=$REDIS_PORT \
--name sdf-developer-demo \
$IMAGE:$VERSION


docker run -d \
-p 8899:8899 \
-v /Users/zhangzihao/Documents/tuya/project/sdf-developer/demo/sdf-developer-demo/logs/api_stdout.log:/data/app/logs/api_stdout.log \
--network host \
--env PORT=8899 \
--env REDIS_HOST=127.0.0.1 \
--env REDIS_PASSWORD=XN07Vh0PPxU1mPDy \
--env REDIS_PORT=6379 \
--name sdf-developer-demo \
iotportal/sdf-developer-demo