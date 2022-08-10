# cdn回源鉴权签名算法(nginx+lua)

#### 介绍
cdn图片或文件的回源鉴权  
支持七牛、阿里等，理论上只要支持回源鉴权的cdn都能用这套方案。


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
* cdn验证: http://cdnauth.opendfl.org.cn/cdnAuth?sign=71fa33cbd826e7a8618ec5ee9f95f5c3&t=630e5942&path=/t2.jpg  
* 图片示例：http://cdntest.opendfl.org.cn/t2.jpg?sign=71fa33cbd826e7a8618ec5ee9f95f5c3&t=630e5942  
注：这个签名有效期10年，即（2032年8月09号之后到期）
