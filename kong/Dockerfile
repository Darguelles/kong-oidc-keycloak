FROM kong:2.0.2-alpine

USER root

ENV KONG_PLUGINS=bundled,oidc,session

# Add libs
ADD lib/resty/openidc.lua /usr/local/openresty/lualib/resty/openidc.lua
ADD lib/resty/session /usr/local/openresty/lualib/resty/session
ADD lib/resty/session.lua /usr/local/openresty/lualib/resty/session.lua

# Add oidc plugin
ADD plugins/oidc /usr/local/share/lua/5.1/kong/plugins/oidc

# Install dependencies
RUN luarocks install lua-resty-http
RUN luarocks install lua-resty-jwt 0.2.2

USER kong
