#include "../Headers/connectionservice.h"

ConnectionService::ConnectionService() {

}

ConnectionService::~ConnectionService() {

}


void ConnectionService::initialize(fd_set* client_sink, int* server_fd, int*max_fd) {
    this->client_fds = client_sink;
    this->server_fd = server_fd;
    this->max_fd = max_fd;
}

void ConnectionService::run() {
    std::cout << "Hello World" << std::endl;
}

void ConnectionService::stop() {
    std::cout << "Hello World" << std::endl;
}

void ConnectionService::loop() {
    std::cout << "Hello World" << std::endl;
}