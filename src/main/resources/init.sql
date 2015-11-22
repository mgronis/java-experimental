CREATE USER experiment@localhost IDENTIFIED BY 'goexperiment';

GRANT USAGE on javaexperimental.* TO experiment@localhost IDENTIFIED BY 'goexperiment';
GRANT ALL PRIVILEGES ON javaexperimental.* TO experiment@localhost;

CREATE SCHEMA IF NOT EXISTS javaexperimental;
