public @Service class Publisher implements CommandLineRunner {

  private final RabbitTemplate rabbitTemplate;
  private final Reader reader;

  public Publisher(RabbitTemplate rabbitTemplate, Reader reader) {
    this.rabbitTemplate = rabbitTemplate;
    this.reader = reader;
  }

  public @Override void run(String... args) throws Exception {
    rabbitTemplate
        .convertAndSend(RabbitMQConfiguration.TOPIC_EXCHANGE_NAME, "msg.new", getMessage());
    reader.getLatch().await(10, TimeUnit.SECONDS);
  }

  private List<Message> getMessage() {
    List<Message> messages = new ArrayList<>();
    
    messages.add("Hi All");
    messages.add("From GreenWeb Docker Camp");

    return messages;
  }
}
