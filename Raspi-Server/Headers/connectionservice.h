#ifndef _CONNECTIONSERVICE_H
#define _CONNECTIONSERVICE_H

#include <iostream>

/* According to POSIX.1-2001, POSIX.1-2008 */
#include <sys/select.h>

/* According to earlier standards */
#include <sys/time.h>
#include <sys/types.h>
#include <unistd.h>

#include "defs.h"
#include "service.h"


class ConnectionService : public Service {
private:
    fd_set client_fds;
    int server_fd;
    int max_fd;

private:
    virtual void loop() override;
    
public:
    ConnectionService();
	~ConnectionService();

	virtual void initialize(fd_set* client_sink, 
                            int* server_fd, 
                            int*max_fd) override;
    virtual void run()  override;
	virtual void stop() override;
};

#endif
