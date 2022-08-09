# cdn回源鉴权签名算法(nginx+lua)

#### 介绍
cdn图片或文件的回源鉴权  
支持七牛、阿里、腾讯等，理论上只要支持回源鉴权的cdn都能用这套方案。


#### 软件架构
软件架构说明  
基于openresty的nginx+lua，性能很好。  



#### 安装教程

1. 安装openresty(略)
2. lua配置，见：src/openresty/cdnSign.lua
3. nginx，见：src/openresty/cdnAuth.conf

#### 演示地址

* lua测试: http://cdnauth.opendfl.org.cn/lua2
* 签名生成：见：src/test/java/utils/CdnSignChainTest.java
* cdn验证: http://cdnauth.opendfl.org.cn/cdnAuth?sign=c55dcc956a25d15722b06f768b7c89f9&t=62fe50a0&path=/test2.jpg  
注：这个签名有效期10年，即（2032年7月28号之后到期）
