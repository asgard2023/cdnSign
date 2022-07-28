# java版的cdn回源鉴权

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