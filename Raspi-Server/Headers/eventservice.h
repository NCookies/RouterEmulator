#ifndef _EVENTSERVICE_H
#define _EVENTSERVICE_H

#include <iostream>
#include <cstdlib>
#include <cstring>
#include <cerrno>

/* According to POSIX.1-2001, POSIX.1-2008 */
#include <sys/select.h>

/* According to earlier standards */
#include <sys/time.h>
#include <sys/types.h>
#include <unistd.h>

#include <sys/socket.h>

#include <thread>

#include "jsonservice.h"
#include "defs.h"
#include "service.h"


class EventService : public Service {
private:
    fd_set* client_fds;
    int* max_fd;
    int* server_fd;

    std::thread* worker_sink;
    bool is_running = false;

    JsonService json_service;

private:
    virtual void loop() override;

public:
    EventService();
	~EventService();

	virtual void initialize(fd_set* client_sink,
                            int* server_fd,
                            int*max_fd) override;
    virtual void run()  override;
	virtual void stop() override;
};

#endif
