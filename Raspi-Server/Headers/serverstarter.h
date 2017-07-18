#ifndef _SERVERSTARTER_H
#define _SERVERSTARTER_H

#include <iostream>
#include <cstdlib>
#include <cstring>
#include <cerrno>

#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>

#include "connectionservice.h"
#include "eventservice.h"
#include "jsonservice.h"


class ServerStarter {

private:
    // 소켓 통신 관련 함수
    int server_fd;
    sockaddr_in server_addr, client_addr;

    // select 관련 변수
    fd_set client_fds;
    int max_fd;

    // 서비스 객체
    ConnectionService connection_service;
    EventService event_service;
public:
    ServerStarter();
    ~ServerStarter();

    int initialize(int port);
    void start();
    void stop();
};

#endif
