CREATE USER user_service_user WITH PASSWORD 'password123';
CREATE DATABASE user_service_db OWNER user_service_user;
GRANT ALL PRIVILEGES ON DATABASE user_service_db TO user_service_user;

CREATE USER catalog_service_user WITH PASSWORD 'password123';
CREATE DATABASE catalog_service_db OWNER catalog_service_user;
GRANT ALL PRIVILEGES ON DATABASE catalog_service_db TO catalog_service_user;

CREATE USER adoption_service_user WITH PASSWORD 'password123';
CREATE DATABASE adoption_service_db OWNER adoption_service_user;
GRANT ALL PRIVILEGES ON DATABASE adoption_service_db TO adoption_service_user;