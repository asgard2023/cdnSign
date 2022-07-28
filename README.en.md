# cdn back-to-source authentication signature algorithm

#### introduce
Back-to-origin authentication of cdn images or files
Support Qiniu, Ali, Tencent, etc. In theory, as long as the cdn that supports back-to-source authentication can use this solution.


#### Software Architecture
Software Architecture Description
Based on openresty's nginx+lua, the performance is very good.



#### Installation Tutorial

1. Install openresty (omitted)
2. lua configuration, see cdnSign.lua
3. nginx, see cdnAuth.conf

#### demo address

* lua test: http://175.178.252.112/lua2
* cdn authentication: http://175.178.252.112/cdnAuth?sign=c55dcc956a25d15722b06f768b7c89f9&t=62fe50a0&path=/test2.jpg  
  Note: This signature is valid for 10 years, i.e. (expires after July 28, 2032)