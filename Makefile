# Use only at the first time
bootstrap:
	docker-compose run --rm kong kong migrations bootstrap
	docker-compose run --rm kong kong migrations up
# Start all containers
start:
	docker-compose up

prepare_kong:
	$(MAKE) -C kong init

start_java_client:
	$(MAKE) -C java-oidc-client run
