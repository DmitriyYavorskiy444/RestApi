package examTask.users;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaService {
    KafkaProducer<String, String> kafkaProducer;

    public KafkaService() {
        this("localhost", 9092);
    }

    public KafkaService(String host, int port) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", String.format("%s:%s", host, port));
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        this.kafkaProducer = new KafkaProducer<>(properties);
    }

    public void sendMessageKey(String topic, String key, String value) {
        try {
            kafkaProducer.send(new ProducerRecord<>(topic, key, value)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("Done");
    }

    public void sendMessage(String topic, String message) {
        sendMessageKey(topic, null, message);
    }

    public void sendMessageBatch(String topic, List<String> messages, double interval) throws InterruptedException {
        for (int i = 0; i < messages.size(); i++) {
            System.out.println(i);
            sendMessageKey(topic, null, messages.get(i));
            Thread.sleep((long) (interval * 1000));
        }
    }
}