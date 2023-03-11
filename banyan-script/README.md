## package.sh
- 打完整安装包的脚本,打成tar包
```
allspark.tgz
│
└───jdk
└───mino
└───mysql
└───allspark
└───redis
└───tools
    README.md
    install.sh
    uninstall.sh
```
## install.sh
- 安装脚本
- 安装完后的目录结构
```
allspark
│
└───bin
│   │   allspark.sh
│   │   admin.sh
└───lib
    │   bootstrap.jar
    │   itings.jar
    │   xxx.jar
└───conf
│   │   application.ymal
└───log
    │   bootstrap.log
└───tmp
    │
└───data
    │
```

## uninstall.sh
- 卸载脚本

## allspark.sh
- 应用启停脚本

## admin.sh
- 应用管理台启停脚本
