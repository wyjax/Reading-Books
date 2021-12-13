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

