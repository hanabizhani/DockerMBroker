import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public @Service class Publisher implements CommandLineRunner {

  private final RabbitTemplate rabbitTemplate;
  private final Consumer consumer;

  public Publisher(RabbitTemplate rabbitTemplate, Consumer consumer) {
    this.rabbitTemplate = rabbitTemplate;
    this.consumer = consumer;
  }

  public @Override void run(String... args) throws Exception {
    rabbitTemplate
        .convertAndSend(RabbitMQConfiguration.TOPIC_EXCHANGE_NAME, "message.new", getMessage());
    reader.getLatch().await(10, TimeUnit.SECONDS);
  }

  private List<Message> getMessage() {
    List<Message> messages = new ArrayList<>();
    
    messages.add("Hi All");
    messages.add("From GreenWeb Docker Camp");

    return messages;
  }
}
