Don't forget to have a RabbitMQ instance running, for instance run this to get sorted:

docker run -d -p 4369:4369 -p 5671-5672:5671-5672 -p 25672:25672 --hostname rabbit-host rabbitmq:3.6