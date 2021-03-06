## item 18 상속보다는 컴포지션을 사용하라



상속은 코드를 재사용하는 강력한 수단이지만, 항상 최선으로 작용하지 않는다. 잘못하면 오류를 내기 쉬운 소프트웨어를 만들게 된다. 상위 클래스와 하위 클래스를 모두 같은 프로그래머가 통제하는 패키지 안에서라면 상속도 안전한 방법이다.



메서드 호출과 달리 상속은 캡슐화를 깨뜨린다. 다르게 말하면, 상위 클래스가 어떻게 구현되느냐에 따라 하위 클래스 동작에 이상이 생길 수 있다. 상위 클래스는 릴리즈마다 내부 구현이 달라질 수 있는데, 이것으로 인해서 코드 한 줄 건드리지 않은 하위 클래스에서 오작동을 일으킬 수 있다.



대표적으로 HashTable과 Vector를 컬렉션 프레임워크에 포함시키자 이와 관련한 보안 구멍들을 수정해야 하는 사태가 벌어졌다. 이것들은 상위 클래스의 메서드 재정의로부터 문제가 되었다. 

메서드를 재정의하는 대신에 새로운 메서드를 추가하면 괜찮다고 생각할 수도 있다. 이 방식이 훨씬 안전한 것은 맞지만, 위험이 전혀 없는 건 아니다 !! >> 다음 릴리즈에 상위 클래스에서 똑같은 메서드명이 존재하고 반환명이 다르다면 이 또한 문제를 일으키게 된다.



문제가 많지만 다행하게도 이 문제를 모두 피해가는 묘안이 있다. 기존 클래스를 확장하는 대신에 새로운 클래스를 만들고 private 필드로 기존 클래스의 인스턴스를 참조하게 하자. 기존 클래스가 새로운 클래스의 구성요소로 쓰인다는 뜻에서 컴포지션이라고 한다.

> 이는 데코레이션 패턴이라고 한다.



#### 핵심정리

```
상속은 강력하지만 캡슐화를 해친다는 문제가 있다. 상속은 상위 클래스와 하위 클래스가 순수한 is-a 관계일 때만 써야 한다. is-a관계일 때도 안심할 수만은 없는게, 하위 클래스의 패키지가 상위 클래스와 다르고, 상위 클래스가 확장을 고려해 설계되지 않았다면 여전히 문제가 될 수 있다. 상속의 취약점을 피하려면 상속 대신 컴포지션과 전달을 사용하자. 특히 래퍼 클래스로 구현할 적당할 인터페이스가 있다면 더욱 그렇다. 래퍼 클래스는 하위 클래스보다 견고하고 강력하다.
```

