# Use only at the first time
bootstrap_kong:
	docker-compose run --rm kong kong migrations bootstrap
	docker-compose run --rm kong kong migrations up
# Start all containers
start:
	docker-compose up

########################  Mock service example ##########################
mock_service:
	curl -s -X POST http://localhost:8001/services \
        -d name=mock-request \
        -d url=http://mockbin.org/request \
        | python -mjson.tool

mock_route:
	curl -i -X POST http://localhost:8001/services/mock-request/routes \
      --data 'paths[]=/mock' \
      --data name=mock-request

verify_mock:
	curl -i -X GET http://localhost:8000/mock/request

# Configure mock service
prepare_mock_service: mock_service mock_route verify_mock

# Get configuration details
check_kong_oidc_config:
	curl -s http://localhost:8180/auth/realms/master/.well-known/openid-configuration | python -mjson.tool

#############  Configure kong-oidc plugin with Kong client #############
set_host_ip:
	HOST_IP=$(ipconfig getifaddr en0)

set_kong_secret:
	CLIENT_SECRET=<client_secret_from_keycloak>

configure_client_kong_keycloak: set_host_ip set_kong_secret
	curl -s -X POST http://localhost:8001/plugins \
          -d name=oidc \
          -d config.client_id=kong \
          -d config.client_secret=${CLIENT_SECRET} \
          -d config.discovery=http://${HOST_IP}:8180/auth/realms/master/.well-known/openid-configuration \
          | python -mjson.tool
