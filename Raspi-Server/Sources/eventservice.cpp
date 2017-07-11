#include "../Headers/eventservice.h"

EventService::EventService()
    : client_fds(nullptr), max_fd(nullptr), server_fd(nullptr), worker_sink(nullptr), is_running(false)
{
}

EventService::~EventService() {

}


void EventService::initialize(fd_set* client_sink, int* server_fd, int*max_fd) {
    this->client_fds = client_sink;
    this->server_fd = server_fd;
    this->max_fd = max_fd;
}

void EventService::run() {
    while (!is_running) {
        is_running = true;
        worker_sink = new std::thread(&EventService::loop, this);
    }
}

void EventService::stop() {
    if (is_running) {
        is_running = false;
        if (worker_sink->joinable()) {
            worker_sink->join();
        }
        delete worker_sink;
        worker_sink = nullptr;
    }
}

void EventService::loop() {
	// check connection of clients and get operation from clients
    timeval timeout_desc;
	fd_set temp_fds;
	int result_value;

    while (is_running) {
        temp_fds = *client_fds;

        timeout_desc.tv_sec = 2;
        timeout_desc.tv_usec = 0;

        result_value = select(*max_fd + 1, &temp_fds, (fd_set *)NULL, (fd_set *)NULL, &timeout_desc);

        if (result_value == ResultCode::ERROR_SOMETHING) {
            std::cout << "Error: Cannot checking select in ConnectionSerivce -> " << strerror(errno) << std::endl;
            continue;
        }

        if (result_value == ResultCode::NOT_FOUND) {
            continue;
        }

        for (register int i = 0; i < *max_fd + 1; ++i) {
            if (FD_ISSET(i, &temp_fds)) {
                if (i != *server_fd) {
                    char temp_buffer[max_buff_size];
                    memset(temp_buffer, 0x00, sizeof(temp_buffer));
                    ssize_t size = recv(i, &temp_buffer, max_buff_size, 0);
                    if (size <= 0) {    // 값을 정상적으로 받아오지 못했음
                        if (i >= *max_fd) {
                            *max_fd = i - 1;
                        }
                        FD_CLR(i, client_fds);
                        std::cout << "FD " << i << " is closed" << std::endl;
                    }
                    std::cout << "hello " << temp_buffer << std::endl;

                }
            }
        }
    }
}
