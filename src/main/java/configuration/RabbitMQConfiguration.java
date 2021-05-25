public @Configuration class RabbitMQConfiguration {

  public static final String TOPIC_EXCHANGE_NAME = "mflash-exchange";
  public static final String QUEUE_NAME = "mflash-queue";
  private static final String ROUTING_KEY = "message.#";

  public @Bean TopicExchange topicExchange() {
    return new TopicExchange(TOPIC_EXCHANGE_NAME);
  }

  public @Bean Queue queue() {
    return new Queue(QUEUE_NAME);
  }

  public @Bean Binding binding() {
    return BindingBuilder.bind(queue()).to(topicExchange()).with(ROUTING_KEY);
  }

  public @Bean SimpleRabbitListenerContainerFactory listenerContainerFactory(
      ConnectionFactory connectionFactory) {
    final var factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setMessageConverter(messageConverter());
    return factory;
  }

  public @Bean RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    final var rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(messageConverter());
    return rabbitTemplate;
  }

  public @Bean MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }
}
