## Chapter. 7

### 

#### 도메인 격리

3가지의 응용 기능

1. Tracking Query : 특정 화물의 과거와 현재 처리 상태에 접근
2. Booking Logging Application : 화물 예약 시스템
3. Incident Logging Application : 각 cargo 처리 내역을 기록

엔티티, Value object 구분

Customer

​	- entity

Cargo

​	- entity

CarrierMovement

​	- entity

location : 

Delivery History

DeliverySpecification : Delivery specification은 cargo에 연관되어 보이지만 배송 명세는 Delivery History에 속해 있는 것이 자연스러워보임, DeliveryHistory와 DeliverySpecification는 따로 다닐 수 없는 것 같다



#### 엔티티의 연관관계

Customer

>customar가 cargo의 모든 객체를 참조한다면 장기적으로 좋지 않다
>
>customer를 특화된 '책임'에서 벗어나게 해야한다
>
>customer -> cargo 를 참조하지 않게 하고, repository를 통해서 역으로 참조할 수 있다

Cargo

Delivery Specification (value object) : value object는 보통 자기 소유주를 참조하면 안된다

##### 순환참조

cargo - delivery history - handling event에서 순환참조가 일어나고 있다. 동일한 데이터를 보관하지 않도록 주의



#### 애그리거트의 경계

Cargo는 애그리거트의 루트가 되어야 한다. 카고가 애그리거트의 경계를 지정하기 조금 어려운거 같은데, 책에서는 카고가 존재하지 않았을 경우 똑같이 존재하지 않는 것들을 예로 들었다. 