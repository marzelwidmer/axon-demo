# Stage 0, "build-stage", based on Node.js, to build and compile the frontend
FROM axoniq/axonserver

RUN chmod -R 0755 ${AXONSERVER_HOME} && \
    chgrp -R 0 ${AXONSERVER_HOME} && \
    chmod -R g=u ${AXONSERVER_HOME} /etc/passwd

USER 10001
WORKDIR ${AXONSERVER_HOME}

#EXPOSE 8024
#EXPOSE 8124

VOLUME [ "${AXONSERVER_HOME}/data" ]
VOLUME [ "${AXONSERVER_HOME}/log" ]
