FROM alpine:3.14

ENV \
    LANG=C.UTF-8 \
    JAVA_HOME=/usr/local/java \
    PATH=$PATH:/usr/local/java/jre/bin:/usr/local/java/bin

############### 更新apk存储库 ###############
RUN \
  echo "" > /etc/apk/repositories && \
  echo "http://mirrors.aliyun.com/alpine/v3.14/main" >> /etc/apk/repositories &&\
  echo "http://mirrors.aliyun.com/alpine/v3.14/community" >> /etc/apk/repositories && \
  apk update

################ 添加用户 ###########################
## 不使用root用户
RUN \
  addgroup -S admin && adduser -S admin -G admin && \
  apk add --no-cache bash busybox-extras curl && \
  apk add --no-cache tzdata && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
  echo ""

################### 安装openjdk ###################
RUN \
# 不指定版本，使用最新openjdk8
#  set -x && apk add --no-cache openjdk8="${JAVA_ALPINE_VERSION}" && \
  set -x && apk --no-cache add openjdk11 --repository=http://dl-cdn.alpinelinux.org/Alpine/Edge/community && \
  ln -s /usr/lib/jvm/java-11-openjdk /usr/local/java && \
  echo ""


ARG source_path=/target
ARG target_path=/data/app

RUN bash -c 'mkdir -p ${target_path}/{lib,logs,bin}'

COPY ${source_path}/*.jar ${target_path}/lib/
COPY /bin/endpoint.sh ${target_path}/bin/

STOPSIGNAL SIGTERM

WORKDIR ${target_path}/bin/

EXPOSE 80
STOPSIGNAL SIGTERM

CMD bash -C endpoint.sh