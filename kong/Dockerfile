FROM kong:2.0.2-alpine

USER root

ENV KONG_PLUGINS=oidc

ADD lib/resty/openidc.lua /usr/local/openresty/lualib/resty/openidc.lua

ADD plugins/oidc /usr/local/share/lua/5.1/kong/plugins/oidc

RUN luarocks install lua-resty-http
RUN luarocks install lua-resty-session
RUN luarocks install lua-resty-jwt 0.2.2

# Disabling import directly this lib because has a conflict with lua-resty-session
# RUN luarocks install lua-resty-openidc

USER kong
