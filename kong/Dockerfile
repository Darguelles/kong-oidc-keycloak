FROM kong:2.0.2-alpine

USER root

ENV KONG_PLUGINS=oidc

ADD lib/resty/openidc.lua /usr/local/openresty/lualib/resty/openidc.lua

ADD plugins/oidc /usr/local/share/lua/5.1/kong/plugins/oidc

ADD plugins/oidc/config/ngx_session.kong.conf /usr/local/kong

RUN luarocks install lua-resty-http
RUN luarocks install lua-resty-session
RUN luarocks install lua-resty-jwt 0.2.2

USER kong
