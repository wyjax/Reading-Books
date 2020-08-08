## 2장 의미있는 이름



#### 의도를 분명히 밝혀라

좋은 이름을 지으려면 시간이 좀 걸린다 > 그렇기 때문에 더 나은 이름이 나온다면 개선해라.

```java
int d // 경과 시간 (단위 : 날짜)
    
- 이름 d는 아무 의미도 드러나지 않는 변수명이다...
```

#### 

#### 속을 알 수 없는 메소드 분석

```java
public List<int[]> getThem() { // them이 의미하는 것은 무엇일까? 무엇을 가져온다는거지?
    List<int[]> list1 = new ArrayList<>(); // list1에는 무엇이 담기는거야?
    
    for (int[] x : theList) {
        if (x[0] == 4) { // x[0], theList라는 배열의 처음은 뭐를 의미하지?
            list1.add(x);
        }
    }
    
    return list1; // list1를 반환해서 어떻게 사용하는 거지?
}
```

위의 메소드는 의미를 알아가는 것이 힘들다. 변수가 무엇을 하고 행동이 무엇을 의미하는지 알기 어렵다...



#### 위의 코드를 클린코드 해보자 !

```java
public List<int[]> getFlaggedCells() { 
    List<int[]> flaggedCells = new ArrayList<>(); 
    
    for (int[] cell : gameBoard) {
        if (cell[STATUS_VALUE] == FLAGGED) { 
			flaggedCells.add(cell);
        }
    }
    
    return flaggedCells; 
}
```



#### 한 번 더 int[] 부분을 class로 바꿔서 써보자

```java
public List<int[]> getFlaggedCells() { 
    List<Cell> flaggedCells = new ArrayList<>(); 
    
    for (Cell cell : gameBoard) {
        if (cell.isFlagged()) { 
			flaggedCells.add(cell);
        }
    }
    
    return flaggedCells; 
}
```

> 결론... 변수명과 함수명 그리고 int[]를 class로 변경하니깐 함수가 무엇을 하는건지 어떤 변수를 어떻게 처리하는건지 보기가 편해졌다.😀



#### 그릇된 정보를 피하라

```java
public static void copyChars(char a1[], char a2[]) { // a1, a2 변수명이 너무 그렇다. 의도가 안보인다.
    for (int i = 0; i < a1.length; i++) {
        a2[i] = a1[i];
    }
}
```

불용어를 추가한 이름은 아무런 정보도 제공하지 못한다. Product 클래스가 있을 때 이것을 ProductInfo, ProductData라고 부른다면 개념을 구분하지 않은 채 이름만 달리한 경우이다.

```java
getActiveAccount();
getActiveAccounts();
getActiveAccountInfo();

>> 고객 급여 이력을 찾으려면 어떤 메소드를 뒤져야 할지 생각해보면 모르겠다... 그나마 2번..ㅋ
```



#### 발음하기 쉬운 이름을 사용하라

발음하기 어려운 이름을 사용한다면 개발자들 간에 소통하는 것이 힘들어 질 수 있다. 



#### 검색하기 쉬운 이름을 사용하라

- 긴 이름이 짧은 이름보다 쉽다.
- 검색하기 쉬운 이름이 상수보다 좋다 ! (userAccountList <-> data)

```java
/* 총제적 난국이다. 34는 뭔지 s는 뭔지 k는 뭔지 t는 도대체 뭔지!!! */
for (int j = 0; j < 34; k++) {
    s += (t[k] * 4) / 5;
}
//================= change =================

int realDaysPerIdeaDay = 4;
const int WORK_DAYS_PER_WEEK = 5;
int sum = 0;

for (int j = 0; j < NUMBER_OF_TASKS; j++) {
    int realTashDays = taskEstimate[j] * realDaysPerIdealDay;
    int realTaskWeeks = (realTaskDays / WORK_DAYS_PER_WEEK);
    sum += realTaskWeeks;	
}
```



#### 인코딩을 피하라

문제 해결에 집중하는 개발자에게 인코딩은 불필요한 정신적 부담이다. 인코딩한 이름은 발음하기에 어려워지고 오타나기도 쉽다.



#### 자신의 기억력을 자랑하지 마라

- 문자 하나만 사용하는 변수명은 문제가 있다. (단, 루프에서 i, j, k 정도는 괜찮다. 그렇지만 루프 범위가 아주 작고 다른 이름과 충돌하지 않을 때만 괜찮다.)
- 전문가 프로그래머는 명료함이 최고라는 사실을 인지하고 있다.



#### 클래스 이름

클래스의 이름과 객체 인스턴스의 이름은 **명사**나 **명사구**가 적합하다. Customer, WikiPage, Account, AddressParser 등이 좋은 예가 될 수 있다. Manager, Processor, Data, Info 등과 같은 단어는 피하고 동사는 사용하지 않는다.



#### 메서드 이름

메서드 이름은 **동사**나 **동사구**가 적합하다.  postPayment, deletePage, save 등이 좋은 예이다. 접근자, 변경자, 조건자는 javabean 표준에 따라 값 앞에 get, set, is를 붙인다.

```java
String name = emplyee.getName();
customer.setName("mike");
if (paycheck.isPosted())...
```

생성자를 중복정의할 때는 정적 팩토리 메서드를 사용한다. 메서드는 인수를 사용하는 이름을 사용한다.

```java
Complex fulcrumPoint = Complex.FromRealNumber(23.0);
// 위에 있는 코드가 아래에 있는 코드보다 좋다.
Complex fulcrumPoint = new Complex(23.0);
```



#### 한 개념에 한 단어를 사용하라

추상적인 개념 하나에 단어 하나를 선택해 이를 고수한다. 예를 들어서 똑같은 메서드를 클래스마다 fetch, retrieve, get으로 

제각각 부르면 혼란스러워 진다.

마찬가지로 동일 코드 기반에 controller, manager, driver를 섞어 쓰면 혼란스럽다.

```
ServerManager, ServiceController
```



#### 말장난을 하지 마라

```java
public void addCompanyManager() { // 이렇게 List에 CompanyManager를 넣는 것을 자주 쓰곤 했다.
    // 구현 코드
}
// 하지만 이 방법보다는... 아래 방법이 더 좋다 !!
public void insertCompanyManager() { // 또는 appendCompanyManager()
    // 구현 코드
}
```



#### 의미있는 맥락을 추가하라

스스로 의미가 분명한 이름이 없지 않다. 하지만 대다수의 이름은 그렇지 못하고 클래스, 메서드, 이름 공간에 넣어 맥락을 부여한다. 모든 방법이 실패하면 마지막 수단으로 접두어를 붙인다. 예를 들어 firstName, LastName, street, houseNumber, city, state, zopcode 정도...

하지만 여기에서 state라는 것이 주소라고 생각하지 않을 수도 있다. 그렇기 때문에 addr 접두어를 추가해서 addrFirstName, addrLastName, addrState라고 쓰면 맥락이 좀 더 분명해진다.

##### 맥락이 불분명한 함수

```java
private void print
```

