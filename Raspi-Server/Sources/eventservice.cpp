#include "../Headers/eventservice.h"

EventService::EventService() {

}

EventService::~EventService() {

}


void EventService::initialize(fd_set* client_sink, int* server_fd, int*max_fd) {
    this->client_fds = client_sink;
    this->server_fd = server_fd;
    this->max_fd = max_fd;
}

void EventService::run() {
    std::cout << "Hello World" << std::endl;
}

void EventService::stop() {
    std::cout << "Hello World" << std::endl;
}

void EventService::loop() {
    std::cout << "Hello World" << std::endl;
}