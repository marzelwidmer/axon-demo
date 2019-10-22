FROM axoniq/axonserver

# TODO CHECK if this needed....
RUN chmod -R 0755 ${AXONSERVER_HOME} && \
    chgrp -R 0 ${AXONSERVER_HOME} && \
    chmod -R g=u ${AXONSERVER_HOME} /etc/passwd

USER 10001
WORKDIR ${AXONSERVER_HOME}

VOLUME [ "${AXONSERVER_HOME}/data" ]
VOLUME [ "${AXONSERVER_HOME}/log" ]
