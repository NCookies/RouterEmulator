#ifndef _SERVICE_H
#define _SERVICE_H

#include <sys/select.h>


class Service {

protected:
	virtual void loop()	= 0; //loop for service

public:

	virtual void initialize(
		fd_set* client_sink,
		int* server_fd,
		int* max_fd)	= 0; //initialize service
	virtual void run()	= 0; //run service's thread
	virtual void stop()	= 0; //stop service's thread
};

#endif
