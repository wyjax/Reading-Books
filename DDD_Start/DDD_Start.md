### DDD Start

이걸 작성하는 목적 > 책읽으면서 쓰면서 되새김하면서 오래 기억되도록



##### 하위 도메인과 모델

```
도메인은 다수의 하위 도메인으로 구성된다. 
```

##### 도메인 모델 패턴

일반적인 애플리케이션은 4가지 계층으로 이루어져 있음 DB - [인프라스트럭처 - 도메인 - 응용 - 표현] 

##### 아키텍처 구성

1. 표현 or 사용자인터페이스 : 사용자의 요청을 처리하고 사용자에게 정보를 보여줌, 외부시스템도 사용자가 될 수 있음
2. 응용 : 사용자가 요청한 기능을 실행함, 업무 로직을 직접 구현하지 않고 도메인 시스템을 조합하여 기능을 실행
3. 도메인 : 시스템이 제공할 도메인의 규칙을 구현 > domain단에서 로직을 구현 !!
4. 인프라스트럭처 : db나 messaging 시스템과 같은 외부 시스템과의 연동을 처리



주문, 배송

```java
public class Order {
  private OrderState state;
  private ShippingInfo shippingInfo;
  
  public void changeShippingInfo(ShippingInfo shippingInfo) {
    if (/* 배송 변경가능한지 체크 */ ) {
      // Exception
    }
    this.shippingInfo = shippingInfo;
  }
}
```



#### 애그리거트

애그리거트는 관련된 모델을 하나로 모으는 것이다. 애그리거트에 속한 객체는 유사하거나 동일한 라이프사이클을 가진다. 

애그리거트에 속한 도메인들은 대부분이 같이 생성되고 소멸된다.



##### 주문

주문, 주문자 정보, 배송지, 배송자 정보, 주문 항목

##### 상품

상품그룹, 상품, 리뷰

##### 결제

결제 (결제는 주문하고 이루어지지 않고 따로 이루어질 수 도 있음)

##### 사용자

사용자, 사용자 권한, 사용자 등급



도메인A가 도메인B를 갖늗다고 하더라도 항상 같은 애그리거트의 관계를 가지지 않는다. 예를 들면 상품과 리뷰를 얘기하면 상품은 관리자가 등록하는 것이고, 리뷰는 상품을 배송받은 주문자가 하는 것이기 때문에 서로 생성되는 시점도 다르고 소멸되는 시점도 다르다.



#### 루트 엔티티

애그리거트의 정상 상태를 가지는 엔티티이다.

```java
public class Order {
  	// 애그리거트 루트는 애그리거트가 제공해야할 도메인 기능을 구현한다.
		public void changeShippingInfo(ShippingInfo shippingInfo) {
      
    }
  
  	public void changeOrderInfo(List<OrderLine> orderLines) {
      
    }
}
```

