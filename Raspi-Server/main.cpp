#include "Headers/serverstarter.h"
// #define BUFSIZE 100

// Usage ./[Executable File Name] [Port Number]
int main(int argc, char** argv)
{
    if (argc != 2) {    // File Name, Port Number
        std::cout << "Unabled Input Usage ./[Executable File Name] [Port Number]" << std::endl;
        return 0;
    }

    int port = atoi(argv[1]);

    ServerStarter starter;
    starter.initialize(port);
    starter.start();

    getchar();

    starter.stop();

    /*
    // 소켓 관련 변수
    sockaddr_in server_addr, client_addr;
    int server_fd, client_fd;
    socklen_t client_size;
    char client_ipaddr[16]; // 접속한 클라이언트의 IP 주소

    int msg_size;
    char buffer[BUFSIZE];

    // select 관련 변수
    fd_set client_fds, temp_fds;
    timeval timeout_desc;
    int max_fd;
    int result_value;
    */


    // if((server_fd = socket(AF_INET, SOCK_STREAM, 0)) == -1){
	// 	std::cout << "Cannot Create Socket..." << std::endl;
	// 	return 0;
	// }
    // memset(&server_addr, 0x00, sizeof(server_addr));
    // server_addr.sin_family = AF_INET;
	// server_addr.sin_addr.s_addr = htonl(INADDR_ANY);
	// server_addr.sin_port = htons(port);

    // if(bind(server_fd, (struct sockaddr *)&server_addr, sizeof(server_addr))){
	// 	std::cout << "Cannot Bind Server Info..." << std::endl;
	// 	return 0;
	// }

    // if (listen(server_fd, 5) < 0) {
	// 	std::cout << "Cannot listen request" << std::endl;
    //     return 0;
	// }

    // FD_ZERO(&client_fds);
    // FD_SET(server_fd, &client_fds);
    
    // max_fd = server_fd;




    // std::cout << "Initialize Connection Service..." << std::endl;

    // while (1) {
    //     temp_fds = client_fds;

    //     timeout_desc.tv_sec = 2;
    //     timeout_desc.tv_usec = 0;

    //     result_value = select(max_fd + 1, &temp_fds, (fd_set *)NULL, (fd_set *)NULL, &timeout_desc);

    //     if (result_value == -1) {
    //         std::cout << "Error: Cannot checking select -> " << strerror(errno) << std::endl;
    //         continue;
    //     }

    //     if (result_value == 0) {
    //         continue;
    //     }

    //     if (FD_ISSET(server_fd, &temp_fds)) {
    //         client_size = sizeof(client_addr);
    //         client_fd = accept(server_fd, (struct sockaddr*)&client_addr, (socklen_t*)&client_size);
    //         FD_SET(client_fd, &client_fds);

    //         if (client_fd > max_fd) {
    //             max_fd = client_fd;
    //         }
    //         inet_ntop(AF_INET, &client_addr.sin_addr.s_addr, client_ipaddr, sizeof(client_ipaddr));
    //         std::cout << "Server: " << client_ipaddr << " client connected -->" << client_fd << "   maxfd : " << max_fd << std::endl;

    //         msg_size = read(client_fd, buffer, 1024);
    //         write(client_fd, buffer, msg_size);
    //     }
    // }
    // close(server_fd);

    return 0;
}
