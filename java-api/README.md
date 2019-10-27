## Getting started
Setting up postgres for local dev:
```bash
docker run --rm -it --network="host" -v /var/lib/postgresql/data:/var/lib/postgresql/data postgres:12
```
### Building
Building java api docker image
```bash
docker build -t java-api .
```
