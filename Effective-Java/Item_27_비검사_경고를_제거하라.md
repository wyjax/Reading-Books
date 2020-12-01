## Item. 27 비검사 경고를 제거하라



 제네릭을 사용하기 시작하면 수많은 컴파일러 경고를 보게 될 것이다. 비검사 형변환 경고, 비검사 메서드 호출 경고, 비검사 등등의 경고가 발생할 것이다.



 ```java
Set<Lark> exaltation = new HashSet();
 ```

 위의 코드는 잘못 작성되어 있다. 컴파일러가 알려준 것을 수정하면 경고를 사라진다. 하지만 자바 7부터는 매개변수 타입을 명시하지 않아도, 다이아몬드 연산자만으로 해결할 수 있다. 그러면 컴파일러가 올바른 실제 타입 매개변수를 추론해준다.

```java
Set<Lark> exaltation = new HashSet<>(); // 다이아몬드 연산자 삽입
```

다이아몬드 연산자가 매개변수 타입을 추론한 해주었다. 이보다 제거하기 훨씬 어려운 경고들이 있다. 여기에서는 그러한 것들을 공부해볼 것이다. 가능한 모든 비검사 경고를 없애자 그러면 타입에 대한 안정성이 보장된다. 즉, ClassCastException이 발생할 일이 없고, 우리가 의도한 대로 동작할 것이다.

만약 경고를 제거할 수는 없지만 타입이 안전하다고 판단되는 경우에는 @SuppressWarnings("unchecked") 애너테이션을 달아 경고를 숨기자. **단, 타입 안정성을 검증하지 않은 채 경고를 숨기게 되면 잘못된 보안 의식을 심을 수 있다.** 그 코드는 컴파일은 되겠지만 런타임에는 ClassCastException을 던질 수 있다.

 @SuppressWarnings 애너테이션은 항상 **가능한 좁은 범위에 적용**하자. 

- 변수 선언
- 아주 짧은 메서드
- 생성자

정도에 달아주면 좋다. 한 줄이 넘는 생성자나 메서드에 @SuppressWarnings를 발견한다면 지역변수 선언 쪽으로 옮기자.



```java
public <T> T[] toArray(T[] a) {
    if (a.length < size) {
        return (T[]) Arrays.copyOf(elements, size, a.getClass());       
    }
    System.arraycopy(elements, 0, a, 0, size);
    
    if (a.length > size) {
        a[size] = null;
    }
    
    return a;
}
```

ArrayList를 컴파일 하면 이 메서드에서는 다음과 같은 경고가 발생하게 된다. 이러한 것은 메서드 전체에 SuppressWarnings 어노테이션을 달면 해결이 되지만 그렇게 하지말고, 메서드 내부에 지역변수를 하나 선언하고 SuppressWarnings 어노테이션을 달도록 하자.

```java
public <T> T[] toArray(T[] a) {
    if (a.length < size) {
        @SuppressWarnings("unchecked")
        T[] ret = (T[]) Arrays.copyOf(elements, size, a.getClass());
        
        return ret;
    }
    System.arraycopy(elements, 0, a, 0, size);
    
    if (a.length > size) {
        a[size] = null;
    }
    
    return a;
}
```

이 코드는 깔끔하게 컴파일이 되고 비검사 경고를 숨기는 범위도 최소로 좁혔다. **@SuppressWarnings("unchecked") 어노테이션을 사용할 때면 그 경고를 무시해도 안전한 이유를 항상 주석으로 남겨야 한다.** 그래야 다른 사람이 코드를 이해하는데 도움이 되고, 수정 시에 예외가 나지 않도록 한다.



#### 핵심 정리

> 비검사 경고는 중요하니 무시하지 말자. 모든 비검사 경고는 런타임에 ClassCastException을 일으킬 수 있는 잠재적 가능성을 뜻하니 최선을 다해 제거하라. 경고를 없앨 방법을 찾지 못한다면, 그 코드가 타입 안전함을 증명하고 가능한 한 범위를 좁혀 @SuppressWarnings("unchecked") 어노테이션으로 경고를 숨겨라. 그런 다음 경고를 숨긴 이유를 주석으로 남기도록 하자.

