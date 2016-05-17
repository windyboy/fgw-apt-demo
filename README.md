# 发改委API演示

使用Java开发API客户端演示
服务使用JWT授权
演示程序包含如何获得JWT授权字符串
并在使用接口的时候在HTTP HEADER里加入授权信息

## 安装

1. 安装java开发环境
2. 安装gradle运行环境
下载 gradle 安装包 http://www.gradle.org/downloads
设置环境变量 GRADLE_HOME 指向gradle 安装位置
把 GRADLE_HOME/bin 加入环境变量 PATH


## 使用方法

1. 根据生产环境修改相关参数配置
修改main.properties中参数
src/main/resources/main.properties 中需要修改的参数分别是 username/password/url
修改build.gradle中参数 
build.gradle 中可以修改的参数主要get方法中第一个参数为项目编号，put方法中第一个参数项目编号和第二个参数SBBH
3. 获取token string
在项目目录中执行指令 gradle executeToken
演示从服务器获得授权字符串的流程
4. 获取项目信息
在项目目录中执行指令 gradel executeGet
演示使用授权字符串，获得项目信息的流程
5. 更新项目SBBH信息
在项目目录中执行指令 gradel executeGet
演示使用授权字符串，更新项目SBBH的流程

## 版本历史

0.0.1 第一个版本

## Credits

Gradle http://gradle.org
OkHTTP https://github.com/square/okhttp
JWT https://jwt.io

## License

The MIT License (MIT)
Copyright (c) 2016 <copyright 广州智能>

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.