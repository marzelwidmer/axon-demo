FROM openjdk:8-jdk

ENV AXONSERVER_HOME /opt/axonserver

COPY bin/uid_entrypoint ${AXONSERVER_HOME}/uid_entrypoint
COPY bin/axonserver.jar ${AXONSERVER_HOME}/axonserver.jar
COPY bin/axonserver-cli.jar ${AXONSERVER_HOME}/axonserver-cli.jar

EXPOSE 8024
EXPOSE 8124

ENV PATH=${AXONSERVER_HOME}:${PATH} HOME=${AXONSERVER_HOME}

RUN chmod -R 0755 ${AXONSERVER_HOME} && \
    chgrp -R 0 ${AXONSERVER_HOME} && \
    chmod -R g=u ${AXONSERVER_HOME} /etc/passwd


USER 10001
WORKDIR ${AXONSERVER_HOME}

ENTRYPOINT [ "uid_entrypoint" ]


VOLUME [ "${AXONSERVER_HOME}/data" ]
VOLUME [ "${AXONSERVER_HOME}/log" ]

CMD java -jar ${AXONSERVER_HOME}/axonserver.jar
