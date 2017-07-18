#include <iostream>
#include <cstring>

void foo(char *c)
{
    char b[] = "fuck you";
    std::cout << c << std::endl;
    std::cout << c[0] << std::endl;
    strcpy(c, b);
}

char (*str_array())[50] {
    static char arr[][50] = {"hello", "world"};

    return arr;
}

int main(void)
{
    char a[1024] = "hello world";

//    foo(a);
    char (*arr)[50];

    arr = str_array();
    std::cout << arr[0] << std::endl;
//    std::cout << &a << std::endl;
//    std::cout << &a[0] << std::endl
//
    std::cout << a << std::endl;

    return 0;
}

