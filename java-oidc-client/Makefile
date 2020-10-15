configure_kong_service:
	curl -s -X POST http://localhost:8001/services \
		-d name=java-project \
		-d url=http://localhost:9000/home

configure_kong_route:
	curl -i -X POST http://localhost:8001/services/java-api/routes \
	  --data 'paths[]=/home' \
	  --data name=java-api

verify_java:
	curl -i -X GET http://localhost:9000/home

#############  Configure kong-oidc plugin with Java client #############
set_host_ip:
	HOST_IP=$(ipconfig getifaddr en0)

set_kong_secret:
	CLIENT_SECRET=665af6a4-6ec9-4c5d-a50a-66c7a651a987

configure_client_kong_keycloak:
	curl -s -X POST http://localhost:8001/plugins \
          -d name=oidc \
          -d config.client_id=java-client \
          -d config.client_secret=665af6a4-6ec9-4c5d-a50a-66c7a651a987 \
          -d config.discovery=http://10.0.2.245:8180/auth/realms/master/.well-known/openid-configuration \
          -d service=java-client \
          | python -mjson.tool