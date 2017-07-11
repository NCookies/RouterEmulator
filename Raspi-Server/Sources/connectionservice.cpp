#include "../Headers/connectionservice.h"

ConnectionService::ConnectionService()
	: client_fds(nullptr), max_fd(nullptr), server_fd(nullptr), worker_sink(nullptr), is_running(false)
{
}

ConnectionService::~ConnectionService() {

}


void ConnectionService::initialize(fd_set* client_sink, int* server_fd, int*max_fd) {
    this->client_fds = client_sink;
    this->server_fd = server_fd;
    this->max_fd = max_fd;
}

void ConnectionService::run() {
    while (!is_running) {
        is_running = true;
        worker_sink = new std::thread(&ConnectionService::loop, this);
    }
}

void ConnectionService::stop() {
    if (is_running) {
        is_running = false;
        if (worker_sink->joinable()) {
            // join() 을 실행시키지 않으면 쓰레드가 종료되기 전에 프로그램이 끝나는 경우도 있음
            // 이 때에는 런타임 에러 발생
            // 따라서 종료 전에 join()을 호출해야 함
            worker_sink->join();
        }
        delete worker_sink;
        worker_sink = nullptr;
    }
}

void ConnectionService::loop() {
    std::cout << "Running Service..." << std::endl;
	std::cout << "Listen Accept..." << std::endl;

    int client_fd;
	int result_value;

	sockaddr_in client_desc;
	socklen_t client_size;
	char client_ipaddr[16]; // 접속한 클라이언트의 IP 주소

	timeval timeout_desc;
	fd_set temp_fds;

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

        if (FD_ISSET(*server_fd, &temp_fds)) {
            client_size = sizeof(client_desc);
            client_fd = accept(*server_fd, (struct sockaddr*)&client_desc, (socklen_t*)&client_size);
            FD_SET(client_fd, client_fds);

            if (client_fd > *max_fd) {
                *max_fd = client_fd;
            }

            inet_ntop(AF_INET, &client_desc.sin_addr.s_addr, client_ipaddr, sizeof(client_ipaddr));
            std::cout << "Server: " << client_ipaddr << " client connected -->"
                << client_fd << "   maxfd : " << max_fd << std::endl;
        }
    }
}
