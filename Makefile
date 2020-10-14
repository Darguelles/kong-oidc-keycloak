BOOTSTRAP_KONG_DB:
	docker-compose run --rm kong kong migrations bootstrap
	docker-compose run --rm kong kong migrations up

START:
	docker-compose up

MOCK_SERVICE:
	curl -s -X POST http://localhost:8001/services \
        -d name=mock-request \
        -d url=http://mockbin.org/request \
        | python -mjson.tool

MOCK_ROUTE:
	curl -i -X POST http://localhost:8001/services/mock-request/routes \
      --data 'paths[]=/mock' \
      --data name=mock-request

VERIFY_MOCK:
	curl -i -X GET http://localhost:8000/mock/request

CHECK_KONG_OIDC_CONFIG:
	curl -s http://localhost:8180/auth/realms/master/.well-known/openid-configuration | python -mjson.tool

SET_HOST_IP:
	HOST_IP=$(ipconfig getifaddr en0)

SET_KONG_SECRET:
	CLIENT_SECRET=<client_secret_from_keycloak>

CONFIGURE_KONG_KEYCLOAK:
	curl -s -X POST http://localhost:8001/plugins \
          -d name=oidc \
          -d config.client_id=kong \
          -d config.client_secret=${CLIENT_SECRET} \
          -d config.discovery=http://${HOST_IP}:8180/auth/realms/master/.well-known/openid-configuration \
          | python -mjson.tool
