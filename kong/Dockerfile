FROM kong:2.0.2-alpine

USER root

ENV KONG_PLUGINS=oidc

ADD plugins/oidc /usr/local/share/lua/5.1/kong/plugins/oidc

RUN luarocks install lua-resty-openidc

USER kong
