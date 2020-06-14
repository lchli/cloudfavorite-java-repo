# Docker image for springboot file run
# 基础镜像使用java
FROM java:8
# 作者
MAINTAINER lch <996863054@qq.com>
# VOLUME 指定了临时文件目录为/tmp。
# 其效果是在主机 /var/lib/docker 目录下创建了一个临时文件，并链接到容器的/tmp
VOLUME /tmp
# 将jar包添加到容器中并更名为app.jar
ADD cloudfavorite-java-0.0.1-SNAPSHOT.jar app.jar
# 运行jar包
RUN bash -c 'touch /app.jar'
# 开放8761端口
EXPOSE 40100
#
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=release","-jar","/app.jar"]
#在当前文件目录执行docker build -t cloud-fav:1.4 .
#从已有镜像创建新镜像docker commit -m="has update" -a="runoob" e218edb10161 runoob/ubuntu:v2
#7、docker镜像导出，tar镜像包可以拷贝到其他机器docker环境中加载
#docker save fav:latest -o favLatest.tar
#8、docker镜像加载
#scp /Users/lichenghang/favLatest.tar root@119.3.44.58:/root/
#scp /Users/lichenghang/lch-open-source/cloudfavorite-java/docker/docker-compose.yml root@119.3.44.58:/root/

#docker load -i favLatest.tar




#docker run -d -p 80:40100 fav:1.0.1


#mvn clean package docker:build -Dmaven.test.skip=true
#mvn clean kotlin:compile package docker:build -Dmaven.test.skip=true