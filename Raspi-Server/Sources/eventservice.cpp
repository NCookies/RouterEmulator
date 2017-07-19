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

    char recv_buff[max_buff_size];
    char res_buff[max_buff_size];
    char rep_buff[max_buff_size];

    ssize_t recv_size;
    size_t res_send_size;
    size_t rep_send_size;
    // char client_ipaddr[16];

    while (is_running) {
        temp_fds = *client_fds;

        timeout_desc.tv_sec = 2;
        timeout_desc.tv_usec = 0;

        result_value = select(*max_fd + 1, &temp_fds, (fd_set *) NULL, (fd_set *) NULL, &timeout_desc);

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
                    recv_buff[max_buff_size] = {0};
                    memset(recv_buff, 0x00, sizeof(recv_buff));
                    recv_size = read(i, &recv_buff, max_buff_size);

                    std::cout << "Received data from client : " << recv_buff << std::endl;

                    if (recv_size <= 0) {    // 값을 정상적으로 받아오지 못했음
                        if (i >= *max_fd) {
                            *max_fd = i - 1;
                        }
                        FD_CLR(i, client_fds);
                        std::cout << "FD " << i << " is closed" << std::endl;
                    } else {
                        // 버퍼 초기화
                        res_buff[max_buff_size] = {0};
                        memset(res_buff, 0x00, sizeof(res_buff));

                        rep_buff[max_buff_size] = {0};
                        memset(rep_buff, 0x00, sizeof(rep_buff));

                        // JSON파싱
                        bool is_report =
                                json_service.parse(recv_buff, res_buff, rep_buff, &res_send_size, &rep_send_size);

                        if (write(i, res_buff, res_send_size) < 0) {
                            std::cout << strerror(errno) << std::endl;
                        }
                        std::cout << "Send data to client : " << res_buff << std::endl;

                        // 브로드캐스팅
                        if (is_report) {
                            for (register int j = 0; j < *max_fd + 1; ++j) {
                                if (j != *server_fd && j != i) {
                                    if (write(j, rep_buff, max_buff_size) < 0) {
                                        std::cout << strerror(errno) << std::endl;
                                    }
                                    std::cout << "Send data to client : " << rep_buff << std::endl;
                                }
                            }
                        }
                    }

                }
            }
        }

    }
}
