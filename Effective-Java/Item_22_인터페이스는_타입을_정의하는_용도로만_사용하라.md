인터페이스는 자신을 구현한 클래스의 인스턴스를 참조할 수 있는 타입 역할을 한다. 클래스가 어떤 인터페이스를 구현한다는 것은 자신의 인스턴스로 무엇을 할 수 있는지를 클라리언트에게 알려주는 것이다.

인터페이스는 오직 이 용도라만 사용해야 한다. 이 지침에 맞는 않는 예로 소위 상수 인터페이스라는 것이 있다. 상수 인터페이스란 메서드 없이, 상수를 뜻하는 static final 필드로만 가득 찬 인터페이스를 말한다. 



```java
public interface PhysicalConstants {
    static final double AVOGADROS_NUMBER = 6.022_140:;
    ...
}
```

상수 인터페이스 안티패턴은 인터페이스를 잘못 사용한 예이다. 클래스 내부에서 사용하는 상수는 외부 인터페이스가 아니라 내부 구현에 해당한다. 그렇기 때문에 상수만 표현하는 것은 내부 구현을 API로 노출하는 행위다. 

상수를 공개할 목적이라면 더 합당한 선택지가 몇 가지 있다. 특정 클래스나 인터페이스와 강하게 연관된 상수라면 그 클래스나 인터페이스 자체에 추가해야 한다. 모든 숫자 기본 타입의 박싱 클래스가 대표적으로, Integer와 Double에 선언된 MIN_VALUE와 MAX_VALUE 상수가 이런 예다. 열거 타입으로 나타내기 적합한 상수라면 열거 타입으로 만들어서 공개 하면 된다. 그것도 아니라면 인스턴스화할 수 없는 유틸리티 클래스에 담아 공개하자. 

```java
public class Wyjax {
    public static final int AGE = 27;
    public static final double WEIGHT = 60.452_232;  // 밑줄로 숫자를 나눠서 보여주도록 사용할 수 있다.
}
```

위의 클래스를 사용하려면 Wyjax.AGE라고 명시해야 한다. 만약 wyjax 클래스가 여러 번 사용되는 경우라면 static import를 통해서 클래스 이름을 생략하도록 할 수 있다.



#### 핵심 정리

> 인터페이스는 타입을 정의하는 목적으로만 사용해야 한다. 상수 공개용 수단으로 사용하지는 말자 !