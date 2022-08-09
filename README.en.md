# cdn back-to-source authentication signature algorithm(nginx+lua)

#### introduce
Back-to-origin authentication of cdn images or files
Support Qiniu, Ali, Tencent, etc. In theory, as long as the cdn that supports back-to-source authentication can use this solution.


#### Software Architecture
Software Architecture Description
Based on openresty's nginx+lua, the performance is very good.



#### Installation Tutorial

1. Install openresty (omitted)
2. lua configuration, see src/openresty/cdnSign.lua
3. nginx, see src/openresty/cdnAuth.conf

#### demo address

* lua test: http://cdnauth.opendfl.org.cn/lua2
* Signature generation: see: src/test/java/utils/CdnSignChainTest.java
* cdn authentication: http://cdnauth.opendfl.org.cn/cdnAuth?sign=71fa33cbd826e7a8618ec5ee9f95f5c3&t=630e5942&path=/t2.jpg
* Sample Pictures: http://cdntest.opendfl.org.cn/t2.jpg?sign=71fa33cbd826e7a8618ec5ee9f95f5c3&t=630e5942
  Note: This signature is valid for 10 years, i.e. (expires after Aug. 9, 2032)