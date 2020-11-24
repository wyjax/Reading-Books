## item 26 로 타입은 사용하지 말라



클래스와 인터페이스 선언에 타입 매개변수가 쓰이면, 일르 제너릭 클래스 혹은 제네릭 인터페이스 라고 한다. 

List 인터페이스는 원소의 타입을 나타내는 타입 매개변수 E를 받는다. 그렇기 때문에 List의 완전한 이름은

List<E> 이다. 그렇지만 그냥 짧게 "List" 라고만 사용한다. 제네릭 클래스, 제네릭 인터페이스를 통틀어서

제네릭 타입이라고도 부른다.



각각의 제네릭 타입은 일련의 매개변수화 타입을 정의한다. 먼저 클래스 이름이 나오고, 이어서 꺽새괄호 안에 실제 타입 매개변수들을 나열한다. List<String> 이라고 쓰면 'String'은 List<E>에서 E에 해당하는 매개변수가 된다.



```java
private final Collection<Stamp> stamps = ...;
```

이렇게 선언하면 컴파일러는 stamps에는 Stamp의 인스턴스만 넣어야 함을 컴파일러가 인지하게 된다. 따라서 아무런 경고 없이 컴파일된다면 의도대로 동작할 것임을 보장한다. stamps에 다른 타입의 변수를 넣게 되면 오류를 범하게 되고 컴파일리가 이것을 알려준다.


컴파일러는 컬렉션에서 원소를 꺼내는 모든 곳에 보이지 않는 형변환을 추가하여 절대 실패하지 않음을 보장한다 !!

List 같은 로 타입은 사용하면 안되고 List<Object>처럼 임의 객체를 허용하는 매개변수화 타입은 괜찮다. 로 타입인 List와 매개변수화 타입에서 완전히 발을 뺀 것이고, List<Object>는 모든 타입을 허용하는 의미다.



```java
public void main() {
    List<String> strings = new ArrayListM<>();
    unsafeAdd(strings, Integer.valueOf(42));
    String s = strings.get(0); // 컴파일러가 자동으로 형변환을 넣어준다.
}

private static void unsafeAdd(List list, Object o) {
    list.add(o); // 이 부분은 오류가 발생한다. list로 되었기 때문... occur ClassCastException
}

private static void unsafeAdd(List<Object> list, Object o) {
    list.add(o); // 이 또한 오류가 난다. 
}
```



##### 원소의 타입을 몰라도 되는 로 타입을 쓰고 싶다면?

```java
static int numElementsInCommon(Set s1, Set s2) {
    int result = 0;
    for (Object o1 : s1) {
        if (s2.contains(o1))
            result++;
    }
    
    return result;
}
```

위의 코드는 동작은 하지만 로 타입을 사용하였으므로 안전하지 않은 코드이다. 따라서 비한정 와일드카드 타입을 대신 사용하는 것이 낫다. 제네릭 타입을 사용하고 싶지만 실제 무슨 타입인지 알고 싶지 않을 때 물음표를 사용하자. Set<E>의 비한정적 와일드카드 타입은 Set<?> 이다.

```java
// 비한정 와일드카드 타입을 사용한 코드
static int numElementsInCommon(Set<?> s1, Set<?> s2) {
    ...
}
```

간단히 바로 위의 두 코드를 비교하자면 와일드 카드 타입은 안전하고 로 타입을 Set은 안전하지 않다.



런타임에는 제네릭 타입 정보가 지워지므로 instanceof 연산자는 비한정적 와일드카드 타입 이외의 매개변수화 타입에는 적용할 수 없다. 그리고 로 타입이든 비한정적 와일드카드 타입이든 instanceof는 완전히 똑같이 동작한다. 그렇기 때문에 Set<?>라고 쓰는 것은 코드만 길어지므로 로 타입으로 쓰는 것이 낫다.



```java
// 로 타입을 써도 좋은 예
if (object instanceof Set) {
    Set<?> s = (Set<?>) o;
    ...
}
```



#### 핵심정리

>o의 타입이 Set임을 확인한 다음 와일드카드 타입은 Set<?>로 형변환해야 한다.(로 타입인 Set이 아니다) 이는 검사 형변환이므로 컴파일러 경고가 뜨지 않는다.
>
>+ 로 타입은 아무 원소나 넣을 수 있으니 타입 불변식을 훼손하기 쉽고 와일드카드 타입은 null 이외에 어떤 원소도 넣을 수 없으니 타입 불변식을 훼손하지 않는다. (넣을시 오류 발생)

