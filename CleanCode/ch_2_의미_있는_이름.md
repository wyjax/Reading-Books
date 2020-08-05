## 2장 의미있는 이름



### 의도를 분명히 밝혀라

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



### 그릇된 정보를 피하라

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



### 발음하기 쉬운 이름을 사용하라



### 검색하기 쉬운 이름을 사용하라

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

