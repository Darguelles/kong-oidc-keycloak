# Use only at the first time
bootstrap:
	docker-compose run --rm kong kong migrations bootstrap
	docker-compose run --rm kong kong migrations up
# Start all containers
start:
	docker-compose up

init_kong:
	$(MAKE) -C kong init
