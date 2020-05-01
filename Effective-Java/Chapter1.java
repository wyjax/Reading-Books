
/**
 *  생성자 대신 정적 패토리 메소드를 고려하라
 *
 *  정적 팩토리 메소드와 public 생성자는 각각의 쓰임새가 있다. 그렇기 때문에 상대적인 장단점을 이해하고 사용해야 한다.
 *  하지만 정적 팩토리 메소드가 가지는 이점이 더 많기 때문에 무작정 생성자를 제공하던 습관을 고치자.
 */

public class Chapter1 {
    public static void main {
        // 매개변수가 1개인 경우 from으로 생성하자.
        Person person = Person.from(name);

        // 매겨변수가 여러개인 경우 of를 통해서 생성하자.
        person person = Person.of(name, age);

        // 인스턴스를 반환하지만, 같은 인스턴스 임을 보장하지 않는다.
        Person person = Person.getInstance(age);

        // 매 호출시마다 새로운 인스턴스를 생성해 반환한다.
        Person person = Person.newInstance(name, age);

        // 다른 클래스의 팩토리 매소드를 사용할 경우
        Person person = People.getPerson(name);

        // 다른 클래스의 팩토리 메소드를 사용, 매번 새로운 인스턴스를 반환
        Person person = People.newPerson(name);

        // getType, newType의 간결한 버전
        Person person = People.person(name);

        /**
         * < 장점 >
         *    1. 이름을 가진다.
         *    2. 호출될때마다 인스턴스를 새로 생성하지 않아도 된다.
         *    3. 반환 타입의 하위 타입 객체를 반환할 수도 있다.
         *    4. 입력 매개변수에 따라 매번 다른 클래스의 객체를 반환할 수도 있다.
         *    5. 정적 팩토리 메소드를 작성하는 시점에는 객체의 클래스가 존재하지 않아도 된다.
         *
         * < 단점 >
         *    1. 상속을 하려면 public or protected 생성자가 필요하니 정적 팩토리 메서드만 제공하면 하위 클래스 만들 수 없다.
         *    2. 정적 패토리 메소드는 개발자의 확인이 필요하다.
         */
    }
}