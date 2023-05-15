echo "build the project"
mvn clean install

echo "deploy with redis"
docker-compose -f docker-compose-tools.yml up -d

echo "deploy with docker compose"
docker-compose -f docker-compose-test.yml up -d --force-recreate --build

echo "clean unused images"
sleep 20
docker rmi $(docker images -a -q)

echo "completed!"