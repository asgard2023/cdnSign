local os = require "os"
local resty_md5 = require "resty.md5"
local md5 = resty_md5:new()
local str = require "resty.string"
local t = nil
local sign = nil
local path = nil
local sourceCdn = nil;
-- 签名密钥
local signKey = 'xxxxxxxxxxxx'
local request_method = ngx.var.request_method

-- 获取参数值
local function getParams(req, p1, p2)
    local v = req()[p1]
    if v == nil then
        v = req()[p2]
    end
    return v;
end

-- 从Head获取参数值
local function getHeadOrParams(req, p)
    local v = ngx.req.get_headers()[p]
    if v == nil then
        v = req()[p]
    end
    return v;
end

-- 去除url后面的所有参数
local function trimUrlParam(url)
    if url == nil then
        return nil
    end

    local i = string.find(url, "?");
    if i ~= nil and i > 1 then
        url = string.sub(url, 0, i - 1)
    end
    return url;
end

-- 获取请求ip
local function getRequestIp()
    local clientIP = ngx.req.get_headers()["X-Real-IP"]
    if clientIP == nil then
        clientIP = ngx.req.get_headers()["x_forwarded_for"]
    end
    if clientIP == nil then
        clientIP = ngx.var.remote_addr
    end
    return clientIP
end

-- 获取请求url中的签名参数(siqn,t)或(signq,tq)
if request_method == "GET" then
    t = getParams(ngx.req.get_uri_args, 't', 'tq')
    sign = getParams(ngx.req.get_uri_args, 'sign', 'signq')
    path = getHeadOrParams(ngx.req.get_uri_args, 'path')
    sourceCdn = getHeadOrParams(ngx.req.get_uri_args, 'sourceCdn')
elseif request_method == "POST" then
    ngx.req.read_body()
    t = getParams(ngx.req.get_post_args, 't', 'tq')
    sign = getParams(ngx.req.get_post_args, 'sign', 'signq')
    path = getHeadOrParams(ngx.req.get_post_args, 'path')
    sourceCdn = getHeadOrParams(ngx.req.get_post_args, 'sourceCdn')
end

-- 验证签名的sign,t是否有效
local function validSign(path, sign, t)
    if t == nill then
        t = 0;
        return false
    end
    local tt = t;
    t = '0x' .. t
    if pcall(function()
        t = string.format("%d", t)
    end) then

    else
        --异常处理
        t = 0
        return false
    end

    if tonumber(t) < os.time() then
        return false
    end

    --签名参数组成
    local signPath = signKey .. path .. tt
    md5:update(signPath)
    local md5_sum = md5:final()
    md5:reset()
    local pathSign = str.to_hex(md5_sum)
    pathSign = string.lower(pathSign)
    if (pathSign == sign) then
        return true
    end
    return false
end

path = trimUrlParam(path)
local isValid = validSign(path, sign, t)

if isValid == false then
    --ngx.say('fail:',getRequestIp())
    ngx.exit(ngx.HTTP_FORBIDDEN)
else
    ngx.say('ok')
end
-- ngx.say('t=',t,',sign=',sign,',path=',path,',isValid=',isValid)