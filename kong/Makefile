kong_secret=<kong-secret>
init: create_service create_route configure_oidc_plugin

create_service:
	curl -s -X POST http://localhost:8001/services \
			-d name=mock-request \
			-d url=http://mockbin.org/request

create_service:
	curl -s -X POST http://localhost:8001/services \
			-d name=userinfo \
			-d url=http://keycloak-host:8180/auth/realms/master/protocol/openid-connect/userinfo

create_route:
	curl -i -X POST http://localhost:8001/services/mock-request/routes \
		  --data 'paths[]=/mock' \
		  --data name=mock-request

create_route:
	curl -i -X POST http://localhost:8001/services/userinfo/routes \
		  --data 'paths[]=/userinfo' \
		  --data name=userinfo

# It will configure the plugin with the Kong client created in Keycloak
configure_oidc_plugin:
	curl -s -X POST http://localhost:8001/plugins \
          -d name=oidc \
          -d config.client_id=kong \
          -d config.client_secret=${kong_secret} \
          -d config.discovery=http://keycloak-host:8180/auth/realms/master/.well-known/openid-configuration \
          -d config.introspection_endpoint=http://keycloak-host:8180/auth/realms/master/protocol/openid-connect/token/introspect \
          -d config.session_name=MY_SESSION \

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


# It will configure the plugin with the Kong client created in Keycloak
patch_oidc_plugin:
	curl -s -X PATCH http://localhost:8001/plugins/844127e0-c54f-4970-96db-156f8abc636f \
          -d name=oidc \
          -d config.client_id=kong \
          -d config.client_secret=${kong_secret} \
          -d config.discovery=http://keycloak-host:8180/auth/realms/master/.well-known/openid-configuration \
          -d config.introspection_endpoint=http://keycloak-host:8180/auth/realms/master/protocol/openid-connect/token/introspect \
          -d config.introspection_cache_ignore=false \
          -d config.session_name=MY_SESSION \
          -d config.session_storage=redis \
          -d config.session_redis_host=redis \
          -d config.session_redis_port=6379