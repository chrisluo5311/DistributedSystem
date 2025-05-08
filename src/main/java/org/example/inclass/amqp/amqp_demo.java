//package org.example.inclass.amqp;
//
//import com.rabbitmq.client.Channel;
//import com.rabbitmq.client.Connection;
//import com.rabbitmq.client.ConnectionFactory;
//import com.rabbitmq.client.DeliverCallback;
//
//public class amqp_demo {
//    public static void main(String[] args) throws Exception {
//        String uri = System.getenv("CLOUDAMQP_URL");
//        if (uri == null) uri = "amqps://nnyvwrbp:3ndZIUbBD5Tq8TuQkD9oOk1425-175Sg@duck.lmq.cloudamqp.com/nnyvwrbp";
//
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setUri(uri);
//
//        //Recommended settings
//        factory.setConnectionTimeout(30000);
//
//        Connection connection = factory.newConnection();
//        Channel channel = connection.createChannel();
//
//        String queue = "hello";     //queue name
//        boolean durable = false;    //durable - RabbitMQ will never lose the queue if a crash occurs
//        boolean exclusive = false;  //exclusive - if queue only will be used by one connection
//        boolean autoDelete = false; //autodelete - queue is deleted when last consumer unsubscribes
//
//        channel.queueDeclare(queue, durable, exclusive, autoDelete, null);
//        String message = "Hello CloudAMQP!";
//
//        String exchangeName = "";
//        String routingKey = "hello";
//        channel.basicPublish(exchangeName, routingKey, null, message.getBytes());
//        System.out.println(" [✅] Sent '" + message + "'");
//
//        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//            String rc_message = new String(delivery.getBody(), "UTF-8");
//            System.out.println(" [✅] Received '" + rc_message + "'");
//        };
//        channel.basicConsume(queue, true, deliverCallback, consumerTag -> { });
//    }
//}
