## Chapter 2.8 finalizer와 cleaner사용을 피하라



자바는 2가지 객체 소멸자를 제공하고 있다. 첫 번째는 **finalizer** 는 예측할 수 없고, 상황에 따라 위험할 수 있어 일반적으로 불필요하다. 오동작, 낮은 성능, 이식성 문제가 원인이 되기도 한다. finalizer는 나름의 쓰임새가 몇 가지 있긴 하지만 **기본적으로 쓰지 말아야 한다.**



finalizer와 cleaner는 즉시 수행된다는 보장이 없다. 객체에 접근 할 수 없게 된 후 finalizer or cleaner가 실행도기까지 얼마나 걸릴지 알 수 없다. 즉, finalizer와 cleaner로는 제때 실행되어야 하는 작업은 절대 할 수 없다. >> 파일 닫기를 finalizer나 cleaner한테 맡긴다면 중대한 오류를 불러올 수 있다. 왜냐하면 시스템이 동시에 열 수 있는 파일의 개수는 정해져 있기 때문에 새로운 파일을 열지 못하는 문제가 발생할 수 있다. 

finalizer와 cleaner가 얼마나 식속히 수행될지는 가비지 컬렉터 알고리즘에 달려 있다. 이는 가비지 컬렉터를 구현한 것마다 천차만별이며 finalizer나 cleaner 수행 시점에 의존하는 프로그램의 동작 또한 마찬가지다. 테스트에서는 잘 동작할지 몰라도 실제 서비스를 하다가 문제가 생길 수 있다!!!

