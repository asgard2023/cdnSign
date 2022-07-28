# java版的cdn回源鉴权
如果要Nginx+lua的回源鉴权，不需要这套java版的，为了方便验签名写了CdnSignChainTest，顺便把java版的代码一起写完了而已。

## 在springmvc的web.xml加入servlet配置

```xml
<servlet>
    <servlet-name>cdnServlet</servlet-name>
    <servlet-class>servlet.org.ccs.cdnsign.servlet.CdnServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>cdnServlet</servlet-name>
    <url-pattern>/cdnauth</url-pattern>
</servlet-mapping>
```

## springboot模式，已配好servlet
### 访问地址
http://localhost:8080/cdnauth?sign=c55dcc956a25d15722b06f768b7c89f9&t=62fe50a0&path=/test2.jpg