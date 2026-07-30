# Copyright 2026 上海如静知华信息科技有限公司
.PHONY: dev build test up down
dev:
	cd frontend && npm run dev:demo
build:
	cd frontend && npm run build
	cd backend && mvn -DskipTests package
test:
	cd backend && mvn test
	cd frontend && npm run build:demo
up:
	docker compose up --build -d
down:
	docker compose down
