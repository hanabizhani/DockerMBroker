public @Service class Consumer {

  private CountDownLatch latch = new CountDownLatch(1);

  @RabbitListener(queues = RabbitMQConfiguration.QUEUE_NAME, containerFactory = "listenerContainerFactory")
  public void receiveMessage(final List<Message> messages) {
    messages.forEach(System.out::println);
    latch.countDown();
  }

  public CountDownLatch getLatch() {
    return latch;
  }
}
