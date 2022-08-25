# Kafka vs RabbitMQ

kafka vs rabbitmq 둘 다 오픈소스임

### RabbitMQ

일반적은 메시지 브로커 목적이다. MQTT, AMQP, STOMP 프로토콜 지원

### Kafka

카프카는 높은 데이터 유입 및 스트림의 용도로 개발된 메시지 버스이다

### 아키텍처의 차이점?

Rabbitmq 

- publisher → exchange → queue → consumer 의 구조
    
    ![Untitled](Untitled%202.png)
    
- 범용 메시지 브로커
- 동기, 비동기 지원
- 다중 노드 클러스터

### Kafka

![Untitled](Untitled%203.png)

이벤트는 카프카 토픽에 대기되고 여러 소비자가 동일한 토픽을 구독할 수 있다. topic은 성능 향상을 위해 브로커 간에 데이터를 분할하는 파티션으로 나뉜다.

- 외부 서비스에서 관리 - Apache Zookeeper

### Pull(Kafka) vs Push (Rabbitmq)

pull 기반 방식

1. 소비자는 메시지 일괄 처리 요청
2. 메시지가 없을 때 타이트한 루프를 방지하기 위해 long 폴롱일 지원
3. 더 많은 메시지가 처리 가능하다

### push 기반

1. push 기반으로 소비자에 정의된 prefetch 제한을 통해 소비자를 중지할 수 있다
2. 메시지를 개별적으로 배포하여 균등하게 병렬화 시킬 수 있고 도착한 순서대로 처리되도록 한다 (소비자가 죽어서 메시지를 못 받는 경우가 생김)

### AMQP (Advanced Message Queuing Protocol)

### 성능 차이

### 카프카

rabbitmq 같은 브로커보다 높은 성능을 제공한다. 순차적 I/O를 사용하여 대기열 구현에 적합함. 

### RabbitMQ

초당 백만 메시지 처리가 가능하지만 더 많은 리소스가 필요하다. 

### 카프카가 필요한 시점

- 복잡한 라우팅, 100k/s 이상의 이벤트 처리량
- 다단계 파이프에서 스트림 데이터 처리

### RabbitMQ가 필요한 시점

- STOMP, AMQP와 같은 레거시 프로토콜을 지원해야 하는 애플리케이션
- 메시지 별로 일관성에 대한 제어
- 소비자에 대한 복잡한 라우팅
- 다양한 pub/sub, 지점간 request/response 메시징 기능이 필요한 경우