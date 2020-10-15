kong_secret=<your-client-secret>
init: create_service create_route configure_oidc_plugin

create_service:
	curl -s -X POST http://localhost:8001/services \
			-d name=mock-request \
			-d url=http://mockbin.org/request

create_route:
	curl -i -X POST http://localhost:8001/services/mock-request/routes \
		  --data 'paths[]=/mock' \
		  --data name=mock-request

# It will configure the plugin with the Kong client created in Keycloak
configure_oidc_plugin:
	curl -s -X POST http://localhost:8001/plugins \
          -d name=oidc \
          -d config.client_id=kong \
          -d config.client_secret=${kong_secret} \
          -d config.discovery=http://keycloak-host:8180/auth/realms/master/.well-known/openid-configuration \
          -d config.introspection_endpoint=http://keycloak-host:8180/auth/realms/master/protocol/openid-connect/token/introspect \

print_access_token:
	curl -s http://keycloak-host:8180/auth/realms/master/protocol/openid-connect/token \
		  -d grant_type=client_credentials \
		  -d client_id=kong \
		  -d client_secret=${kong_secret} \
		  | jq -r .access_token

verify_active_token:
	curl -s -X POST http://keycloak-host:8180/auth/realms/master/protocol/openid-connect/token/introspect \
		 -d token=<access-token> \
		 -d client_id=kong \
		 -d client_secret=${kong_secret} \
		 | python -mjson.tool
